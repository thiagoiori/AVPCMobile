package com.avpc.avpcmobile.util.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

/**
 * Created by thiago on 27/06/17.
 */

public class FirebaseLogin implements ILogin {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.IdTokenListener mTokenListener;
    private FirebaseUser mUser;
    private ILoginFirebase mLoginFirebase;

    public FirebaseLogin(ILoginFirebase loginFirebase) {
        mLoginFirebase = loginFirebase;
        this.mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createITokenStateListener();
    }

    public void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("loginActivity", "signInWithEmail:success");
                    mUser = mAuth.getCurrentUser();
                    mLoginFirebase.onUserLoggedIn();
                } else {
                    mLoginFirebase.onUserLoggedOut();
                }
            }
        });
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public void addListeners() {
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.addIdTokenListener(mTokenListener);
    }

    public void removeListeners() {
        if(mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.removeIdTokenListener(mTokenListener);
        }
    }

    public void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    mLoginFirebase.onUserLoggedIn();
                } else {
                    mLoginFirebase.onUserLoggedOut();
                }
            }
        };
    }

    public void createITokenStateListener() {
        mTokenListener = new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
//                        mToken = task.getResult().getToken();
//                        userToken.setToken(mToken);
                    }
                });
            }
        };
    }

    public FirebaseAuth.AuthStateListener getAuthListener() {
        return mAuthListener;
    }

    public interface ILoginFirebase {
        void onUserLoggedIn();
        void receiveUserToken(String token);
        void onUserLoggedOut();
    }

}
