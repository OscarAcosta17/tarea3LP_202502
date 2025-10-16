package objetos;

/*
 * Enum con todos los tipos de ítems del juego
 */
public enum ItemTipo {
    // Recursos básicos
    CUARZO,
    SILICIO,
    COBRE,

    // Recursos profundos
    PLATA,
    ORO,
    ACERO,
    DIAMANTE,
    MAGNETITA,

    // Recursos volcánicos
    TITANIO,
    SULFURO,
    URANIO,

    // Especiales de progresión
    PIEZA_TANQUE,
    PLANO_NAVE,
    MODULO_PROFUNDIDAD,

    // Extras de NaveEstrellada
    CABLES,
    PIEZAS_METAL,

    //objetos creados
    MEJORA_TANQUE,
    ROBOT_EXCAVADOR
}