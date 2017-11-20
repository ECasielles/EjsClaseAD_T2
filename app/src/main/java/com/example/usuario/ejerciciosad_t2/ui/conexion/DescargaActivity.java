package com.example.usuario.ejerciciosad_t2.ui.conexion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.usuario.ejerciciosad_t2.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class DescargaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText texto;
    Button botonArchivo, botonImagen;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga);
        texto = (EditText) findViewById(R.id.edtSubida);
        botonArchivo = (Button) findViewById(R.id.descargarArchivo);
        botonImagen = (Button) findViewById(R.id.descargarImagen);
        botonImagen.setOnClickListener(this);
        botonArchivo.setOnClickListener(this);
        imagen = (ImageView) findViewById(R.id.imagen);
    }
    @Override
    public void onClick(View v) {
        String url = texto.getText().toString();
        if (v == botonImagen)
            descargaImagen(url);
        if (v == botonArchivo) {
            descargaImagen(texto.getText().toString());
        }
    }

    private void descargaImagen(String url) {
        /*Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                //.resize(300, 400)
                //.rotate(45)
                .into(imagen);*/
        //Con OkHttp3
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(DescargaActivity.this)
                .downloader(new OkHttp3Downloader(client))
                .build();
        picasso.with(this).load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imagen);
    }
}
