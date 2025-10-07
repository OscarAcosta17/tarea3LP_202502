package entorno;

import objetos.ItemTipo;
import player.Jugador;
import Util.Dialogo;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class ZonaEstrellada extends Zona {
    private boolean moduloEncontrado;
    private boolean exploradoSinTraje;

    public ZonaEstrellada() {
        super("Nave Estrellada", 0, 0, EnumSet.of(ItemTipo.CABLES, ItemTipo.PIEZAS_METAL),0, 4);
        this.moduloEncontrado = false;
        this.exploradoSinTraje= false;
    }

    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Has ingresado a la " + nombre + ". Entre restos metálicos y humo, observas la estructura dañada de una antigua nave.");
    }

    @Override
    public void moverse(Jugador jugador, Scanner sc) {
        Dialogo.error("No puedes moverte libremente aquí, estás dentro de la nave estrellada.");
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        boolean tieneTraje = jugador.tieneTrajeTermico();
        boolean tieneModulo = jugador.tieneItem(ItemTipo.MODULO_PROFUNDIDAD);

        if (!tieneTraje && exploradoSinTraje) {
            Dialogo.error("El calor es insoportable... No puedes volver a explorar sin un traje térmico.");
            Dialogo.error("HAS MUERTO.............");

            System.exit(0);
        }

        if (tieneModulo || moduloEncontrado) {
            Dialogo.aviso("Ya has explorado esta zona y no hay nada más por descubrir.");
            return;
        }

        Dialogo.aviso("Explorando los restos de la nave...");

        if (!tieneTraje) {
            exploradoSinTraje = true;
            Dialogo.aviso("El calor te afecta rápidamente. Solo podrás explorar esta vez sin traje térmico.");
        }

        if (Math.random() < 0.25) {
            moduloEncontrado = true;
            jugador.agregarItem(ItemTipo.MODULO_PROFUNDIDAD, 1);
            Dialogo.aviso("¡Has encontrado el MÓDULO DE PROFUNDIDAD! Tu nave ahora podrá descender a más de 1000m.");
        } else if (tieneTraje) {
            List<ItemTipo> lootExtra = List.of(ItemTipo.CABLES, ItemTipo.PIEZAS_METAL);
            ItemTipo recurso = lootExtra.get((int) (Math.random() * lootExtra.size()));
            jugador.agregarItem(recurso, 1);
            Dialogo.sistema("Recolectaste materiales entre los restos: " + recurso + ".");
        } else {
            Dialogo.error("El calor te obligó a retirarte antes de encontrar algo valioso.");
        }
    }

    public boolean isModuloEncontrado() {
        return moduloEncontrado;
    }
}
