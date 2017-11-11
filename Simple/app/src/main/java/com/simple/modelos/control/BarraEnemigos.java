package com.simple.modelos.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.simple.modelos.Modelo;

/**
 * Created by Fernando on 24/10/2017.
 */

public class BarraEnemigos extends Modelo {

    private int valorMaximo;
    private int valorActual;
    private double xInicio;
    private double yInicio;
    private double xFin;
    private double yFin;
    private int anchoEnemigo;
    private int altoEnemigo;

    public BarraEnemigos(Context context, double x, double y,
                         int valorMaximo, int valorActual,int anchoEnemigo,int altoEnemigo) {

        super(context, x, y);
        this.valorActual = valorActual;
        this.valorMaximo = valorMaximo;
        this.xInicio = x;
        this.yInicio = y;
        this.anchoEnemigo = anchoEnemigo;
        this.altoEnemigo = altoEnemigo;
        this.xFin = x+(anchoEnemigo/2);
        this.yFin = (y-altoEnemigo/2)-5;
    }

    public void actualizarPosiciones(double x, double y){
        this.xInicio =(x-anchoEnemigo/2);
        this.yInicio=(y-altoEnemigo/2)-5;
        this.xFin = x+(anchoEnemigo/2);
        this.yFin = (y-altoEnemigo/2)-5;
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        Paint linea = new Paint();
        linea.setColor(Color.BLACK);
        linea.setStrokeWidth(20);
        canvas.drawLine((int)xInicio, (int) yInicio, (int) xFin, (int) yFin, linea);

        linea.setColor(Color.RED);
        linea.setStrokeWidth(15);
        canvas.drawLine((int)xInicio+5,(int) yInicio,(int)xFin-5,(int)yFin,linea);

        linea.setColor(Color.GREEN);
        linea.setStrokeWidth(15);
        double longitud = (xFin-5)-(xInicio+5);
        canvas.drawLine((int)xInicio+5,(int) yInicio,(int) ((xInicio+5) +((longitud/valorMaximo)*valorActual)),(int)yFin,linea);
    }

    public int getValorActual() {
        return valorActual;
    }

    public void setValorActual(int valorActual) {
        this.valorActual = valorActual;
    }
}
