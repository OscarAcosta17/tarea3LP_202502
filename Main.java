import player.Jugador;
import Util.Dialogo;
import Util.Entradas;
import entorno.ZonaArrecife;
// import objetos.ItemTipo;
import objetos.NaveExploradora;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Dialogo.sistema("==================================");
        Dialogo.sistema("     BIENVENIDO  A SUBNAUTICA      ");
        Dialogo.sistema("==================================");

        NaveExploradora nave = new NaveExploradora();
        Jugador jugador = new Jugador(60, nave);

        /* 
        // === Test de las cositas ===
        boolean DEBUG = true;

        if (DEBUG) {
            Dialogo.aviso("=== modo test activado :D ===");

            jugador.setMejoraTanque(true);
            jugador.setTrajeTermico(true);
            jugador.agregarItem(ItemTipo.ROBOT_EXCAVADOR, 1);
            jugador.getTanqueOxigeno().setCapacidadMaxima(150);
            jugador.agregarItem(ItemTipo.MODULO_PROFUNDIDAD, 1);
            jugador.agregarItem(ItemTipo.PLATA, 20);
            jugador.agregarItem(ItemTipo.CUARZO, 20);
            jugador.agregarItem(ItemTipo.ACERO, 10);
            jugador.agregarItem(ItemTipo.ORO, 10);
            jugador.agregarItem(ItemTipo.SILICIO, 10);


        }*/

        ZonaArrecife arrecife= new ZonaArrecife();

        arrecife.entrar(jugador);
        
        boolean jugando= true;
        while (jugando) {
            jugador.verEstadoJugador();
            Dialogo.narrar("\nQue quieres hacer?");
            Dialogo.sistema("1.Subir o descender en profundidad \n2.Explorar \n3.Recoger recursos \n4.Volver a la nave\n5.Ver inventario\n6. Salir del juego");

            int opcion = Entradas.leerEnteroEnRango(sc, "Selecciona una opción:", 1, 6);

            switch (opcion) {
                case 1: //moverse en profundidad

                    if (jugador.getZonaActual() != null) {
                        jugador.getZonaActual().moverse(jugador, sc);
                    } 
                    break;
                case 2: //explorar
                    if (jugador.getZonaActual() != null) {
                        jugador.getZonaActual().explorar(jugador, sc);
                    } 
                    break;
                case 3: // recursos
                    if (jugador.getZonaActual() != null) {
                        // Recolectar solo si la zona lo permite
                        try {
                            jugador.getZonaActual().getClass().getMethod("recolectarRecursos", Jugador.class, Scanner.class).invoke(jugador.getZonaActual(), jugador, sc);
                        } catch (NoSuchMethodException e) {
                            Dialogo.error("Esta zona no tiene recursos para recolectar.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 4: // volver nave
                    Dialogo.narrar("Regresando a la Nave Exploradora...");
                    jugador.getNave().abrirMenuNave(jugador, sc);
                    break;
                case 5: //inventario
                    jugador.verInventario();
                    break;
                case 6:
                    Dialogo.narrar("Saliendo del juego...");
                    jugando = false;
                    break;
                default:
                    Dialogo.error("Opción inválida, pone otra cosa");
                    break;
            }
        }
        sc.close();
    }
}