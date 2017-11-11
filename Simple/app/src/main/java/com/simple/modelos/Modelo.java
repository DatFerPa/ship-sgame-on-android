package com.simple.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.simple.utilidades.Ar;

/**
 * Created by UO244588 on 25/09/2017.
 */

public class Modelo {
    // Propiedades del canvas
    protected Context context;
    protected double mCanvasAltura;
    protected double mCanvasAncho;

    // Propiedades del Modelo
    protected double x;
    protected double y;
    protected int altura;
    protected int ancho;
    protected Drawable imagen;


    public Modelo(Context context, double x, double y){

        this.context = context;
        this.x = x;
        this.y = y;
        this.mCanvasAltura = Ar.pantallaAltura;
        this.mCanvasAncho = Ar.pantallaAncho;
    }

    public void dibujarEnPantalla(Canvas canvas){
        int yArriba = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba, xIzquierda
                + ancho, yArriba + altura);
        imagen.draw(canvas);
    }


    public boolean colisiona (Modelo modelo){
        boolean colisiona = false;

        if (modelo.getX() - modelo.getAncho() / 2 <= (x + ancho / 2)
                && (modelo.getX() + modelo.getAncho() / 2) >= (x - ancho / 2)
                && (y + altura / 2) >= (modelo.getY() - modelo.getAltura() / 2)
                && (y - altura / 2) < (modelo.getY() + modelo.getAltura() / 2)) {
            colisiona = true;
        }
        return colisiona;
    }




    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public double getX() {

        return x;
    }

    public double getY() {
        return y;
    }

    public int getAltura() {
        return altura;
    }

    public int getAncho() {
        return ancho;
    }

}
