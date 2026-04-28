import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cabezas extends Entidad{
    private int x;
    private int y;

    public Cabezas(String nombre, String tipo, int vida, int dano, int starX, int starY){
        super(nombre, tipo, vida, dano);
        this.x=starX;
        this.y=starY;
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

    public int getX() {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
