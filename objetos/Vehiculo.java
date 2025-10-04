package objetos;

import java.util.List;

/*
 * Clase abstracta base para vehículos
 */
public abstract class Vehiculo {
    protected List<Item> bodega;

    public abstract void transferirObjetos();
}
