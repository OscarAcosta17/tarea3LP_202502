package Util;

import java.util.Scanner;

public class Entradas {
    /*
     * Lee un número entero desde la entrada estándar validando que sea numérico.
     * Muestra un mensaje personalizado y solicita reingreso en caso de error.
     * @param sc: Scanner utilizado para la lectura.
     * @param mensaje: String que se muestra al usuario antes de leer.
     * @return int: número entero ingresado por el usuario.
     */
    public static int leerEntero(Scanner sc, String mensaje) {
        int valor;
        while (true) {
            Dialogo.sistema(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                valor = Integer.parseInt(entrada);
                return valor;
            } catch (NumberFormatException e) {
                Dialogo.error("Entrada inválida, por favor ingresa un número.");
            }
        }
    }

    /*
     * Lee un número entero dentro de un rango definido, validando límites.
     * Si el valor ingresado no está dentro del rango, solicita reintento.
     * @param sc: Scanner utilizado para la lectura.
     * @param mensaje: String que se muestra al usuario antes de leer.
     * @param min: int valor mínimo permitido.
     * @param max: int valor máximo permitido.
     * @return int: número entero válido dentro del rango especificado.
     */
    public static int leerEnteroEnRango(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            int valor = leerEntero(sc, mensaje);
            if (valor < min || valor > max) {
                Dialogo.error("Debe ser un número entre " + min + " y " + max + ".");
            } else {
                return valor;
            }
        }
    }
}
