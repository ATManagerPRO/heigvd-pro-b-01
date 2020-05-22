package com.heig.atmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.heig.atmanager.dialog.InviteDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : Simon Mattei
 * Date   : 21.03.2020
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient; //google client account
    private final int RC_SIGN_IN = 1; //request number when calling google sign in
    private final String TAG = getClass().getSimpleName(); //name of class for errors

    private GoogleSignInAccount account;

    private Uri data;
    private boolean isInvited;

    @Override
    protected void onStart() {
        super.onStart();

        //get userId and token from the local sharedPreferences
        account = GoogleSignIn.getLastSignedInAccount(this);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("user", 0);
        String token = settings.getString("userToken", null);
        long id = settings.getLong("userId", 0);

        updateUI(account, token, id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_google);

        //set button listener to this activity
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Request email allows to also access their email
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.server_client_id))
                        .requestEmail()
                        .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    private void updateUI(GoogleSignInAccount account, String token, long id) {
        //If user is connected, launch mainActivity
        if (account != null) {
            //launch main activity
            Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
            mainActivity.putExtra("userToken", token);
            mainActivity.putExtra("userId", id);
            startActivity(mainActivity);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            //..others buttons (probably none)
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN); //start google sign in activity
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            fetchUser(account);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null, "", 0);
        }
    }

    private void fetchUser(final GoogleSignInAccount googleAccount) {
        //post request to the server
        String URL = "https://atmanager.gollgot.app/api/v1/auth";
        JSONObject jsonBody = new JSONObject();

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status code") == 200 || response.getInt("status code") == 201) {
                        String token = response.getJSONObject("resource").getString("tokenAPI");
                        long id = response.getJSONObject("resource").getInt("userId");

                        //store userId and userToken locally in the shared preferences
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("user", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putLong("userId", id);
                        editor.putString("userToken", token);
                        editor.apply();

                        updateUI(googleAccount, token, id);
                    }
                    if (response.getInt("status code") == 400 || response.getInt("status code") == 500) {
                        //MainActivity.signOut();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + googleAccount.getIdToken());//put your token here
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonObject);
    }
}


