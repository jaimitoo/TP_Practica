package logica.reglas;

import logica.Constantes;
import logica.Ficha;
import logica.tablero.Tablero;

public class ReglasJuegoReversi extends ReglasJuego {

    @Override
    public Tablero iniciaTablero() {

        int filaAux = (Constantes.ALTORE / 2) - 1;
        int columnaAux = (Constantes.ANCHORE / 2) - 1;

        Tablero tab = new Tablero(Constantes.ALTORE, Constantes.ANCHORE);
        tab.ponerFicha(filaAux, columnaAux, Ficha.NEGRAS);
        tab.ponerFicha(filaAux, columnaAux + 1, Ficha.BLANCAS);
        tab.ponerFicha(filaAux + 1, columnaAux, Ficha.BLANCAS);
        tab.ponerFicha(filaAux + 1, columnaAux + 1, Ficha.NEGRAS);

        return tab;
    }

    @Override
    public Ficha hayGanador(int f, int c, Ficha ganador, Tablero tablero) {

        int fichasBlancas = 0, fichasNegras = 0, fichasVacias = 0;

        for (int fila = 0; fila < tablero.getAlto(); fila++) {
            for (int columna = 0; columna < tablero.getAncho(); columna++) {

                if (tablero.getFicha(fila, columna) == Ficha.BLANCAS) {
                    fichasBlancas++;
                } else if (tablero.getFicha(fila, columna) == Ficha.NEGRAS) {
                    fichasNegras++;
                } else {
                    fichasVacias++;
                }
            }
        }

        //Si un juguador se queda sin fichas gana el jugador contrario.
        if (fichasBlancas == 0) {
            return Ficha.NEGRAS;
        } else if (fichasNegras == 0) {
            return Ficha.BLANCAS;
        } else if (fichasVacias > 0 || (fichasVacias == 0 && fichasBlancas == fichasNegras)) {
            return Ficha.VACIA;
        } else {
            return (fichasBlancas > fichasNegras) ? Ficha.BLANCAS : Ficha.NEGRAS;
        }

    }

    @Override
    public boolean tablas(Tablero t) {
        return t.isCompleto();
    }

    @Override
    public Ficha jugadorInicial() {
        return Ficha.NEGRAS;
    }

    @Override
    public boolean esPosibleMover(Tablero t, Ficha turno) {
        
        for(int fila = 0; fila < t.getAlto(); fila++){
            for(int columna = 0; columna < t.getAncho(); columna++){
                
                if(t.getFicha(fila, columna) == Ficha.VACIA && posicionValida(t, fila, columna, turno)){
                    return true;
                }
                
            }
        }
        
        return false;
    }

    public static boolean posicionValida(Tablero t, int fila, int columna, Ficha turno) {

        return validaPorDebajo(t, fila, columna, turno)
                || validaPorEncima(t, fila, columna, turno)
                || validaPorDerecha(t, fila, columna, turno)
                || validaPorIzquierda(t, fila, columna, turno)
                || validaDiagonalSupDer(t, fila, columna, turno)
                || validaDiagonalInfDer(t, fila, columna, turno)
                || validaDiagonalSupIzq(t, fila, columna, turno)
                || validaDiagonalInfIzq(t, fila, columna, turno);
    }

    public static boolean validaPorEncima(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones por encima.
        if (fila <= t.getAlto() - 3) {

            int f = fila + 1;
            int numFichas = 0;

            while ((f < t.getAlto() - 1)
                    && (t.getFicha(f, columna) != Ficha.VACIA) && (t.getFicha(f, columna) != turno)) {
                numFichas++;
                f++;
            }
            if (numFichas != 0 && t.getFicha(f, columna) == turno) {
                return true;
            }
        }

        return false;
    }

