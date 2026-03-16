import java.util.Scanner;

public class Jugador extends Entidad{
    private int x,y;
    private Scanner scanner;
    private boolean menuD=false;

    public Jugador(String nombre, String Tipo, int vida,int dano, int starX, int starY) {

        super(nombre, Tipo, vida,dano);

        this.x = starX;
        this.y = starY;
        this.scanner = new Scanner(System.in);
    }

    public boolean mover(char[][] mapa, Scanner sc){
        System.out.println("que quieres hacer: ");
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
            case "p": //abrir menu desarrollo
                menuD=true;
                while(menuD) {
                    System.out.println("menu");
                    System.out.println("1: tienda");
                    System.out.println("2: reiniciar");
                    System.out.println("3: salir");
                    int menud = scanner.nextInt();
                    switch (menud) {
                        case 1:
                            Vendedor vendedor = new Vendedor(1);
                            boolean volver = vendedor.mostrarTienda(this);
                            if (volver) return true;
                            break;
                        case 2:
                            new Laverinto();
                            break;
                        case 3:
                            menuD = false;
                    }
                }
                break;
            case "q": //volver al menu
                System.out.println("volviendo al menu");
                return true;
            default: System.out.println("movimiento invalido");
                return false;
        }

        //asegurarse de que el movimiento es posible
        if (newX >= 0 && newX < mapa.length && newY >= 0 && newY < mapa[0].length && mapa[newX][newY] != '#'){
            x = newX;
            y = newY;
        }else {
            System.out.println("eso es una pared");
        }
        return false;
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
