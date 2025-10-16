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

    protected int nmin;
    protected int nmax;
    /* (Constructor de Zona)
    * Inicializa una zona con su nombre, rango de profundidad, recursos disponibles
    * y parámetros mínimos y máximos de producción.
    * @param nombre: String nombre de la zona.
    * @param profundidadMin: int profundidad mínima permitida.
    * @param profundidadMax: int profundidad máxima permitida.
    * @param recursos: EnumSet<ItemTipo> conjunto de recursos disponibles en la zona.
    * @param nmin: int cantidad mínima base de recursos recolectables.
    * @param nmax: int cantidad máxima base de recursos recolectables.
    * @return void
    */
    public Zona(String nombre, int profundidadMin, int profundidadMax, EnumSet<ItemTipo> recursos, int nmin, int nmax) {
        this.nombre = nombre;
        this.profundidadMin = profundidadMin;
        this.profundidadMax = profundidadMax;
        this.recursos = recursos;
        this.nmin = nmin;
        this.nmax = nmax;
    }
    /* (getProfundidadMin)
    * Retorna la profundidad mínima permitida en la zona.
    * @return int: profundidad mínima.
    */
    public int getProfundidadMin() {
        return profundidadMin;
    }
    /* (getProfundidadMax)
    * Retorna la profundidad máxima permitida en la zona.
    * @return int: profundidad máxima.
    */
    public int getProfundidadMax() {
        return profundidadMax;
    }
    /* (getRecursos)
    * Retorna el conjunto de recursos disponibles en la zona.
    * @return EnumSet<ItemTipo>: conjunto de tipos de ítems presentes.
    */
    public EnumSet<ItemTipo> getRecursos() {
        return recursos;
    }

    /* (entrar)
    * Método abstracto que define el comportamiento al ingresar a una zona.
    * @param jugador: Jugador que entra en la zona.
    * @return void
    */
    public abstract void entrar(Jugador jugador);
    /* (explorar)
    * Método abstracto que define la acción de exploración dentro de una zona.
    * @param jugador: Jugador que realiza la exploración.
    * @param sc: Scanner utilizado para interacción durante la acción.
    * @return void
    */
    public abstract void explorar(Jugador jugador, Scanner sc);
    /* (moverse)
    * Método abstracto que define el desplazamiento del jugador dentro de la zona.
    * @param jugador: Jugador que se mueve.
    * @param sc: Scanner utilizado para recibir la entrada del usuario.
    * @return void
    */
    public abstract void moverse(Jugador jugador, Scanner sc);
}