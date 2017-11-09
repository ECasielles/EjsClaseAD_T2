package com.example.usuario.ejerciciosad_t2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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
}
