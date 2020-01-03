package com.example.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class InputMK extends AppCompatActivity {

    EditText kodemk, namaMk,jam, hari, ruangan, nidn, nama;
    Button btnKirim;

    private RequestQueue mRequestQueue;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mk);
        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        kodemk = (EditText) findViewById(R.id.kodemk);
        namaMk = (EditText) findViewById(R.id.namamk);
        jam = (EditText) findViewById(R.id.jam);
        hari = (EditText) findViewById(R.id.hari);
        ruangan = (EditText) findViewById(R.id.ruangan);
        nidn = (EditText) findViewById(R.id.nidn);
        nama = (EditText) findViewById(R.id.nama);

        btnKirim = (Button) findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strKdmk = kodemk.getText().toString();
                String strNamaMk = namaMk.getText().toString();
                String strJam = jam.getText().toString();
                String strHari = hari.getText().toString();
                String strRuang = ruangan.getText().toString();
                String strNidn = nidn.getText().toString();
                String strNama = nama.getText().toString();
                if (strKdmk.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kode Matakuliah Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strNamaMk.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Matakuliah Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strJam.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Jam Matakuliah Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strHari.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Hari Matakuliah Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strRuang.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ruang Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strNidn.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nidn Dosen Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(strNama.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Dosen Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
                inputMatakuliah(strKdmk, strNamaMk, strJam, strHari, strRuang, strNidn, strNama);
            }
        });
    }

    private void inputMatakuliah(String kodeMk, String namaMk, String jam, String hari, String ruang, String nidn, String namaDosen){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("kodemk", kodeMk);
        params.put("namamk", namaMk);
        params.put("jam", jam);
        params.put("hari", hari);
        params.put("ruangan", ruang);
        params.put("nidn", nidn);
        params.put("namadosen", namaDosen);

        pDialog.setMessage("Mohon tunggu");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.simpanMk, new JSONObject(params),
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
                                Intent i = new Intent(InputMK.this,
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
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(InputMK.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
