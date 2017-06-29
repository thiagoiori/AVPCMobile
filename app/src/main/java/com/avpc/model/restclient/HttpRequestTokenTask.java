package com.avpc.model.restclient;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.net.ConnectivityManagerCompat;
import android.util.Log;

import com.avpc.avpcmobile.R;
import com.avpc.model.Member;

//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.ClientHttpRequest;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class HttpRequestTokenTask
        //extends AsyncTask<Void, Void, Boolean>
{

//    private String BASE_URL;
//    private RestTemplate restTemplate;
//    private ITokenListener mListener;
//    private String mToken;
//
//    public HttpRequestTokenTask(
//            Context context
//            ,String token
//            ,ITokenListener listener) {
//        BASE_URL = context.getString(R.string.rest_server);
//        mToken = token;
//        mListener = listener;
//        restTemplate = new RestTemplate();
//    }
//
//    @Override
//    protected Boolean doInBackground(Void... params) {
//        ResponseEntity<Boolean> response = makeRestCall(mToken);
//        return response.getBody();
//    }
//
//    private ResponseEntity<Boolean> makeRestCall(String idToken){
//
//        ConnectivityManagerCompat connectivityManagerCompat;
//
//        setConverters();
//        HttpHeaders header  = setHeader();
//        HttpEntity<String> requestEntity = new HttpEntity<>(idToken, header);
//        URI uri = URI.create(BASE_URL + "/members/login");
//
//        ResponseEntity<Boolean> responseLoginToken = restTemplate.exchange(uri
//                ,HttpMethod.POST
//                , requestEntity
//                , Boolean.class);
//
//
//        URI uriMembers = URI.create(BASE_URL + "/members/");
//        List<String> cookies = responseLoginToken.getHeaders().get("Cookie");
//        if (cookies == null) {
//            cookies = responseLoginToken.getHeaders().get("Set-Cookie");
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headerMember = new HttpHeaders();
//        String cookieStream = "";
//        for (String cookie : cookies) {
//            cookieStream = cookieStream.concat(cookieStream).concat(cookie).concat(";");
//        }
//        headerMember.set("Cookie", cookieStream);
//
//        HttpEntity<String> requestMember = new HttpEntity<>("", headerMember);
//
//        ResponseEntity<Member[]> teste;
//
//        try {
////            members = restTemplate.getForEntity(
////                    uriMembers
////                    ,Member.class);
//
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//
//            teste = restTemplate.exchange(uriMembers
//                    ,HttpMethod.GET
//                    ,requestMember
//                    ,Member[].class);
//
//            String abc = "";
//        } catch (Exception ex) {
//            Log.d("Error", ex.getMessage());
//        }
//
//
//        return responseLoginToken;
//    }
//
//    private void setConverters() {
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//    }
//
//    private HttpHeaders setHeader(){
//        HttpHeaders header  = new HttpHeaders();
//        header.setContentType(MediaType.TEXT_PLAIN);
//        return header;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean result){
//        if (mListener != null) {
//            mListener.SetTokenValid(result);
//        }
//    }
}
