import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Laverinto{
    private static final int Tamanox = 15;
    private static final int Tamanoy = 25;
    private static final int Max_intentos = 100;
    private char[][] mapa = new char[Tamanox][Tamanoy];
    private Scanner scanner = new Scanner(System.in);
    private Random rand = new Random(System.currentTimeMillis());
    private Enemigo enemigo = new Enemigo("Minos","Enemigo",100,10,Tamanox /2,Tamanoy /2);

    public Laverinto(){
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
            return;
        }

        while (true){
            mostrarMapa();
            if (mapa[jugadorX][jugadorY] == 'S'){
                System.out.println("felicidades escapastes");
                guardarMapa();
                break;
            }
            if (jugadorX == enemigo.getX() $$ jugadorY == enemigo.getY()){
                System.out.println("El minotauro te a comido");
                break;
            }
            mover();
            enemigo.mover(mapa,rand);
        }
    }

    private void generarMapa(){
        for(int i = 0; i < Tamanox; i++){
            Arrays.fill(mapa[i], '#');
        }

        camino(1,1);
        for (int i = 0; i < Tamanox; i++){
            mapa[i][Tamanoy-1] = '#';
        }
        for (int j = 0; j < Tamanoy; j++){
            mapa[Tamanox-1][j] = '#';
        }
        mapa[0][1] = ' '; //entrada
        mapa[Tamanox - 1][1]= 'S'; //salida
    }

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
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                if (i == jugadorX && j == jugadorY){
                    System.out.println('P');
                }else if (i == enemigo.getX() && j == enemigo.getY()){
                    System.out.println('M');
                }else {
                    System.out.println(mapa[i][j]);
                }
            }
            System.out.println();
        }
    }
    private void guardarMapa(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("UltimoMapa.txt"))){
            for(int i = 0; i < Tamanox; i++){
                for(int j = 0; j < Tamanoy; j++){
                    writer.write(String.valueOf(mapa[i][j]));
                }
                if (i < Tamanox - 1){
                    writer.newLine();
                }
            }
            System.out.println("Mapa guardado correctamente");
        }catch (IOException e){
            System.out.println("Error guardando mapa: " + e.getMessage());
        }
    }
}