package com.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.simple.gestores.GestorAudio;
import com.simple.gestores.GestorNiveles;
import com.simple.global.Estados;
import com.simple.modelos.Balas;
import com.simple.modelos.Corazon;
import com.simple.modelos.DisparoAbstracto;
import com.simple.modelos.DisparoEnemigo;
import com.simple.modelos.DisparoNave;
import com.simple.modelos.Enemigo;
import com.simple.modelos.EnemigoAvioneta;
import com.simple.modelos.Fondo;
import com.simple.modelos.Moneda;
import com.simple.modelos.Nave;
import com.simple.modelos.control.Barra;
import com.simple.modelos.control.BotonDisparar;
import com.simple.modelos.control.BotonPausa;
import com.simple.modelos.control.Marcador;
import com.simple.modelos.control.MensajePausa;
import com.simple.modelos.control.Pad;
import com.simple.utilidades.Ar;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by UO244588 on 25/09/2017.
 */

public class GameView extends View {


    //Variables globales con uso de logica
    public static boolean pausa = false;

    //Android
    Context context;
    GameLoop gameLoop;
    private boolean finjuego = false;
    private GestorAudio gestorAudio;

    //Juego
    private Nave nave;
    private List<Enemigo> enemigos;
    private Fondo fondo;
    private List<DisparoNave> disparosNave;
    private List<DisparoAbstracto> disparosEnemigo;
    private List<Moneda> monedas;
    private List<Corazon> corazones;
    private List<Balas> balas;

    //controles
    private Pad pad;
    private BotonPausa botonPausa;
    private BotonDisparar botonDisparar;
    private Marcador marcador;
    private Barra barraVidas;
    private MensajePausa mensajePausa;



    public GameView(Context context) {
        super(context);
        setFocusable(true);
        fondo = new Fondo(context, Ar.x(160), Ar.y(240));
        inicializarGestorAudio(context);
        inicializar(context);
        this.context = context;
    }

    private void inicializar(Context context) {
        pausa = false;
        nave = new Nave(context, Ar.x(160), Ar.y(400));

        enemigos = new LinkedList<Enemigo>();

        enemigos = GestorNiveles.getInstancia().getEnemigosNivelActual( context);

        pad = new Pad(context, Ar.x(70), Ar.y(410));
        botonDisparar = new BotonDisparar(context, Ar.x(250), Ar.y(410));
        botonPausa = new BotonPausa(context,Ar.x(300),Ar.y(50));
        mensajePausa = new MensajePausa(context,Ar.x(320/2),Ar.y(480/2));
        marcador = new Marcador(context, Ar.x(30), Ar.y(30));
        disparosNave = new LinkedList<DisparoNave>();
        disparosEnemigo = new LinkedList<DisparoAbstracto>();
        monedas = GestorNiveles.getInstancia().getMonedasNivelActual(context);
        barraVidas = new Barra(context,Ar.x(80),Ar.y(25),5,5);

        corazones = GestorNiveles.getInstancia().getCorazonesNivelActual(context);
        balas = GestorNiveles.getInstancia().getBalasNivelActual(context);


        /////
        gameLoop = new GameLoop();
        gameLoop.start();
    }


    public void inicializarGestorAudio(Context context) {
        gestorAudio = GestorAudio.getInstancia(context, R.raw.musica_fondo);
        gestorAudio.reproducirMusicaAmbiente();
        gestorAudio.registrarSonido(GestorAudio.SONIDO_DISPARO_NAVE,
                R.raw.nave_disparo);
        gestorAudio.registrarSonido(GestorAudio.SONIDO_DISPARO_ENEMIGO,
                R.raw.enemigo_disparo);
        gestorAudio.registrarSonido(GestorAudio.SONIDO_EXPLOSION_AVION,
                R.raw.explosion_avion);
        gestorAudio.registrarSonido(GestorAudio.SONIDO_NAVE_HIT,
                R.raw.nave_hit_2);
    }



