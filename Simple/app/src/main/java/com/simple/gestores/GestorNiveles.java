package com.simple.gestores;

import android.content.Context;

import com.simple.R;
import com.simple.modelos.Balas;
import com.simple.modelos.Corazon;
import com.simple.modelos.Enemigo;
import com.simple.modelos.EnemigoAvioneta;
import com.simple.modelos.EnemigoHelicoptero;
import com.simple.modelos.EnemigoJefe;
import com.simple.modelos.Moneda;
import com.simple.utilidades.Ar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by UO244588 on 09/10/2017.
 */

public class GestorNiveles {
    private static GestorNiveles instancia = null;
    private int nivelActual = 1;

    private GestorNiveles() {
    }

    public static GestorNiveles getInstancia() {
        synchronized (GestorNiveles.class) {
            if (instancia == null) {
                instancia = new GestorNiveles();
            }
        }
        return instancia;
    }


    public List<Enemigo> getEnemigosNivelActual(Context context) {
        List<Enemigo> enemigos = new LinkedList<Enemigo>();
        switch (nivelActual) {
            case 1:
                enemigos = cargarEnemigosXML(context, R.raw.level1);
                break;
            case 2:
                enemigos = cargarEnemigosXML(context, R.raw.level2);
                break;
            case 3:
                enemigos = cargarEnemigosXML(context,R.raw.level3);
                break;
        }
        return enemigos;
    }

    public List<Moneda> getMonedasNivelActual(Context context){
        List<Moneda> monedas = new LinkedList<Moneda>();
        switch (nivelActual) {
            case 1:
                monedas = cargarColleccionablesXML(context, R.raw.level1);
                break;
            case 2:
                ;
                break;
            case 3:

                break;
        }
        return monedas;
    }

    public List<Balas> getBalasNivelActual(Context context){
        List<Balas> balas = new LinkedList<Balas>();
        switch (nivelActual) {
            case 1:
                balas = cargarBalasXML(context, R.raw.level1);
                break;
            case 2:
                balas = cargarBalasXML(context, R.raw.level2);
                break;
            case 3:

                break;
        }
        return balas;
    }

    public List<Corazon> getCorazonesNivelActual(Context context){
        List<Corazon> corazones = new LinkedList<Corazon>();
        switch (nivelActual) {
            case 1:
                corazones = cargarCorazonesXML(context, R.raw.level1);
                break;
            case 2:
                corazones = cargarCorazonesXML(context, R.raw.level2);
                break;
            case 3:

                break;
        }
        return corazones;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }



    public LinkedList<Enemigo> cargarEnemigosXML(Context context,
                                                 int recursoNivel) {
        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);

        LinkedList<Enemigo> enemigos = new LinkedList<Enemigo>();
        NodeList nodos = doc.getElementsByTagName("enemyAvioneta");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            enemigos.add(new EnemigoAvioneta(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));

        }
        nodos = doc.getElementsByTagName("enemyHelicoptero");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            enemigos.add(new EnemigoHelicoptero(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));

        }
        nodos = doc.getElementsByTagName("enemyJefe");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            enemigos.add(new EnemigoJefe(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));
        }
        return enemigos;


    }




    public LinkedList<Moneda> cargarColleccionablesXML(Context context,
                                                       int recursoNivel){

        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);

        LinkedList<Moneda> monedas = new LinkedList<Moneda>();
        NodeList nodos = doc.getElementsByTagName("moneda");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            monedas.add(new Moneda(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));

        }
        return monedas;
    }

    public LinkedList<Balas> cargarBalasXML(Context context,
                                                       int recursoNivel){

        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);

        LinkedList<Balas> balas = new LinkedList<Balas>();
        NodeList nodos = doc.getElementsByTagName("bala");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            balas.add(new Balas(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));

        }
        return balas;
    }

    public LinkedList<Corazon> cargarCorazonesXML(Context context,
                                            int recursoNivel){

        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);

        LinkedList<Corazon> corazones = new LinkedList<Corazon>();
        NodeList nodos = doc.getElementsByTagName("corazon");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            corazones.add(new Corazon(context, Ar.x(Double.parseDouble(x)),
                    Ar.y(Double.parseDouble(y))));

        }
        return corazones;
    }












}
