package com.avpc.avpcmobile.util.validators;

/**
 * Created by thiago on 22/06/17.
 */



public class LoginValidator implements ILoginValidator {

    private IEmailValidator mMailValidator;
    private IPasswordValidator mPasswordValidator;
    public LoginValidator() {
        mMailValidator = new EmailValidator();
        mPasswordValidator = new PasswordValidator();
    }

    @Override
    public boolean isLoginValid(String login, String password) {
        boolean isLoginValid;
        isLoginValid = mMailValidator.isEmailValid(login);
        isLoginValid =  isLoginValid && mPasswordValidator.isPasswordValid(password);
        return isLoginValid;
    }
}
