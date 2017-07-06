package com.avpc.model;

/**
 * Created by thiago on 14/06/17.
 */

public class LoggedUser {

    private static LoggedUser internalUserToken;

    private LoggedUser() {

    }

    static {
        internalUserToken = new LoggedUser();
    }

    private static String mToken = "";
    private static String mSession = "";

    public static void setToken(String token) { mToken = token; }
    public static void setSession(String session) { mSession = session; }
    public static String getToken() { return mToken; }
    public static String getSession() { return mSession; }
}
