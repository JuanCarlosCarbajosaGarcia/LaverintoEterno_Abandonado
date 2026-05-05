import java.util.ArrayList;
import java.util.List;

public class Tesoro extends Entidad{
    private int x,y;
    public static final List<Tesoro> tesoros = new ArrayList<>();

    public Tesoro(String nombre, String tipo, int vida, int dano, int starX, int starY){
        super(nombre, tipo, vida, dano);
        this.x=starX;
        this.y=starY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void Cofres() {
        tesoros.clear();
        int numcofres = Laverinto.rand.nextInt(10)+1;

        for (int i = 0; i < numcofres; i++) {
            boolean CofreValido = false;

            while (!CofreValido) {
                int newX = Laverinto.rand.nextInt(Laverinto.Tamanox);
                int newY = Laverinto.rand.nextInt(Laverinto.Tamanoy);

                int distancia = Math.abs(newX - jugador.getX()) + Math.abs(newY - jugador.getY());
                int entrada = mapa[0][1];

                //verificar la posicion
                if (mapa[newX][newY] != '█' && distancia >= 10 && mapa[newX][newY] != entrada && !(newX == jugador.getX() && newY == jugador.getY()) && mapa[newX][newY] != 'T') {
                    Tesoro nuevotesoro = new Tesoro("cofre", "Tesoro", 1, 0, newX,newY);
                    tesoros.add(nuevotesoro);
                    mapa[newX][newY] = 'T';

                    CofreValido = true;
                }
            }
        }
    }
}
