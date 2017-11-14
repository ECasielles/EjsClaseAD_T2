package com.example.usuario.ejerciciosad_t2.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.RestClient;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * AAHC: Android Asynchronous HTTP Client
 */
public class ConexionAAHCActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    Button btnConectar;
    WebView webvWeb;
    TextView txvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_aahc);
        iniciar();
    }
    private void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        webvWeb = (WebView) findViewById(R.id.webvWeb);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        //Quitamos el StrictMode
    }
    @Override
    public void onClick(View v) {
        AAHC();
    }

    private void AAHC() {
        final String texto = edtUrl.getText().toString();
        final long inicio = System.currentTimeMillis();
        final ProgressDialog progreso = new ProgressDialog(this);

        RestClient.get(texto, new TextHttpResponseHandler() {
            long fin;

            @Override
            public void onStart() {
                // called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        RestClient.cancelRequests(ConexionAAHCActivity.this, true);
                    }
                });
                progreso.show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                fin = System.currentTimeMillis();
                progreso.dismiss();
                webvWeb.loadDataWithBaseURL(texto, "Fallo: " + responseString + " " + throwable.getMessage(), "text/html", "UTF-8", null);
                txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // called when response HTTP status is "200"
                fin = System.currentTimeMillis();
                progreso.dismiss();
                webvWeb.loadDataWithBaseURL(texto, responseString, "text/html", "UTF-8", null);
                txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
            }
        });
    }

}
