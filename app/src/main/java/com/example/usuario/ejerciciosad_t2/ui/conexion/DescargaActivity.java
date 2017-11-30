package com.example.usuario.ejerciciosad_t2.ui.conexion;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.RestClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.File;

import cz.msebera.android.httpclient.Header;
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
        if (v == botonImagen) descargaImagen(url);
        if (v == botonArchivo) descargaArchivo(url);
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
        new Picasso.Builder(DescargaActivity.this)
                .downloader(new OkHttp3Downloader(client))
                .build();
        Picasso.with(this).load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imagen);
    }
    private void descargaArchivo(String url) {
        imagen.setImageResource(R.drawable.placeholder);
        final ProgressDialog progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        //RestClient tiene el método que permite hacer el guardado a fichero asíncrono
        RestClient.get(url, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(),"Fallo en la descarga: " + statusCode + ", " + throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(),"Descarga con éxito",Toast.LENGTH_LONG).show();
            }
        });
    }

}
