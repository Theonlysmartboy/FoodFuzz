package com.otemainc.foodfuzz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText _nameText, _emailText, _passwordText, _confirmpasswordText;
    private Button _signupButton;
    private TextView _loginLink;
    private static String URL_REGIST = "http://192.168.100.250:8082/foodfuzzbackend/auth/register.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _confirmpasswordText = findViewById(R.id.input_cpassword);
        _signupButton=findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        //  signup logic.
       final String name = _nameText.getText().toString().trim();
       final String email = _emailText.getText().toString().trim();
       final String password = _passwordText.getText().toString().trim();
        StringRequest registerStringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerObject = new JSONObject(response);
                            String registerSuccess = registerObject.getString("success");
                            if(registerSuccess.equals("1")){
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                progressDialog.dismiss();
                                                Toast.makeText(RegisterActivity.this,"Registration Successfull", Toast.LENGTH_SHORT).show();
                                                onSignupSuccess();
                                            }
                                        }, 3000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Registration Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                            onSignupFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Registration Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        onSignupFailed();
                    }
                }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue registerrequestQueue = Volley.newRequestQueue(this);
        registerrequestQueue.add(registerStringRequest);
            }

    public void onSignupSuccess() {
        Intent main = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(main);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
               _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = false;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String cpassword = _confirmpasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Name should be at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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
        if(cpassword.isEmpty()){
            _confirmpasswordText.setError("Retype Password");
            valid = false;
        }else if(!cpassword.equals(password)){
            _confirmpasswordText.setError("Passwords do not match");
            valid = false;
        }else{
            _confirmpasswordText.setError(null);
        }
        return valid;
    }
}