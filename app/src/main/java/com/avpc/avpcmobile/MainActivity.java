package com.avpc.avpcmobile;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.avpc.avpcmobile.login.ForgotPasswordFragment;
import com.avpc.avpcmobile.login.LoginFragment;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnForgotPasswordListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.loginContainer, loginFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forgotpasswordmenu, menu);
        return true;
    }

    @Override
    public void onForgotPasswordClicked(String email) {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        args.putString("EMAIL", email);
        forgotPasswordFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.loginContainer, forgotPasswordFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
}
