public interface IEntidad {

    String getNombre();
    int getVida();
    void setVida(int vida);
    int getVidaMax();
    int getDano();
    int getOro();
    void setOro(int oro);
    boolean estaVivo();
    void curar();
    void aumentarVidaMax(int cantidad);
    void subirDano(int cantidad);
    void restarOro(int cantidad);
}
