package com.simple.graficos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by UO244588 on 02/10/2017.
 */

public class Sprite {
    private boolean bucle;
    //animacion se repite
    private Bitmap bitmap;
    // Fichero con los frames.
    private Rect rectanguloDibujo;
    // El rectangulo sobre el que se pinta el dibujo
    private int framesTotales;
    // Número total de frames en el bitmap.
    private int frameActual;
    // El frame que se esta pintando actualmente
    private long tiempoUltimaActualizacion;
    // El tiempo que ha pasado desde que se ha cambiado de frame
    private int interavaloEntreFrames;
    // Milisegundos, tiempo entre frames (1000/fps)

    // Medidas reales del Bitmap del sprite, el .png.
    private int spriteAncho;
    private int spriteAltura;

    // Medidas en pixeles del modelo que se representara en la pantalla del dispositivo
    private int modeloAncho;
    private int modeloAltura;


    public Sprite(Bitmap bitmap, int modeloAncho, int modeloAltura, int fps, int framesTotales, boolean bucle) {
        this.bitmap = bitmap;
        this.modeloAncho = modeloAncho;
        this.modeloAltura = modeloAltura;
        this.framesTotales = framesTotales;
        this.bucle = bucle;

        frameActual = 0;
        spriteAncho = bitmap.getWidth() / framesTotales;
        spriteAltura = bitmap.getHeight();
        rectanguloDibujo = new Rect(0, 0, spriteAncho, spriteAltura);
        interavaloEntreFrames = 1000 / fps;
        tiempoUltimaActualizacion = 0l;
    }


    public boolean actualizar(long tiempo) {

        boolean finSprite = false;


        if (tiempo > tiempoUltimaActualizacion + interavaloEntreFrames) {
            tiempoUltimaActualizacion = tiempo;
            // actualizar el frame
            frameActual++;
            if (frameActual >= framesTotales) {
                if (bucle) {
                    frameActual = 0;
                } else {
                    finSprite = true;
                }
            }
        }
        // definir el rectangulo
        this.rectanguloDibujo.left = frameActual * spriteAncho;
        this.rectanguloDibujo.right = this.rectanguloDibujo.left + spriteAncho;

        return finSprite;
    }


    public void dibujarSprite(Canvas canvas, int x, int y) {
        Rect destRect = new Rect(x - modeloAncho / 2, y - modeloAltura / 2, x
                + modeloAncho / 2, y + modeloAltura / 2);
        canvas.drawBitmap(bitmap, rectanguloDibujo, destRect, null);
    }


    public void setFrameActual(int frameActual) {
        this.frameActual = frameActual;
    }
}
