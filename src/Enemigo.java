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
        int newX = x + dy[dir];
        int newY = y + dx[dir];
        if (newX>=0 && newX<mapa[0].length && newY>=0 && newY<mapa.length && mapa[newY][newX]!='#'){
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
    @Override
    public String toString() {
        return super.toString();
    }
}