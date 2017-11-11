package com.simple.modelos;

import android.content.Context;

import com.simple.R;
import com.simple.utilidades.Ar;

/**
 * Created by UO244588 on 25/09/2017.
 */

public class DisparoNave extends DisparoAbstracto {

    public DisparoNave(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(20);
        aceleracionY = -6;
        imagen =
                context.getResources().getDrawable(R.drawable.disparo_nave);

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
