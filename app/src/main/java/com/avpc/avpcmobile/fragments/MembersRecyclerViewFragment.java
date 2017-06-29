package com.avpc.avpcmobile.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.adapter.RecyclerViewMemberItemCursorAdapter;
import com.avpc.avpcmobile.presenter.MemberPresenter;
import com.avpc.avpcmobile.data.member.IMemberRepository;
import com.avpc.avpcmobile.data.member.MemberRepository;
import com.avpc.model.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersRecyclerViewFragment extends Fragment
implements RecyclerViewMemberItemCursorAdapter.IMemberListener {

    private MemberPresenter presenter;
    private RecyclerViewMemberItemCursorAdapter memberCursorAdapter;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private OnMemberListListener mListener;

    public MembersRecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_recycler_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.members_list_recycler_view);
        showMembers();
        return view;
    }



    public static MembersRecyclerViewFragment newInstance() {
        MembersRecyclerViewFragment fragment = new MembersRecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        presenter = new MemberPresenter(mContext);
        String userToken = getArguments().getString(getString(R.string.user_token));
        createRequestQueue();
        loadMembers(userToken);

    }

    @Override
    public void onResume() {
        super.onResume();
        mRequestQueue.start();
    }

    @Override
    public void onStop() {
        mRequestQueue.stop();
        super.onStop();
    }

    private void createRequestQueue() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
    }

    private void loadMembers(String userToken) {

        final String token = userToken;

        String url = getString(R.string.rest_server) + "/members/login";
        StringRequest tokenRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestMembers(response);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Internet access...", Toast.LENGTH_LONG);
                Log.e("loadMembersError", error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return token != null ? token.getBytes("utf-8") : null;
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", token, "utf-8");
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

        mRequestQueue.add(tokenRequest);

    }

    private void requestMembers(String jSessionIdToken) {

        final IMemberRepository repository = new MemberRepository(getContext());

        JSONArray jsonReturn = null;
        final String token = jSessionIdToken;
        String url = getString(R.string.rest_server) + "/members";
        JsonArrayRequest tokenRequest = new JsonArrayRequest(Request.Method.GET, url, jsonReturn,
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
                            //repository.insertMembers(members);
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
                //super.getHeaders();
                Map<String, String> mapa = new HashMap<>();
                mapa.put("Cookie", token);
                return mapa;
            }

//            @Override
//            protected Response parseNetworkResponse(NetworkResponse response) {
//                return Response.success(response.statusCode, HttpHeaderParser.parseCacheHeaders(response));
//            }
        };

        // Add the request to the RequestQueue.
        mRequestQueue.add(tokenRequest);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    private void showMembers() {
        Cursor cursorMembers = presenter.getMembers();
        if (cursorMembers.getCount() > 0) {
            if (memberCursorAdapter == null) {
                memberCursorAdapter = new RecyclerViewMemberItemCursorAdapter(getActivity(), cursorMembers, this);

            } else {
                memberCursorAdapter.changeCursor(cursorMembers);
                memberCursorAdapter.notifyDataSetChanged();
            }

            mRecyclerView.setAdapter(memberCursorAdapter);

//            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(
                    mContext
                    ,1
                    ,GridLayoutManager.VERTICAL
                    ,false);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMemberListListener) {
            mListener = (OnMemberListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMemberListListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public void onMemberSelected(long memberId) {
//
//    }

    @Override
    public void itemClicked(long memberId) {
        mListener.onMemberSelected(memberId);
    }

    public interface OnMemberListListener {
        void onMemberSelected(long memberId);
    }
//
//    public interface CallbackMembersLoaded {
//        void membersLoaded();
//    }
}