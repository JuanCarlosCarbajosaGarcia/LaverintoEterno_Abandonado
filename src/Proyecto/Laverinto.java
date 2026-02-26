package Proyecto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Laverinto implements ILaberinto, IJuego{
    private static final int Tamanox = 10;
    private static final int Tamanoy = 20;
    private static final int Max_intentos = 100;

    private final char[][] mapa = new char[Tamanox][Tamanoy];
    private final Random rand = new Random(System.currentTimeMillis());
    private final Scanner scanner = new Scanner(System.in);

    private final IInterfaz interfaz = new InterfazConsola();

    private final Jugador jugador;
    private Enemigo enemigo;
    private int nivel = 1;
    private boolean juego = true;
    private boolean primerJuego = true;

    public Laverinto(){
        interfaz.mostrarMensaje("=== BIEMVENIDO AL LAVERINTO ETERNO ===");
        //nombre del jugador
        String nombre = interfaz.pedirLinea("ingrese el nombre del jugador: ");

        if (nombre == null||nombre.trim().isEmpty()){
            //referencia a libro
            nombre = "Maze_Runer";
        }

        //iniciar el jugador
        jugador = new Jugador(nombre,"jugador",10,10,0,1);
        jugador.setOro(0);

        //bucle de juego
        while(juego){
            iniciarNivel();
        }
    }

    @Override
    public void iniciarNivel(){
        interfaz.mostrarMensaje("\n=== INICIANDO NIVEL " + nivel + " ===");

        //generar laverinto
        boolean posible;
        int intentos = 0;

        do {
            generarMapa();
            posible = esPosible();
            intentos++;

        } while(!posible && intentos <Max_intentos);

        if (!posible){
            interfaz.mostrarError("no se pudo generar el nivel");
            return;
        }

        enemigo = new Enemigo("Minos","enemigo",100,10,Tamanox/2,Tamanoy/2);
        colocarEnemigos();

        //resetear jugador
        jugador.setX(0);
        jugador.setY(1);

        //bucle del nivel
        boolean nivelActivo = true;

        while (nivelActivo && juego){
            mostrarMapa();
            interfaz.mostrarInfo(nivel,jugador.getVida(),jugador.getVidaMax(),jugador.getOro(),jugador.getDano());

            //termino el nivel el jugador
            if (mapa[jugador.getX()][jugador.getY()] == 'S'){
                interfaz.mostrarExito("¡Felicidades! Sobreviviste el nivel " + nivel);
                nivel++;
                Random num = new Random();
                int premio = num.nextInt(5)+1;
                jugador.setOro(jugador.getOro() + premio);
                interfaz.mostrarMensaje(InterfazConsola.AMARILLO+"Ganaste: " + premio + " de Oro"+InterfazConsola.RESET);

                guardarMapa();

                //ir al vendedor
                Vendedor vendedor = new Vendedor(nivel);
                boolean volveraMenu = vendedor.mostrarTienda(jugador);

                if (volveraMenu){
                    interfaz.mostrarMensaje("voviendo al menu principal");
                    juego = false;
                    return;
                }

                nivelActivo = false;
                continue;
            }

            //ataque de enemigo
            if (jugador.getX()==enemigo.getX() && jugador.getY()==enemigo.getY()){
                interfaz.mostrarMensaje("El Minotauro te a atacado");

                int danoRecibido = enemigo.getDano();
                int danoHecho = jugador.getDano();

                jugador.setVida(jugador.getVida() - danoRecibido);
                interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

                enemigo.setVida(enemigo.getVida() - danoHecho);

                if (jugador.estaVivo()) {
                    //jugador sobrevive
                    interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño al enemigo.");
                    interfaz.mostrarExito("has escapado con vida.");
                    separar();
                } else {
                    //jugador muere
                    interfaz.mostrarError("el minotauro te ha devorado");
                    interfaz.mostrarMensaje("alcanzaste el nivel: " + nivel);
                    interfaz.mostrarMensaje(InterfazConsola.AMARILLO+"oro total: " + jugador.getOro()+InterfazConsola.RESET);
                    guardarMapa();
                    juego = false;
                    return;
                }
            }

            //movimiento jugador
            boolean salirJuego=jugador.mover(mapa,scanner);
            if (salirJuego){
                interfaz.mostrarMensaje("volviendo al menu principal.....");
                juego = false;
                return;
            }

            enemigo.mover(mapa,rand, mapa.length, mapa[0].length);//movimiento Minos
        }
    }

    @Override
    public boolean estaActivo() {
        return juego;
    }

    @Override
    public void terminarJuego() {
        juego = false;
    }

    private void limpiarPantalla() {
        System.out.println("\n");
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private void separar(){
    int[] dx = {-2, 0, 2, 0};
    int[] dy = {0, 2, 0, -2};

        //buscar celda valida
        for (int s = 0; s < 4; s++) {
            int newX = jugador.getX() + dx[s];
            int newY = jugador.getY() + dy[s];

            //verificar nueva posicion
            if (newX >= 0 && newX < Tamanox && newY >= 0 && newY < Tamanoy && mapa[newX][newY] != '#' && (newX != enemigo.getX() || newY != enemigo.getY())) {

                jugador.setX(newX);
                jugador.setY(newY);
                mostrarMapa();
                return;
            }
        }

        //si no puede moverse
        interfaz.mostrarError("¡No puedes moverte! Estas atrapado.");
    }

    private void colocarEnemigos(){
        boolean posicionValida = false;
        while(!posicionValida){
            int newX = rand.nextInt(Tamanox);
            int newY = rand.nextInt(Tamanoy);

            int distancia = Math.abs(newX - jugador.getX()) + Math.abs(newY - jugador.getY());

            //verificar la posicion
            if (mapa[newX][newY]!='#' && distancia >= 5){
                enemigo.setX(newX);
                enemigo.setY(newY);
                posicionValida = true;
            }
        }
    }

    //generar el mapa
    @Override
    public void generarMapa(){
        for(int i = 0; i < Tamanox; i++){
            Arrays.fill(mapa[i], '#');
        }

        camino(1,1);

        //poner los bordes
        for (int i = 0; i < Tamanox; i++){
            mapa[i][Tamanoy-1] = '#';
        }
        for (int j = 0; j < Tamanoy; j++){
            mapa[Tamanox-1][j] = '#';
        }
        mapa[0][1] = 'E'; //entrada
        mapa[Tamanox - 1][1]= 'S'; //salida
    }

    //generar el camino
    private void camino(int x, int y){
        mapa[x][y]=' ';
        int[] direccion = {0,1,2,3};
        shuffleArray(direccion);

        for(int dir: direccion){
            int nx = x,  ny = y;
            switch (dir){
                case 0: nx -= 2;
                break; // Arriba

                case 1: ny += 2;
                break; // Derecha

                case 2: nx += 2;
                break; // Abajo

                case 3: ny -= 2;
                break; // Izquierda
            }
            //verificar que los limites sean pared
            if (nx >0 && nx < Tamanox && ny > 0 && ny < Tamanoy && mapa[nx][ny]=='#'){
                mapa[nx][ny] = ' '; //quitar pared
                mapa[x + (nx-x)/2][y + (ny-y)/2]=' ';
                camino(nx,ny);
            }
        }
    }

    private void shuffleArray(int[] array){
        for(int i = array.length - 1; i > 0; i--){
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    @Override
    public boolean esPosible(){
        boolean[][] comprobado = new boolean[Tamanox][Tamanoy];
        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{0,1}); //entrada
        comprobado[0][1] = true;

        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};

        while(!cola.isEmpty()){
            int[] actual = cola.poll();
            int x = actual[0], y = actual[1];

            if (x == Tamanox - 1 && y == 1){
                return true;
            }

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i], ny = y + dy[i];
                if (nx >= 0 && nx < Tamanox && ny >= 0 && ny < Tamanoy && mapa[nx][ny]!= '#' && !comprobado[nx][ny]){
                    comprobado[nx][ny] = true;
                    cola.add(new int[]{nx, ny});
                }
            }
        }
        //si no existe camino
        return false;
    }

    @Override
    public void mostrarMapa(){
        System.out.println("\n"+ InterfazConsola.CIAN+"Mapa del laverinto:");
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                if (i == jugador.getX() && j == jugador.getY()){
                    System.out.print(InterfazConsola.VERDE+'P'+InterfazConsola.RESET);
                }else if (i == enemigo.getX() && j == enemigo.getY()){
                    System.out.print(InterfazConsola.ROJO+'M'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='E') {
                    System.out.print(InterfazConsola.AZUL+'E'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='S') {
                    System.out.print(InterfazConsola.VERDE_CLARO+'S'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='#') {
                    System.out.print(InterfazConsola.GRIS+'#'+InterfazConsola.RESET);
                } else {
                    System.out.print(mapa[i][j]);
                }
            }
            System.out.println("|"); //borde derecho
        }
    }

    @Override
    public void mostrarMenu(){

    }

    @Override
    public char[][] getMapa() {
        return mapa;
    }

    @Override
    public int[] getEntrada() {
        return new int[]{0,1};
    }

    @Override
    public int[] getSalida() {
        return new int[]{Tamanox -1,1};
    }

    private void guardarMapa(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Mapa.txt", !primerJuego))){
            //encabezado del archivo
            if(primerJuego){
               writer.write("=== Laberinto de " + jugador.nombre + " ===");
               writer.newLine();
               writer.newLine();
               primerJuego = false;
            }

            int nivelmapa = nivel -1;

            //indicador del nivel
            writer.write("\n--- Nivel " + nivelmapa + " ---");
            writer.newLine();

            for(int i = 0; i < Tamanox; i++){
                for(int j = 0; j < Tamanoy; j++){
                    if (i == jugador.getX() && j == jugador.getY()){
                        writer.write('P');
                    } else if (i == enemigo.getX() && j == enemigo.getY()){
                        writer.write('M');
                    } else {
                        writer.write(mapa[i][j]);
                    }
                }
                writer.write('|');
                writer.newLine();
            }
            writer.newLine();
            interfaz.mostrarExito("Mapa guardado correctamente");
        }catch (IOException e){
            interfaz.mostrarError("Error guardando mapa: " + e.getMessage());
        }
    }
}
