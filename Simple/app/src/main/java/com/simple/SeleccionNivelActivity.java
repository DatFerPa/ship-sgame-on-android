package com.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.simple.gestores.GestorNiveles;

public class SeleccionNivelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pantalla completa, sin título
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_seleccion_nivel);
    }



    public void abrirNivel_1(View v){
        // Seleccionar el nivel
        GestorNiveles.getInstancia().setNivelActual(1);

        Intent actividadJuego = new Intent(this,MainActivity.class);
        startActivity(actividadJuego);
    }


    public void abrirNivel_2(View v){
        // Seleccionar el nivel
        GestorNiveles.getInstancia().setNivelActual(2);

        Intent actividadJuego = new Intent(this,MainActivity.class);
        startActivity(actividadJuego);
    }

    public void abrirNivel_3(View v){
        // Seleccionar el nivel
        GestorNiveles.getInstancia().setNivelActual(3);

        Intent actividadJuego = new Intent(this,MainActivity.class);
        startActivity(actividadJuego);
    }

}
