import main.java.org.juego.Laverinto;

static Scanner mainsc = new Scanner(System.in);
static boolean juegoA = true;


void main() {
    Laverinto.interfaz.mostrarBienvenida();
    mostrarMenu();
}

static void mostrarMenu() {
    while (juegoA) {

        Laverinto.interfaz.mostrarMenu();

        try{
            int opcion = mainsc.nextInt();
            mainsc.nextLine();

            switch (opcion) {
                case 1:
                    iniciarJuego();
                    break;
                case 2:
                    Laverinto.interfaz.mostrarControles();
                    break;
                case 3:
                    Laverinto.interfaz.mostrarSalida();
                    juegoA = false;
                    break;
                default:
                    Laverinto.interfaz.mostrarError("opcion invalida");
                    Laverinto.interfaz.mostrarMenu();
            }
        }catch (Exception e){
            Laverinto.interfaz.mostrarError("introduce una opcion valida");
            mainsc.nextLine();
        }
    }
}

static void iniciarJuego() {
    Laverinto.juego=true;
    Laverinto.nivelActivo=true;
    new Laverinto();
}
