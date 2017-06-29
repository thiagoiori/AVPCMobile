package com.avpc.avpcmobile.util.login;

/**
 * Created by thiago on 27/06/17.
 */

public interface ILogin {
    void signIn(String username, String password);
    void addListeners();
    void removeListeners();
}
