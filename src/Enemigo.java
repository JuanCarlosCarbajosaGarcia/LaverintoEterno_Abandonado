import java.util.Random;

public class Enemigo extends entidad{
    private int x,y;

    public Enemigo(String nombre, String Tipo, int vida, int dano, int starX, int starY) {
        super(nombre, Tipo, vida, dano);

        setNombre("Minos");
        setTipo("Enemigo");
        setDano(10);
        setVida(1000);

        this.x=starX;
        this.y=starY;
    }

    public void mover(char[][] mapa, Random rand){
        int[] dx ={-1,0,1,0};
        int[] dy={0,1,0,-1};
        int dir = rand.nextInt(4);
        int newX = x + dx[dir];
        int newY = y + dy[dir];
        if (newX >=0 && newX < mapa.length && newY >= 0 && newY < mapa[0].length && mapa[newX][newY]!='#'){
            x=newX;
            y=newY;
        }
        //evitar moverse a las paredes
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

    @Override
    public String toString() {
        return super.toString();
    }
}