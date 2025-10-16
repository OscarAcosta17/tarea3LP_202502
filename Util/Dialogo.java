package Util;

public class Dialogo {

    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    /*
    * Imprime un texto carácter por carácter con un retardo específico y color ANSI.
    * @param mensaje: String mensaje a mostrar en pantalla.
    * @param retardoMillis: long tiempo de espera entre cada carácter.
    * @param colorAnsi: String código de color ANSI utilizado.
    * @return void
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
    /*
    * Imprime un texto carácter por carácter sin aplicar color.
    * @param mensaje: String mensaje a mostrar.
    * @param retardoMillis: long tiempo de espera entre caracteres.
    * @return void
    */
    public static void escribirLento(String mensaje, long retardoMillis) {
        escribirLento(mensaje, retardoMillis, "");
    }
    /*
    * Muestra un mensaje narrativo con color cian y velocidad moderada.
    * @param mensaje: String mensaje narrativo.
    * @return void
    */
    public static void narrar(String mensaje) {
        escribirLento(mensaje, 35, CYAN);
    }
    /*
    * Muestra un mensaje de aviso en color amarillo.
    * @param mensaje: String mensaje de aviso.
    * @return void
    */
    public static void aviso(String mensaje) {
        escribirLento(mensaje, 25, YELLOW);
    }
    /*
    * Muestra un mensaje del sistema en color gris con velocidad rápida.
    * @param mensaje: String mensaje del sistema.
    * @return void
    */
    public static void sistema(String mensaje) {
        escribirLento(mensaje, 10, GREY);
    }
    /*
    * Muestra un mensaje de error en color rojo con velocidad media.
    * @param mensaje: String mensaje de error.
    * @return void
    */
    public static void error(String mensaje) {
        escribirLento(mensaje, 20, RED);
    }

}