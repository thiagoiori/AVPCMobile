package com.avpc.avpcmobile.util.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thiago on 22/06/17.
 */

public class EmailValidator implements IEmailValidator{

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}
