import java.util.Random;
import java.util.Scanner;

public class Vendedor {
    private Scanner oro = new Scanner(System.in);
    private int nivelActual;
    Random num = new Random();
    int vid = num.nextInt(5) +1;

    public Vendedor(int nivel){
        this.nivelActual=nivel;
    }

    public void mostrarTienda(Jugador jugador){
        boolean salir=false;

        while(!salir){
            System.out.println("\n=== ÁREA DE DESCANSO - NIVEL: " + nivelActual + " ===");
            System.out.println("Oro actual: " + jugador.getOro());
            System.out.println("Salud: " + jugador.getVida() + "/" + jugador.getVidaMax());
            System.out.println("daño: " + jugador.getDano());
            System.out.println("\n--- TIENDA ---");
            System.out.println("1. descansar (1 oro) - curacion completa");
            System.out.println("2. armadura de " + (jugador.vidaMax + vid) + " (5 oro) - incrementa la vida maxima");
            System.out.println("3. mejorar arma " + (jugador.dano + 5) + " (5 oro) - incrementa el daño");
            System.out.println("F. ir al siguiente nivel");
            System.out.println("Q. salir del juego");
            System.out.println("que quieres hacer: ");

            String opcion = oro.nextLine();

            switch (opcion){
                case "1":
                    if (jugador.getOro() >= 1){
                        jugador.restarOro(1);
                        jugador.curar();
                        System.out.println("te sientes revitalizado");
                    }else {
                        System.out.println("Oro insuficiente");
                    }
                    break;
                case "2":
                    if (jugador.getOro() >= 5){
                        jugador.restarOro(5);
                        jugador.aumentarVidaMax(vid);
                        System.out.println("ahora puedes aguantar mas daño");
                    }else {
                        System.out.println("Oro insuficiente");
                    }
                    break;
                case "3":
                    if (jugador.getOro() >= 5){
                        jugador.restarOro(5);
                        jugador.suvirDano(5);
                        System.out.println("tu arma ahora hace mas daño");
                    }else {
                        System.out.println("Oro insuficiente");
                    }
                    break;
                case "f":
                    salir=true;
                    System.out.println("suerte en tu aventura");
                    break;
                case "p": jugador.setOro(100);
                    break;
                case "q":
                    System.out.println("¡gracias por jugar!");
                    System.exit(0);
                default:
                    System.out.println("operacion invalida");
            }
        }
    }
}