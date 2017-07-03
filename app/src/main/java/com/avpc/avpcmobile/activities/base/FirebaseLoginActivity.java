package com.avpc.avpcmobile.activities.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.avpc.avpcmobile.data.user.remote.LoginTokenValidator;
import com.avpc.model.UserToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class FirebaseLoginActivity extends AppCompatActivity
implements LoginTokenValidator.UserTokenValidatorListener{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.IdTokenListener mTokenListener;
    private FirebaseUser mUser;
    private static final String USER_TOKEN = "user_token";
    private static final String TAG = FirebaseLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    if (UserToken.getToken().isEmpty())
                        UserToken.setToken(mUser.getIdToken(false).getResult().getToken());
                    callMainMenu();
                } else {
                    redirectToLogin();
                }
            }
        };

        mTokenListener = new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getIdToken(false)
                            .addOnCompleteListener(
                                    new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            String token = null;
                                            try {
                                                token = task.getResult().getToken();
                                                beginUserSession(token);
                                            } catch (Exception e) {
                                                Log.e(TAG, e.getMessage());
                                            }
                                        }
                                    });
                }
            }
        };
    }

    private void beginUserSession(String token) {
//        if (UserToken.getToken() == null || !UserToken.getToken().equals(token)) {
//            UserToken.setToken(token);
//            LoginTokenValidator loginTokenValidator = new LoginTokenValidator(getApplicationContext(),
//                    FirebaseLoginActivity.this);
//            loginTokenValidator.getTokenSession(token);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.addIdTokenListener(mTokenListener);
    }

    @Override
    public void onStop() {
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.removeIdTokenListener(mTokenListener);
        }
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USER_TOKEN, UserToken.getToken());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(USER_TOKEN)) {
            UserToken.setToken(savedInstanceState.getString(USER_TOKEN));
        }
    }

    public void callMainMenu() {

    }

    public void redirectToLogin() {

    }

    public void signOut() {
        mAuth.signOut();
    }

    public void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser = mAuth.getCurrentUser();
                            callMainMenu();
                        } else {
                            redirectToLogin();
                        }
                    }
                });
    }

    @Override
    public void receiveValidatedToken(String tokenSession) {
        UserToken.setSession(tokenSession);
    }

    @Override
    public void showErrorMessage(String messageError) {
        Toast.makeText(FirebaseLoginActivity.this, messageError, Toast.LENGTH_LONG).show();
    }
}