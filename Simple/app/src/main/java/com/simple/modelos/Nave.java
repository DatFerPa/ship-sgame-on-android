package com.simple.modelos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.simple.R;
import com.simple.graficos.Sprite;
import com.simple.utilidades.Ar;

import java.util.HashMap;

/**
 * Created by UO244588 on 25/09/2017.
 */


public class Nave extends Modelo {

    public static final String BASICO = "Basico";
    public static final String IMPACTADO = "Explotar";
    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();

    public static int MAXIMA_VEL_DISPARO = 10;


    private int vel_disparo= 50;
    private int tiempoActualDisparo =50;


    private double aceleracionX;
    private double aceleracionY;

    protected boolean disparando = false;

    public Nave(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(63);
        ancho = Ar.ancho(50);


        Sprite basico = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_nave),
                ancho, altura,
                4, 4, true);
        sprites.put(BASICO, basico);

        Sprite impactado = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_nave_explota),
                ancho, altura,
                4, 12, false);
        sprites.put(IMPACTADO, impactado);

        sprite = basico;

    }

    public void acelerar(float factorMovimientoX,float factorMovimientoY ) {
        if (factorMovimientoX < 0) {
            aceleracionX = 4;
        }
        if (factorMovimientoX >= 0) {
            aceleracionX = -4;
        }
        if(factorMovimientoY > 0){
            aceleracionY = -4;
        }
        if(factorMovimientoY <= 0){
            aceleracionY = 4;
        }
    }


    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x, (int) y);
    }


    public void impactado(){
        sprite = sprites.get(IMPACTADO);
        sprite.setFrameActual(0); // Reiniciar el Sprite
    }


    public void mover() {

        boolean finalizaSprite =
                sprite.actualizar(System.currentTimeMillis());

        if (sprite == sprites.get(IMPACTADO) && finalizaSprite) {
            sprite = sprites.get(BASICO);
        }



        x = x + aceleracionX;
        y = y + aceleracionY;

        if (x < ancho / 2) {
            x = ancho / 2;
        }
        if (x > mCanvasAncho - ancho / 2) {
            x = mCanvasAncho - ancho / 2;
        }
        if(y > mCanvasAltura -altura/2){
            y=mCanvasAltura-altura/2;
        }

        if(y < (mCanvasAltura*0.6)+altura/2){
            y = (mCanvasAltura*0.6)+altura/2;
        }

    }
    public void frenar() {
        aceleracionX = 0;
        aceleracionY = 0;
    }

    public void reducirTiempoDisparo(){
        if(vel_disparo>MAXIMA_VEL_DISPARO){
            vel_disparo = vel_disparo - 10;
        }else{
            vel_disparo = MAXIMA_VEL_DISPARO;
        }
    }




    public void sumarTiempo(){
        ++tiempoActualDisparo;
    }


    public DisparoNave disparar() {
        if (disparando && tiempoActualDisparo >= vel_disparo) {
            disparando = false;
            tiempoActualDisparo = 0;
            return new DisparoNave(context, x, y);

        }
        return null;

    }

    public boolean isDisparando() {
        return disparando;
    }

    public void setDisparando(boolean disparando) {
        this.disparando = disparando;
    }
}



