import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Mecanicas {

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
                juego = false;
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
                juego = false;
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
            jugador.setEstatua(false);
        }

        //ataque de lernaean
        if (jugador.getX() == lernaean.getX() && jugador.getY() == lernaean.getY()) {
            interfaz.mostrarMensaje("Lernaean te a atacado");

            int danoRecibido = lernaean.getDano();
            int danoHecho = jugador.getDano();

            jugador.setVida(jugador.getVida() - danoRecibido);
            interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

            lernaean.setVida(lernaean.getVida() - danoHecho);

            if (jugador.estaVivo()) {
                //jugador sobrevive
                interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a Lernaean.");
                interfaz.mostrarExito("has escapado con vida.");
                separar();
            } else {
                //jugador muere
                interfaz.mostrarMensaje("Lernaean te ha devorado");
                interfaz.mostrarMensaje("alcanzaste el nivel: " + nivel);
                interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + jugador.getOro() + InterfazConsola.RESET);
                Laverinto.guardarMapa();
                juego = false;
                return;
            }

            if (lernaean.getVida() == 0) {
                //lernaean muere
                lernaean.aumentarMuertes(1);
                interfaz.mostrarMensaje("Lernaean a muerto");
                Mecanicas.dificultad();
                lernaean.setX(mapa.length + 1);
                lernaean.setY(mapa.length + 1);
            }
        }
        //cabezas de lernaean
        Iterator<Cabezas> itcabeza = Cabezas.cabeza.iterator();
        while (itcabeza.hasNext()) {
            Cabezas cabezas = itcabeza.next();
            if (jugador.getX() == cabezas.getX() && jugador.getY() == cabezas.getY()) {
                interfaz.mostrarMensaje("Lernaean te a atacado");

                int danoRecibido = cabezas.getDano();
                int danoHecho = jugador.getDano();

                jugador.setVida(jugador.getVida() - danoRecibido);
                interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

                cabezas.setVida(cabezas.getVida() - danoHecho);

                if (jugador.estaVivo()) {
                    //jugador sobrevive
                    interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño a la cabeza de Lernaean.");
                    interfaz.mostrarExito("has escapado con vida.");
                    separar();
                } else {
                    //jugador muere
                    interfaz.mostrarMensaje("Lernaean te ha devorado");
                    interfaz.mostrarMensaje("alcanzaste el nivel: " + nivel);
                    interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + jugador.getOro() + InterfazConsola.RESET);
                    Laverinto.guardarMapa();
                    juego = false;
                    return;
                }

                if (cabezas.getVida() == 0) {
                    interfaz.mostrarMensaje("cabeza cortada");
                    Mecanicas.dificultad();
                    mapa[cabezas.getX()][cabezas.getY()] = ' ';
                    itcabeza.remove();
                    Cabezas.totalcabezas++;
                }
            }
        }

        //encontrar cofre
        Iterator<Tesoro> itcofre = tesoros.iterator();
        while (itcofre.hasNext()) {
            Tesoro tesoro = itcofre.next();
            if (jugador.getX() == tesoro.getX() && jugador.getY() == tesoro.getY()) {
                interfaz.mostrarMensaje("Has encontrado un cofre");
                Random num = new Random();
                int premio = num.nextInt(10) + 1;
                jugador.setOro(jugador.getOro() + premio);
                interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "Encontraste: " + premio + " de Oro" + InterfazConsola.RESET);

                mapa[tesoro.getX()][tesoro.getY()] = ' ';
                itcofre.remove();
            }
        }
        //movimiento jugador
        boolean salirJuego = jugador.mover(mapa, laverintosc);
        if (salirJuego) {
            interfaz.mostrarMensaje("volviendo al menu principal.....");
            juego = false;
            return;
        }

        //movimiento asterion
        asterion.mover(mapa, Laverinto.rand, mapa.length, mapa[0].length);
        //Movimiento Medusa
        medusa.mover(mapa, Laverinto.rand, mapa.length, mapa[0].length);

    }
    public void mover(char[][] mapa, Random rand, int maxX, int maxY){
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
    public static void separar(){
        jugador.setX(0);
        jugador.setY(1);
        mostrarMapa();
    }

    public static void dificultad(){
        int vid = 150;
        int dan = 10;

        int muertesvid;
        int muertesdan;

        if (asterion.muertes <= 1) {
            muertesvid = vid;
            asterion.setVida(muertesvid);
            muertesdan = dan;
            asterion.setDano(muertesdan);
        } else {
            muertesvid = asterion.muertes * vid;
            asterion.setVida(muertesvid);
            muertesdan = asterion.muertes * dan;
            asterion.setDano(muertesdan);
        }

        if (medusa.muertes <= 1) {
            muertesvid = vid;
            medusa.setVida(muertesvid);
            muertesdan = dan;
            medusa.setDano(muertesdan);
        } else {
            muertesvid = medusa.muertes * vid;
            medusa.setVida(muertesvid);
            muertesdan = medusa.muertes * dan;
            medusa.setDano(muertesdan);
        }

        if (lernaean.muertes <= 1) {
            muertesvid = vid;
            lernaean.setVida(muertesvid);
            muertesdan = dan;
            lernaean.setDano(muertesdan);
        }else  {
            muertesvid = lernaean.muertes * vid;
            lernaean.setVida(muertesvid);
            muertesdan = lernaean.muertes * dan;
            lernaean.setDano(muertesdan);
        }
        if (lernaean.muertes <= 1) {
            Cabezas.totalcabezas = Laverinto.rand.nextInt(4)+1;
        } else {
            Cabezas.totalcabezas++;
        }
    }
    public static void terminarNivel(){
        //termino el nivel el jugador
        if (mapa[jugador.getX()][jugador.getY()] == 'S') {
            Laverinto.interfaz.mostrarExito("¡Felicidades! Sobreviviste el nivel " + nivel);
            nivel++;
            Random num = new Random();
            int premio = num.nextInt(5) + 1;
            jugador.setOro(jugador.getOro() + premio);
            Laverinto.interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "Ganaste: " + premio + " de Oro" + InterfazConsola.RESET);

            Laverinto.guardarMapa();
            Laverinto.resetEnemigos();

            //ir al vendedor
            Vendedor vendedor = new Vendedor(nivel);
            boolean volveraMenu = vendedor.mostrarTienda(jugador);

            if (volveraMenu) {
                Laverinto.interfaz.mostrarMensaje("voviendo al menu principal");
                juego = false;
                return;
            }

            Laverinto.nivelActivo = false;
            Laverinto.asterion.curar();
            Laverinto.medusa.curar();
            Laverinto.lernaean.curar();
        }
    }
}
