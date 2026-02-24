import java.util.Scanner;

public class Jugador extends entidad {
    private int x,y;

    public Jugador(String nombre, String Tipo, int vida,int dano, int starX, int starY) {

        super(nombre, Tipo, vida,dano);

        this.x = starX;
        this.y = starY;
    }

    public void mover(char[][] mapa,Scanner sc){
        System.out.println("movimiento (w/a/s/d) para moverte arriva/izquierda/abajo/derecha, q para salir: ");
        String input = sc.nextLine().toLowerCase();

        int newX = x, newY = y;
        switch (input){
            case "w": newX--;
                break; //arriva
            case "s": newX++;
                break; //abajo
            case "a": newY--;
                break; //izquierda
            case "d": newY++;
                break; //derecha
            case "q": System.exit(0);
            case "p": Vendedor vendedor = new Vendedor(0);vendedor.mostrarTienda(Jugador.this);
                break;
            default: System.out.println("movimiento invalido");
                return;
        }

        //asegurarse de que el movimiento es posible
        if (newX >= 0 && newX < mapa.length && newY >= 0 && newY < mapa[0].length && mapa[newX][newY] != '#'){
            x = newX;
            y = newY;
        }else {
            System.out.println("eso es una pared");
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

    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}