    public static boolean validaPorDebajo(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones por debajo.
        if (fila >= 2) {

            int f = fila - 1;
            int numFichas = 0;

            while ((f > 0)
                    && (t.getFicha(f, columna) != Ficha.VACIA) && (t.getFicha(f, columna) != turno)) {
                numFichas++;
                f--;
            }
            if (numFichas != 0 && t.getFicha(f, columna) == turno) {
                return true;

            }
        }
        return false;
    }

    public static boolean validaPorDerecha(Tablero t, int fila, int columna, Ficha turno) {

        boolean valido = false;

        //Comprueba todas las posiciones por la derecha.
        if (columna <= t.getAncho() - 3) {

            int c = columna + 1;
            int numFichas = 0;

            while ((c < t.getAncho() - 1)
                    && (t.getFicha(fila, c) != Ficha.VACIA) && (t.getFicha(fila, c) != turno)) {
                numFichas++;
                c++;
            }
            if (numFichas != 0 && t.getFicha(fila, c) == turno) {
                return true;
            }
        }
        return false;
    }

    public static boolean validaPorIzquierda(Tablero t, int fila, int columna, Ficha turno) {

        boolean valido = false;

        //Comprueba todas las posiciones por la izquierda.
        if (columna >= 2) {

            int c = columna - 1;
            int numFichas = 0;

            while ((c > 0)
                    && (t.getFicha(fila, c) != Ficha.VACIA) && (t.getFicha(fila, c) != turno)) {
                numFichas++;
                c--;
            }
            if (numFichas > 0 && t.getFicha(fila, c) == turno) {
                return true;
            }
        }
        return false;
    }

    public static boolean validaDiagonalSupDer(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones en la diagonal superior derecha.
        if (fila <= t.getAlto() - 3 && columna <= t.getAncho() - 3) {

            int f = fila + 1;
            int c = columna + 1;
            int numFichas = 0;

            while ((f < t.getAlto() - 1) && (c < t.getAncho() - 1)
                    && (t.getFicha(f, c) != Ficha.VACIA) && (t.getFicha(f, c) != turno)) {
                numFichas++;
                f++;
                c++;
            }
            if (numFichas != 0 && t.getFicha(f, c) == turno) {
                return true;

            }
        }
        return false;
    }

    public static boolean validaDiagonalSupIzq(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones en la diagonal superior izquierda.
        if (fila <= t.getAlto() - 3 && columna >= 2) {

            int f = fila + 1;
            int c = columna - 1;
            int numFichas = 0;

            while ((f < t.getAlto() - 1) && (c > 0)
                    && (t.getFicha(f, c) != Ficha.VACIA) && (t.getFicha(f, c) != turno)) {
                numFichas++;
                f++;
                c--;
            }
            if (numFichas != 0 && t.getFicha(f, c) == turno) {
                return true;
            }
        }
        return false;
    }

    public static boolean validaDiagonalInfDer(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones en la diagonal inferior derecha.
        if (fila >= 2 && columna <= t.getAncho() - 3) {

            int f = fila - 1;
            int c = columna + 1;
            int numFichas = 0;

            while ((f > 0) && (c < t.getAncho() - 1)
                    && (t.getFicha(f, c) != Ficha.VACIA) && (t.getFicha(f, c) != turno)) {
                numFichas++;
                f--;
                c++;
            }
            if (numFichas != 0 && t.getFicha(f, c) == turno) {
                return true;
            }
        }
        return false;
    }

    public static boolean validaDiagonalInfIzq(Tablero t, int fila, int columna, Ficha turno) {

        //Comprueba todas las posiciones en la diagonal inferior izquierda.
        if (fila >= 2 && columna >= 2) {

            int f = fila - 1;
            int c = columna - 1;
            int numFichas = 0;

            while ((f > 0) && (c > 0)
                    && (t.getFicha(f, c) != Ficha.VACIA) && (t.getFicha(f, c) != turno)) {
                numFichas++;
                f--;
                c--;
            }
            if (numFichas != 0 && t.getFicha(f, c) == turno) {
                return true;
            }
        }
        return false;
    }

}
