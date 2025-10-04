package objetos;

import java.util.ArrayList;

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {
    private int profundidadSoportada;

    public NaveExploradora() {
        this.profundidadSoportada = 500; // base
        this.bodega = new ArrayList<>();
    }

    @Override
    public boolean puedeAcceder(int requerido) {
        return requerido <= profundidadSoportada;
    }

    @Override
    public void transferirObjetos() {
        System.out.println("Transfiriendo objetos entre jugador y nave...");
    }

    // Clase anidada para el módulo de profundidad
    public class ModuloProfundidad {
        private int profundidadExtra = 1000;

        public void aumentarProfundidad() {
            profundidadSoportada += profundidadExtra;
        }
    }
}
