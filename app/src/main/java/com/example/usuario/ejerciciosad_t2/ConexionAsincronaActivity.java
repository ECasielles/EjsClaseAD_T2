package com.example.usuario.ejerciciosad_t2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionAsincronaActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUrl;
    RadioButton rbtnJava, rbtnApache, rbtnAAHC;
    Button btnConectar;
    WebView webvWeb;
    TextView txvResultado;
    long inicio, fin;
    TareaAsincrona miTarea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_asincrona);
    }
    private void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        rbtnJava = (RadioButton) findViewById(R.id.rbtnJava);
        rbtnApache = (RadioButton) findViewById(R.id.rbtnApache);
        rbtnAAHC = (RadioButton) findViewById(R.id.rbtnAAHC);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        webvWeb = (WebView) findViewById(R.id.webvWeb);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        //Quitamos el StrictMode
    }
    @Override
    public void onClick(View v) {
        String texto = edtUrl.getText().toString();
        String tipo = Conexion.APACHE;
        inicio = System.currentTimeMillis();

        if (v == btnConectar) {
                if (rbtnJava.isChecked())
                    tipo = Conexion.JAVA;
                miTarea = new TareaAsincrona(this);
                miTarea.execute(tipo, texto);
            }
            txvResultado.setText("Esperando...");
        }
    }
}

