package com.example.usuario.ejerciciosad_t2.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.usuario.ejerciciosad_t2.ui.ConexionAsincronaActivity;

public class TareaAsincrona extends AsyncTask<String, Integer, Resultado> {
    private ProgressDialog progreso;
    private Context context;
    private ConexionAsincronaActivity conexionAsincronaActivity;

    public TareaAsincrona(Context context) {
        this.context = context;
        conexionAsincronaActivity = ((ConexionAsincronaActivity) context);
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
            if (cadena[0].equals((Conexion.JAVA)))
                resultado = Conexion.conectarJava(cadena[1]);
            else
                resultado = Conexion.conectarApache(cadena[1]);
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

        // mostrar el resultado
        if (resultado.getCodigo())
            conexionAsincronaActivity.webvWeb.loadDataWithBaseURL(
                            null, resultado.getContenido(),
                            "text/html", "UTF-8", null);
        else
            conexionAsincronaActivity.webvWeb.loadDataWithBaseURL(
                            null, resultado.getMensaje(),
                            "text/html", "UTF-8", null);
        conexionAsincronaActivity.txvResultado.setText(
                "Duración: " + String.valueOf(fin - conexionAsincronaActivity.inicio) + " milisegundos");
    }

    protected void onProgressUpdate(Integer... progress) {
        progreso.setMessage("Conectando " + Integer.toString(progress[0]));
    }
    protected void onCancelled() {
        progreso.dismiss();

        long fin = System.currentTimeMillis();

        conexionAsincronaActivity.webvWeb.loadDataWithBaseURL(
                null, "Cancelado", "text/html", "UTF-8", null);
        conexionAsincronaActivity.txvResultado.setText(
                "Duración: " + String.valueOf(fin - conexionAsincronaActivity.inicio) + " milisegundos");
    }

}