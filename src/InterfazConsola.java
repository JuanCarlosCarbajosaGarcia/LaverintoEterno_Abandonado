import java.util.Scanner;

public class InterfazConsola implements IInterfaz{

    //colores
    public static final String RESET = "\u001B[0m";
    public static final String NEGRITA = "\u001B[1m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CIAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";
    public static final String GRIS = "\u001B[90m";
    public static final String ROJO_CLARO = "\u001B[91m";
    public static final String VERDE_CLARO = "\u001B[92m";

    private final Scanner interfacsc = new Scanner(System.in);

    @Override
    public void limpiarPantalla() {
        // Intentar limpiar la pantalla
        try {
            String sistema = System.getProperty("os.name");

            if (sistema!=null && sistema.toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }catch(Exception e){
            for (int i=0;i<50;i++) {
                System.out.println();
            }
        }
    }

    @Override
    public void mostrarBienvenida() {
        limpiarPantalla();
        System.out.println(CIAN + NEGRITA);
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                      ║");
        System.out.println("║                                                                      ║");
        System.out.println("║                    ╔═══════════════════════════╗                     ║");
        System.out.println("║                    ║  E L   L A B E R I N T O  ║                     ║");
        System.out.println("║                    ║        E T E R N O        ║                     ║");
        System.out.println("║                    ╚═══════════════════════════╝                     ║");
        System.out.println("║                                                                      ║");
        System.out.println("║      ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓      ║");
        System.out.println("║      ▓▓                                                      ▓▓      ║");
        System.out.println("║      ▓▓           Una aventura de supervivencia              ▓▓      ║");
        System.out.println("║      ▓▓                 contra el laverinto                  ▓▓      ║");
        System.out.println("║      ▓▓                                                      ▓▓      ║");
        System.out.println("║      ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓      ║");
        System.out.println("║                                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println(RESET);

        System.out.println("\n" + AMARILLO + NEGRITA + " Presiona ENTER para continuar" + RESET);
        interfacsc.nextLine();
    }

    @Override
    public void mostrarMenu() {
        System.out.println(CIAN + NEGRITA);
        System.out.println("╔════════════════════════════╗");
        System.out.println("║       MENÚ PRINCIPAL       ║");
        System.out.println("╠════════════════════════════╣");
        System.out.println("║  "+ VERDE +"1. JUGAR"+ CIAN +"                  ║");
        System.out.println("║  "+ AZUL +"2. INSTRUCCIONES"+ CIAN +"          ║");
        System.out.println("║  "+ ROJO +"3. SALIR"+ CIAN +"                  ║");
        System.out.println("╚════════════════════════════╝");
        System.out.println(RESET);

        System.out.print("\n"+ NEGRITA+"Elige una opción: " + RESET);
    }

    @Override
    public void mostrarControles() {
        System.out.println(CIAN + NEGRITA);
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                         INSTRUCCIONES                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                              ║");
        System.out.println("║  " + VERDE + "OBJETIVO:" + CIAN + "                                                   ║");
        System.out.println("║  Escapa del laberinto evitando al los enemigos.              ║");
        System.out.println("║  Cada nivel tiene una salida (S) que debes encontrar.        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  " + AMARILLO + "  CONTROLES:" + CIAN + "                                                ║");
        System.out.println("║  ┌─────────┬──────────────────────────────────────┐          ║");
        System.out.println("║  │   W     │  Moverse hacia ARRIBA                │          ║");
        System.out.println("║  │   S     │  Moverse hacia ABAJO                 │          ║");
        System.out.println("║  │   A     │  Moverse hacia la IZQUIERDA          │          ║");
        System.out.println("║  │   D     │  Moverse hacia la DERECHA            │          ║");
        System.out.println("║  │   Q     │  Volver al menú principal            │          ║");
        System.out.println("║  └─────────┴──────────────────────────────────────┘          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  " + MAGENTA + "SÍMBOLOS:" + CIAN + "                                                   ║");
        System.out.println("║  ┌─────────┬──────────────────────────────────────┐          ║");
        System.out.println("║  │   P     │  TU PERSONAJE (Jugador)              │          ║");
        System.out.println("║  │   M     │  EL MINOTAURO (Enemigo)              │          ║");
        System.out.println("║  │   G     │  LA GORGONA   (Enemigo)              │          ║");
        System.out.println("║  │   C     │  COFRE (oro)                         │          ║");
        System.out.println("║  │   E     │  ENTRADA del laberinto               │          ║");
        System.out.println("║  │   S     │  SALIDA del laberinto (objetivo)     │          ║");
        System.out.println("║  │   █     │  PARED (No se puede pasar)           │          ║");
        System.out.println("║  │  ' '    │  CAMINO LIBRE                        │          ║");
        System.out.println("║  └─────────┴──────────────────────────────────────┘          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  " + ROJO + "COMBATE:" + CIAN + "                                                    ║");
        System.out.println("║           Si te encuentras con cualquier enemigo,            ║");
        System.out.println("║             ¡combatirás! ambos sufrireis daño                ║");
        System.out.println("║               ¡Sobrevive todo lo que puedas!                 ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println(RESET);
        System.out.println("presiona enter para continuar");
        interfacsc.nextLine();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(BLANCO+mensaje+RESET);
    }

    @Override
    public void mostrarError(String mensaje) {
        System.out.println(ROJO+NEGRITA+mensaje+RESET);
    }

    @Override
    public void mostrarExito(String mensaje) {
        System.out.println("\n" + VERDE + NEGRITA + mensaje + RESET);
    }

    @Override
    public void mostrarInfo(String mensaje) {
        System.out.println(AZUL + mensaje + RESET);
    }

    @Override
    public String pedirLinea(String mensaje) {
        System.out.println(VERDE_CLARO + mensaje + RESET);
        return interfacsc.nextLine();
    }

    @Override
    public int pedirNumero(String mensaje) {
        while(true){
            try{
                System.out.println(VERDE_CLARO + mensaje + RESET);
                return interfacsc.nextInt();
            }catch (Exception e){
                interfacsc.nextLine();
                mostrarError("por favor ingrese un numero valido" + e.getMessage());
            }
        }
    }

    @Override
    public void mostrarVida(int vida, int vidaMax){
        int longitud = 15;
        int segmento = (int) ((double)vida/vidaMax*longitud);

        StringBuilder Vida = new StringBuilder("[");
        for (int i =0; i<longitud;i++){
            if (i<segmento){
                Vida.append(ROJO).append("█").append(RESET);
            }else {
                Vida.append(GRIS).append("░").append(RESET);
            }
        }
        Vida.append("]");

        System.out.println("vida: " + Vida + " " + vida + "/" + vidaMax);
    }

    @Override
    public void mostrarInfo(int nivel,int vida,int vidaMax, int oro, int dano){
        System.out.println("\n" + CIAN + NEGRITA + "═══════════════════════════════════════" + RESET);
        System.out.println(CIAN + "  NIVEL: " + ROJO_CLARO + nivel + RESET);
        mostrarVida(vida,vidaMax);
        System.out.println();
        System.out.println(CIAN + "  ORO: " + AMARILLO + oro + RESET);
        System.out.println(CIAN + "  DAÑO: " + ROJO + dano + RESET);
        System.out.println(CIAN + NEGRITA + "═══════════════════════════════════════" + RESET);
    }
    @Override
    public void mostrarSalida() {
        System.out.println(CIAN + NEGRITA);
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   ¡ G R A C I A S   P O R         ║");
        System.out.println("║           "+ROJO_CLARO+"J U G A R !"+CIAN+"             ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.println(RESET);
    }
}
