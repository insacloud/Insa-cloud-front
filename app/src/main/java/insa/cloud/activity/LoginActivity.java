package insa.cloud.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import insa.cloud.R;
import insa.cloud.global.RequestCallback;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestVolley;
import insa.cloud.global.SessionManager;
import insa.cloud.global.Url;
import insa.cloud.global.VolleyController;

public class LoginActivity extends AppCompatActivity{
    private final String TAG =LoginActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private Button btnSkip;
    private LoginButton btnLoginFacebook;

    private ProgressDialog pDialog;
    private EditText inputEmail;
    private EditText inputPassword;
    private SessionManager session;

    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnLoginFacebook = (LoginButton)findViewById(R.id.login_button_facebook);
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile, email"));

        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                session.setLogin(true);
                Intent intent = new Intent(LoginActivity.this, EventListActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),
                        "Facebook cancel", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(),
                        "Erreur Facebook : " + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
        });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                    //ProfileTracker Facebook
            }
        };


        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    try {
                        Login(email, password);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),
                                "Exception Volley : "+e.toString(), Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter email + password maybe ?", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        //Register
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Skip
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EventListActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Session Manager
        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, EventListActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        //try to logout from facebook, prevent facebook login cancel callback.
        try{
            LoginManager.getInstance().logOut();
        }
        catch(Exception e)
        {
        }
    }


    public void Login(final String email, final String password) throws JSONException{
         pDialog = new ProgressDialog(this);
        pDialog.setMessage("Logging in ...");
        pDialog.show();

        MainActivity.request.login(email, password, new RequestCallback<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        String token = response.getString("Token");
                        if (!token.isEmpty()) {
                            if (pDialog != null) {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                            }
                            session.setLogin(true);
                            session.setToken(token);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                if (pDialog != null) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
