package com.example.usuario.ejerciciosad_t2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

public class CodificacionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String UTF8 = "UTF-8";
    private static final String UTF16 = "UTF-16";
    private static final String ISO = "ISO-8856-15";

    EditText edtLectura, edtEscritura, edtContenido;
    RadioButton rbtUtf8, rbtUtf16, rbtIso;
    Button btnLeer, btnGuardar;
    Memoria miMemoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificacion);

        edtLectura = (EditText) findViewById(R.id.edtLectura);
        edtEscritura = (EditText) findViewById(R.id.edtEscritura);
        edtContenido = (EditText) findViewById(R.id.edtContenido);
        rbtUtf8 = (RadioButton) findViewById(R.id.rbtUtf8);
        rbtUtf16 = (RadioButton) findViewById(R.id.rbtUtf16);
        rbtIso = (RadioButton) findViewById(R.id.rbtIso);
        btnLeer = (Button) findViewById(R.id.btnLeer);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        //miMemoria = new Memoria(getApplicationContext());
        miMemoria = new Memoria(this);
    }

    @Override
    public void onClick(View view) {
        String cadenaLectura = edtLectura.getText().toString();
        String cadenaEscritura = edtEscritura.getText().toString();
        String cadenaContenido = edtContenido.getText().toString();
        String codificacion = "";
        Resultado resultado;
        String mensaje = "";

        if (rbtUtf8.isChecked()) codificacion = UTF8;
        if (rbtUtf16.isChecked()) codificacion = UTF16;
        if (rbtIso.isChecked()) codificacion = ISO;

        if (!codificacion.equals("")) {
            switch (view.getId()) {
                case R.id.btnLeer:
                    resultado = miMemoria.leerExterna(cadenaLectura, codificacion);
                    if (resultado.getCodigo()) {
                        edtContenido.setText(resultado.getContenido());
                        mensaje = "Archivo leído con éxito";
                    } else {
                        edtContenido.setText("");
                        mensaje = "Error al leer el archivo " + cadenaLectura + " " + resultado.getMensaje();
                    }
                    break;
                case R.id.btnGuardar:
                    if (cadenaContenido.isEmpty())
                        mensaje = "Debe introducir un nombre para el archivo";
                    else {
                        if (miMemoria.disponibleEscritura())
                            if (miMemoria.escribirExterna(cadenaEscritura, cadenaContenido, false, codificacion))
                                mensaje = "Fichero escrito correctamente";
                            else
                                mensaje = "Error al escribir el archivo";
                        else
                            mensaje = "Memoria externa no disponible";
                    }
                    break;
                case R.id.btnBuscarLectura:
                    miMemoria.LeerRutaExternaFilePicher(this);
                    break;
                case R.id.btnBuscarEscritura:
                    miMemoria.LeerRutaExternaFilePicher(this);
                    break;
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            edtLectura.setText(filePath);
        } else
            Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
    }

    //FALTA LEER RUTA Y LEER CONTENIDO

}
