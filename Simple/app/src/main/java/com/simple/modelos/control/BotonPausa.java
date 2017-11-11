package com.simple.modelos.control;

import android.content.Context;

import com.simple.R;
import com.simple.modelos.Modelo;
import com.simple.utilidades.Ar;

/**
 * Created by Fernando on 15/10/2017.
 */

public class BotonPausa extends Modelo {

    public BotonPausa(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(36);
        ancho = Ar.ancho(36);
        imagen = context.getResources().getDrawable(R.drawable.boton_pausa);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) && clickX >= (x - ancho / 2)
                && clickY <= (y + altura / 2) && clickY >= (y - altura / 2)

                ) {
            estaPulsado = true;
        }
        return estaPulsado;
    }




}
