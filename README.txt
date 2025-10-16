Tarea 3 - Lenguajes de Programación
Nombre: Oscar Acosta
Rol: 202373511-6

1. Descripción General:

- version del compilador usada:
    javac 21.0.7
- se utilizo la consola WSL para esta tarea(sirve cualquiera que acepte caracteres ANSI, esto es muy importante ya que si no lo tiene no corre.)
- se compila usando: make run 
- al ejecutar el make run se crea una carpeta llamada "out" en donde se queda lo que se compiló"

| IMPORTANTE |
    *ES MUY IMPORTANTE QUE SE DEJE QUE EL JUEGO ESCRIBA LOS MENSAJES ANTES DE ESCRIBIR SOBRE LA CONSOLA*

    * Dentro del main.java hay comentado un test, que entrega materiales y objetos especiales, por si se necesita para hacer las pruebas que necesiten

2. Lógica del juego:

- El jugador inicia fuera de la nave en la zona arrecife.
- se recolectan recursos en la zona para conseguir las 3 piezas del tanque,con esto se mejora en la nave para conseguir el doble de oxigeno y resistir a la presion
- Viaja a la Zona Profunda para recolectar plata, oro, acero, diamante y magnetita.
- Crea mejoras adicionales (por ejemplo, Mejora de Oxígeno o Traje Térmico).
- Una vez tengas el Traje Térmico, viaja a la Nave Estrellada y busca el Módulo de Profundidad.(puedes ir sin el traje termico pero puedes morir y solo tienes 1 intento de busqueda.)
- Instala el módulo en la nave para alcanzar profundidades mayores (hasta 1500 m).
- viaja a la Zona Volcánica, donde podrás obtener el PLANO_NAVE.
- una vez lo tengas vuelve a la nave y crea la reparacion de la nave estrellada con el plano y los materiales necesarios
- Repara la nave y escapa del planeta para ganar la partida.

3. Supuestos:

- el jugador no puede nadar/anclar la nave fuera de los rangos de la zona, por lo que para moverse fuera de estos limites hay que moverse de zona en la nave.
- al explorar en busqueda del objeto especial de cada zona, si no encuentra nada, recoge un poco de material al azar de esa zona para compensar
- cada zona mantiene su estado unico durante toda la partida
- al viajar a otra zona, la nave se ancla en la minima profundidad de esa zona. 
- te mueres si intentas explorar la zona volcanica sin el traje termico
- si te quedas sin oxigeno / exploras por segunda vez en nave estrellada vuelves a la nave exploradora sin inventario, por lo que puedes no poder completar el juego si pierdes items importantes.
- para usar el robot hay que dejarlo en la nave
- 
4. Añadidos:
- Clase Util.Entradas
    - Se agregó para validar entradas numéricas y evitar que el juego se caiga si el usuario ingresa texto en lugar de números.
- Clase Util.Dialogo
    - Centraliza los mensajes del sistema (narrar, aviso, error, sistema) para mantener una interfaz uniforme y clara,  además de añadirle vida al juego
- se crearon ciertas funciones con el fin de hacer mas sencillo el desarrollo de la tarea :D 