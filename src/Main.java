static Scanner mainsc = new Scanner(System.in);
static boolean juegoA = true;


void main() {
    Laverinto.interfaz.mostrarBienvenida();
    mostrarControles();
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
                    mostrarControles();
                    break;
                case 3:
                    mostrarSalida();
                    System.exit(0);
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
    new Laverinto();
}

static void mostrarControles() {
    Laverinto.interfaz.mostrarControles();
}

static void mostrarSalida() {
    Laverinto.interfaz.mostrarSalida();
}
