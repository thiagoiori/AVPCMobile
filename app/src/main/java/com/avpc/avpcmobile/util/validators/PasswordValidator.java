package com.avpc.avpcmobile.util.validators;

/**
 * Created by thiago on 22/06/17.
 */

public class PasswordValidator implements IPasswordValidator{
    @Override
    public boolean isPasswordValid(String password) {
        return !password.isEmpty();
    }
}
