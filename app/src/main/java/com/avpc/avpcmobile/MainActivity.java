package com.avpc.avpcmobile;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.avpc.avpcmobile.activities.MainMenuActivity;
import com.avpc.avpcmobile.login.ForgotPasswordFragment;
import com.avpc.avpcmobile.login.LoginFragment;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.loginContainer, loginFragment).commit();
//        Intent mainMenuIntent = new Intent(MainActivity.this, MapsFragment.class);
//        startActivity(mainMenuIntent);
//        FacebookFragment facebookFragment = new FacebookFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.loginContainer, facebookFragment).commit();
        //callMainMenu();
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

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onUserLoggedIn() {
        callMainMenu();
    }

    private void callMainMenu() {
        Intent mainMenuIntent = new Intent(MainActivity.this, MainMenuActivity.class);
        startActivity(mainMenuIntent);
        finish();
    }
}