    @Override
    protected void onDraw(Canvas canvas) {

        try {
            // Dibujar elementos gráficos
            fondo.dibujarEnPantalla(canvas);
            for (DisparoNave disparoNave : disparosNave) {
                disparoNave.dibujarEnPantalla(canvas);
            }
            nave.dibujarEnPantalla(canvas);


            for (DisparoAbstracto disparoEnemigo : disparosEnemigo) {
                if (disparoEnemigo.estaEnPantalla() == 1) {
                    disparoEnemigo.dibujarEnPantalla(canvas);
                }
            }

            for(Moneda moneda:monedas){
                if(moneda.estaEnPantalla()==1){
                    moneda.dibujarEnPantalla(canvas);
                }
            }


            for (Enemigo enemigo : enemigos) {
                if (enemigo.estaEnPantalla() == 1) {
                    enemigo.dibujarEnPantalla(canvas);
                }
            }

            for(Balas bala:balas){
                if(bala.estaEnPantalla() == 1){
                    bala.dibujarEnPantalla(canvas);
                }
            }

            for(Corazon corazon:corazones){
                if(corazon.estaEnPantalla()==1){
                    corazon.dibujarEnPantalla(canvas);
                }
            }


            if (pausa) {
                mensajePausa.dibujarEnPantalla(canvas);
            }

            marcador.dibujarEnPantalla(canvas);
            pad.dibujarEnPantalla(canvas);
            botonDisparar.dibujarEnPantalla(canvas);
            barraVidas.dibujarEnPantalla(canvas);
            botonPausa.dibujarEnPantalla(canvas);
        } catch (Exception ex) {

        }
    }


    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() &
                MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;


    }


    private void procesarEventosTouch() {

        boolean pulsacionPadMover = false;

        for (int i = 0; i < 6; i++) {

            if (accion[i] == ACTION_DOWN && finjuego){
                finjuego = false;
                inicializar(context);
            }
            if (accion[i] == ACTION_DOWN && pausa == true) {
                pausa = false;
                gameLoop = new GameLoop();
                gameLoop.start();
            }

            if (accion[i] != NO_ACTION) {

                if (pad.estaPulsado(x[i], y[i])) {

                    float orientacionX = pad.getOrientacionX((int) x[i]);
                    float orientacionY = pad.getOrientacionY((int) y[i]);

                    if (accion[i] == ACTION_DOWN
                            || accion[i] == ACTION_MOVE) {
                        nave.acelerar(orientacionX,orientacionY);
                        pulsacionPadMover = true;
                    }

                }

                if (botonDisparar.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        // Disparar
                        nave.setDisparando(true);
                    }
                }
                if (botonPausa.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        pausa = true;
                    }
                }
            }
        }
        if (!pulsacionPadMover) {
            nave.frenar();
        }

    }


    private class GameLoop extends Thread {



        public void run() {
            try {
                while (!finjuego && !pausa) {
                    // 1000 / 25 = 50fps aproximadamente.
                    TimeUnit.MILLISECONDS.sleep(25);

                    gl_moverElementos();
                    gl_comprobarColisiones();
                    gl_comprobarDisparos();
                    gl_comprobarNivelFinalizado();
                    // Re-dibujar onDraw(Canvas canvas)
                    postInvalidate();
                }
            } catch (InterruptedException ex) {

            }
        }


        private void gl_comprobarColisiones() {

            Enemigo enemigoSacarLista = null;
            DisparoNave disparoNaveSacarLista = null;
            DisparoAbstracto disparoEnemigoSacarLista = null;
            Moneda monedaSacarLista = null;
            Balas balaSacarLista = null;
            Corazon corazonSacarLista = null;
            for (Enemigo enemigo : enemigos) {

                if (enemigo.estaEnPantalla() == -1 || enemigo.getEstado() == Estados.INACTIVO) {
                    enemigoSacarLista = enemigo;
                }


                for (DisparoNave disparoNave : disparosNave) {

                    if (disparoNave.estaEnPantalla() != 1) {
                        disparoNaveSacarLista = disparoNave;
                    }


                    if (enemigo.colisiona(disparoNave) && enemigo.estaEnPantalla() == 1
                            && enemigo.getEstado() == Estados.ACTIVO) {
                        disparoNaveSacarLista = disparoNave;
                        enemigo.quitarVida();
                        if(enemigo.getVidaActual()==0) {
                            gestorAudio.reproducirSonido(gestorAudio.SONIDO_EXPLOSION_AVION);
                            enemigo.destruir();


                            marcador.setPuntos(marcador.getPuntos() + 1);
                        }
                    }

                }
            }

            if (enemigoSacarLista != null) {
                enemigos.remove(enemigoSacarLista);
                //para nivel con infinitos enemigos
               // gl_generaNuevosEnemigos();
            }
            if (disparoNaveSacarLista != null) {
                disparosNave.remove(disparoNaveSacarLista);
            }


            for (DisparoAbstracto disparoEnemigo : disparosEnemigo) {


                if (disparoEnemigo.colisiona(nave)) {
                    gestorAudio.reproducirSonido(gestorAudio.SONIDO_NAVE_HIT);
                    if (barraVidas.getValorActual() > 1) {
                        nave.impactado();
                        disparoEnemigoSacarLista = disparoEnemigo;
                        barraVidas.setValorActual(barraVidas.getValorActual() - 1);
                    } else {
                        nave.impactado();
                        finjuego = true;
                    }



                }


                if (disparoEnemigo.estaEnPantalla() == -1) {
                    disparoEnemigoSacarLista = disparoEnemigo;
                }
            }


            for(Moneda moneda:monedas){
                if(moneda.colisiona(nave)){

                    //hay que sacar la monenda

                    monedaSacarLista = moneda;
                    marcador.setPuntos(marcador.getPuntos() + 10);
                }

            }
            if(monedaSacarLista != null){
                monedas.remove(monedaSacarLista);
            }

            for(Balas bala:balas){
                if(bala.colisiona(nave)){
                    balaSacarLista = bala;

                    nave.reducirTiempoDisparo();

                }
            }
            if (balaSacarLista != null) {
                balas.remove(balaSacarLista);
            }

            for(Corazon corazon:corazones){
                if(corazon.colisiona(nave)){
                    corazonSacarLista = corazon;

                    barraVidas.setValorActual(barraVidas.getValorActual()+1);
                }
            }

            if (corazonSacarLista != null) {
                corazones.remove(corazonSacarLista);
            }


            if (disparoEnemigoSacarLista != null) {
                disparosEnemigo.remove(disparoEnemigoSacarLista);
            }

        }

        private void gl_moverElementos() {

            for (Enemigo enemigo : enemigos) {
                enemigo.moverAutomaticamente();
            }

            for (DisparoAbstracto disparoEnemigo : disparosEnemigo) {
                disparoEnemigo.moverAutomaticamente();
            }

            for (DisparoNave disparoNave : disparosNave) {
                disparoNave.moverAutomaticamente();
            }
            for(Moneda moneda:monedas){
                moneda.moverAutomaticamente();
            }
            for(Balas bala:balas){
                bala.moverAutomaticamente();
            }
            for(Corazon corazon:corazones){
                corazon.moverAutomaticamente();
            }

            fondo.mover();
            nave.mover();
        }

        private void gl_comprobarDisparos() {
            nave.sumarTiempo();
            DisparoNave disparoNave = nave.disparar();
            if (disparoNave != null) {
                disparosNave.add(disparoNave);
                gestorAudio.reproducirSonido(gestorAudio.SONIDO_DISPARO_NAVE);
            }


            long tiempo = System.currentTimeMillis();
            for (Enemigo enemigo : enemigos) {
                if (enemigo.estaEnPantalla() == 1 &&
                        enemigo.getEstado() == Estados.ACTIVO) {

                    DisparoAbstracto disparo = enemigo.disparar(tiempo);
                    if (disparo != null) {
                        disparosEnemigo.add(disparo);
                        gestorAudio.reproducirSonido(gestorAudio.SONIDO_DISPARO_ENEMIGO);
                    }
                }
            }



        }


        private void gl_generaNuevosEnemigos() {
            if (enemigos.size() < 2) {

                int nuevosEnemigos = new Random().nextInt(2) + 1;

                for (int i = 0; i < nuevosEnemigos; i++) {

                    enemigos.add(new EnemigoAvioneta(context, new Random().nextInt(320),
                            (50 + new Random().nextInt(100) * -1)));
                }
            }
        }

        private void gl_comprobarNivelFinalizado() {
            if (enemigos.size() == 0) {
                finjuego = true;
                ((MainActivity)context).finish();
            }
        }



    }//fin clase privada cameloop


}
