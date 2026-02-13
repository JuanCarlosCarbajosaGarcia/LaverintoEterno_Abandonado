import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Laverinto{
    private static final int Tamanox = 10;
    private static final int Tamanoy = 20;
    private char[][] mapa = new char[Tamanox][Tamanoy];
    private Random rand = new Random();

    public Laverinto(){
        do{generarMapa();}while(!Posible());
        mostrarMapa();
        guardarMapa();
    }

    private void generarMapa(){
        for(int i = 0; i < Tamanox; i++){
            Arrays.fill(mapa[i], '#');
        }

        camino(1,1);
        mapa[0][1] = ' ';
        mapa[Tamanox - 1][Tamanoy - 2]= 'S';
    }

    private void camino(int x, int y){
        mapa[x][y]=' ';
        int[] direccion = {1,2,3,4};
        shuffleArray(direccion);

        for(int dir: direccion){
            int nx = x,  ny = y;
            switch (dir){
                case 1:ny -=2; break;
                case 2:nx +=2; break;
                case 3:ny +=2; break;
                case 4:nx -=2; break;
            }

            if (nx >0 && nx < Tamanox -1 && ny > 0 && ny < Tamanoy -1 && mapa[nx][ny]=='#'){
                mapa[nx][ny] = ' ';
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

    private boolean Posible(){
    }

    private void mostrarMapa(){
        for(int i = 0; i < Tamanox; i++){
            for(int j = 0; j < Tamanoy; j++){
                System.out.print(mapa[i][j]);
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
                writer.newLine();
            }
            System.out.println("Mapa guardado correctamente");
        }catch (IOException e){
            System.out.println("Error guardando mapa" + e.getMessage());
        }
    }
}