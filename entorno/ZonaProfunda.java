package entorno;

import objetos.ItemTipo;
import player.Jugador;
import Util.Dialogo;
import java.util.EnumSet;
import java.util.Scanner;

/*
 * Zona Profunda (200–999 m).
 * Para la entrega mínima, solo necesitamos que se pueda instanciar y que el jugador entre.
 */
public class ZonaProfunda extends Zona {
    private int presion; // en el diagrama aparece este atributo

    public ZonaProfunda() {
        super("Zona Profunda", 200, 999,
              EnumSet.of(ItemTipo.PLATA, ItemTipo.ORO, ItemTipo.ACERO, ItemTipo.DIAMANTE, ItemTipo.MAGNETITA));
        this.presion = 10; // presión base
    }

    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste a la " + nombre +
                       " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        Dialogo.aviso("Explorando la zona profunda... (pendiente loot)");
    }
}
