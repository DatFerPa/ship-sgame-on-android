package com.simple.modelos;

import android.content.Context;

/**
 * Created by Fernando on 16/10/2017.
 */

public abstract class DisparoAbstracto extends Modelo{


    protected double aceleracionY;

    public DisparoAbstracto(Context context, double x, double y) {
        super(context, x, y);
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
