package com.avpc.avpcmobile.data.user.remote;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.data.VolleySingleton;

import java.io.UnsupportedEncodingException;


public class LoginTokenValidator {

    private static final String TAG = LoginTokenValidator.class.getSimpleName();

    private UserTokenValidatorListener mUserTokenListener;
    private static String mTokenSession;
    private Context mContext;

    public interface UserTokenValidatorListener {
        void receiveValidatedToken(String validateToken);
        void showErrorMessage(String messageError);
    }

    public LoginTokenValidator(Context context, UserTokenValidatorListener listener) {
        mContext = context;
        mUserTokenListener = listener;
    }

    public void getTokenSession(String firebaseToken) {
        if (mTokenSession != null) {
            mUserTokenListener.receiveValidatedToken(mTokenSession);
        } else {
            validateUserToken(firebaseToken);
        }
    }

    private synchronized void validateUserToken(String userToken) {
        final String token = userToken;

        String url = mContext.getString(R.string.rest_server) + "/members/login";
        StringRequest tokenRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTokenSession = response;
                        mUserTokenListener.receiveValidatedToken(response);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                Log.e(TAG, errorMessage);
                mUserTokenListener.showErrorMessage(errorMessage);
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

        VolleySingleton volley = new VolleySingleton(mContext);
        volley.getRequestQueue().add(tokenRequest);
        //mRequestQueue.add(tokenRequest);
    }
}
