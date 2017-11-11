package com.simple.modelos;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.simple.R;
import com.simple.graficos.Sprite;
import com.simple.modelos.control.BarraEnemigos;
import com.simple.utilidades.Ar;

/**
 * Created by Fernando on 15/10/2017.
 */



public class EnemigoAvioneta extends Enemigo {
    public EnemigoAvioneta(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(50);
        ancho = Ar.ancho(50);

        Sprite basico = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_enemigo), ancho,
                altura, 4, 4,true);
        sprites.put(BASICO, basico);

        Sprite explotar = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_enemigo_explotar), ancho,
                altura, 6, 6,false);
        sprites.put(EXPLOTAR, explotar);

        sprite = basico;

        aceleracionX = (float) Ar.x(3);
        aceleracionY =(float) Ar.y(1);

        cadenciaDisparo =3000;

        vidasMaximas = 2;
        vidasActual = vidasMaximas;
        barraVida = new BarraEnemigos(context,(x-ancho/2),(y-altura/2)-5,vidasMaximas,vidasActual,ancho,altura);
    }

    @Override
    double aceleracionTrasChoque() {
        return  0.5 + Math.random() * 2.5 ;
    }

    @Override
    DisparoAbstracto getTipoDisparo() {
        return  new DisparoEnemigo(context,x,y);
    }
}
