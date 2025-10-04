import player.Jugador;
import Util.Dialogo;
import entorno.ZonaArrecife;
import entorno.ZonaProfunda;
import objetos.NaveExploradora;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Dialogo.sistema("==========================");
        Dialogo.sistema("        BIENVENIDO        ");
        Dialogo.sistema("==========================");

        NaveExploradora nave = new NaveExploradora();
        Jugador jugador = new Jugador(60, nave);

        ZonaProfunda profunda= new ZonaProfunda();
        ZonaArrecife arrecife= new ZonaArrecife();

        arrecife.entrar(jugador);
        
        boolean jugando= true;
        while (jugando) {
            Dialogo.sistema("\n Que quieres hacer?");
            Dialogo.sistema("1. Ver estado\n2. Explorar zona actual\n3. Viajar a otra zona\n4. Salir ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    jugador.verEstadoJugador();
                    break;
                case 2:
                    if (jugador.getZonaActual() != null) {
                        jugador.getZonaActual().explorar(jugador, sc);
                    } 
                    break;
                case 3:
                    if (jugador.getZonaActual() instanceof ZonaArrecife) {
                        profunda.entrar(jugador);
                    } else {
                        arrecife.entrar(jugador);
                    }
                    break;
                case 4:
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