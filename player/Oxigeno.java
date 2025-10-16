package player;

import java.util.Scanner;
import Util.Dialogo;

public class Oxigeno {
    private int oxigenoRestante;
    private int capacidadMaxima;
    /* (Constructor de Oxigeno)
    * Inicializa un tanque de oxígeno con capacidad máxima definida.
    * @param capacidadMaxima: int capacidad total del tanque de oxígeno.
    * @return void
    */
    public Oxigeno(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.oxigenoRestante = capacidadMaxima;
    }
    /* (getOxigenoRestante)
    * Retorna la cantidad actual de oxígeno restante en el tanque.
    * @return int: unidades actuales de oxígeno disponibles.
    */
    public int getOxigenoRestante() {
        return oxigenoRestante;
    }
    /* (getCapacidadMaxima)
    * Retorna la capacidad total máxima del tanque de oxígeno.
    * @return int: capacidad máxima de oxígeno.
    */
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    /* (consumirO2)
    * Resta unidades de oxígeno según el consumo indicado.
    * Si el oxígeno llega a cero, el jugador pierde el inventario y reaparece en la nave exploradora.
    * @param unidades: int cantidad de oxígeno a consumir.
    * @param jugador: Jugador asociado que pierde inventario y reaparece.
    * @param sc: Scanner utilizado para interacción con el menú de la nave.
    * @return void
    */
    public void consumirO2(int unidades, Jugador jugador, Scanner sc) {
        oxigenoRestante -= unidades;
        if (oxigenoRestante < 0) {
            oxigenoRestante = 0;
        }
        if (oxigenoRestante == 0) {
            Dialogo.error("¡Te has quedado sin oxígeno!");
            Dialogo.narrar("Has perdido el conocimiento y reapareces en la nave exploradora.");
            jugador.perderInventario();
            recargarCompleto();
            jugador.getNave().abrirMenuNave(jugador, sc);
        }
    }
    
    /* (recargarCompleto)
    * Restaura el oxígeno del tanque a su capacidad máxima.
    * @return void
    */
    public void recargarCompleto() {
        oxigenoRestante = capacidadMaxima;
    }

    /* (costoExplorar)
    * Calcula el costo de oxígeno por acción de exploración según profundidad y presión.
    * @param d: double profundidad normalizada de la zona.
    * @param presion: int nivel de presión ambiental.
    * @return int: unidades de oxígeno consumidas por explorar.
    */
    public static int costoExplorar(double d, int presion) {
        return (int) Math.ceil(12 + 10*d + presion);
    }
    /* (costoRecolectar)
    * Calcula el costo de oxígeno por acción de recolección según profundidad y presión.
    * @param d: double profundidad normalizada.
    * @param presion: int nivel de presión ambiental.
    * @return int: unidades de oxígeno consumidas por recolectar.
    */
    public static int costoRecolectar(double d, int presion) {
        return (int) Math.ceil(10 + 6*d + presion);
    }
    /* (costoMover)
    * Calcula el costo de oxígeno por desplazamiento vertical dentro de una zona.
    * @param d: double profundidad normalizada.
    * @param deltaZ: int variación de profundidad en metros.
    * @return int: unidades de oxígeno consumidas por moverse.
    */
    public static int costoMover(double d, int deltaZ) {
        return (int) Math.ceil((3 + 3*d) * (Math.abs(deltaZ) / 50.0));
    }
    /* (setCapacidadMaxima)
    * Define una nueva capacidad máxima para el tanque de oxígeno.
    * @param nuevaCapacidad: int nueva capacidad total del tanque.
    * @return void
    */
    public void setCapacidadMaxima(int nuevaCapacidad) {
        capacidadMaxima = nuevaCapacidad;
    }

}