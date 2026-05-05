import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minotauro extends Entidad{
    private int x,y;

    public Minotauro(String nombre, String Tipo, int vida, int dano, int starX, int starY) {

        super(nombre, Tipo, vida, dano);

        this.x=starX;
        this.y=starY;

        this.vidaMax=vida;
    }

    public void mover(char[][] mapa, Random rand, int maxX, int maxY) {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        //moverse
        List<Integer> movimiento = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY && mapa[newX][newY] != '█') {
                movimiento.add(i);
            }
        }
        //elegir una direcion valida al azar
        if (!movimiento.isEmpty()) {
            int dir = movimiento.get(rand.nextInt(movimiento.size()));
            x += dx[dir];
            y += dy[dir];
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
