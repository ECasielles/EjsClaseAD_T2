package com.example.usuario.ejerciciosad_t2.ui.conexion;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.Conexion;
import com.example.usuario.ejerciciosad_t2.utils.Resultado;

public class ConexionHTTPActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUrl;
    RadioButton rbtnJava, rbtnApache;
    Button btnConectar;
    WebView webvWeb;
    TextView txvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_http);
        iniciar();
    }
    private void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        rbtnJava = (RadioButton) findViewById(R.id.rbtnJava);
        rbtnApache = (RadioButton) findViewById(R.id.rbtnApache);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        webvWeb = (WebView) findViewById(R.id.webvWeb);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }
    @Override
    public void onClick(View v) {
        String texto = edtUrl.getText().toString();
        long inicio, fin;
        Resultado resultado;
        if (v == btnConectar) {
            inicio = System.currentTimeMillis();
            if (rbtnJava.isChecked())
                resultado = Conexion.conectarJava(texto);
            else
                resultado = Conexion.conectarApache(texto);
            fin = System.currentTimeMillis();
            if (resultado.getCodigo())
                webvWeb.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                webvWeb.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }
    }

}
