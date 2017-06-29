package com.avpc.model.restclient;

import android.os.AsyncTask;
import android.util.Log;

import com.avpc.model.Member;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by thiago on 07/06/17.
 */

public class MemberRestClient implements ITokenListener {



    private String token;

    public boolean SetToken(String token) {

        this.token = token;
        //try {

//            Map<String, String> request = new HashMap<>();
//            request.put("idToken", token);
//
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//
//             ResponseEntity<String> response
//                     = restTemplate.postForEntity(BASE_URL + "/login"
//                    , request
//                    , Boolean.class);

//            return true;
//        }
//        catch(Exception e){
//            Log.d("restTemplateSetToken", e.getMessage());
//        }

        return false;
    }

    @Override
    public Boolean SetTokenValid(Boolean isTokenValid) {
        return false;
    }

//    public class HttpRequestTokenTask extends AsyncTask<Void, Void, Boolean> {
//
//        private String BASE_URL = "http://192.168.99.100:8080/avpc-1.0-SNAPSHOT/";
//        private RestTemplate restTemplate = new RestTemplate();
//
//        private ITokenListener mListener;
//        private String mToken;
//
//        public HttpRequestTokenTask(String token, ITokenListener listener) {
//            mToken = token;
//            mListener = listener;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//
//            Map<String, String> request = new HashMap<>();
//            request.put("idToken", mToken);
//
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//
//            ResponseEntity<Boolean> response
//                    = restTemplate.postForEntity(BASE_URL + "/login"
//                    , request
//                    , Boolean.class);
//
//            return response.getBody();
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result){
//            if (mListener != null) {
//                mListener.SetTokenValid(result);
//            }
//        }
//
//    }
}
