package com.simple.modelos.control;

import android.content.Context;

import com.simple.R;
import com.simple.modelos.Modelo;
import com.simple.utilidades.Ar;

/**
 * Created by UO244588 on 25/09/2017.
 */

public class BotonDisparar extends Modelo {




    public BotonDisparar(Context context, double x, double y) {
            super(context, x, y);
            altura = Ar.altura(100);
            ancho = Ar.ancho(100);
            imagen = context.getResources().getDrawable(R.drawable.boton_disparar);
        }

    public int getOrientacionX(float cliclX) {
        return (int) (x - cliclX);
    }


    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) &&
                clickX >= (x - ancho / 2) &&
                clickY <= (y + altura / 2) &&
                clickY >= (y - altura / 2)) {

            estaPulsado = true;
        }

        return estaPulsado;
    }

}
