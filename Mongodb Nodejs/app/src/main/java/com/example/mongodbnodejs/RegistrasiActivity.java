package com.example.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegistrasiActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;

    private EditText edtNpm, edtNama, edtProdi, edtPassword;
    private Button btnRegistrasi;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtNpm      = (EditText) findViewById(R.id.edtNpm);
        edtNama     = (EditText) findViewById(R.id.edtNama);
        edtProdi    = (EditText) findViewById(R.id.edtProdi);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnRegistrasi = (Button) findViewById(R.id.btnRegis);
        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNpm   = edtNpm.getText().toString();
                String strNama  = edtNama.getText().toString();
                String strProdi = edtProdi.getText().toString();
                String strPass  = edtPassword.getText().toString();

                if(strNpm.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Npm tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if(strNama.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if(strProdi.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Prodi tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                }else if(strPass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strNpm, strNama, strProdi, strPass);
                }
            }
        });
    }

    private void registrasi(String npm, String nama, String prodi, String pass){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("npm", npm);
        params.put("nama", nama);
        params.put("prodi", prodi);
        params.put("password", pass);

        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.inputDataMhs, new JSONObject(params),
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
                                msg = response.getString("pesan");
                                Intent i = new Intent(RegistrasiActivity.this,
                                        LoginActivity.class);
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
