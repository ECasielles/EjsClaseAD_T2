package com.example.usuario.ejerciciosad_t2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.usuario.ejerciciosad_t2.R;
import com.example.usuario.ejerciciosad_t2.utils.Resultado;
import com.example.usuario.ejerciciosad_t2.utils.Conexion;

public class ConexionAsincronaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    RadioButton rbtnJava, rbtnApache;
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

    public class TareaAsincrona extends AsyncTask<String, Integer, Resultado> {
        private Context context;
        private ProgressDialog progreso;

        public TareaAsincrona(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Conectando . . .");
            progreso.setCancelable(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                public void onCancel(DialogInterface dialog){
                    TareaAsincrona.this.cancel(true);
                }
            });
            progreso.show();
        }
        //Los (...) al lado del tipo significa que es parámetro de la clase
        protected Resultado doInBackground(String... cadena) {
            Resultado resultado;
            int i = 1;

            // operaciones en el hilo secundario
            try {
                publishProgress(i++);
                if (cadena[0].equals(Conexion.JAVA))
                    resultado = Conexion.conectarJava(cadena[1]);
                else
                    resultado = Conexion.conectarApache(cadena[1]);
                Log.d("TipoConexion", cadena[0]);
                Log.d("Url", cadena[1]);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                resultado = new Resultado();
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
                cancel(true);
            }
            return resultado;
        }
        protected void onPostExecute(Resultado resultado) {
            progreso.dismiss();
            long fin = System.currentTimeMillis();

            if (resultado.getCodigo())
                webvWeb.loadDataWithBaseURL(null, resultado.getContenido(),
                        "text/html", "UTF-8", null);
            else
                webvWeb.loadDataWithBaseURL(null, resultado.getMensaje(),
                        "text/html", "UTF-8", null);
            txvResultado.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }
        protected void onProgressUpdate(Integer... progress) {
            progreso.setMessage("Conectando " + Integer.toString(progress[0]));
        }
        protected void onCancelled() {
            progreso.dismiss();
            long fin = System.currentTimeMillis();

            webvWeb.loadDataWithBaseURL(
                    null, "Cancelado", "text/html", "UTF-8", null);
            txvResultado.setText(
                    "Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }

    }



}


