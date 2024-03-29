package com.simple.utilidades;

/**
 * Created by UO244588 on 02/10/2017.
 */

public class Ar {
    //Adapator de resolución
    public static double pantallaAltura;
    public static double pantallaAncho;
    private static double factorX;
    private static double factorY;


    public static void configurar(int ancho, int altura) {
        pantallaAltura = altura;
        pantallaAncho = ancho;
        factorY = pantallaAltura / 480d;
        factorX = pantallaAncho / 320d;
    }


    public static double y(double y) {
        return (y * factorY);
    }

    public static double x(double x) {
        return (x * factorX);
    }

    public static int altura(int altura) {

        if (factorX < factorY) {
            return (int) (altura * factorX);
        } else {
            return (int) (altura * factorY);
        }
    }

    public static int ancho(int ancho) {

        if (factorY < factorX) {
            return (int) (ancho * factorY);
        } else {
            return (int) (ancho * factorX);


        }
    }


}
