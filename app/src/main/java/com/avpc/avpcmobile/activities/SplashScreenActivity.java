package com.avpc.avpcmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.members.MembersActivity;
import com.avpc.model.LoggedUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.IdTokenListener mTokenListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    redirectToLogin();
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
                                            LoggedUser.setToken(mUser.getIdToken(false).getResult().getToken());
                                            callMainMenu();
                                        }
                                    });
                }
            }
        };
    }

    private void redirectToLogin() {

    }

    private void callMainMenu() {
        Intent mainMenuIntent = new Intent(SplashScreenActivity.this, MembersActivity.class);
        startActivity(mainMenuIntent);
        finish();
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
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
}
