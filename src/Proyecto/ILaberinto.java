package Proyecto;

public interface ILaberinto {
    void generarMapa();
    boolean esPosible();
    void mostrarMapa();
    char[][] getMapa();
    int[] getEntrada();
    int[] getSalida();
}
