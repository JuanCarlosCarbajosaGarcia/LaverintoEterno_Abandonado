static Scanner sc = new Scanner(System.in);
static boolean juegoA = true;
static IInterfaz interfaz = new InterfazConsola();

void main() {
    interfaz.mostrarBienvenida();
    mostrarMenu();
}

static void mostrarMenu() {
    while (juegoA) {
        interfaz.mostrarMenu();

        try{
            int opcion = sc.nextInt();
            sc.nextLine();

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
                    interfaz.mostrarError("opcion invalida");
            }
        }catch (Exception e){
            sc.nextLine();
            interfaz.mostrarError("introduce un numero");
        }
    }
}

static void iniciarJuego() {
    new Laverinto();
}

static void mostrarControles() {
    interfaz.mostrarControles();
}

static void mostrarSalida() {
    interfaz.mostrarSalida();
}
