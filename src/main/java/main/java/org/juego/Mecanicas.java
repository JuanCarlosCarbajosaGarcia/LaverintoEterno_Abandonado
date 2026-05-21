package main.java.org.juego;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Mecanicas {
    private static int x;
    private static int y;

    public static void movimiento() {
        //movimiento jugador
        boolean salirJuego = Laverinto.jugador.mover(Laverinto.mapa, Laverinto.laverintosc);
        if (salirJuego) {
            Laverinto.interfaz.mostrarMensaje("volviendo al menu principal.....");
            Laverinto.juego = false;
            return;
        }
        //movimiento de los enemigos
        if (Laverinto.activo == 1){
            //asterion
            Mecanicas.x = Laverinto.asterion.getX();
            Mecanicas.y = Laverinto.asterion.getY();
            Mecanicas.mover(Laverinto.mapa, Laverinto.rand, Laverinto.mapa.length, Laverinto.mapa[0].length);
            Laverinto.asterion.setX(Mecanicas.x);
            Laverinto.asterion.setY(Mecanicas.y);
        }if (Laverinto.activo == 2){
            //medusa
            Mecanicas.x = Laverinto.medusa.getX();
            Mecanicas.y = Laverinto.medusa.getY();
            Mecanicas.mover(Laverinto.mapa, Laverinto.rand, Laverinto.mapa.length, Laverinto.mapa[0].length);
            Laverinto.medusa.setX(Mecanicas.x);
            Laverinto.medusa.setY(Mecanicas.y);
        }if (Laverinto.activo == 3){
            //lerneae
            for (Cabezas cabeza : Cabezas.cabeza) {
                int cabezavx = cabeza.getX();
                int cabezavy = cabeza.getY();

                if (cabezavx >= 0 && cabezavx < Laverinto.mapa.length
                        && cabezavy >= 0 && cabezavy < Laverinto.mapa[0].length
                        && Laverinto.mapa[cabezavx][cabezavy] == 'C'){
                    Laverinto.mapa[cabezavx][cabezavy] = ' ';
                }

                Mecanicas.x = cabeza.getX();
                Mecanicas.y = cabeza.getY();
                Mecanicas.mover(Laverinto.mapa, Laverinto.rand, Laverinto.mapa.length, Laverinto.mapa[0].length);

                cabeza.setX(Mecanicas.x);
                cabeza.setY(Mecanicas.y);

                int nuevaX = cabeza.getX();
                int nuevaY = cabeza.getY();
                if (nuevaX >= 0 && nuevaX < Laverinto.mapa.length
                        && nuevaY >= 0 && nuevaY < Laverinto.mapa[0].length
                        && Laverinto.mapa[nuevaX][nuevaY] != '█'){
                    Laverinto.mapa[nuevaX][nuevaY] = 'C';
                }
            }
        }
    }
    public static void mover(char[][] mapa, Random rand, int maxX, int maxY){
        int[] dx ={-1,0,1,0};
        int[] dy={0,1,0,-1};


        //moverse
        List<Integer> movimiento = new ArrayList<>();

        for(int i=0;i<4;i++){
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY && mapa[newX][newY] != '█'){
                movimiento.add(i);
            }
        }
        //elegir una direcion valida al azar
        if(!movimiento.isEmpty()){
            int dir = movimiento.get(rand.nextInt(movimiento.size()));
            x += dx[dir];
            y += dy[dir];
        }
    }

    public  static void ataque() {
        //ataque de asterion
        if (Laverinto.jugador.getX() == Laverinto.asterion.getX() && Laverinto.jugador.getY() == Laverinto.asterion.getY()) {
            Laverinto.interfaz.mostrarMensaje("asterion te a atacado");

            int danoRecibido = Laverinto.asterion.getDano();
            int danoHecho = Laverinto.jugador.getDano();

            Laverinto.jugador.setVida(Laverinto.jugador.getVida() - danoRecibido);
            Laverinto.interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

            Laverinto.asterion.setVida(Laverinto.asterion.getVida() - danoHecho);

            if (Laverinto.jugador.estaVivo()) {
                //jugador sobrevive
                Laverinto.interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a Asterion.");
                Laverinto.interfaz.mostrarExito("has escapado con vida.");
                separar();
            } else {
                //jugador muere
                Laverinto.interfaz.mostrarError("Asterion te ha devorado");
                Laverinto.interfaz.mostrarMensaje("alcanzaste el nivel: " + Laverinto.nivel);
                Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + Laverinto.jugador.getOro() + InterfazConsola.RESET);
                Laverinto.guardarMapa();
                Laverinto.juego = false;
                return;
            }

            if (Laverinto.asterion.getVida() == 0) {
                //asterion muere
                Laverinto.asterion.aumentarMuertes(1);
                Laverinto.interfaz.mostrarMensaje("Asterion a muerto");
                Mecanicas.dificultad();
                Laverinto.asterion.setX(Laverinto.mapa.length + 1);
                Laverinto.asterion.setY(Laverinto.mapa.length + 1);
            }
        }
        //berserker asterion
        if (Laverinto.asterion.vida <= (Laverinto.asterion.vidaMax / 4)) {
            Laverinto.asterion.setDano(Laverinto.asterion.dano * 2);
            System.out.println("Asterion se ha enfurecido");
        }

        //ataque de medusa
        if (Laverinto.jugador.getX() == Laverinto.medusa.getX() && Laverinto.jugador.getY() == Laverinto.medusa.getY()) {
            Laverinto.interfaz.mostrarMensaje("Medusa te a atacado");

            int danoRecibido = Laverinto.medusa.getDano();
            int danoHecho = Laverinto.jugador.getDano();

            Laverinto.jugador.setVida(Laverinto.jugador.getVida() - danoRecibido);
            Laverinto.interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

            Laverinto.medusa.setVida((Laverinto.medusa.getVida() - danoHecho));

            if (Laverinto.jugador.estaVivo()) {
                //jugador sobrevive
                Laverinto.interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a Medusa.");
                Laverinto.interfaz.mostrarExito("has escapado con vida.");
                separar();
            } else {
                //jugador muere
                Laverinto.interfaz.mostrarMensaje("Medusa te ha petrificado");
                Laverinto.interfaz.mostrarMensaje("alcanzaste el nivel: " + Laverinto.nivel);
                Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + Laverinto.jugador.getOro() + InterfazConsola.RESET);
                Laverinto.guardarMapa();
                Laverinto.juego = false;
                return;
            }

            if (Laverinto.medusa.getVida() == 0) {
                //medusa muere
                Laverinto.medusa.aumentarMuertes(1);
                Laverinto.interfaz.mostrarMensaje("Medusa a muerto");
                Mecanicas.dificultad();
                Laverinto.medusa.setX(Laverinto.mapa.length + 1);
                Laverinto.medusa.setY(Laverinto.mapa.length + 1);
            }
        }
        //proximidad de medusa
        double distamcia = Math.sqrt(
                Math.pow(Laverinto.jugador.getX() - Laverinto.medusa.getX(), 2) +
                        Math.pow(Laverinto.jugador.getY() - Laverinto.medusa.getY(), 2)
        );

        if (distamcia <= 1.5) {
            Laverinto.jugador.setEstatua(true);
        } else {
            Laverinto.jugador.setEstatua(false);
        }

        //ataque de lernaean
        if (Laverinto.jugador.getX() == Laverinto.lernaean.getX() && Laverinto.jugador.getY() == Laverinto.lernaean.getY()) {
            Laverinto.interfaz.mostrarMensaje("Lernaean te a atacado");

            int danoRecibido = Laverinto.lernaean.getDano();
            int danoHecho = Laverinto.jugador.getDano();

            Laverinto.jugador.setVida(Laverinto.jugador.getVida() - danoRecibido);
            Laverinto.interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

            Laverinto.lernaean.setVida(Laverinto.lernaean.getVida() - danoHecho);

            if (Laverinto.jugador.estaVivo()) {
                //jugador sobrevive
                Laverinto.interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a Lernaean.");
                Laverinto.interfaz.mostrarExito("has escapado con vida.");
                separar();
            } else {
                //jugador muere
                Laverinto.interfaz.mostrarMensaje("Lernaean te ha devorado");
                Laverinto.interfaz.mostrarMensaje("alcanzaste el nivel: " + Laverinto.nivel);
                Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + Laverinto.jugador.getOro() + InterfazConsola.RESET);
                Laverinto.guardarMapa();
                Laverinto.juego = false;
                return;
            }

            if (Laverinto.lernaean.getVida() == 0) {
                //lernaean muere
                Laverinto.lernaean.aumentarMuertes(1);
                Laverinto.interfaz.mostrarMensaje("Lernaean a muerto");
                Mecanicas.dificultad();
                Laverinto.lernaean.setX(Laverinto.mapa.length + 1);
                Laverinto.lernaean.setY(Laverinto.mapa.length + 1);
            }
        }
        //cabezas de lernaean
        Iterator<Cabezas> itcabeza = Cabezas.cabeza.iterator();
        while (itcabeza.hasNext()) {
            Cabezas cabezas = itcabeza.next();
            if (Laverinto.jugador.getX() == cabezas.getX() && Laverinto.jugador.getY() == cabezas.getY()) {
                Laverinto.interfaz.mostrarMensaje("Lernaean te a atacado");

                int danoRecibido = cabezas.getDano();
                int danoHecho = Laverinto.jugador.getDano();

                Laverinto.jugador.setVida(Laverinto.jugador.getVida() - danoRecibido);
                Laverinto.interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

                cabezas.setVida(cabezas.getVida() - danoHecho);

                if (Laverinto.jugador.estaVivo()) {
                    //jugador sobrevive
                    Laverinto.interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a la cabeza de Lernaean.");
                    Laverinto.interfaz.mostrarExito("has escapado con vida.");
                    separar();
                } else {
                    //jugador muere
                    Laverinto.interfaz.mostrarMensaje("Lernaean te ha devorado");
                    Laverinto.interfaz.mostrarMensaje("alcanzaste el nivel: " + Laverinto.nivel);
                    Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + Laverinto.jugador.getOro() + InterfazConsola.RESET);
                    Laverinto.guardarMapa();
                    Laverinto.juego = false;
                    return;
                }

                if (cabezas.getVida() == 0) {
                    Laverinto.interfaz.mostrarMensaje("cabeza cortada");
                    Mecanicas.dificultad();
                    Laverinto.mapa[cabezas.getX()][cabezas.getY()] = ' ';
                    itcabeza.remove();
                    Cabezas.cabezascortadas++;
                }
            }
        }
    }

    public static void Tesoros(){
        //encontrar cofre
        Iterator<Tesoro> itcofre = Tesoro.tesoros.iterator();
        while (itcofre.hasNext()) {
            Tesoro tesoro = itcofre.next();
            if (Laverinto.jugador.getX() == tesoro.getX() && Laverinto.jugador.getY() == tesoro.getY()) {
                Laverinto.interfaz.mostrarMensaje("Has encontrado un cofre");
                Random num = new Random();
                int premio = num.nextInt(10) + 1;
                Laverinto.jugador.setOro(Laverinto.jugador.getOro() + premio);
                Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "Encontraste: " + premio + " de Oro" + InterfazConsola.RESET);

                Laverinto.mapa[tesoro.getX()][tesoro.getY()] = ' ';
                itcofre.remove();
            }
        }
    }

    public static void separar(){
        Laverinto.jugador.setX(0);
        Laverinto.jugador.setY(1);
        Laverinto.mostrarMapa();
    }

    public static void dificultad(){
        int vid = 150;
        int dan = 10;

        int muertesvid;
        int muertesdan;

        if (Laverinto.asterion.muertes <= 1) {
            muertesvid = vid;
            Laverinto.asterion.setVida(muertesvid);
            muertesdan = dan;
            Laverinto.asterion.setDano(muertesdan);
        } else {
            muertesvid = Laverinto.asterion.muertes * vid;
            Laverinto.asterion.setVida(muertesvid);
            muertesdan = Laverinto.asterion.muertes * dan;
            Laverinto.asterion.setDano(muertesdan);
        }

        if (Laverinto.medusa.muertes <= 1) {
            muertesvid = vid;
            Laverinto.medusa.setVida(muertesvid);
            muertesdan = dan;
            Laverinto.medusa.setDano(muertesdan);
        } else {
            muertesvid = Laverinto.medusa.muertes * vid;
            Laverinto.medusa.setVida(muertesvid);
            muertesdan = Laverinto.medusa.muertes * dan;
            Laverinto.medusa.setDano(muertesdan);
        }

        if (Laverinto.lernaean.muertes <= 1) {
            muertesvid = vid;
            Laverinto.lernaean.setVida(muertesvid);
            muertesdan = dan;
            Laverinto.lernaean.setDano(muertesdan);
        }else  {
            muertesvid = Laverinto.lernaean.muertes * vid;
            Laverinto.lernaean.setVida(muertesvid);
            muertesdan = Laverinto.lernaean.muertes * dan;
            Laverinto.lernaean.setDano(muertesdan);
        }
        if (Laverinto.lernaean.muertes <= 1) {
            Cabezas.totalcabezas = Laverinto.rand.nextInt(4)+1;
        } else {
            Cabezas.totalcabezas++;
            Cabezas.totalcabezas = Cabezas.totalcabezas+ Cabezas.cabezascortadas;
        }
    }
    public static void terminarNivel(){
        //termino el nivel el jugador
        if (Laverinto.mapa[Laverinto.jugador.getX()][Laverinto.jugador.getY()] == 'S') {
            Laverinto.interfaz.mostrarExito("¡Felicidades! Sobreviviste el nivel " + Laverinto.nivel);
            Laverinto.nivel++;
            Random num = new Random();
            int premio = num.nextInt(5) + 1;
            Laverinto.jugador.setOro(Laverinto.jugador.getOro() + premio);
            Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "Ganaste: " + premio + " de Oro" + InterfazConsola.RESET);

            Laverinto.guardarMapa();
            Laverinto.resetEnemigos();
            Laverinto.nivelActivo = false;
            Laverinto.asterion.curar();
            Laverinto.medusa.curar();
            Laverinto.lernaean.curar();
            Laverinto.activo=0;

            //ir al vendedor
            Vendedor vendedor = new Vendedor(Laverinto.nivel);
            boolean volveraMenu = vendedor.mostrarTienda(Laverinto.jugador);

            if (volveraMenu) {
                Laverinto.interfaz.mostrarMensaje("voviendo al menu principal");
                Laverinto.juego = false;
            }
        }
    }
}
