package entorno;

import objetos.ItemTipo;
import player.Jugador;
import player.Oxigeno;
import Util.Dialogo;
import java.util.EnumSet;
import java.util.Scanner;


public class ZonaArrecife extends Zona {
    private int piezasTanque; // stock limitado de 3

    public ZonaArrecife() {
        super("Zona Arrecife", 0, 199,EnumSet.of(ItemTipo.CUARZO, ItemTipo.SILICIO, ItemTipo.COBRE));
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


    @Override
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        Dialogo.narrar("Ingresaste al " + nombre + " entre " + getProfundidadMin() + "m y " + getProfundidadMax() + "m.");
    }

    @Override
    public void explorar(Jugador jugador, Scanner sc) {
        boolean enZona = true;
        Dialogo.narrar("Has entrado a la "+ nombre +".....");
        while (enZona) {
            Dialogo.sistema("\n--- Opciones en " + nombre + " ---");
            Dialogo.sistema("1. Recolectar recursos");
            Dialogo.sistema("2. Moverse en profundidad");
            Dialogo.sistema("0. Volver al menú principal");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    int costoRecolecta = Oxigeno.costoRecolectar(0, 0);
                    jugador.getTanqueOxigeno().consumirO2(costoRecolecta);
                    Dialogo.aviso("Recolectaste algo. Costó " + costoRecolecta + " de O₂.");
                    break;
                case 2:
                    Dialogo.aviso("¿A qué profundidad quieres moverte (0-199)? ");
                    int nuevaProf = sc.nextInt();
                    sc.nextLine();
                    moverJugador(jugador, nuevaProf);
                    break;
                case 0:
                    enZona = false;
                    Dialogo.aviso("Has vuelto... ");
                    break;
                default:
                    Dialogo.error("Opción no válida.");
            }
        }
    }


}
