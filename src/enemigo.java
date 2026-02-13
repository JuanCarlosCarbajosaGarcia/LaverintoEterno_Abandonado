public class enemigo extends entidad{
    public enemigo(String nombre, String Tipo, int vida, int dano) {
        super(nombre, Tipo, vida, dano);

        setNombre("minotauro");
        setTipo("enemigo");
        setDano(10);
        setVida(1000);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}