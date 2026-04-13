import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Laverinto implements ILaberinto, IJuego{
    private static final int Tamanox = 20;
    private static final int Tamanoy = 50;
    private static final int Max_intentos = 100;

    private final char[][] mapa = new char[Tamanox][Tamanoy];
    private final Random rand = new Random(System.currentTimeMillis());
    private final Random enemigos = new Random();
    private final Scanner laverintosc = new Scanner(System.in);

    private final IInterfaz interfaz = new InterfazConsola();

    private final Jugador jugador;
    private Minotauro minos;
    private Gorgona medusa;
    private int nivel = 1;
    private boolean juego = true;
    private boolean primerJuego = true;
    private final List<Tesoro> tesoros = new ArrayList<>();
    private static final int minpasos = 1;
    private static final int maxpasos = 9999;

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

        //iniciar enemigo
        minotauro();
        medusa();

        //bucle de juego
        while(juego){
            iniciarNivel();
        }
    }

    @Override
    public void iniciarNivel() {
        interfaz.mostrarMensaje("\n=== INICIANDO NIVEL " + nivel + " ===");

        //generar laverinto
        boolean posible;
        int intentos = 0;

        do {
            generarMapa();
            int pasos=contarPasos();
            posible = esPosible() && pasos >= minpasos && pasos <= maxpasos;
            intentos++;

        } while (!posible && intentos < Max_intentos);

        if (!posible) {
            caminosE();
            return;
        }


        //colocar los enemigos del nivel
        colocarEnemigos();

        //colocar los cofres minimo 1
        Cofres();

        //resetear jugador
        jugador.setX(0);
        jugador.setY(1);

        //bucle del nivel
        boolean nivelActivo = true;

        while (nivelActivo && juego) {
            interfaz.limpiarPantalla();

            mostrarMapa();
            interfaz.mostrarInfo(nivel, jugador.getVida(), jugador.getVidaMax(), jugador.getOro(), jugador.getDano());

            //termino el nivel el jugador
            if (mapa[jugador.getX()][jugador.getY()] == 'S') {
                interfaz.mostrarExito("¡Felicidades! Sobreviviste el nivel " + nivel);
                nivel++;
                Random num = new Random();
                int premio = num.nextInt(5) + 1;
                jugador.setOro(jugador.getOro() + premio);
                interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "Ganaste: " + premio + " de Oro" + InterfazConsola.RESET);

                guardarMapa();
                resetEnemigos();

                //ir al vendedor
                Vendedor vendedor = new Vendedor(nivel);
                boolean volveraMenu = vendedor.mostrarTienda(jugador);

                if (volveraMenu) {
                    interfaz.mostrarMensaje("voviendo al menu principal");
                    juego = false;
                    return;
                }

                nivelActivo = false;
                minos.curar();
                medusa.curar();
                continue;
            }

            //ataque de enemigo (minos)
            if (jugador.getX() == minos.getX() && jugador.getY() == minos.getY()) {
                interfaz.mostrarMensaje("Minos te a atacado");

                int danoRecibido = minos.getDano();
                int danoHecho = jugador.getDano();

                jugador.setVida(jugador.getVida() - danoRecibido);
                interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

                minos.setVida(minos.getVida() - danoHecho);

                if (jugador.estaVivo()) {
                    //jugador sobrevive
                    interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño al enemigo.");
                    interfaz.mostrarExito("has escapado con vida.");
                    separar();
                } else {
                    //jugador muere
                    interfaz.mostrarError("Minos te ha devorado");
                    interfaz.mostrarMensaje("alcanzaste el nivel: " + nivel);
                    interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + jugador.getOro() + InterfazConsola.RESET);
                    guardarMapa();
                    juego = false;
                    return;
                }

                if (minos.getVida() == 0) {
                    //minos muere
                    minos.aumentarMuertes(1);
                    interfaz.mostrarMensaje("Minos a muerto");
                    dificultad();
                    minos.setX(mapa.length + 1);
                    minos.setY(mapa.length + 1);
                }
            }

            if (jugador.getX() == medusa.getX() && jugador.getY() == medusa.getY()) {
                interfaz.mostrarMensaje("Medusa te a atacado");

                int danoRecibido = medusa.getDano();
                int danoHecho = jugador.getDano();

                jugador.setVida(jugador.getVida() - danoRecibido);
                interfaz.mostrarMensaje("reciviste " + danoRecibido + " de daño.");

                medusa.setVida((medusa.getVida() - danoHecho));

                if (jugador.estaVivo()) {
                    //jugador sobrevive
                    interfaz.mostrarMensaje("le hiciste " + danoHecho + " de daño al enemigo.");
                    interfaz.mostrarExito("has escapado con vida.");
                    separar();
                }else {
                    //jugador muere
                    interfaz.mostrarMensaje("Medusa te ha petrificado");
                    interfaz.mostrarMensaje("alcanzaste el nivel: " + nivel);
                    interfaz.mostrarMensaje(InterfazConsola.AMARILLO + "oro total: " + jugador.getOro() + InterfazConsola.RESET);
                    guardarMapa();
                    juego = false;
                    return;
                }

                if (medusa.getVida() == 0) {
                    //medusa muere
                    medusa.aumentarMuertes(1);
                    interfaz.mostrarMensaje("Medusa a muerto");
                    dificultad();
                    medusa.setX(mapa.length + 1);
                    medusa.setY(mapa.length + 1);
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

            //movimiento Minos
            minos.mover(mapa, rand, mapa.length, mapa[0].length);
            //Movimiento Medusa
            medusa.mover(mapa, rand, mapa.length, mapa[0].length);
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

    private void separar(){
                jugador.setX(0);
                jugador.setY(1);
                mostrarMapa();
    }

    private void colocarEnemigos(){
            boolean posicionValida = false;
            while (!posicionValida) {

                int newX = rand.nextInt(Tamanox);
                int newY = rand.nextInt(Tamanoy);

                int distancia = Math.abs(newX - jugador.getX()) + Math.abs(newY - jugador.getY());
                int entrada = mapa[0][1];

                //verificar la posicion
                if (mapa[newX][newY] != '#' && distancia >= 5 && mapa[newX][newY] != entrada) {
                    switch (enemigos.nextInt(2)+1) {
                        case 1:
                                minos.setX(newX);
                                minos.setY(newY);
                        break;
                        case 2:
                                medusa.setX(newX);
                                medusa.setY(newY);
                        break;
                    }
                    posicionValida = true;
                }
            }
    }

    //generar el mapa
    public void generarMapa(){
        mapaB();
        //caminoD();
        complejo();
        mapaF();
    }


    public void mapaB() {
        for (int i = 0; i < Tamanox; i++) {
            Arrays.fill(mapa[i], '#');
        }
        caminoP();

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

    //generar el camino
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
    //generar mas posibilidades

    private void caminosE(){
        for (int i = 0; i < Tamanox; i++) {
            mapa[i][1] = ' ';
        }
    }

    private void complejo(){
        Set<String> celVisi = new HashSet<>();
        for (int i = 1; i < Tamanox-2; i++) {
                if (mapa[i][1] == ' ' && !celVisi.contains(i + ",1")) {
                    if (rand.nextDouble() < 0.8){
                        caminoR(i,1,celVisi);
                    }
                }
        }
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
                if (nx > 1 && nx < Tamanox - 1 && ny > 1 && ny < Tamanoy - 1 && mapa[nx][ny] == '#') {
                    mapa[nx][ny] = ' ';
                    mapa[(x + nx) / 2][(y + ny) / 2] = ' ';
                    caminoR(nx, ny, Visi);
                    break;
                }
            }
            contador++;
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
            mapa[i][0] = '#';
            mapa[i][Tamanoy-1] = '#';
        }
        for (int j = 0; j < Tamanoy; j++) {
            mapa[0][j] = '#';
            mapa[Tamanox-1][j] = '#';
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
        int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};

        while (!cola.isEmpty()) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                int[] actual = cola.poll();
                assert actual != null;
                int x = actual[0], y = actual[1];

                if (x==Tamanox-1 && y==1){
                    return pasos;
                }
                for (int d = 0; d < 4; d++){
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if (nx >=0 && nx<Tamanox && ny >= 0 && ny< Tamanoy && mapa[nx][ny] != '#' && !visi[nx][ny]){
                        visi[nx][ny] = true;
                        cola.add(new int[]{nx,ny});
                    }
                }
            }
            pasos++;
        }
        return -1;
    }

    @Override
    public boolean esPosible(){
        return contarPasos() != -1;
    }

    @Override
    public void mostrarMapa(){
        System.out.println("\n"+ InterfazConsola.CIAN+"Mapa del laverinto:");
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                if (i == jugador.getX() && j == jugador.getY()){
                    System.out.print(InterfazConsola.VERDE+'P'+InterfazConsola.RESET);
                }else if (i == minos.getX() && j == minos.getY()) {
                    System.out.print(InterfazConsola.ROJO + 'M' + InterfazConsola.RESET);
                }else if (i == medusa.getX() && j == medusa.getY()) {
                    System.out.print(InterfazConsola.ROJO + 'G' + InterfazConsola.RESET);
                }else if (mapa[i][j]=='E') {
                    System.out.print(InterfazConsola.AZUL+'E'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='S') {
                    System.out.print(InterfazConsola.VERDE_CLARO+'S'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='#') {
                    System.out.print(InterfazConsola.GRIS+'#'+InterfazConsola.RESET);
                } else if (mapa[i][j]=='C') {
                    System.out.print(InterfazConsola.AMARILLO+'C'+InterfazConsola.RESET);
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
                    } else if (i == minos.getX() && j == minos.getY()){
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
        minos = new Minotauro("Minos", "enemigo", 100, 10, Tamanox +1, Tamanoy +1);
    }
    private void medusa(){
        //gorgona
        medusa = new Gorgona("Medusa", "enemigo", 100, 10, Tamanox +1, Tamanoy +1);
    }
    private void dificultad(){
        int vid = 150;
        int dan = 10;

        int muertesvid;
        int muertesdan;

        if (minos.muertes <= 1) {
            muertesvid = vid;
            minos.setVida(muertesvid);
            muertesdan = dan;
            minos.setDano(muertesdan);
        } else {
            muertesvid = minos.muertes * vid;
            minos.setVida(muertesvid);
            muertesdan = minos.muertes * dan;
            minos.setDano(muertesdan);
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
    }
    private void Cofres() {
        tesoros.clear();
        int numcofres = rand.nextInt(10)+1;

        for (int i = 0; i < numcofres; i++) {
            boolean CofreValido = false;

            while (!CofreValido) {
                int newX = rand.nextInt(Tamanox);
                int newY = rand.nextInt(Tamanoy);

                int distancia = Math.abs(newX - jugador.getX()) + Math.abs(newY - jugador.getY());
                int entrada = mapa[0][1];

                //verificar la posicion
                if (mapa[newX][newY] != '#' && distancia >= 10 && mapa[newX][newY] != entrada && !(newX == jugador.getX() && newY == jugador.getY())) {
                    Tesoro nuevotesoro = new Tesoro("cofre", "Tesoro", 1, 0, newX,newY);
                    tesoros.add(nuevotesoro);
                    mapa[newX][newY] = 'C';

                    CofreValido = true;
                }
            }
        }
    }
    private void resetEnemigos(){
        minos.setX(Tamanox+1);
        minos.setY(Tamanoy+1);
        medusa.setX(Tamanox+1);
        medusa.setY(Tamanoy+1);
    }
}
