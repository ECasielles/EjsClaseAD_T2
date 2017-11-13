package com.example.usuario.ejerciciosad_t2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.TareaAsincrona;
import com.example.usuario.ejerciciosad_t2.utils.Conexion;

public class ConexionAsincronaActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUrl;
    RadioButton rbtnJava, rbtnApache, rbtnAAHC;
    Button btnConectar;
    public WebView webvWeb;
    public TextView txvResultado;
    public long inicio;
    TareaAsincrona miTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_asincrona);
        iniciar();
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

