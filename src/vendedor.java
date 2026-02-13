public class vendedor extends entidad{
    public vendedor(String nombre, String Tipo, int vida, int dano) {
        super(nombre, Tipo, vida, dano);

        setNombre("vendedor");
        setTipo("vendedor");
        setVida(10);
        setDano(10);

        interface tienda{
            public void setTienda(vendedor v);
        }
    }
    @Override
    public void setTipo(String tipo) {
        super.setTipo(tipo);
    }
}