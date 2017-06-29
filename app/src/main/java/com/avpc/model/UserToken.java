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

    private String mToken;

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        propertyChangeSupport.firePropertyChange(
                "mToken"
                , this.mToken
                , mToken);
        this.mToken = mToken;
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangedListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
