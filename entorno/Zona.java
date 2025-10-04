package entorno;

import objetos.ItemTipo;
import player.Jugador;
import java.util.EnumSet;
import java.util.Scanner;

/*
 * Clase abstracta que representa una zona del juego
 */
public abstract class Zona {
    public String nombre;
    private int profundidadMin;
    private int profundidadMax;
    private EnumSet<ItemTipo> recursos;

    public Zona(String nombre, int profundidadMin, int profundidadMax, EnumSet<ItemTipo> recursos) {
        this.nombre = nombre;
        this.profundidadMin = profundidadMin;
        this.profundidadMax = profundidadMax;
        this.recursos = recursos;
    }

    public int getProfundidadMin() {
        return profundidadMin;
    }

    public int getProfundidadMax() {
        return profundidadMax;
    }

    public EnumSet<ItemTipo> getRecursos() {
        return recursos;
    }

    // Métodos a implementar en las subclases
    public abstract void entrar(Jugador jugador);
    public abstract void explorar(Jugador jugador, Scanner sc);
}
