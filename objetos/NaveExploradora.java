package objetos;
import Util.Dialogo;
import Util.Entradas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import player.Jugador;

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {
    private int profundidadSoportada;
    private int profundidadAnclaje = 0;  // anclaje actual de la nave
    /* (Constructor de NaveExploradora)
    * Inicializa una nueva nave exploradora con profundidad base de 500 m y bodega vacía.
    * @return void
    */
    public NaveExploradora() {
        this.profundidadSoportada = 500; // base
        this.bodega = new ArrayList<>();
    }
    /* (getProfundidadSoportada)
    * Retorna la profundidad máxima que la nave puede alcanzar actualmente.
    * @return int: profundidad máxima soportada en metros.
    */
    public int getProfundidadSoportada() {
        return profundidadSoportada;
    }
    
    /* (getProfundidadAnclaje)
    * Retorna la profundidad actual de anclaje de la nave.
    * @return int: profundidad del anclaje en metros.
    */
    public int getProfundidadAnclaje() {
        return profundidadAnclaje;
    }
    
    /* (anclarNave)
    * Ajusta la profundidad de anclaje de la nave dentro del rango permitido.
    * @param nuevaProfundidad: int nueva profundidad deseada.
    * @return void
    */
    public void anclarNave(int nuevaProfundidad) {
        if (nuevaProfundidad < 0 || nuevaProfundidad > profundidadSoportada) {
            Dialogo.error("No puedes anclar más allá de la profundidad soportada (" + profundidadSoportada + " m).");
            return;
        }
        this.profundidadAnclaje = nuevaProfundidad;
        Dialogo.aviso("Anclaje ajustado a " + profundidadAnclaje + " m.");
    }
    /* (abrirMenuNave)
    * Muestra el menú principal de la nave exploradora y permite al jugador realizar acciones
    * como ajustar anclaje, transferir objetos, fabricar mejoras, viajar entre zonas o salir.
    * @param jugador: Jugador que interactúa con la nave.
    * @param sc: Scanner utilizado para capturar las entradas del usuario.
    * @return void
    */
    public void abrirMenuNave(Jugador jugador, Scanner sc) {
        boolean enNave = true;

        while (enNave) {
            Dialogo.aviso(  "\n-- NAVE EXPLORADORA --");
            Dialogo.aviso("Zona actual: " + jugador.getZonaActual().nombre + " | Anclaje: "+ profundidadAnclaje +"m | Inventario nave: " + getCantidadTotalEnBodega());

            Dialogo.sistema("1) Ajustar profundidad de nave (anclaje)");
            Dialogo.sistema("2) Guardar todos los objetos en la nave");
            Dialogo.sistema("3) Crear objetos ");
            Dialogo.sistema("4) Moverse a otra zona");
            Dialogo.sistema("5) Salir de la nave");
            Dialogo.sistema("6) Ver inventario de la nave");
            Dialogo.sistema("7) Recargar oxigeno");
            Dialogo.sistema("8) Usar robot excavador");

            int opcion = Entradas.leerEnteroEnRango(sc, "Selecciona una opción:", 1, 8);

            switch (opcion) {
                case 1:
                    Dialogo.aviso("Nueva profundidad de anclaje (" + jugador.getZonaActual().getProfundidadMin()+ "-" + jugador.getZonaActual().getProfundidadMax() + "): ");
                    int nuevaProfundidad = sc.nextInt();
                    sc.nextLine();

                    if (nuevaProfundidad < jugador.getZonaActual().getProfundidadMin() || nuevaProfundidad > jugador.getZonaActual().getProfundidadMax()) {
                        Dialogo.error("Para anclar fuera del rango ("+ jugador.getZonaActual().getProfundidadMin() + "m - "+ jugador.getZonaActual().getProfundidadMax() + "m) debes moverte entre zonas con la nave.");
                        break;
                    }
                    anclarNave(nuevaProfundidad);
                    break;
                case 2:
                    Dialogo.narrar("Transfiriendo objetos del jugador a la nave...");
                    transferirObjetos(jugador);
                    break;
                case 3:
                    Dialogo.sistema("\n-- CREAR OBJETOS --");
                    Dialogo.sistema("1) Crear MEJORA DE TANQUE (requiere 3x PIEZA_TANQUE)");
                    Dialogo.sistema("2) Crear TRAJE TÉRMICO (requiere 2x ACERO, 1x MAGNETITA, 1x DIAMANTE)");
                    Dialogo.sistema("3) Crear MEJORA DE OXIGENO (10x PLATA, 15x CUARZO )");
                    Dialogo.sistema("4) Instalar modulo de profundidad(1x modulo profundidad)");
                    Dialogo.sistema("5) Reparar Nave exploradora(50x titanio, 30x acero, 15x uranio, 20x sulfuro)");
                    Dialogo.sistema("6) Crear robot explorador(15x cobre, 10x magnetita, 5x diamante, 20x acero)");
                    Dialogo.sistema("0) Cancelar");

                    int eleccion = Entradas.leerEnteroEnRango(sc, "Selecciona una opción:", 0, 6);

                    switch (eleccion) {
                        case 1:
                            crearMejoraTanque(jugador);
                            break;
                        case 2:
                            crearTrajeTermico(jugador);
                            break;
                        case 3:
                            crearMejoraOxigeno(jugador);
                            break;
                        case 4:
                            instalarModuloProfunidad(jugador);
                            break;
                        case 5:
                            repararNave(jugador);
                            break;
                        case 6:
                            crearRobotExcavador(jugador);
                            break;
                        case 0:
                            Dialogo.aviso("Cancelando...");
                            break; 
                        default:
                            Dialogo.error("Opción inválida.");
                            break;
                    }
                    break;
                case 4:
                    Dialogo.aviso("\n-- Moverse a otra zona --");
                    Dialogo.sistema("1) Zona Arrecife");
                    Dialogo.sistema("2) Zona Profunda");
                    Dialogo.sistema("3) Nave Estrellada");
                    Dialogo.sistema("4) Zona Volcánica");
                    Dialogo.sistema("0) Cancelar");

                    int opcionZona = Entradas.leerEnteroEnRango(sc, "Selecciona una zona:", 0, 4);

                    if (opcionZona == 0) {
                        Dialogo.error("Cancelando viaje...");
                        break;
                    }

                    entorno.Zona zonaDestino = null;
                    String nombreZona = "";

                    switch (opcionZona) {
                        case 1:
                            zonaDestino = new entorno.ZonaArrecife();
                            nombreZona = "Zona Arrecife";
                            break;
                        case 2:
                            zonaDestino = new entorno.ZonaProfunda();
                            nombreZona = "Zona Profunda";
                            break;
                        case 3:
                            zonaDestino = new entorno.ZonaEstrellada();
                            nombreZona = "Nave Estrellada";
                            break;
                        case 4:
                            zonaDestino = new entorno.ZonaVolcanica();
                            nombreZona = "Zona Volcanica";
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
                    if (jugador.getZonaActual() != null &&
                        jugador.getZonaActual().getClass().equals(zonaDestino.getClass())) {
                        Dialogo.error("Ya te encuentras en " + nombreZona + ".");
                        break;
                    }
                    int profundidadDestino = zonaDestino.getProfundidadMin();
                    if (!puedeAcceder(profundidadDestino)) {
                        Dialogo.error("Tu nave no puede alcanzar esa profundidad.");
                        break;
                    }

                    Dialogo.aviso("Viajando a " + nombreZona + "...");
                    zonaDestino.entrar(jugador);

                    this.profundidadAnclaje = zonaDestino.getProfundidadMin();
                    jugador.setProfundidadActual(profundidadAnclaje);

                    Dialogo.aviso("La nave se ha anclado a " + profundidadAnclaje + " m.");
                    Dialogo.aviso("Has llegado a la " + nombreZona + ".");
                    break;
                case 5:
                    Dialogo.aviso("Saliendo de la nave...");

                    jugador.setProfundidadActual(profundidadAnclaje);
                    Dialogo.aviso("Has salido de la nave en la profundidad:"+ profundidadAnclaje +" m");
                    enNave = false;
                    break;
                case 6:
                    Dialogo.aviso("[Inventario de la nave]");
                    if (bodega.isEmpty()) Dialogo.error("(Vacío)");
                    else bodega.forEach(item -> Dialogo.aviso("- " + item));
                    break;
                case 7:
                    jugador.getTanqueOxigeno().recargarCompleto();
                    Dialogo.aviso("Tanque de oxígeno completamente recargado.");
                    break;
                case 8:
                    if (contarItem(ItemTipo.ROBOT_EXCAVADOR) > 0) {
                    RobotExcavador robot = new RobotExcavador();
                    robot.abrirMenuRobot(jugador, sc);
                    } else {
                        Dialogo.error("No tienes un Robot Excavador disponible en la nave, si lo tienes guardalo en la nave para usarlo.");
                    }
                    break;
                default:
                    Dialogo.error("Opción no válida.");
            }
        }
    }
    /* (contarItem)
    * Busca un ítem dentro de la bodega de la nave y retorna su cantidad.
    * @param tipo: ItemTipo tipo del ítem buscado.
    * @return int: cantidad disponible del ítem, o 0 si no existe.
    */
    private int contarItem(ItemTipo tipo) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                return item.getCantidad();
            }
        }
        return 0;
    }
    /* (restarItem)
    * Resta una cantidad específica de un ítem en la bodega de la nave.
    * Elimina el ítem si su cantidad llega a cero.
    * @param tipo: ItemTipo tipo del ítem.
    * @param cantidad: int cantidad a restar.
    * @return void
    */
    private void restarItem(ItemTipo tipo, int cantidad) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() - cantidad);
                if (item.getCantidad() <= 0) {
                    bodega.remove(item);
                }
                return;
            }
        }
    }
    /* (agregarItemNave)
    * Agrega un nuevo ítem a la bodega de la nave o aumenta su cantidad si ya existe.
    * @param tipo: ItemTipo tipo del ítem.
    * @param cantidad: int cantidad a agregar.
    * @return void
    */
    private void agregarItemNave(ItemTipo tipo, int cantidad) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        bodega.add(new Item(tipo, cantidad));
    }
    /* (crearMejoraTanque)
    * Crea la mejora de tanque si hay suficientes piezas, la almacena en la nave
    * y aplica la mejora al jugador.
    * @param jugador: Jugador que recibe la mejora.
    * @return void
    */
    private void crearMejoraTanque(Jugador jugador) {
        int piezas = contarItem(ItemTipo.PIEZA_TANQUE);

        if (piezas >= 3) {
            restarItem(ItemTipo.PIEZA_TANQUE, 3);
            agregarItemNave(ItemTipo.MEJORA_TANQUE, 1);
            Dialogo.aviso("Has creado una MEJORA DE TANQUE.");

            jugador.aplicarMejoraTanque();
        } else {
            Dialogo.error("No tienes suficientes piezas de tanque (necesitas 3).");
        }
    }
    /* (crearMejoraOxigeno)
    * Incrementa la capacidad máxima del tanque de oxígeno del jugador si posee
    * los materiales requeridos.
    * @param jugador: Jugador que obtiene la mejora de oxígeno.
    * @return void
    */
    private void crearMejoraOxigeno(Jugador jugador) {
        if (!jugador.tieneMejoraTanque()) {
            Dialogo.error("Necesitas la MEJORA DE TANQUE antes de mejorar el oxígeno.");
            return;
        }

        int plata = contarItem(ItemTipo.PLATA);
        int cuarzo = contarItem(ItemTipo.CUARZO);

        if (plata >= 10 && cuarzo >= 15) {
            restarItem(ItemTipo.PLATA, 10);
            restarItem(ItemTipo.CUARZO, 15);

            jugador.getTanqueOxigeno().setCapacidadMaxima(
                jugador.getTanqueOxigeno().getCapacidadMaxima() + 30
            );

            Dialogo.aviso("¡Has mejorado tu tanque de oxígeno! Capacidad +30 unidades.");
        } else {
            Dialogo.error("No tienes suficientes materiales. Requiere 10x PLATA y 15x CUARZO.");
        }
    }
    /* (crearTrajeTermico)
    * Crea el traje térmico si hay suficientes materiales en la bodega de la nave.
    * @param jugador: Jugador que recibe el traje térmico.
    * @return void
    */
    private void crearTrajeTermico(Jugador jugador) {
        int silicio = contarItem(ItemTipo.SILICIO);
        int oro = contarItem(ItemTipo.ORO);
        int cuarzo = contarItem(ItemTipo.CUARZO);

        if (silicio >= 10 && oro >= 3 && cuarzo >= 1) {

            restarItem(ItemTipo.SILICIO, 10);
            restarItem(ItemTipo.ORO, 3);
            restarItem(ItemTipo.CUARZO, 5);

            if (!jugador.tieneTrajeTermico()) {
                jugador.setTrajeTermico(true);
                Dialogo.aviso("Has creado el TRAJE TÉRMICO. Ahora puedes resistir el calor y explorar zonas extremas.");
            } else {
                Dialogo.sistema("Ya tienes el traje térmico equipado.");
            }

        } else {
            Dialogo.error("No tienes suficientes materiales. Se requieren 10x Silicio, 3x Oro y 5x cuarzo.");
        }
    }
    /* (instalarModuloProfunidad)
    * Instala el módulo de profundidad si el jugador posee el ítem correspondiente,
    * permitiendo alcanzar mayores profundidades.
    * @param jugador: Jugador que realiza la instalación.
    * @return void
    */
    private void instalarModuloProfunidad(Jugador jugador){
        if (jugador.tieneItem(ItemTipo.MODULO_PROFUNDIDAD)) {
            this.profundidadSoportada = 1500;
            Dialogo.aviso("Módulo de profundidad instalado correctamente. La nave puede ahora descender hasta los 1500m.");
            restarItem(ItemTipo.MODULO_PROFUNDIDAD, 1);

        } else {
            Dialogo.error("No tienes el MÓDULO DE PROFUNDIDAD en tu inventario para instalarlo.");
        }
    }
    /* (repararNave)
    * Permite reparar la nave estrellada si el jugador posee los planos y materiales requeridos.
    * Finaliza el juego en caso de éxito.
    * @param jugador: Jugador que ejecuta la reparación.
    * @return void
    */
    private void repararNave(Jugador jugador){
        if (!jugador.tienePlanos()) {
            Dialogo.error("No puedes reparar la nave sin el PLANO_NAVE.");
            return;
        }

        int titanio = contarItem(ItemTipo.TITANIO);
        int acero = contarItem(ItemTipo.ACERO);
        int uranio = contarItem(ItemTipo.URANIO);
        int sulfuro = contarItem(ItemTipo.SULFURO);

        if (titanio >= 50 && acero >= 30 && uranio >= 15 && sulfuro >= 20) {
            Dialogo.narrar("Iniciando secuencia de reparación...");
            Dialogo.aviso("Motores encendidos, sistemas restaurados. Escape exitoso!");
            Dialogo.narrar("Has completado el juego. Felicitaciones, has logrado escapar....");

            restarItem(ItemTipo.TITANIO, 50);
            restarItem(ItemTipo.ACERO, 30);
            restarItem(ItemTipo.URANIO, 15);
            restarItem(ItemTipo.SULFURO, 20);

            System.exit(0);

        } else {
            Dialogo.error("Faltan materiales para reparar la nave:");
            Dialogo.sistema("Requiere 50x TITANIO, 30x ACERO, 15x URANIO, 20x SULFURO.");
        }
    }
    /*
    * Crea un Robot Excavador si existen los materiales necesarios en la bodega.
    * @param jugador: Jugador que construye el robot.
    * @return void
    */
    private void crearRobotExcavador(Jugador jugador) {
        int cobre = contarItem(ItemTipo.COBRE);
        int magnetita = contarItem(ItemTipo.MAGNETITA);
        int diamante = contarItem(ItemTipo.DIAMANTE);
        int acero = contarItem(ItemTipo.ACERO);

        if (cobre >= 15 && magnetita >= 10 && diamante >= 5 && acero >= 20) {
            restarItem(ItemTipo.COBRE, 15);
            restarItem(ItemTipo.MAGNETITA, 10);
            restarItem(ItemTipo.DIAMANTE, 5);
            restarItem(ItemTipo.ACERO, 20);

            agregarItemNave(ItemTipo.ROBOT_EXCAVADOR, 1);
            Dialogo.aviso("Has creado un ROBOT EXCAVADOR. Se ha añadido al inventario de la nave.");
        } else {
            Dialogo.error("Faltan materiales para construir el ROBOT EXCAVADOR:");
            Dialogo.sistema("Requiere 15x COBRE, 10x MAGNETITA, 5x DIAMANTE y 20x ACERO.");
        }
    }

    /* (puedeAcceder)
    * Verifica si la nave puede acceder a una profundidad dada según su límite actual.
    * @param requerido: int profundidad mínima requerida por la zona.
    * @return boolean: true si puede acceder, false si no.
    */
    @Override
    public boolean puedeAcceder(int requerido) {
        return requerido <= profundidadSoportada;
    }
    /* (transferirObjetos)
    * Transfiere los objetos del inventario del jugador a la bodega de la nave.
    * Si el jugador no tiene ítems, muestra un mensaje de error.
    * @param jugador: Jugador que transfiere los ítems.
    * @return void
    */
    @Override
    public void transferirObjetos(Jugador jugador) {
        List<Item> inventarioJugador = jugador.getInventario();

        if (inventarioJugador.isEmpty()) {
            Dialogo.error("El inventario del jugador está vacío, nada que transferir.");
            return;
        }

        if (bodega == null) {
            bodega = new ArrayList<>();
        }

        for (Item itemJugador : inventarioJugador) {
            boolean encontrado = false;
            for (Item itemNave : bodega) {
                if (itemNave.getTipo() == itemJugador.getTipo()) {
                    itemNave.setCantidad(itemNave.getCantidad() + itemJugador.getCantidad());
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                bodega.add(new Item(itemJugador.getTipo(), itemJugador.getCantidad()));
            }
        }

        inventarioJugador.clear();
        Dialogo.aviso("Todos los objetos del jugador se han transferido a la nave.");
    }

    /* (Clase interna ModuloProfundidad)
    * Representa el módulo de profundidad de la nave, el cual permite aumentar
    * su límite máximo de descenso al instalarse.
    */
    public class ModuloProfundidad {
        private int profundidadExtra = 1000;
        
        /* (aumentarProfundidad)
        * Aumenta la profundidad soportada de la nave en la cantidad establecida por el módulo.
        * @return void
        */
        public void aumentarProfundidad() {
            profundidadSoportada += profundidadExtra;
        }
    }

    /*
    * Retorna la cantidad total de unidades de recursos almacenadas en la bodega de la nave.
    * Suma todas las cantidades de cada ítem.
    * @return int: total de unidades almacenadas.
    */
    private int getCantidadTotalEnBodega() {
        int total = 0;
        if (bodega != null) {
            for (Item item : bodega) {
                total += item.getCantidad();
            }
        }
        return total;
    }
}