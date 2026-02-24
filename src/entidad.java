public class entidad{
    protected String nombre;
    protected String Tipo;
    protected int vida;
    protected int dano;

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

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public entidad(String nombre, String Tipo, int vida, int dano) {
        this.nombre = nombre;
        this.Tipo = Tipo;
        this.vida = vida;
        this.dano = dano;
    }
}