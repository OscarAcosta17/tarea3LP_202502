package entorno;

import objetos.ItemTipo;
import player.Jugador;
import player.Oxigeno;
import Util.Dialogo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;


public class ZonaArrecife extends Zona {
    private int piezasTanque; // stock limitado de 3

    public ZonaArrecife() {
        super("Zona Arrecife", 0, 199,EnumSet.of(ItemTipo.CUARZO, ItemTipo.SILICIO, ItemTipo.COBRE),1,3);
        this.piezasTanque = 3;
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
        // Lista de recursos disponibles en esta zona
        List<ItemTipo> listaRecursos = new ArrayList<>(getRecursos());

        System.out.println("\n¿Qué recurso deseas extraer?");
        for (int i = 0; i < listaRecursos.size(); i++) {
            System.out.println((i + 1) + ") " + listaRecursos.get(i));
        }
        System.out.print("> ");

        int eleccion = sc.nextInt();
        sc.nextLine();

        if (eleccion < 1 || eleccion > listaRecursos.size()) {
            Dialogo.error("Selección inválida, intenta de nuevo.");
            return;
        }

        ItemTipo recursoElegido = listaRecursos.get(eleccion - 1);

        // Calcular profundidad normalizada d
        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());

        int costo = Oxigeno.costoRecolectar(d, 0); 
        int cantidad = Math.max(1, (int) Math.floor(nmin + (nmax - nmin) * d));
        jugador.getTanqueOxigeno().consumirO2(costo);
        jugador.agregarItem(recursoElegido, cantidad);

        Dialogo.aviso("Has extraído " + cantidad + " de " + recursoElegido + ". Costo O₂ = " + costo + ". O₂ restante = " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }


    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste a la " + nombre + " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }

    @Override
    public void moverse(Jugador jugador, Scanner sc){
        Dialogo.aviso("¿A qué profundidad quieres moverte (0-199)? ");
        int nuevaProf = sc.nextInt();
        sc.nextLine();
        moverJugador(jugador, nuevaProf);
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());

        int costo = Oxigeno.costoExplorar(d, 0);
        jugador.getTanqueOxigeno().consumirO2(costo);

        double probabilidad = Math.random(); // entre 0.0 y 1.0
        boolean obtuvoTanque = (probabilidad < 0.30) && (piezasTanque > 0);

        if (obtuvoTanque) {
            piezasTanque--;
            jugador.agregarItem(ItemTipo.PIEZA_TANQUE, 1);

            Dialogo.aviso("¡Has encontrado una PIEZA DE TANQUE! (" + "Stock restante: " + piezasTanque + ")");
        } else {
            List<ItemTipo> listaRecursos = new ArrayList<>(getRecursos());
            ItemTipo recurso = listaRecursos.get((int)(Math.random() * listaRecursos.size()));

            int cantidad = Math.max(1, (int)Math.floor(nmin + (nmax - nmin) * d));
            jugador.agregarItem(recurso, cantidad);

            Dialogo.aviso("No encontraste piezas especiales, pero obtuviste " + cantidad + "x " + recurso + ".");
        }
        Dialogo.aviso("Costo de exploración: " + costo + " de O₂. Restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }



}