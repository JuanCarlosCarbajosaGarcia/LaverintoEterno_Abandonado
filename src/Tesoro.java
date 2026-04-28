public class Tesoro extends Entidad{
    private int x,y;

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

}
