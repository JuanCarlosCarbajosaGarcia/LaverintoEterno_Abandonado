package main.java.org.juego;

public interface IInterfaz {

    //metodos
    void mostrarBienvenida();
    void mostrarMenu();
    void mostrarSalida();
    void mostrarControles();
    void mostrarMensaje(String mensaje);
    void mostrarError(String mensaje);
    void mostrarExito(String mensaje);
    void mostrarInfo(String mensaje);

    //metodos de inicio
    String pedirLinea(String mensaje);
    int pedirNumero(String mensaje);

    //metodos del juego
    void mostrarVida(int vida, int VidaMax);
    void mostrarInfo(int nivel,int vida,int vidaMax, int oro, int dano);

    void limpiarPantalla();
}
