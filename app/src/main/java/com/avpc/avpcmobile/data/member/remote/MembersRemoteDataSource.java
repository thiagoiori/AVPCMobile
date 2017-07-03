package com.avpc.avpcmobile.data.member.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.data.VolleySingleton;
import com.avpc.avpcmobile.data.member.MembersDataSource;
import com.avpc.model.Member;
import com.avpc.model.UserToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MembersRemoteDataSource implements
        MembersDataSource,
        MembersDataSource.LoadMembersCallback {

    private static MembersRemoteDataSource INSTANCE;
    private static Context mContext;

    private MembersRemoteDataSource() {

    }

    public static MembersRemoteDataSource getInstance(Context context) {
        if (INSTANCE  == null) {
            INSTANCE = new MembersRemoteDataSource();
        }
        mContext = context;

        return INSTANCE;
    }

    @Override
    public List<Member> getMembers() {
        return requestMembersSync(UserToken.getSession());
    }

    @Override
    public void getMember(@NonNull String memberId, @NonNull GetMemberCallback callback) {

    }

    @Override
    public void saveMembers(@NonNull List<Member> members) {

    }

    @Override
    public void saveMember(@NonNull Member member) {

    }

    @Override
    public void completeMember(@NonNull Member member) {

    }

    @Override
    public void completeMember(@NonNull String memberId) {

    }

    @Override
    public void refreshMembers() {

    }

    @Override
    public void deleteAllMembers() {

    }

    @Override
    public void deleteMember(@NonNull String memberId) {

    }

    private List<Member> requestMembersSync(String jSessionIdToken) {

        VolleySingleton volley = new VolleySingleton(mContext);
        String url = mContext.getString(R.string.rest_server) + "/members/login";
        String urlMembers = mContext.getString(R.string.rest_server) + "/members";
        final String userToken = UserToken.getToken();
        GsonBuilder gson = new GsonBuilder();

        RequestFuture<String> requestLoginSession = RequestFuture.newFuture();
        StringRequest tokenRequest = new StringRequest(Request.Method.POST, url, requestLoginSession, requestLoginSession) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return userToken != null ? userToken.getBytes("utf-8") : null;
                } catch (UnsupportedEncodingException uee) {
                    Log.i("UnsupportedEncode", uee.getMessage());
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            userToken, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = response.headers.get("Set-Cookie");
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        volley.getRequestQueue().add(tokenRequest);

        String tokenReturn = null;
        try {
            tokenReturn = requestLoginSession.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //TODO: Send Error to caller
            e.printStackTrace();
        } catch (ExecutionException e) {
            //TODO: Send Error to caller
            e.printStackTrace();
        } catch (TimeoutException e) {
            //TODO: Send Error to caller
            e.printStackTrace();
        }

        final String token = tokenReturn;
        RequestFuture<JSONArray> requestMember = RequestFuture.newFuture();
        JsonArrayRequest membersArrayRequest = new JsonArrayRequest(urlMembers, requestMember, requestMember) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapa = new HashMap<>();
                mapa.put("Cookie", token);
                return mapa;
            }
        };

        volley.getRequestQueue().add(membersArrayRequest);

        JSONArray retornoArray = null;
        try {
            retornoArray = requestMember.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson objGson = gson.setPrettyPrinting().create();
        Member[] members = null;
        try {
            members = objGson.fromJson(retornoArray.toString(), Member[].class);
        } catch (Exception ex) {
            Log.e("ParseMember[]", ex.getMessage());
        }

        return Arrays.asList(members);

    }

    private void requestMembersAsync(String jSessionIdToken) {



        JSONArray jsonReturn = null;
        final String token = jSessionIdToken;
        String url = mContext.getString(R.string.rest_server) + "/members";

        JsonArrayRequest membersRequest = new JsonArrayRequest(Request.Method.GET ,url, jsonReturn,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // call members request
                        //requestMembers(response);
                        JSONArray result = response;
                        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
                        GsonBuilder gson = new GsonBuilder();
                        //Gson gson = new Gson();
                        //gson.registerTypeAdapter(Date.class, new DateDeserializer());
                        Gson objGson = gson.setPrettyPrinting().create();
                        try {
                            Member[] members = objGson.fromJson(result.toString(), Member[].class);
                            List<Member> membersList = new ArrayList<>(Arrays.asList(members));
                            onMembersLoaded(membersList);
                        } catch (Exception ex) {
                            Log.e("ParseMember[]", ex.getMessage());
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapa = new HashMap<>();
                mapa.put("Cookie", token);
                return mapa;
            }
        };

        // Add the request to the RequestQueue.
        //VolleySingleton.getIntance(mContext.getApplicationContext()).getRequestQueue().add(membersRequest);

        VolleySingleton volley = new VolleySingleton(mContext);
        volley.getRequestQueue().add(membersRequest);

    }

    @Override
    public void onMembersLoaded(List<Member> members) {

    }

    @Override
    public void onDataNotAvailable() {

    }
}
