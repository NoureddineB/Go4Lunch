package com.heuss.benomari.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Nullable
    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        startSigningActivity();
        if (isCurrentUserLogged()) {
            startMainActivity();

        }

    }

    private void startSigningActivity()

    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.drawable.meal_v3_final)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), //EMAIL
                                        new AuthUI.IdpConfig.GoogleBuilder().build())) // SUPPORT GOOGLE
                        .setIsSmartLockEnabled(false, true)

                        .build(),

                RC_SIGN_IN);

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public Boolean isCurrentUserLogged() {
        return (getCurrentUser() != null);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                startMainActivity();
            }
        }

    }


}





