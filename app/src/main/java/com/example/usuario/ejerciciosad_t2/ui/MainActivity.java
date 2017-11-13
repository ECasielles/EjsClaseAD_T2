package com.example.usuario.ejerciciosad_t2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.usuario.ejerciciosad_t2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCodificacion:
                startActivity(new Intent(this, CodificacionActivity.class));
            case R.id.btnAdminFicheros:
                startActivity(new Intent(this, AdministradorArchivosActivity.class));
        }
    }
}
