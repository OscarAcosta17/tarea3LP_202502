package objetos;

import java.util.List;
import player.Jugador;

/* (Clase Vehiculo)
 * Clase abstracta base que define el comportamiento común de los vehículos del juego.
 * Contiene la estructura de almacenamiento (bodega) y el método abstracto para transferir objetos.
 */
public abstract class Vehiculo {
    protected List<Item> bodega;
    /* (transferirObjetos)
    * Transfiere los objetos del jugador hacia la bodega del vehículo.
    * Debe ser implementado por las subclases según su tipo de vehículo.
    * @param jugador: Jugador origen de los ítems a transferir.
    * @return void
    */
    public abstract void transferirObjetos(Jugador jugador);
    /* (getBodega)
    * Retorna la lista de ítems almacenados en la bodega del vehículo.
    * @return List<Item>: lista de ítems contenidos en la bodega.
    */ 
    public List<Item> getBodega() {
        return bodega;
    }
}