package entorno;

import objetos.ItemTipo;
import player.Jugador;
import player.Oxigeno;
import Util.Dialogo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

/*
 * Zona Profunda (200–999 m).
 * Para la entrega mínima, solo necesitamos que se pueda instanciar y que el jugador entre.
 */
public class ZonaProfunda extends Zona {
    private int presion; // en el diagrama aparece este atributo

    public ZonaProfunda() {
        super("Zona Profunda", 200, 999, EnumSet.of(ItemTipo.PLATA, ItemTipo.ORO, ItemTipo.ACERO, ItemTipo.DIAMANTE, ItemTipo.MAGNETITA),2,6);
        this.presion = 10; // presión base
    }

    public void moverJugador(Jugador jugador, int nuevaProfundidad) {
        if (nuevaProfundidad < getProfundidadMin() || nuevaProfundidad > getProfundidadMax()) {
            Dialogo.error("No puedes moverte a esa profundidad en el arrecife.");
            return;
        }

        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());
        int delta = Math.abs(nuevaProfundidad - jugador.getProfundidadActual());

        // 🔹 reutilizamos Oxigeno
        int costo = Oxigeno.costoMover(d, delta);

        jugador.getTanqueOxigeno().consumirO2(costo);
        jugador.setProfundidadActual(nuevaProfundidad);

        Dialogo.aviso("Te moviste a " + nuevaProfundidad + "m. Costo: " + costo + " de O₂. Restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }

    public void recolectarRecursos(Jugador jugador, Scanner sc) {
        List<ItemTipo> listaRecursos = new ArrayList<>(getRecursos());

        System.out.println("\n¿Qué recurso deseas extraer?");
        for (int i = 0; i < listaRecursos.size(); i++) {
            System.out.println((i + 1) + ") " + listaRecursos.get(i));
        }
        System.out.print("> ");
        int eleccion = sc.nextInt();
        sc.nextLine();

        if (eleccion < 1 || eleccion > listaRecursos.size()) {
            Dialogo.error("Selección inválida.");
            return;
        }

        ItemTipo recursoElegido = listaRecursos.get(eleccion - 1);
        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) /Math.max(1, getProfundidadMax() - getProfundidadMin());

        int presionActual = jugador.tieneMejoraTanque() ? 0 : (int) Math.ceil(this.presion + 6 * d);
        int costo = Oxigeno.costoRecolectar(d, presionActual);

        int cantidad = Math.max(1, (int) Math.floor(nmin + (nmax - nmin) * d));
        jugador.getTanqueOxigeno().consumirO2(costo);
        jugador.agregarItem(recursoElegido, cantidad);

        Dialogo.aviso("Recolectaste " + cantidad + "x " + recursoElegido +" (costo " + costo + " de O₂, presión " + presion + ").");
    }

    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste a la " + nombre + " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }

    @Override
    public void moverse(Jugador jugador, Scanner sc){
        Dialogo.aviso("¿A qué profundidad quieres moverte (200-999)? ");
        int nuevaProf = sc.nextInt();
        sc.nextLine();
        moverJugador(jugador, nuevaProf);
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        Dialogo.aviso("Explorando la zona profunda... (pendiente loot)");
    }
}
