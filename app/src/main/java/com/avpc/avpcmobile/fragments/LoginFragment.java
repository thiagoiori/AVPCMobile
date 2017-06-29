package com.avpc.avpcmobile.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.util.validators.ILoginValidator;
import com.avpc.avpcmobile.util.validators.LoginValidator;



/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private LoginListener mListener;

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnForgotPassword;
    private ILoginValidator loginValidator;

    private final String TAG = "AVPC Mobile Login";

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loginValidator = new LoginValidator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnForgotPassword = (Button) view.findViewById(R.id.btnForgotPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPasswordClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            mListener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void login() {
        String userMail = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if (!loginValidator.isLoginValid(userMail, password)) {
            Toast.makeText(getActivity(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
            return;
        }
        mListener.sendUsernamePassword(userMail, password);
    }

    private void onForgotPasswordClicked() {
        if (mListener != null && txtEmail != null){
            mListener.onForgotPasswordClicked(txtEmail.getText().toString());
        }
    }


    public interface LoginListener {
        void onForgotPasswordClicked(String email);
        void sendUsernamePassword(String username, String password);
    }

}
