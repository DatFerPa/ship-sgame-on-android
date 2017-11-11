package com.simple.modelos;

import android.content.Context;

import com.simple.R;
import com.simple.utilidades.Ar;

/**
 * Created by UO244588 on 25/09/2017.
 */

public class DisparoEnemigo extends DisparoAbstracto {


    public DisparoEnemigo(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(26);
        aceleracionY = 7;
        imagen =
                context.getResources().getDrawable(R.drawable.disparo_enemigo);

    }

}
