package objetos;

/* (Interfaz AccesoProfundidad)
 * Define el comportamiento para validar si un objeto o entidad
 * puede acceder a una profundidad determinada dentro del juego.
 */
public interface AccesoProfundidad {
    /* (puedeAcceder)
    * Verifica si la profundidad requerida es alcanzable por el objeto.
    * @param requerido: int profundidad mínima o valor requerido por la zona.
    * @return boolean: true si puede acceder, false en caso contrario.
    */
    boolean puedeAcceder(int requerido);
}