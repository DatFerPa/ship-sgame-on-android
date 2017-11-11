package com.simple.modelos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.graphics.Canvas;

import com.simple.R;
import com.simple.global.Estados;
import com.simple.graficos.Sprite;
import com.simple.modelos.control.Barra;
import com.simple.modelos.control.BarraEnemigos;
import com.simple.utilidades.Ar;

import java.util.HashMap;

/**
 * Created by UO244588 on 25/09/2017.
 */


public abstract class Enemigo extends Modelo {

    public static final String BASICO = "Basico";
    public static final String EXPLOTAR = "Explotar";
    protected Sprite sprite;//puntero para la hashmap
    protected HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();
    protected int cadenciaDisparo ;
    protected long milisegundosDisparo = 0;
    protected float aceleracionY ;
    protected float aceleracionX ;
    public int estado = Estados.ACTIVO;
    protected int vidasMaximas;
    protected int vidasActual;
    public BarraEnemigos barraVida;

    public Enemigo(Context context, double x, double y) {
        super(context, x, y);
    }
    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        // Solamente se pinta si esta activo
        if (estado != Estados.INACTIVO) {
            sprite.dibujarSprite(canvas, (int) x, (int) y);
            barraVida.actualizarPosiciones(x,y);
            barraVida.dibujarEnPantalla(canvas);
        }
    }

    public void moverAutomaticamente() {
        // El enemigo se mueve derecha o izquierda,
        // si se sale de la pantalla cambia de dirección
        Log.v("Enemigo", "La x es :" + x);

        boolean finalizarSprite = sprite.actualizar(System.currentTimeMillis());
        if (finalizarSprite && sprite == sprites.get(EXPLOTAR)){
            estado = Estados.INACTIVO;
        }
        if (estado == Estados.ACTIVO) {
            if (x + ancho / 2 >= mCanvasAncho) {
                aceleracionX =(float) Ar.x (aceleracionTrasChoque()* -1);
            }
            if (x - ancho / 2 <= 0) {
                aceleracionX = (float) Ar.x((aceleracionTrasChoque()));
            }
            x = x + aceleracionX;
            y = y + aceleracionY;
        }
    }

    abstract double aceleracionTrasChoque();

    public void destruir()
    {
        estado = Estados.EXPLOTANDO;
        sprite = sprites.get(EXPLOTAR);
    }
    public void quitarVida(){
        vidasActual--;
        barraVida.setValorActual(vidasActual);
    }
    public int getVidaActual(){
        return vidasActual;
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

    public DisparoAbstracto disparar(long milisegundos){
        if (milisegundos - milisegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {

            milisegundosDisparo = milisegundos;
            return getTipoDisparo();
        }
        return null;
    }

    abstract DisparoAbstracto getTipoDisparo();

    public int getEstado() {
        return estado;
    }
}