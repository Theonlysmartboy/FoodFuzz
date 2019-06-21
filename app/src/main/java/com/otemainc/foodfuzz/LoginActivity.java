package com.otemainc.foodfuzz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText _emailText, _passwordText;
    private Button _loginButton;
    private TextView _signupLink;
    private static String URL_LOGIN = "http://192.168.100.250:8082/foodfuzzbackend/auth/login.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        // authentication logic.
        final String email = _emailText.getText().toString().trim();
        final String password = _passwordText.getText().toString().trim();
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject loginObject = new JSONObject(response);
                            String loginSuccess = loginObject.getString("success");
                            JSONArray loginArray = loginObject.getJSONArray("login");
                            //if login is successful
                            if(loginSuccess.equals("1")){
                                for(int i =0; i< loginArray.length();i++){
                                    JSONObject object = loginArray.getJSONObject(i);
                                    final String name = object.getString("name").trim();
                                    final String email = object.getString("email").trim();
                                   new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Login Success.\n Welcome " +name, Toast.LENGTH_LONG).show();
                                                    onLoginSuccess();
                                                }
                                            }, 3000);
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Invalid Email/Password",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                onLoginFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Login Error" + e.toString(),Toast.LENGTH_LONG).show();
                            onLoginFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Failed "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue loginRequestQue = Volley.newRequestQueue(this);
        loginRequestQue.add(loginStringRequest);
           }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // successful signup logic
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent main = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(main);
        finish();
    }

    public void onLoginFailed() {
               _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("Password should be at least 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}


