package Proyecto;

import java.util.Random;
import java.util.Scanner;

public class Vendedor implements IVendedor{
    private final IInterfaz interfaz = new InterfazConsola();

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

    @Override
    public boolean mostrarTienda(IEntidad jugador){
        boolean salir=false;

        while(!salir){

            //menu dinamico
            interfaz.mostrarMensaje("\n=== ÁREA DE DESCANSO - NIVEL: " + nivelActual + " ===");
            interfaz.mostrarMensaje("Oro actual: " + jugador.getOro());
            interfaz.mostrarMensaje("Salud: " + jugador.getVida() + "/" + jugador.getVidaMax());
            interfaz.mostrarMensaje("daño: " + jugador.getDano());
            interfaz.mostrarMensaje("\n--- TIENDA ---");
            interfaz.mostrarMensaje("1. descansar (1 oro) - curacion completa");
            if (armadura) {
                interfaz.mostrarMensaje("2. armadura + " + vid + " vida maxima (5 oro)");
            }else {
                interfaz.mostrarMensaje("[AGOTADO] armadura ");
            }
            if (arma) {
                if (armadura) {
                    interfaz.mostrarMensaje("3. mejorar arma +5 de daño (5 oro)");
                } else {
                    interfaz.mostrarMensaje("2. mejorar arma +5 de daño (5 oro)");
                }
            }else {
                interfaz.mostrarMensaje("[AGOTADO] armas");
            }
            interfaz.mostrarMensaje("F. ir al siguiente nivel");
            interfaz.mostrarMensaje("Q. salir del juego");
            interfaz.mostrarMensaje("que quieres hacer: ");

            String opcion = oro.nextLine().toLowerCase();

            switch (opcion){
                case "1":
                    if (jugador.getOro() >= 1){
                        jugador.restarOro(jugador.getOro()-1);
                        jugador.curar();
                        interfaz.mostrarExito("te sientes revitalizado");
                    }else {
                        interfaz.mostrarError("Oro insuficiente");
                    }
                    break;
                case "2":
                    if (armadura){
                        if (jugador.getOro() >= 5){
                            jugador.restarOro(jugador.getOro()-5);
                            jugador.aumentarVidaMax(vid);
                            armadura=false;
                            interfaz.mostrarMensaje("ahora puedes aguantar mas daño");
                        }else {
                            interfaz.mostrarError("Oro insuficiente");
                        }
                    }else {
                        if (jugador.getOro() >= 5) {
                            jugador.restarOro(jugador.getOro()-5);
                            jugador.subirDano(5);
                            arma = false;
                            interfaz.mostrarMensaje("tu arma ahora hace mas daño");
                        } else {
                            interfaz.mostrarError("Oro insuficiente");
                        }
                    }if (!arma&&!armadura){
                        interfaz.mostrarError("operacion invalida");
                    }
                    break;
                case "3":
                        if (arma && armadura) {
                            if ((jugador.getOro() >= 5) && (arma = true)) {
                                jugador.restarOro(jugador.getOro()-5);
                                jugador.subirDano(5);
                                arma = false;
                                interfaz.mostrarMensaje("tu arma ahora hace mas daño");
                            } else {
                                interfaz.mostrarError("Oro insuficiente");
                            }
                        }else {
                            interfaz.mostrarError("operacion invalida");
                        }
                    break;
                case "f":
                    interfaz.mostrarMensaje("suerte en tu aventura");
                    salir=true;
                    break;

                case "p":
                    interfaz.mostrarMensaje("cantidad de oro:");
                    jugador.setOro(sc.nextInt());interfaz.mostrarMensaje("oro puesto a: " + jugador.getOro());
                    break;
                case "q":
                    interfaz.mostrarMensaje("volviendo al menu");
                    return true;

                default:
                    interfaz.mostrarMensaje("operacion invalida");
            }
        }
        return false;
    }
}
