import main.java.org.juego.InterfazConsola;
import main.java.org.juego.Laverinto;
import main.java.org.juego.PantallaGUI;

static Scanner mainsc = new Scanner(System.in);
static boolean juegoA = true;


void main(String[] args) {
    //preguntar vista
    mostrarInterfaz();
}

static void mostrarInterfaz() {
    System.out.println(InterfazConsola.CIAN + InterfazConsola.NEGRITA);
    System.out.println("╔═══════════════════════════════════╗");
    System.out.println("║   E L   L A B E R I N T O         ║");
    System.out.println("║         E T E R N O               ║");
    System.out.println("╠═══════════════════════════════════╣");
    System.out.println("║  1. MODO CONSOLA                  ║");
    System.out.println("║  2. MODO GRAFICO (JavaFX)         ║");
    System.out.println("╚═══════════════════════════════════╝");
    System.out.println(InterfazConsola.RESET);

    System.out.print("\nElige una opcion: ");

    try {
        int opcion = mainsc.nextInt();
        switch (opcion) {
            case 1:
                Laverinto.interfaz.mostrarBienvenida();
                mostrarMenu();
                break;//modo consola
            case 2:
                PantallaGUI.iniciarJavaFX();
                break;//modo grafico
            default:
                System.out.println("opcion invalida");
                mostrarInterfaz();
        }
    } catch (Exception e){
        System.out.println("opcion invalida");
        mainsc.nextLine();
        mostrarInterfaz();
    }
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
