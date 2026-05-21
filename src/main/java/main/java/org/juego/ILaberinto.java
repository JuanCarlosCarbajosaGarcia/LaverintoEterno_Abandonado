package main.java.org.juego;

public interface ILaberinto {
    void generarMapa();
    boolean esPosible();
    static void mostrarMapa() {}
    char[][] getMapa();
    int[] getEntrada();
    int[] getSalida();
}
