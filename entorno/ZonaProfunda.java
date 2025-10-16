package entorno;

import objetos.ItemTipo;
import player.Jugador;
import player.Oxigeno;
import Util.Dialogo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class ZonaProfunda extends Zona {
    private int presion; // en el diagrama aparece este atributo
    /* (Constructor de ZonaProfunda)
    * Inicializa la zona profunda con su rango de profundidad, recursos disponibles,
    * presión base y parámetros de producción por recolección.
    * @return void
    */
    public ZonaProfunda() {
        super("Zona Profunda", 200, 999, EnumSet.of(ItemTipo.PLATA, ItemTipo.ORO, ItemTipo.ACERO, ItemTipo.DIAMANTE, ItemTipo.MAGNETITA),2,6);
        this.presion = 10;
    }
    /* (moverJugador)
    * Permite al jugador cambiar de profundidad dentro de la zona profunda.
    * Calcula el costo de oxígeno por desplazamiento y aplica el consumo correspondiente.
    * @param jugador: Jugador que realiza el movimiento.
    * @param nuevaProfundidad: int profundidad objetivo en metros.
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
    * Permite al jugador seleccionar y recolectar un recurso disponible en la zona profunda.
    * Calcula el consumo de oxígeno y la cantidad obtenida según la profundidad y presión.
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
            Dialogo.error("Selección inválida.");
            return;
        }

        ItemTipo recursoElegido = listaRecursos.get(eleccion - 1);
        double d = (double)(jugador.getProfundidadActual() - getProfundidadMin()) /Math.max(1, getProfundidadMax() - getProfundidadMin());

        int presionActual = jugador.tieneMejoraTanque() ? 0 : (int) Math.ceil(this.presion + 6 * d);
        int costo = Oxigeno.costoRecolectar(d, presionActual);

        int cantidad = Math.max(1, (int) Math.floor(nmin + (nmax - nmin) * d));
        jugador.getTanqueOxigeno().consumirO2(costo, jugador, sc);
        jugador.agregarItem(recursoElegido, cantidad);

        Dialogo.aviso("Recolectaste " + cantidad + "x " + recursoElegido +" (costo " + costo + " de O₂, presión " + presion + ").");
    }
    
    /* (entrar)
    * Establece la zona profunda como la zona actual del jugador y muestra un mensaje narrativo.
    * @param jugador: Jugador que ingresa a la zona.
    * @return void
    */
    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste a la " + nombre + " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }
    /* (moverse)
    * Solicita una nueva profundidad al jugador y ejecuta el movimiento si está dentro del rango permitido.
    * @param jugador: Jugador que se desplaza en la zona.
    * @param sc: Scanner utilizado para ingresar la profundidad deseada.
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
    * Ejecuta la acción de exploración dentro de la zona profunda.
    * No otorga ítems especiales, solo muestra mensajes narrativos, ya que no hay cosas especiales
    * @param jugador: Jugador que realiza la exploración.
    * @param sc: Scanner utilizado para interacción.
    * @return void
    */
    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        Dialogo.aviso("Explorando la zona profunda... ");
        Dialogo.error("no hay nada especial que obtener aquí....\n sigue tu camino..");
    }
}