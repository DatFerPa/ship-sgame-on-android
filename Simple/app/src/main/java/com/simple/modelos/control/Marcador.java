package com.simple.modelos.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.simple.GameView;
import com.simple.modelos.Modelo;
import com.simple.utilidades.Ar;

/**
 * Created by UO244588 on 02/10/2017.
 */

public class Marcador extends Modelo {

    private int puntos;

    public Marcador(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(Ar.ancho(30));
        canvas.drawText(String.valueOf(puntos), (int)x, (int)y, paint);
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }
}
