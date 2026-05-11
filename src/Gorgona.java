public class Gorgona extends Entidad{
    private int x,y;

    public Gorgona(String nombre, String Tipo, int vida, int dano, int starX, int starY){

        super(nombre, Tipo, vida, dano);

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
