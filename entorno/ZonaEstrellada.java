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
    /* (Constructor de ZonaEstrellada)
    * Inicializa la zona "Nave Estrellada" con su conjunto de recursos y parámetros de exploración.
    * @return void
    */
    public ZonaEstrellada() {
        super("Nave Estrellada", 0, 0, EnumSet.of(ItemTipo.CABLES, ItemTipo.PIEZAS_METAL),0, 4);
        this.moduloEncontrado = false;
        this.exploradoSinTraje= false;
    }
    /* (entrar)
    * Asigna la zona estrellada como la zona actual del jugador y muestra un mensaje narrativo.
    * @param jugador: Jugador que ingresa a la zona.
    * @return void
    */
    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Has ingresado a la " + nombre + ". Entre restos metálicos y humo, observas la estructura dañada de una antigua nave.");
    }
    /* (moverse)
    * Indica que el jugador no puede desplazarse libremente dentro de la nave estrellada.
    * @param jugador: Jugador que intenta moverse.
    * @param sc: Scanner utilizado para la entrada (no se usa en este contexto).
    * @return void
    */
    @Override
    public void moverse(Jugador jugador, Scanner sc) {
        Dialogo.error("No puedes moverte libremente aquí, estás dentro de la nave estrellada.");
    }
    /* (explorar)
    * Permite al jugador explorar los restos de la nave estrellada.
    * Si no posee traje térmico, puede explorar una sola vez antes de perder el conocimiento.
    * Puede encontrar el MÓDULO DE PROFUNDIDAD con probabilidad o recolectar materiales secundarios si tiene traje térmico.
    * @param jugador: Jugador que realiza la exploración.
    * @param sc: Scanner utilizado para interacción durante el evento.
    * @return void
    */
    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        boolean tieneTraje = jugador.tieneTrajeTermico();
        boolean tieneModulo = jugador.tieneItem(ItemTipo.MODULO_PROFUNDIDAD);

        if (!tieneTraje && exploradoSinTraje) {
            Dialogo.error("El calor es insoportable... No puedes volver a explorar sin un traje térmico.");
            Dialogo.narrar("Has perdido el conocimiento y reapareces en la nave exploradora.");

            jugador.perderInventario();
            jugador.getTanqueOxigeno().recargarCompleto();
            jugador.getNave().abrirMenuNave(jugador, sc);
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
    /* (isModuloEncontrado)
    * Indica si el módulo de profundidad ya fue hallado en la nave estrellada.
    * @return boolean: true si se encontró el módulo, false en caso contrario.
    */
    public boolean isModuloEncontrado() {
        return moduloEncontrado;
    }
}