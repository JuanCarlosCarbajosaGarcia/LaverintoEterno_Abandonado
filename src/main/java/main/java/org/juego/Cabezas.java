package main.java.org.juego;

import java.util.ArrayList;
import java.util.List;

public class Cabezas extends Entidad {
    private int x;
    private int y;
    public static int totalcabezas;
    public static int cabezascortadas;
    public static final List<Cabezas> cabeza = new ArrayList<>();

    public Cabezas(String nombre, String tipo, int vida, int dano, int starX, int starY){
        super(nombre, tipo, vida, dano);
        this.x=starX;
        this.y=starY;
    }

    public static void cabezas() {
        cabeza.clear();

        if (totalcabezas >20) {
            totalcabezas = 20;
        } else {
            for (int numcabezas = 0; numcabezas < totalcabezas; numcabezas++) {
                boolean CabezaValido = false;
                while (!CabezaValido) {
                    int newX = Laverinto.rand.nextInt(Laverinto.Tamanox);
                    int newY = Laverinto.rand.nextInt(Laverinto.Tamanoy);

                    int distancia = Math.abs(newX - Laverinto.jugador.getX()) + Math.abs(newY - Laverinto.jugador.getY());
                    int entrada = Laverinto.mapa[0][1];

                    if (Laverinto.mapa[newX][newY] != '█' && distancia >= 10 && Laverinto.mapa[newX][newY] != entrada
                            && !(newX == Laverinto.jugador.getX() && newY == Laverinto.jugador.getY())
                            && Laverinto.mapa[newX][newY] != 'H'
                            && Laverinto.mapa[newX][newY] != 'C'
                            && Laverinto.mapa[newX][newY] != 'T') {
                        Cabezas nuevacabeza = new Cabezas("cabeza", "enemigo", 20, 5, newX, newY);
                        cabeza.add(nuevacabeza);
                        Laverinto.mapa[newX][newY] = 'C';

                        CabezaValido = true;
                    }
                }
            }
        }
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
