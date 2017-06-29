package com.avpc.model.restclient;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.net.ConnectivityManagerCompat;
import android.util.Log;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.fragments.MemberFragment.CallbackMembersLoaded;
import com.avpc.model.Member;

//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created by thiago on 09/06/17.
 */

public class HttpMembersTask //extends AsyncTask<String, Void, Member[]>
{

//    private CallbackMembersLoaded callMembersLoaded;
//    private String BASE_URL;
//    //private RestTemplate restTemplate;
//
//    public HttpMembersTask(Context context, CallbackMembersLoaded callbackMembersLoaded) {
//        BASE_URL = context.getString(R.string.rest_server);
//        callMembersLoaded = callbackMembersLoaded;
//    }
//
//    @Override
//    protected Member[] doInBackground(String... params) {
////        ResponseEntity<Member[]> response = null;
////        try {
////            response = makeRestCall(params[0]);
////        } catch(Exception ex) {
////            Log.e("LoadMemeber", ex.getMessage());
////        }
////        return response.getBody();
//    }
//
//    private ResponseEntity<Member[]> makeRestCall(String idToken){
//        restTemplate = new RestTemplate();
//
//        setConverters();
//        HttpHeaders header  = setHeader();
//        header.set("Accept", "application/json");
//        HttpEntity<String> requestEntity = new HttpEntity<>(idToken, header);
//        URI uri = URI.create(BASE_URL + "/members/login");
//
//        ResponseEntity<Boolean> responseLoginToken = restTemplate.exchange(uri
//                , HttpMethod.POST
//                , requestEntity
//                , Boolean.class);
//
//        URI uriMembers = URI.create(BASE_URL + "/members/");
//        List<String> cookies = responseLoginToken.getHeaders().get("Cookie");
//        if (cookies == null) {
//            cookies = responseLoginToken.getHeaders().get("Set-Cookie");
//        }
//
//        //RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headerMember = new HttpHeaders();
//        String cookieStream = "";
//        for (String cookie : cookies) {
//            cookieStream = cookieStream.concat(cookieStream).concat(cookie).concat(";");
//        }
//        headerMember.set("Cookie", cookieStream);
//
//        HttpEntity<String> requestMember = new HttpEntity<>("", headerMember);
//
//        ResponseEntity<Member[]> members = null;
//
//        try {
//
//            members = restTemplate.exchange(uriMembers
//                    ,HttpMethod.GET
//                    ,requestMember
//                    ,Member[].class);
//
//
//        } catch (Exception ex) {
//            Log.d("Error", ex.getMessage());
//        }
//
//
//        return members;
//    }
//
//    @Override
//    protected void onPostExecute(Member[] members) {
//        super.onPostExecute(members);
//        callMembersLoaded.membersLoaded();
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
}
