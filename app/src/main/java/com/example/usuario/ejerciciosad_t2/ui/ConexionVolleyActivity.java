package com.example.usuario.ejerciciosad_t2.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.ejerciciosad_t2.R;

import com.android.volley.RequestQueue;

import java.io.UnsupportedEncodingException;

public class ConexionVolleyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MyTag";
    EditText edtUrl;
    Button btnConectar;
    WebView webvWeb;
    TextView txvResultado;
    RequestQueue mRequestQueue;
    private long inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_volley);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        webvWeb = (WebView) findViewById(R.id.webvWeb);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
    }
    @Override
    public void onClick(View view) {
        inicio = System.currentTimeMillis();
        final String url;
        //mRequestQueue = Volley.newRequestQueue(this);
        if (view == btnConectar) {
            url = edtUrl.getText().toString();
            makeRequest(url);
        }
    }
    public void makeRequest(String url) {
        final String enlace = url;
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);

        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Conectando . . .");
        progreso.setCancelable(true);
        progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
            public void onCancel(DialogInterface dialog){
                mRequestQueue.cancelAll(TAG);
            }
        });
        progreso.show();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        long fin = System.currentTimeMillis();
                        progreso.dismiss();
                        webvWeb.loadDataWithBaseURL(enlace, response, "text/html", "utf-8", null);
                        txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String mensaje = "Error";
                        long fin = System.currentTimeMillis();
                        progreso.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                            mensaje = "Timeout Error: " + error.getMessage();
                        else {
                            NetworkResponse errorResponse = error.networkResponse;
                            if (errorResponse != null && errorResponse.data != null)
                                try {
                                    mensaje = "Error: " + errorResponse.statusCode + " " + "\n" + new
                                            String(errorResponse.data, "UTF-8");
                                    Log.e("Error", mensaje);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    mensaje = "Error sin información";
                                }
                        }
                        webvWeb.loadDataWithBaseURL(null, mensaje, "text/html", "UTF-8", null);
                        txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
                    }
                });
        // Set the tag on the request.
        stringRequest.setTag(TAG);
        // Set retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

}
