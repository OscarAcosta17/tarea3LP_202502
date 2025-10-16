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
    private int piezasTanque;
    /* (Constructor de ZonaArrecife)
    * Inicializa la zona arrecife con su rango de profundidad, recursos disponibles
    * y parámetros de producción por recolección. Define un stock inicial de 3 PIEZA_TANQUE.
    * @return void
    */
    public ZonaArrecife() {
        super("Zona Arrecife", 0, 199,EnumSet.of(ItemTipo.CUARZO, ItemTipo.SILICIO, ItemTipo.COBRE),1,3);
        this.piezasTanque = 3;
    }
    /* (moverJugador)
    * Permite al jugador desplazarse dentro del rango de profundidad del arrecife.
    * Calcula el costo de oxígeno y actualiza la profundidad del jugador.
    * @param jugador: Jugador que realiza el movimiento.
    * @param nuevaProfundidad: int nueva profundidad a la que se moverá el jugador.
    * @param sc: Scanner utilizado para interacción con el usuario.
    * @return void
    */
    public void moverJugador(Jugador jugador, int nuevaProfundidad, Scanner sc) {
        if (nuevaProfundidad < getProfundidadMin() || nuevaProfundidad > getProfundidadMax()) {
            Dialogo.error("No puedes moverte a esa profundidad en el arrecife.");
            return;
        }

        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());
        int delta = Math.abs(nuevaProfundidad - jugador.getProfundidadActual());

        int costo = Oxigeno.costoMover(d, delta);

        jugador.getTanqueOxigeno().consumirO2(costo, jugador, sc);
        jugador.setProfundidadActual(nuevaProfundidad);

        Dialogo.aviso("Te moviste a " + nuevaProfundidad + "m. Costo: " + costo + " de O₂. Restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }
    /* (recolectarRecursos)
    * Permite al jugador seleccionar y extraer un recurso disponible en la zona.
    * Calcula el costo de oxígeno y la cantidad recolectada según la profundidad actual.
    * @param jugador: Jugador que realiza la recolección.
    * @param sc: Scanner utilizado para la selección del recurso.
    * @return void
    */
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
            Dialogo.error("Selección inválida, intenta de nuevo.");
            return;
        }

        ItemTipo recursoElegido = listaRecursos.get(eleccion - 1);

        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());

        int costo = Oxigeno.costoRecolectar(d, 0); 
        int cantidad = Math.max(1, (int) Math.floor(nmin + (nmax - nmin) * d));
        jugador.getTanqueOxigeno().consumirO2(costo, jugador, sc);
        jugador.agregarItem(recursoElegido, cantidad);

        Dialogo.aviso("Has extraído " + cantidad + " de " + recursoElegido + ". Costo O₂ = " + costo + ". O₂ restante = " + jugador.getTanqueOxigeno().getOxigenoRestante());
    }

    /* (entrar)
    * Asigna la zona arrecife como la zona actual del jugador y muestra un mensaje narrativo.
    * @param jugador: Jugador que ingresa a la zona.
    * @return void
    */
    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste a la " + nombre + " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }
    /* (moverse)
    * Solicita al jugador una nueva profundidad dentro del rango del arrecife y ejecuta el movimiento.
    * Muestra error si intenta salir del rango permitido de la zona.
    * @param jugador: Jugador que se desplaza en la zona.
    * @param sc: Scanner utilizado para leer la entrada del usuario.
    * @return void
    */
    @Override
    public void moverse(Jugador jugador, Scanner sc){
        Dialogo.aviso("¿A qué profundidad quieres moverte ("+ getProfundidadMin() + "-"+ getProfundidadMax()+ ") ?");
        int nuevaProf = Util.Entradas.leerEntero(sc, "Ingresa la nueva profundidad:");

        if (nuevaProf < getProfundidadMin() || nuevaProf > getProfundidadMax()) {
            Dialogo.error("Para salir de esta zona, usa la Nave Exploradora.");
            return;
        } else {
            moverJugador(jugador, nuevaProf, sc);
        }
    }
    /* (explorar)
    * Permite al jugador realizar la acción de exploración en la zona arrecife.
    * Consume oxígeno según la profundidad y otorga recursos o piezas de tanque con probabilidad del 30%.
    * @param jugador: Jugador que explora la zona.
    * @param sc: Scanner utilizado para interacción durante la acción.
    * @return void
    */
    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) / Math.max(1, getProfundidadMax() - getProfundidadMin());

        int costo = Oxigeno.costoExplorar(d, 0);
        jugador.getTanqueOxigeno().consumirO2(costo, jugador, sc);

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