package com.avpc.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by thiago on 14/06/17.
 */

public class UserToken {

    private static UserToken internalUserToken;

    private UserToken() {

    }

    static {
        internalUserToken = new UserToken();
    }

    private static String mToken = "";
    private static String mSession = "";

    public static void setToken(String token) { mToken = token; }
    public static void setSession(String session) { mSession = session; }
    public static String getToken() { return mToken; }
    public static String getSession() { return mSession; }
}
