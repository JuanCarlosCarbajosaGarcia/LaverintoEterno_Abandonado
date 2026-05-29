package main.java.org.juego;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class PantallaGUI extends Application {

    private boolean mover = false;

    private static final int CELDA = 25;

    private Canvas lienzo;
    private GraphicsContext gc;
    private Label etiqueta;

    //referenciar laverinto
    private Laverinto juego;

    @Override
    public void start(Stage etapas) {

        //configurar ventana
        etapas.setTitle("El laberinto Eterno");
        etapas.setResizable(false);

        //configurar ventana
        int anchoCanvas = Laverinto.Tamanoy * CELDA;
        int altoCanvas = Laverinto.Tamanox * CELDA + 60;

        //crear UI
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1a1a;");

        lienzo = new Canvas(anchoCanvas, altoCanvas);
        gc = lienzo.getGraphicsContext2D();

        //centrar
        BorderPane centro = new BorderPane(lienzo);
        centro.setStyle("-fx-background-color: #1a1a1a;");

        //etiquetar info
        etiqueta = new Label();
        etiqueta.setStyle(
            "-fx-background-color: #2a2a2a; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px;"
        );
        etiqueta.setMinHeight(50);
        etiqueta.setAlignment(Pos.CENTER_LEFT);
        etiqueta.setPadding(new Insets(10));

        root.setCenter(centro);
        root.setBottom(etiqueta);

        //escena
        Scene escena = new Scene(root);
        etapas.setScene(escena);

        //teclado
        escena.setOnKeyPressed(this::Teclado);

        etapas.show();

        //iniciar juego en javaFX
        iniciarJuego();

        //iniciar bucle renderizado
        inibuclejuego();
    }

    private void iniciarJuego() {
        //crear el juego en un hilo separado
        Thread hiloJuego = new Thread(() -> {
            //iniciar juego
            juego = new Laverinto();
        });
        hiloJuego.setDaemon(true);
        hiloJuego.start();

        boolean juegoActivo = true;
    }

    private void inibuclejuego() {
        Thread bucle = new Thread(() -> {
            while (juego!=null && juego.estaActivo()){
                try {
                    Thread.sleep(100);
                    Platform.runLater(this::actualizarPantalla);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bucle.setDaemon(true);
        bucle.start();
    }

    private void actualizarPantalla() {
        //limpiar lienzo/canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, lienzo.getWidth(), lienzo.getHeight());

        if (Laverinto.jugador == null) return;

        //dibujar laberinto, entidades e info
         dibuLaverinto();
         dibuEntidades();
         actuInfo();
    }

    private void dibuLaverinto() {
        for (int i = 0; i < Laverinto.Tamanox; i++) {
            for (int j = 0; j < Laverinto.Tamanoy; j++) {
                char celda = Laverinto.mapa[i][j];

                int x = j * CELDA;
                int y = i * CELDA;

                switch (celda) {
                    case '█': // Pared
                        gc.setFill(Color.rgb(80, 80, 80));
                        gc.fillRect(x, y, CELDA, CELDA);
                        gc.setStroke(Color.rgb(60, 60, 60));
                        gc.strokeRect(x, y, CELDA, CELDA);
                        break;
                    case ' ': // Pasillo
                        gc.setFill(Color.BLACK);
                        gc.fillRect(x, y, CELDA, CELDA);
                        break;
                    case 'E': // Entrada
                        gc.setFill(Color.rgb(50, 100, 200));
                        gc.fillRect(x, y, CELDA, CELDA);
                        gc.setFill(Color.WHITE);
                        gc.fillText("E", x + 8, y + 17);
                        break;
                    case 'S': // Salida
                        gc.setFill(Color.rgb(50, 200, 50));
                        gc.fillRect(x, y, CELDA, CELDA);
                        gc.setFill(Color.BLACK);
                        gc.fillText("S", x + 8, y + 17);
                        break;
                    case 'T': // Tesoro
                        gc.setFill(Color.BLACK);
                        gc.fillRect(x, y, CELDA, CELDA);
                        gc.setFill(Color.YELLOW);
                        gc.fillRect(x + 5, y + 5, CELDA - 10, CELDA - 10);
                        break;
                }
            }
        }
    }

    private void dibuEntidades() {
        // Jugador (P) - convertir coordenadas del juego a pantalla
        if (Laverinto.jugador != null && Laverinto.jugador.estaVivo()) {
            int x = Laverinto.jugador.getY() * CELDA;
            int y = Laverinto.jugador.getX() * CELDA;

            gc.setFill(Color.rgb(0, 200, 100));
            gc.fillOval(x + 3, y + 3, CELDA - 6, CELDA - 6);
            gc.setStroke(Color.WHITE);
            gc.strokeOval(x + 3, y + 3, CELDA - 6, CELDA - 6);

            gc.setFill(Color.BLACK);
            gc.fillText("P", x + 8, y + 17);
        }

        // Minotauro (M)
        if (Laverinto.asterion.estaVivo()) {
            dibuEntidad(
                    Laverinto.asterion.getX(),
                    Laverinto.asterion.getY(),
                    "M",
                    Color.rgb(200, 50, 50)
            );
        }

        // Medusa (G)
        if (Laverinto.medusa.estaVivo()) {
            dibuEntidad(
                    Laverinto.medusa.getX(),
                    Laverinto.medusa.getY(),
                    "G",
                    Color.rgb(150, 50, 150)
            );
        }

        // Hidra (H)
        if (Laverinto.lernaean.estaVivo()) {
            dibuEntidad(
                    Laverinto.lernaean.getX(),
                    Laverinto.lernaean.getY(),
                    "H",
                    Color.rgb(50, 150, 50)
            );
        }

        // Cabezas de la Hidra
        if (Laverinto.lernaean.estaVivo()) {
            for (Cabezas cabeza : Cabezas.cabeza) {
                if (cabeza.getX() != Laverinto.Tamanox + 1) {
                    dibuEntidad(
                            cabeza.getX(),
                            cabeza.getY(),
                            "C",
                            Color.rgb(50, 180, 50)
                    );
                }
            }
        }
    }

    private void dibuEntidad(int xJuego, int yJuego, String texto, Color color) {
        int x = yJuego * CELDA;
        int y = xJuego * CELDA;

        gc.setFill(color);
        gc.fillRect(x + 3, y + 3, CELDA - 6, CELDA - 6);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x + 3, y + 3, CELDA - 6, CELDA - 6);

        gc.setFill(Color.WHITE);
        gc.fillText(texto, x + 8, y + 17);
    }

    private void actuInfo(){
        if (Laverinto.jugador == null) return;

        String info = String.format(
            "  NIVEL: %d  |  VIDA: %d/%d  |  ORO: %d  |  DAÑO: %d  |  W/A/S/D: Mover  |  Q: Salir",
            Laverinto.nivel,
            Laverinto.jugador.getVida(),
            Laverinto.jugador.getVidaMax(),
            Laverinto.jugador.getOro(),
            Laverinto.jugador.getDano()
        );

        etiqueta.setText(info);
    }

    private void Teclado(KeyEvent event){
        if (Laverinto.jugador == null || !Laverinto.nivelActivo) return;

        KeyCode codigo = event.getCode();

        //movimiento
        int dx=0, dy=0;

        switch (codigo){
            case UP:
            case W:
                dx = -1;
                mover = true;
                break;
            case DOWN:
            case S:
                dx = 1;
                mover = true;
                break;
            case LEFT:
            case A:
                dy = -1;
                mover = true;
                break;
            case RIGHT:
            case D:
                dy = 1;
                mover = true;
                break;
            case Q:
                // Salir al menu
                mostrarConfirmacionSalida();
                break;
            case P:
                // Menu de desarrollo
                abrirMenuDesarrollo();
                break;
            default:
                return;
        }

        if (mover) {
            moverJugador(dx,dy);
        }
    }

    private void moverJugador(int dx, int dy) {
        int nuevoX = Laverinto.jugador.getX() + dx;
        int nuevoY = Laverinto.jugador.getY() + dy;

        // Verificar limites
        if (nuevoX < 0 || nuevoX >= Laverinto.Tamanox ||
                nuevoY < 0 || nuevoY >= Laverinto.Tamanoy) {
            return;
        }

        // Verificar paredes
        char tile = Laverinto.mapa[nuevoX][nuevoY];
        if (tile == '█') {
            Mecanicas.movimiento();
            return;
        }

        // Mover jugador
        Laverinto.jugador.setX(nuevoX);
        Laverinto.jugador.setY(nuevoY);

        if (tile =='S') {
            Mecanicas.terminarNivel();//fin nivel
        } else if (tile =='T') {
            Mecanicas.Tesoros();//cofres
        }

        Mecanicas.movimiento();

        mover=false;
    }

    private void mostrarConfirmacionSalida() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("El Laberinto Eterno");
        alerta.setHeaderText("¿Quieres salir al menú principal?");
        alerta.showAndWait();

        if (alerta.getResult().getButtonData().isDefaultButton()) {
            Laverinto.juego = false;
            Platform.exit();
        }
    }

    private void abrirMenuDesarrollo() {
        Stage menu = new Stage();
        AtomicBoolean menuActivo = new AtomicBoolean(true);

        while (menuActivo.get() && Laverinto.juego) {
            // Mostrar menú
            Button tienda = new Button("Tienda");
            tienda.setOnAction(event1 -> {Vendedor vendedor = new Vendedor(0);});

            Button camino = new Button("Camino facil");
            camino.setOnAction(event2 -> Laverinto.caminoE());

            Button salir = new Button("Salir");
            salir.setOnAction(event3 -> menuActivo.set(false));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(tienda, 0, 0);
            gridPane.add(camino, 0, 1);
            gridPane.add(salir, 0, 2);
            Scene scene = new Scene(gridPane, 300, 300);

            menu.setTitle("Menu pruebas");
            menu.setScene(scene);
            menu.show();
        }
    }

    // Metodo para iniciar desde Main
    public static void iniciarJavaFX() {
        launch();
    }
}
