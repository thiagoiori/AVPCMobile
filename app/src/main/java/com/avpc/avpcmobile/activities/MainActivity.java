package com.avpc.avpcmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.fragments.ForgotPasswordFragment;
import com.avpc.avpcmobile.fragments.LoginFragment;
import com.avpc.avpcmobile.members.MembersActivity;
import com.avpc.model.UserToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.IdTokenListener mTokenListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {

                }
            }
        };

        mTokenListener = new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getIdToken(true)
                            .addOnCompleteListener(
                                    new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            UserToken.setToken(mUser.getIdToken(false).getResult().getToken());
                                            callMainMenu();
                                        }
                                    });
                }
            }
        };

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.loginContainer, loginFragment).commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.addIdTokenListener(mTokenListener);
    }

    @Override
    public void onStop() {
        if(mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.removeIdTokenListener(mTokenListener);
        }
        super.onStop();
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

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(R.id.loginContainer, forgotPasswordFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendUsernamePassword(String username, String password) {
        signIn(username, password);
    }

    public void callMainMenu() {
        Intent mainMenuIntent = new Intent(MainActivity.this, MembersActivity.class);
        startActivity(mainMenuIntent);
        finish();
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    public void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser = mAuth.getCurrentUser();
                            callMainMenu();
                    }
                }});

    }
}
