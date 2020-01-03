package com.example.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;
import session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private Button btnLinkRegister;

    private EditText edtNpm, edtPassword;
    private Button btnLogin;

    private ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(this);

        if(session.isLoggedIn()){
            Intent i = new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(i);
            finish();
        }

        edtNpm      = (EditText) findViewById(R.id.edtNpm);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin    = (Button) findViewById(R.id.btnLogin);

        btnLinkRegister = (Button) findViewById(R.id.btnLinkRegister);
        btnLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,
                        RegistrasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNpm   = edtNpm.getText().toString();
                String strPass  = edtPassword.getText().toString();

                if(strNpm.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Npm tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if(strPass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    login(strNpm, strPass);
                }
            }
        });
    }

    private void login(String npm, String pass){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("npm", npm);
        params.put("password", pass);

        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            String msg;
                            if(status == true){
                                msg = response.getString("pesan");
                            }else {
                                session.setLogin(true);
                                msg = response.getString("pesan");
                                Intent i = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), msg,
                                    Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        // ApplicationController.getInstance().addToRequestQueue(req);
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
