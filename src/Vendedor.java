import java.util.Random;
import java.util.Scanner;

public class Vendedor implements IVendedor{
    private final IInterfaz interfaz = new InterfazConsola();

    private final Scanner dinerosc = new Scanner(System.in);
    private final int nivelActual;
    Random num = new Random();
    int vid = num.nextInt(10) +1;
    boolean armadura = true;
    boolean arma = true;
    boolean tiendaPruebas=false;
    private final Scanner vendedorsc = new Scanner(System.in);

    public Vendedor(int nivel){
        this.nivelActual=nivel -1;
    }

    @Override
    public boolean mostrarTienda(IEntidad jugador){
        boolean salir=false;

        while(!salir){

            //menu dinamico
            interfaz.mostrarMensaje(InterfazConsola.NEGRITA+InterfazConsola.VERDE_CLARO);
            interfaz.mostrarMensaje("\n=== ÁREA DE DESCANSO - NIVEL: " + nivelActual + " ===");
            interfaz.mostrarMensaje(InterfazConsola.AMARILLO+"Oro actual: " + jugador.getOro()+InterfazConsola.RESET);
            interfaz.mostrarMensaje(InterfazConsola.ROJO+"Salud: " + jugador.getVida() + "/" + jugador.getVidaMax()+InterfazConsola.RESET);
            interfaz.mostrarMensaje(InterfazConsola.MAGENTA+"daño: " + jugador.getDano()+InterfazConsola.RESET);
            interfaz.mostrarMensaje(InterfazConsola.NEGRITA+InterfazConsola.VERDE_CLARO);
            interfaz.mostrarMensaje("\n--- TIENDA ---");
            interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO+"1. descansar (1 oro) - curacion completa"+InterfazConsola.RESET);
            if (armadura) {
                interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO+"2. armadura + " + vid + " vida maxima (5 oro)"+ InterfazConsola.RESET);
            }else {
                interfaz.mostrarMensaje(InterfazConsola.ROJO_CLARO+"[AGOTADO] armadura "+InterfazConsola.RESET);
            }
            if (arma) {
                if (armadura) {
                    interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO+"3. mejorar arma +5 de daño (5 oro)"+InterfazConsola.RESET);
                } else {
                    interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO+"2. mejorar arma +5 de daño (5 oro)"+InterfazConsola.RESET);
                }
            }else {
                interfaz.mostrarMensaje(InterfazConsola.ROJO_CLARO+"[AGOTADO] armas"+InterfazConsola.RESET);
            }
            interfaz.mostrarMensaje(InterfazConsola.AMARILLO+"F. ir al siguiente nivel"+InterfazConsola.RESET);
            interfaz.mostrarMensaje(InterfazConsola.AZUL+"Q. salir del juego"+InterfazConsola.RESET);
            interfaz.mostrarMensaje("que quieres hacer: ");

            String opcion = dinerosc.nextLine().toLowerCase();

            switch (opcion){
                case "1":
                    if (jugador.getOro() >= 1){
                        jugador.restarOro(1);
                        jugador.curar();
                        interfaz.mostrarExito(InterfazConsola.VERDE_CLARO+"te sientes revitalizado");
                        interfaz.mostrarMensaje(InterfazConsola.RESET);
                    }else {
                        interfaz.mostrarError(InterfazConsola.ROJO_CLARO+"Oro insuficiente"+InterfazConsola.RESET);
                    }
                    break;
                case "2":
                    if (!arma&&!armadura){
                        interfaz.mostrarError(InterfazConsola.ROJO+"operacion invalida"+InterfazConsola.RESET);
                    }else if (armadura) {
                        if (jugador.getOro() >= 5) {
                            jugador.restarOro(5);
                            jugador.aumentarVidaMax(vid);
                            armadura = false;
                            interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO + "ahora puedes aguantar mas daño" + InterfazConsola.RESET);
                        } else {
                            interfaz.mostrarError(InterfazConsola.ROJO_CLARO + "Oro insuficiente" + InterfazConsola.RESET);
                        }
                    }else {
                        if (jugador.getOro() >= 5) {
                            jugador.restarOro(5);
                            jugador.subirDano(5);
                            arma = false;
                            interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO + "tu arma ahora hace mas daño" + InterfazConsola.RESET);
                        } else {
                            interfaz.mostrarError(InterfazConsola.ROJO_CLARO + "Oro insuficiente" + InterfazConsola.RESET);
                        }
                    }
                        break;
                case "3":
                        if (arma && armadura) {
                            if ((jugador.getOro() >= 5) && (arma = true)) {
                                jugador.restarOro(5);
                                jugador.subirDano(5);
                                arma = false;
                                interfaz.mostrarMensaje(InterfazConsola.VERDE_CLARO+"tu arma ahora hace mas daño"+InterfazConsola.RESET);
                            } else {
                                interfaz.mostrarError(InterfazConsola.ROJO_CLARO+"Oro insuficiente"+InterfazConsola.RESET);
                            }
                        }else {
                            interfaz.mostrarError(InterfazConsola.ROJO+"operacion invalida"+InterfazConsola.RESET);
                        }
                    break;
                case "f":
                    interfaz.mostrarMensaje(InterfazConsola.AMARILLO+"suerte en tu aventura"+InterfazConsola.RESET);
                    salir=true;
                    break;

                case "p":
                    tiendaPruebas=true;
                    while (tiendaPruebas){
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "menu de pruebas" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "opciones:" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "1: oro" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "2: arma" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "3: armadura" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "4: curar" + InterfazConsola.RESET);
                        interfaz.mostrarMensaje(InterfazConsola.MAGENTA + "5: Salir" + InterfazConsola.RESET);

                        String Pruebas = dinerosc.nextLine().toLowerCase();

                        switch (Pruebas) {
                            case "1":
                                interfaz.mostrarMensaje("cantidad de oro:");
                                jugador.setOro(vendedorsc.nextInt());
                                interfaz.mostrarMensaje("oro puesto a: " + jugador.getOro());
                                break;
                            case "2":
                                interfaz.mostrarMensaje("cantidad de daño:");
                                jugador.subirDano(vendedorsc.nextInt());
                                interfaz.mostrarMensaje("daño aumentado: " + jugador.getDano());
                                break;
                            case "3":
                                interfaz.mostrarMensaje("cantidad de armadura:");
                                jugador.aumentarVidaMax(vendedorsc.nextInt());
                                interfaz.mostrarMensaje("armadura aumentada: " + jugador.getVidaMax());
                                break;
                            case "4":
                                jugador.curar();
                                interfaz.mostrarMensaje("curacion completada");
                                break;
                            case "5":
                                tiendaPruebas=false;
                        }
                    }
                    break;
                case "q":
                    interfaz.mostrarMensaje(InterfazConsola.AZUL+"volviendo al menu"+InterfazConsola.RESET);
                    return true;
                default:
                    interfaz.mostrarMensaje(InterfazConsola.ROJO+"operacion invalida"+InterfazConsola.RESET);
            }
        }
        return false;
    }
}
