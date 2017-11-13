package com.example.usuario.ejerciciosad_t2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.RestClient;
import com.example.usuario.ejerciciosad_t2.utils.TareaAsincrona;
import com.loopj.android.http.TextHttpResponseHandler;

public class ConexionAAHCActivity extends AppCompatActivity {

    EditText edtUrl;
    RadioButton rbtnJava, rbtnApache, rbtnAAHC;
    Button btnConectar;
    WebView webvWeb;
    TextView txvResultado;
    public static final String JAVA = "Java";
    public static final String APACHE = "Apache";
    long inicio, fin;
    TareaAsincrona miTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_aahc);
    }

    private void AAHC() {
        final String texto = edtUrl.getText().toString();
        final long inicio;
        final long[] fin = new long[1];
        final ProgressDialog progreso = new ProgressDialog(this);

        inicio = System.currentTimeMillis();

        RestClient.get(texto, new TextHttpResponseHandler() {
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
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                fin[0] = System.currentTimeMillis();
                progreso.dismiss();
                webvWeb.loadDataWithBaseURL(texto, "Fallo: " + responseString + " " + throwable.getMessage(), "text/html", "UTF-8", null);
                txvResultado.setText("Duración: " + String.valueOf(fin[0] - inicio) + " milisegundos");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                // called when response HTTP status is "200"
                fin[0] = System.currentTimeMillis();
                progreso.dismiss();
                webvWeb.loadDataWithBaseURL(texto, responseString, "text/html", "UTF-8", null);
                txvResultado.setText("Duración: " + String.valueOf(fin[0] - inicio) + " milisegundos");
            }
        });
    }
}
