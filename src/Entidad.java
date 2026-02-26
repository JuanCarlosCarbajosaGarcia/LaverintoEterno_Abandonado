public class Entidad implements IEntidad {

    protected String nombre;
    protected String tipo;
    protected int vida;
    protected int vidaMax;
    protected int dano;
    protected int oro;

    public Entidad(String nombre, String tipo, int vida, int dano) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vida = vida;
        this.vidaMax = vida;
        this.dano = dano;
        this.oro = 0;
    }

    //implementar interfaz
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getVida() {
        return vida;
    }

    @Override
    public void setVida(int vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMax));
    }

    @Override
    public int getVidaMax() {
        return vidaMax;
    }

    @Override
    public int getDano() {
        return dano;
    }

    @Override
    public int getOro() {
        return oro;
    }

    @Override
    public void setOro(int oro) {
        this.oro = Math.max(0, oro);
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }

    @Override
    public void curar() {
        this.vida = vidaMax;
    }

    @Override
    public void aumentarVidaMax(int cantidad) {
        this.vidaMax += cantidad;
        this.vida += cantidad;
    }

    @Override
    public void subirDano(int cantidad) {
        this.dano += cantidad;
    }

    @Override
    public void restarOro(int cantidad) {
        this.oro = this.oro - cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Vida: %d/%d, Daño: %d, Oro: %d",
                nombre, tipo, vida, vidaMax, dano, oro);
    }
}
