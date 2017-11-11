package com.simple.modelos;

import android.content.Context;

import com.simple.R;
import com.simple.utilidades.Ar;

/**
 * Created by Fernando on 16/10/2017.
 */

public class DisparoJefe extends DisparoAbstracto {

    public DisparoJefe(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(26);
        ancho = Ar.ancho(26);
        aceleracionY = 5;
        imagen =
                context.getResources().getDrawable(R.drawable.disparo_jefe_1);

    }




}
