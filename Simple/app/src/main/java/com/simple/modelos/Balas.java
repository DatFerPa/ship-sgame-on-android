package com.simple.modelos;

import android.content.Context;

import com.simple.R;
import com.simple.utilidades.Ar;

/**
 * Created by Fernando on 24/10/2017.
 */

public class Balas extends Modelo {

    private double aceleracionY = 5;

    public Balas(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(37);
        ancho = Ar.ancho(37);

        imagen = context.getResources().getDrawable(R.drawable.balas);

    }

    public void moverAutomaticamente(){
        y = y+aceleracionY;
    }

    public int estaEnPantalla() {
        if (y + altura / 2 < 0) {
            return 0; // Va a aparecer
        }
        if (y + altura / 2 >= 0 && y - altura / 2 < mCanvasAltura) {
            return 1; // Está en pantalla
        }
        return -1; // Se ha salido por debajo

    }



}
