package insa.cloud.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import insa.cloud.R;
import insa.cloud.global.SessionManager;
import insa.cloud.global.Url;
import insa.cloud.global.VolleyController;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private Button btnSkip;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());


        // Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    try {
                        registerUser(email, password);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),
                                "Exception Volley : "+e.toString(), Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();*/
                Intent i = new Intent(getApplicationContext(), EventDetailsActivity.class);
                startActivity(i);
                finish();
            }
        });


        if (session.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void registerUser(final String email, final String password) throws JSONException {

        pDialog.setMessage("Registering ...");
        pDialog.show();

        JSONObject jsonObj = new JSONObject();
        /*
        jsonObj.put("email", email);
        jsonObj.put("password", password);
        */

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST ,
                Url.Register, jsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Register Response: " + response.toString());
                if(pDialog != null)
                    pDialog.dismiss();

                try {
                    String token = response.getString("Token");
                    session.setLogin(true);
                    session.setToken(token);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                if(pDialog != null)
                    pDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }



}
