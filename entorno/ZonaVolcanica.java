package entorno;

import objetos.ItemTipo;
import player.Jugador;
import player.Oxigeno;
import Util.Dialogo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;


public class ZonaVolcanica extends Zona {
    private boolean planoEncontrado;

    public ZonaVolcanica() {
        super("Zona Volcánica", 1000, 1500, EnumSet.of(ItemTipo.TITANIO, ItemTipo.SULFURO, ItemTipo.URANIO),3, 8);
        this.planoEncontrado = false;
    }

    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Has ingresado a la " + nombre + ". La temperatura y presión son extremas. " + "El entorno vibra por la actividad volcánica.");
    }

    @Override
    public void moverse(Jugador jugador, Scanner sc) {
        Dialogo.aviso("¿A qué profundidad quieres moverte (1000-1500)? ");
        int nuevaProf = sc.nextInt();
        sc.nextLine();

        if (nuevaProf < getProfundidadMin() || nuevaProf > getProfundidadMax()) {
            Dialogo.error("Esa profundidad no está permitida en la zona volcánica.");
            return;
        }

        double d = (double)(nuevaProf - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());
        int delta = Math.abs(nuevaProf - jugador.getProfundidadActual());
        int costo = Oxigeno.costoMover(d, delta);

        jugador.getTanqueOxigeno().consumirO2(costo);
        jugador.setProfundidadActual(nuevaProf);

        Dialogo.sistema("Profundidad actual: " + nuevaProf + "m. Costo: " + costo + " de O₂.");
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        // 🔹 Requisitos
        if (!jugador.tieneTrajeTermico()) {
            Dialogo.error("La temperatura es letal. No puedes explorar sin TRAJE TÉRMICO.");
            Dialogo.error("HAS MUERTO.......");
            System.exit(0);
        }

        if (!jugador.tieneMejoraTanque()) {
            Dialogo.error("Tu tanque no soporta la presión de esta zona.");
            Dialogo.error("HAS MUERTO.......");
            System.exit(0);
        }

        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());
        int presion = jugador.tieneMejoraTanque() ? 0 : (int)Math.ceil(15 + 10 * d);
        int costo = Oxigeno.costoExplorar(d, presion);

        jugador.getTanqueOxigeno().consumirO2(costo);

        // 🔹 Loot especial: 15% chance de obtener el plano
        if (!planoEncontrado && Math.random() < 0.15) {
            planoEncontrado = true;
            jugador.agregarItem(ItemTipo.PLANO_NAVE, 1);
            jugador.setTienePlanos(true);
            Dialogo.aviso("¡Has encontrado el PLANO DE LA NAVE! Con esto podrás repararla y escapar.");
        } else {
            List<ItemTipo> recursos = new ArrayList<>(getRecursos());
            ItemTipo recurso = recursos.get((int)(Math.random() * recursos.size()));
            int cantidad = Math.max(1, (int)Math.floor(nmin + (nmax - nmin) * d));
            jugador.agregarItem(recurso, cantidad);
            Dialogo.sistema("Has recolectado " + cantidad + "x " + recurso + ".");
        }

        Dialogo.sistema("Costo de exploración: " + costo + " de O₂. Restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }

    public boolean isPlanoEncontrado() {
        return planoEncontrado;
    }
}
