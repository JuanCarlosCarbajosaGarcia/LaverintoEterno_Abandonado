import java.util.Random;
import java.util.Scanner;

public class Vendedor {
    private final Scanner oro = new Scanner(System.in);
    private final int nivelActual;
    Random num = new Random();
    int vid = num.nextInt(10) +1;
    boolean armadura = true;
    boolean arma = true;
    private final Scanner sc = new Scanner(System.in);

    public Vendedor(int nivel){
        this.nivelActual=nivel -1;
    }

    public boolean mostrarTienda(Jugador jugador){
        boolean salir=false;

        while(!salir){

            //menu dinamico
            System.out.println("\n=== ÁREA DE DESCANSO - NIVEL: " + nivelActual + " ===");
            System.out.println("Oro actual: " + jugador.getOro());
            System.out.println("Salud: " + jugador.getVida() + "/" + jugador.getVidaMax());
            System.out.println("daño: " + jugador.getDano());
            System.out.println("\n--- TIENDA ---");
            System.out.println("1. descansar (1 oro) - curacion completa");
            if (armadura) {
                System.out.println("2. armadura de " + (jugador.vidaMax + vid) + " (5 oro) - incrementa la vida maxima");
            }else {
                System.out.println("armaduras agotadas");
            }
            if (arma) {
                if (armadura) {
                    System.out.println("3. mejorar arma " + (jugador.dano + 5) + " (5 oro) - incrementa el daño");
                } else {
                    System.out.println("2. mejorar arma " + (jugador.dano + 5) + " (5 oro) - incrementa el daño");
                }
            }else {
                System.out.println("armas agotadas");
            }
            System.out.println("F. ir al siguiente nivel");
            System.out.println("Q. salir del juego");
            System.out.println("que quieres hacer: ");

            String opcion = oro.nextLine().toLowerCase();

            switch (opcion){
                case "1":
                    if (jugador.getOro() >= 1){
                        jugador.restarOro(jugador.getOro()-1);
                        jugador.curar();
                        System.out.println("te sientes revitalizado");
                    }else {
                        System.out.println("Oro insuficiente");
                    }
                    break;
                case "2":
                    if (armadura){
                        if (jugador.getOro() >= 5){
                            jugador.restarOro(jugador.getOro()-5);
                            jugador.aumentarVidaMax(vid);
                            armadura=false;
                            System.out.println("ahora puedes aguantar mas daño");
                        }else {
                            System.out.println("Oro insuficiente");
                        }
                    }else {
                        if (jugador.getOro() >= 5) {
                            jugador.restarOro(jugador.getOro()-5);
                            jugador.suvirDano(5);
                            arma = false;
                            System.out.println("tu arma ahora hace mas daño");
                        } else {
                            System.out.println("Oro insuficiente");
                        }
                    }if (!arma&&!armadura){
                        System.out.println("operacion invalida");
                    }
                    break;
                case "3":
                        if (arma && armadura) {
                            if ((jugador.getOro() >= 5) && (arma = true)) {
                                jugador.restarOro(jugador.getOro()-5);
                                jugador.suvirDano(5);
                                arma = false;
                                System.out.println("tu arma ahora hace mas daño");
                            } else {
                                System.out.println("Oro insuficiente");
                            }
                        }else {
                            System.out.println("operacion invalida");
                        }
                    break;
                case "f":
                    System.out.println("suerte en tu aventura");
                    salir=true;
                    break;

                case "p":
                    System.out.println("cantidad de oro:");
                    jugador.setOro(sc.nextInt());System.out.println("oro puesto a: " + jugador.getOro());
                    break;
                case "q":
                    System.out.println("volviendo al menu");
                    return true;

                default:
                    System.out.println("operacion invalida");
            }
        }
        return false;
    }
}