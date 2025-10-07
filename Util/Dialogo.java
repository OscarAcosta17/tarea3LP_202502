package Util;
/**
* Clase utilitaria para mostrar mensajes en consola con diferentes estilos y colores.
* Simula una escritura lenta con efectos de texto y formato ANSI.
*/

public class Dialogo {
    /** Código ANSI para resetear el color. */
    public static final String RESET = "\u001B[0m";
    /** Código ANSI para color cian (narración). */
    public static final String CYAN = "\u001B[36m";
    /** Código ANSI para color gris (sistema). */
    public static final String GREY = "\u001B[37m";
    /** Código ANSI para color amarillo (avisos). */
    public static final String YELLOW = "\u001B[33m";
    /** Código ANSI para color rojo (errores, pero realmente se usa para destacar mejor). */
    public static final String RED = "\u001B[31m";

    /**
    * Muestra un mensaje en consola con escritura lenta, usando color ANSI y retardo por carácter.
    *
    * mensaje: es el mensaje a imprimir.
    * retardoMillis: son los milisegundos de espera entre cada carácter.
    * colorAnsi: es el código ANSI para el color del texto que se declaro antes.
    */
    private static void escribirLento(String mensaje, long retardoMillis, String colorAnsi) {
        System.out.print(colorAnsi);
        for (char c : mensaje.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(retardoMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(RESET);
    }
    /**
    * Imprime un mensaje lentamente sin color.
    * parametros: mensaje, el retardo y el color
    */
    public static void escribirLento(String mensaje, long retardoMillis) {
        escribirLento(mensaje, retardoMillis, "");
    }
    /**
    * Imprime un mensaje narrativo (azul cian, escritura lenta).
    * parametros: mensaje, el retardo y el color
    */
    public static void narrar(String mensaje) {
        escribirLento(mensaje, 35, CYAN);
    }
    /**
    * Imprime un aviso importante (amarillo).
    * parametros: mensaje, el retardo y el color
    */
    public static void aviso(String mensaje) {
        escribirLento(mensaje, 25, YELLOW);
    }
    /**
    * Imprime mensajes del sistema (gris, más rápido).
    * parametros: mensaje, el retardo y el color
    */
    public static void sistema(String mensaje) {
        escribirLento(mensaje, 10, GREY);
    }
    /**
    * Imprime un mensaje en rojo.
    *Aunque se llame error realmente se usa para destacar cosas como el campeon o cosas del estilo
    * parametros: mensaje, el retardo y el color
    */
    public static void error(String mensaje) {
        escribirLento(mensaje, 20, RED);
    }

}