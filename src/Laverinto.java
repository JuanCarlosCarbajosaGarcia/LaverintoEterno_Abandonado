import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Laverinto implements ILaberinto, IJuego{
    protected static final int Tamanox = 15;
    protected static final int Tamanoy = 30;
    private static final int Max_intentos = 100;

    public static final char[][] mapa = new char[Tamanox][Tamanoy];
    public static final Random rand = new Random(System.currentTimeMillis());
    private final Random enemigos = new Random();
    public static final Scanner laverintosc = new Scanner(System.in);
    static boolean nivelActivo = true;

    public static final IInterfaz interfaz = new InterfazConsola();

    public static Jugador jugador;
    public static Minotauro asterion;
    public static Gorgona medusa;
    public static Hidra lernaean;
    public static int nivel = 1;
    public static boolean juego = true;
    public static int activo = 0;
    private static boolean primerJuego = true;
    private static final int minpasos = 1;
    private static final int maxpasos = 9999;

    public Laverinto(){
        //iniciar enemigos
        minotauro();
        medusa();
        hidra();

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
        jugador.setEstatua(false);


        //bucle de juego
        while(juego){
            iniciarNivel();
        }
    }

    @Override
    public void iniciarNivel() {
        interfaz.mostrarMensaje("\n=== INICIANDO NIVEL " + nivel + " ===");

        //generar laverinto
        generarMapa();

        //colocar los enemigos del nivel
        colocarEnemigos();

        //colocar los cofres minimo 1
        Tesoro.Cofres();

        //resetear jugador
        jugador.setX(0);
        jugador.setY(1);
        nivelActivo = true;

        //bucle del nivel

        while (nivelActivo && juego) {
            interfaz.limpiarPantalla();

            mostrarMapa();
            interfaz.mostrarInfo(nivel, jugador.getVida(), jugador.getVidaMax(), jugador.getOro(), jugador.getDano());
            Mecanicas.movimiento();
            Mecanicas.ataque();
            Mecanicas.Tesoros();

            Mecanicas.terminarNivel();
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

    private void colocarEnemigos(){
            boolean posicionValida = false;
            while (!posicionValida) {

                int newX = rand.nextInt(Tamanox);
                int newY = rand.nextInt(Tamanoy);

                int distancia = Math.abs(newX - jugador.getX()) + Math.abs(newY - jugador.getY());
                int entrada = mapa[0][1];

                //verificar la posicion
                if (mapa[newX][newY] != '█' && distancia >= 5 && mapa[newX][newY] != entrada) {
                    switch (enemigos.nextInt(3)+1) {
                        case 1:
                            activo=1;
                            asterion.setX(newX);
                            asterion.setY(newY);
                        break;
                        case 2:
                            activo=2;
                            medusa.setX(newX);
                            medusa.setY(newY);
                        break;
                        case 3:
                            activo=3;
                            lernaean.setX(newX);
                            lernaean.setY(newY);
                            Cabezas.cabezas();
                        break;
                    }
                    posicionValida = true;
                }
            }
    }

    //generar el mapa
    public void generarMapa(){
        boolean posible = false;
        int intentos = 0;

        while (!posible && intentos < Max_intentos) {

            mapaB();
            complejo();
            mapaF();

            esPosible();
            complejo();

            if (esPosible() && contarPasos() >= minpasos) {
                posible = true;
            }
            intentos++;
        }

        if (!esPosible() && intentos < Max_intentos) {
            System.out.println("error al generar nivel");
            generarMapa();
        }
    }


    public void mapaB() {
        for (int i = 0; i < Tamanox; i++) {
            Arrays.fill(mapa[i], '█');
        }
        caminoP();

        camino(1,1);

        //poner los bordes
        for (int i = 0; i < Tamanox; i++){
            mapa[i][Tamanoy-1] = '█';
        }
        for (int j = 0; j < Tamanoy; j++){
            mapa[Tamanox-1][j] = '█';
        }
        mapa[0][1] = 'E'; //entrada
        mapa[Tamanox - 1][1]= 'S'; //salida
    }

    //generar el camino principal
    private void caminoP(){
        int x=0, y=1;
        mapa[x][y]='E';

        while (x < Tamanox - 2){
            mapa[x][y] = ' ';
            x += rand.nextInt(2)+1;
            if (x < Tamanox - 1){
                mapa[x][y] = ' ';
            }
        }
        mapa[Tamanox-1][1] ='S';
        mapa[Tamanox-2][1] =' ';
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
            if (nx >0 && nx < Tamanox && ny > 0 && ny < Tamanoy && mapa[nx][ny]=='█'){
                mapa[nx][ny] = ' '; //quitar pared
                mapa[x + (nx-x)/2][y + (ny-y)/2]=' ';
                camino(nx,ny);
            }
        }
    }

    //camino recto para testeo
    public static void caminoE(){
        for (int i = 0; i < Tamanox; i++) {
            mapa[i][1] = ' ';
        }
        mapa[Tamanox-1][1] ='S';
    }

    private void caminoR(int x, int y, Set<String> Visi){
        if (Visi.size() > 10) {
            return;
        }
        Visi.add(x+","+y);
        int[] direcion = {0,1,2,3};
        int contador=0;
        while (contador<=2) {
            shuffleArray(direcion);
            for (int dir : direcion) {
                int nx = x, ny = y;
                switch (dir) {
                    case 0:
                        nx -= 2;
                        break;
                    case 1:
                        ny += 2;
                        break;
                    case 2:
                        nx += 2;
                        break;
                    case 3:
                        ny -= 2;
                        break;
                }
                if (nx > 1 && nx < Tamanox - 1 && ny > 1 && ny < Tamanoy - 1 && mapa[nx][ny] == '█') {
                    mapa[nx][ny] = ' ';
                    mapa[(x + nx) / 2][(y + ny) / 2] = ' ';
                    caminoR(nx, ny, Visi);
                    break;
                }
            }
            contador++;
        }
    }

    //generar mas posibilidades

    private void complejo(){
        Set<String> celVisi = new HashSet<>();
        for (int i = 1; i < Tamanox-2; i++) {
            if (mapa[i][1] == ' ' && !celVisi.contains(i + ",1")) {
                if (rand.nextDouble() < 0.4){
                    caminoR(i,1,celVisi);
                }
            }
        }
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void mapaF(){
        for (int i = 0; i < Tamanox; i++) {
            mapa[i][0] = '█';
            mapa[i][Tamanoy-1] = '█';
        }
        for (int j = 0; j < Tamanoy; j++) {
            mapa[0][j] = '█';
            mapa[Tamanox-1][j] = '█';
        }
        mapa[0][1] = 'E';
        mapa[Tamanox-1][1] = 'S';
    }

    private int contarPasos(){
        boolean[][] visi = new boolean[Tamanox][Tamanoy];
        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{0,1});
        visi[0][1] = true;

        int pasos = 0;
        int acceso = 1;
        int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};

        while (!cola.isEmpty() && pasos < maxpasos) {
            int size = cola.size();
            pasos ++;

            for (int i = 0; i < size; i++) {
                int[] actual = cola.poll();
                if(actual==null) continue;

                int x = actual[0], y = actual[1];

                for (int d = 0; d < 4; d++){
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if (nx >=0 && nx<Tamanox && ny >= 0 && ny< Tamanoy
                            && !visi[nx][ny] && esCamino(nx,ny)) {

                        visi[nx][ny] = true;
                        cola.add(new int[]{nx,ny});
                        acceso++;
                    }
                }
            }
        }

        int accesoTotal = (Tamanox*Tamanoy)/5;
        if (acceso< accesoTotal){
            return 0;
        }
        return 1;
    }

    @Override
    public boolean esPosible(){
        boolean[][] visi = new boolean[Tamanox][Tamanoy];
        Queue<int[]> cola = new LinkedList<>();

        cola.add(new int[]{0,1});
        visi[0][1] = true;

        int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            int x = actual[0], y = actual[1];

            if (x==Tamanox-1 && y==Tamanoy-1){
                return true;
            }

            for (int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx >= 0 && nx<Tamanox
                        && ny >= 0 && ny< Tamanoy
                        && !visi[nx][ny]
                        && esCamino(nx,ny)){

                    visi[nx][ny] = true;
                    cola.add(new int[]{nx,ny});
                }
            }
        }

        return false;
    }

    public boolean esCamino(int x, int y){
        char celda = mapa[x][y];
        return celda == ' ' || celda == 'E' || celda == 'S';
    }

    public static void mostrarMapa(){
        System.out.println("\n"+ InterfazConsola.CIAN+"Mapa del laverinto:");
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                if (i == jugador.getX() && j == jugador.getY()){
                    System.out.print(InterfazConsola.VERDE+'P'+InterfazConsola.RESET);
                }else if (i == asterion.getX() && j == asterion.getY()) {
                    System.out.print(InterfazConsola.ROJO + 'M' + InterfazConsola.RESET);
                }else if (i == medusa.getX() && j == medusa.getY()) {
                    System.out.print(InterfazConsola.ROJO + 'G' + InterfazConsola.RESET);
                } else if (i == lernaean.getX() && j == lernaean.getY()) {
                    System.out.print(InterfazConsola.ROJO + 'H' + InterfazConsola.RESET);
                } else if (mapa[i][j]=='C') {
                    System.out.print(InterfazConsola.ROJO + 'C' + InterfazConsola.RESET);
                } else if (mapa[i][j]=='E') {
                    System.out.print(InterfazConsola.AZUL+'E'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='S') {
                    System.out.print(InterfazConsola.VERDE_CLARO+'S'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='█') {
                    System.out.print(InterfazConsola.GRIS+'█'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='T') {
                    System.out.print(InterfazConsola.AMARILLO+'T'+InterfazConsola.RESET);
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

    public static void guardarMapa(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(jugador.nombre+"_Mapa.txt", !primerJuego))){
            //encabezado del archivo
            if(primerJuego){
               writer.write("=== Laberinto de " + jugador.nombre + " ===");
               writer.newLine();
               writer.newLine();
               primerJuego = false;
            }

            int nivelmapa = nivel -1;
            if(!jugador.estaVivo()){
                nivelmapa++;
            }

            //indicador del nivel
            writer.write("\n--- Nivel " + nivelmapa + " ---");
            writer.newLine();

            for(int i = 0; i < Tamanox; i++){
                for(int j = 0; j < Tamanoy; j++){
                    if (i == jugador.getX() && j == jugador.getY()){
                        writer.write('P');
                    } else if (i == asterion.getX() && j == asterion.getY()){
                        writer.write('M');
                    } else if (i == medusa.getX() && j == medusa.getY()) {
                        writer.write('G');
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
    private void minotauro(){
        //minotauro
        asterion = new Minotauro("asterion", "enemigo", 100, 10, Tamanox +1, Tamanoy +1);
    }
    private void medusa(){
        //gorgona
        medusa = new Gorgona("Medusa", "enemigo", 100, 10, Tamanox +1, Tamanoy +1);
    }
    private void hidra() {
        //hidra
        lernaean = new Hidra("lernaean", "enemigo",100,10,Tamanox +1,Tamanoy +1);
        Cabezas.totalcabezas = rand.nextInt(4)+1;
    }
    public static void resetEnemigos(){
        asterion.setX(Tamanox+1);
        asterion.setY(Tamanoy+1);
        medusa.setX(Tamanox+1);
        medusa.setY(Tamanoy+1);
        lernaean.setX(Tamanox+1);
        lernaean.setY(Tamanoy+1);
        Cabezas.cabeza.clear();
        activo=0;
    }
}
