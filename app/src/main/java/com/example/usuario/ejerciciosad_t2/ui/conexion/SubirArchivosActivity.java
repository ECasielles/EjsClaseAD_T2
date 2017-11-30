package com.example.usuario.ejerciciosad_t2.ui.conexion;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.RestClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class SubirArchivosActivity extends AppCompatActivity {

    TextView informacion;
    EditText texto, edtPwd;
    Button btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_archivos);
        informacion = (TextView) findViewById(R.id.txvInfo);
        texto = (EditText) findViewById(R.id.edtFichero);
        edtPwd = (EditText) findViewById(R.id.edtPassword);
        btnSubir = (Button) findViewById(R.id.btnSubida);
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subida();
            }
        });
    }

    public final static String WEB = "https://alumno.mobi/~alumno/superior/casielles/test/php/upload.php";

    private void subida() {
        String fichero = texto.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(SubirArchivosActivity.this);
        File myFile;
        Boolean existe = true;
        myFile = new File(Environment.getExternalStorageDirectory(), fichero);
        RequestParams params = new RequestParams();
        params.put("password", edtPwd.getText().toString());
        try {
            params.put("fileToUpload", myFile);
        } catch (FileNotFoundException e) {
            existe = false;
            informacion.setText("Error en el fichero: " + e.getMessage());
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    // called before request is started
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
                    //progreso.setCancelable(false);
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    progreso.dismiss();
                    informacion.setText(response);
                    informacion.setText("Correcto: " + statusCode + " " + response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                    progreso.dismiss();
                    informacion.setText("Fallo: " + statusCode + " " + t.getMessage() + " " + response);
                }
            });
    }
}
