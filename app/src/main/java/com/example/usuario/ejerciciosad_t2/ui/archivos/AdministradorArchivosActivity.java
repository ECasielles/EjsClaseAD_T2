package com.example.usuario.ejerciciosad_t2.ui.archivos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ejerciciosad_t2.R;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class AdministradorArchivosActivity extends AppCompatActivity {

    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private Button botonAbrir;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_archivos);
        botonAbrir = (Button) findViewById(R.id.botonAbrir);
        txtInfo = (TextView) findViewById(R.id.txtInfo) ;
        botonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file*//*");
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
                else
                    //informar que no hay ninguna aplicación para manejar ficheros
                    Toast.makeText(
                            AdministradorArchivosActivity.this,
                            "No hay aplicación para manejar ficheros",
                            Toast.LENGTH_SHORT
                    ).show();*/

                new MaterialFilePicker()
                        .withActivity(AdministradorArchivosActivity.this)
                        .withRequestCode(FILE_PICKER_REQUEST_CODE)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                        .withFilterDirectories(true) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            txtInfo.setText(filePath);
        } else
            Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();

        /*if (requestCode == FILE_PICKER_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                // Mostramos en la etiqueta la ruta del archivo seleccionado
                String ruta = data.getData().getPath();
                txtInfo.setText(ruta);
            } else
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();*/
    }
}
