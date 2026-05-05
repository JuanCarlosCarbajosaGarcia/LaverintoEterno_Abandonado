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
