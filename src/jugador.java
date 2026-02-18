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

    public void mover(){
        System.out.println("movimiento (w/a/s/d) para moverte arriva/izquierda/abajo/derecha, q para salir: ");
        String input = scanner.nexLine().toLowerCase();
        int newX = jugadorX, newY = jugadorY;
        switch (input){
            case "w": newY--;
                break; //arriva
            case "s": newy++;
                break; //abajo
            case "a": newX--;
                break; //izquierda
            case "d": newX++;
                break; //derecha
            case "q": System.exit(0);
                break; //salir
            default: System.out.println("movimiento invalido");
                return;
        }
        if (newX >= 0 && newX < Tamanoy && newY >= 0 && newY < Tamanox && mapa[newY][newX] != '#') {
            jugadorX = newX;
            jugadorY = newY;
        }else {
            System.out.println("eso es una pared");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}