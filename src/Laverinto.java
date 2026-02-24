import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Laverinto{
    private static final int Tamanox = 10;
    private static final int Tamanoy = 20;
    private static final int Max_intentos = 100;
    private char[][] mapa = new char[Tamanox][Tamanoy];
    private Random rand = new Random(System.currentTimeMillis());
    private Scanner scanner = new Scanner(System.in);
    private Jugador jugador;
    private Enemigo enemigo;

    public Laverinto(){
        //nombre del jugador
        System.out.println("ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine();

        //iniciar el jugador
        jugador = new Jugador(nombre,"jugador",10,1,0,1);

        int intentos = 0;
        boolean Posible = false;
        do{
            generarMapa();
            Posible = esPosible();
            intentos++;
            if (!Posible && intentos < Max_intentos){
                System.out.println("Generando laverinto...  (intento " + intentos + ")");
            }
        }while(!Posible && intentos <Max_intentos);

        if (!Posible){
            System.err.println("no se pudo generar el laverinto");
            scanner.close();
            return;
        }

        //iniciar a Minos
        enemigo = new Enemigo("Minos","enemigo",100,10,Tamanox/2,Tamanoy/2);

        colocarEnemigos();

        while (true){
            mostrarMapa();
            if (mapa[jugador.getX()][jugador.getY()] == 'S'){
                System.out.println("felicidades " + nombre + " escapastes");
                guardarMapa();
                break;
            }
            if (jugador.getX() == enemigo.getX() && jugador.getY() == enemigo.getY()){
                System.out.println("El minotauro te a devorado");
                break;
            }
            jugador.mover(mapa,scanner);//movimiento jugador
            enemigo.mover(mapa,rand, mapa.length, mapa[0].length);//movimiento Minos
        }
        scanner.close();
    }
    private void colocarEnemigos(){
        boolean posicionValida = false;
        while(!posicionValida){
            int newX = rand.nextInt(Tamanox);
            int newY = rand.nextInt(Tamanoy);

            //verificar la posicion
            if (mapa[newX][newY]!='#' &&
                !(newX == jugador.getX() && newY == jugador.getY())){
                enemigo.setX(newX);
                enemigo.setY(newY);
                posicionValida = true;
            }
        }
    }

    //generar el mapa
    private void generarMapa(){
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

    private boolean esPosible(){
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

    private void mostrarMapa(){
        System.out.println("Mapa del laverinto: ");
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                if (i == jugador.getX() && j == jugador.getY()){
                    System.out.print('P');
                }else if (i == enemigo.getX() && j == enemigo.getY()){
                    System.out.print('M');
                }else {
                    System.out.print(mapa[i][j]);
                }
            }
            System.out.println("|"); //borde derecho
        }
    }
    private void guardarMapa(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("UltimoMapa.txt"))){
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
            System.out.println("Mapa guardado correctamente");
        }catch (IOException e){
            System.out.println("Error guardando mapa: " + e.getMessage());
        }
    }
}