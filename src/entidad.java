public class entidad{
    protected String nombre;
    protected String Tipo;
    protected int vida;
    protected int vidaMax;
    protected int dano;
    protected int oro;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public void restarOro(int cantidad){
        this.oro = cantidad;
    }

    public void curar(){
        this.vida = vidaMax;
    }

    public void aumentarVidaMax(int cantidad){
        this.vidaMax += cantidad;
        this.vida += cantidad;
    }

    public void suvirDano(int cantidad){
        this.dano += cantidad;
    }

    public entidad(String nombre, String Tipo, int vida, int dano) {
        this.nombre = nombre;
        this.Tipo = Tipo;
        this.vida = vida;
        this.vidaMax = vida;
        this.dano = dano;
        this.oro = 0;
    }

    public boolean estaVivo(){
        return vida > 0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Vida: %d%d, Daño: %d, Oro: %d", nombre, Tipo, vida, vidaMax, dano, oro);
    }
}