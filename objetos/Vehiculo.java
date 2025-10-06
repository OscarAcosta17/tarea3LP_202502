package objetos;

import java.util.List;

import player.Jugador;

/*
 * Clase abstracta base para vehículos
 */
public abstract class Vehiculo {
    protected List<Item> bodega;

    public abstract void transferirObjetos(Jugador jugador);

    public List<Item> getBodega() {
        return bodega;
    }

}

