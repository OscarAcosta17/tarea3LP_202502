import player.Jugador;
import Util.Dialogo;
import entorno.ZonaArrecife;
import entorno.ZonaProfunda;
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

        
        ZonaArrecife arrecife= new ZonaArrecife();

        arrecife.entrar(jugador);
        
        boolean jugando= true;
        while (jugando) {
            jugador.verEstadoJugador();
            Dialogo.narrar("\nQue quieres hacer?");
            Dialogo.sistema("1.Subir o descender en profundidad \n2.Explorar \n3.Recoger recursos \n4.Volver a la nave\n5.Ver inventario\n6. Salir del juego");

            int opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    arrecife.moverse(jugador, sc);
                    break;
                case 2: //hay q arreglarlo (explorar)
                    if (jugador.getZonaActual() != null) {
                        jugador.getZonaActual().explorar(jugador, sc);
                    } 
                    break;
                case 3: // recursos
                    if (jugador.getZonaActual() instanceof ZonaArrecife) {
                        ((ZonaArrecife) jugador.getZonaActual()).recolectarRecursos(jugador, sc);
                    } else if (jugador.getZonaActual() instanceof ZonaProfunda){
                        ((ZonaProfunda) jugador.getZonaActual()).recolectarRecursos(jugador, sc);
                    } else {
                        Dialogo.error("Solo puedes recolectar recursos en el Arrecife por ahora.");
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