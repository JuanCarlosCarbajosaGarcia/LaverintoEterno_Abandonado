static Scanner sc = new Scanner(System.in);
static boolean juegoA = true;

void main() {
    mostrarMenu();
}

static void mostrarMenu() {
    while (juegoA) {
        System.out.println("\n╔════════════════════════════╗");
        System.out.println("║       MENÚ PRINCIPAL       ║");
        System.out.println("╠════════════════════════════╣");
        System.out.println("║  1. JUGAR                  ║");
        System.out.println("║  2. INSTRUCCIONES          ║");
        System.out.println("║  3. SALIR                  ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("Elige una opción: ");

        int opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                iniciarJuego();
                break;
            case 2:
                mostrarControles();
                break;
            case 3:
                System.out.println("gracias por jugar!");
                System.exit(0);
            default:
                System.out.println("operacion invalida");
        }
    }
}

static void iniciarJuego() {
    new Laverinto();
}

static void mostrarControles() {
    System.out.println("\n===CONTROLES===");
    System.out.println("W. ir hacia arriba");
    System.out.println("S. ir hacia abajo");
    System.out.println("A. ir hacia la izquierda");
    System.out.println("D. ir hacia la derecha");
    System.out.println("Q. salir del juego");
    System.out.println("enter para continuar");
    sc.nextLine();
    sc.nextLine();
}