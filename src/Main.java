public class Main {
    public static void main(String[] args) {
        Movimiento movimiento = new Movimiento();

        Ataque ataque = new Ataque();

        Controlador controlador = new Controlador(movimiento, ataque);
    }
}