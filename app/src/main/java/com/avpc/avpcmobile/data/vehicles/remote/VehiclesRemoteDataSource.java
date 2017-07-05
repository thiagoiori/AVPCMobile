package com.avpc.avpcmobile.data.vehicles.remote;

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
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.data.VolleySingleton;
import com.avpc.avpcmobile.data.vehicles.VehiclesDataSource;
import com.avpc.model.UserToken;
import com.avpc.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VehiclesRemoteDataSource implements VehiclesDataSource,
		VehiclesDataSource.LoadVehiclesCallback {

    private static VehiclesRemoteDataSource INSTANCE;
    private static Context mContext;

    private VehiclesRemoteDataSource() {

    }

    public static VehiclesRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VehiclesRemoteDataSource();
        }
        mContext = context;

        return INSTANCE;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return requestVehiclesSync(UserToken.getSession());
    }

    @Override
    public void getVehicle(@NonNull String vehicleId, @NonNull GetVehicleCallback callback) {

    }

    @Override
    public void saveVehicles(@NonNull List<Vehicle> vehicles) {

    }

    @Override
    public void saveVehicle(@NonNull Vehicle vehicle) {

    }

    @Override
    public void completeVehicle(@NonNull Vehicle vehicle) {

    }

    @Override
    public void completeVehicle(@NonNull String vehicleId) {

    }

    @Override
    public void refreshVehicles() {

    }

    @Override
    public void deleteAllVehicles() {

    }

    @Override
    public void deleteVehicle(@NonNull String vehicleId) {

    }

    private List<Vehicle> requestVehiclesSync(String jSessionIdToken) {

        VolleySingleton volley = new VolleySingleton(mContext);
        String url = mContext.getString(R.string.rest_server) + "/members/login";
        String urlVehicles = mContext.getString(R.string.rest_server) + "/vehicle";
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
        RequestFuture<JSONArray> requestVehicle = RequestFuture.newFuture();
        JsonArrayRequest vehiclesArrayRequest = new JsonArrayRequest(urlVehicles, requestVehicle, requestVehicle) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapa = new HashMap<>();
                mapa.put("Cookie", token);
                return mapa;
            }
        };

        volley.getRequestQueue().add(vehiclesArrayRequest);

        JSONArray retornoArray = null;
        try {
            retornoArray = requestVehicle.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson objGson = gson.setPrettyPrinting().create();
        Vehicle[] vehicles = null;
        try {
            vehicles = objGson.fromJson(retornoArray.toString(), Vehicle[].class);
        } catch (Exception ex) {
            Log.e("ParseVehicle[]", ex.getMessage());
        }

        return Arrays.asList(vehicles);

    }

    private void requestVehiclesAsync(String jSessionIdToken) {


        JSONArray jsonReturn = null;
        final String token = jSessionIdToken;
        String url = mContext.getString(R.string.rest_server) + "/vehicles";

        JsonArrayRequest vehiclesRequest = new JsonArrayRequest(Request.Method.GET, url, jsonReturn,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // call vehicles request
                        //requestVehicles(response);
                        JSONArray result = response;
                        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
                        GsonBuilder gson = new GsonBuilder();
                        //Gson gson = new Gson();
                        //gson.registerTypeAdapter(Date.class, new DateDeserializer());
                        Gson objGson = gson.setPrettyPrinting().create();
                        try {
                            Vehicle[] vehicles = objGson.fromJson(result.toString(), Vehicle[].class);
                            List<Vehicle> vehiclesList = new ArrayList<>(Arrays.asList(vehicles));
                            onVehiclesLoaded(vehiclesList);
                        } catch (Exception ex) {
                            Log.e("ParseVehicle[]", ex.getMessage());
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
        //VolleySingleton.getIntance(mContext.getApplicationContext()).getRequestQueue().add(vehiclesRequest);

        VolleySingleton volley = new VolleySingleton(mContext);
        volley.getRequestQueue().add(vehiclesRequest);

    }

    @Override
    public void onVehiclesLoaded(List<Vehicle> vehicles) {

    }

    @Override
    public void onDataNotAvailable() {

    }

}
