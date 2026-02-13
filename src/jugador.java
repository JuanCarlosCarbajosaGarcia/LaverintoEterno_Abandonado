import java.util.Scanner;

public class jugador extends entidad {
    Scanner sc = new Scanner(System.in);
    public jugador(String nombre, String Tipo, int vida, int dano) {
        super(nombre, Tipo, vida, dano);


        System.out.println("Ingrese el nombre del jugador: ");
        setNombre(sc.next());

        setTipo("jugador");
        setDano(1);
        setVida(10);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}