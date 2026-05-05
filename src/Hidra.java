public class Hidra extends Entidad{
    private int x,y;

    public Hidra(String nombre, String tipo, int vida, int dano, int starX, int starY) {

        super(nombre, tipo, vida, dano);

        this.x=starX;
        this.y=starY;

        this.vidaMax=vida;
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
