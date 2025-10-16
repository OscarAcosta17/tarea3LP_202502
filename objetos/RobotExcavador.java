package objetos;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Util.Dialogo;
import player.Jugador;

public class RobotExcavador extends Vehiculo {
    private int capacidadCarga;
    private boolean operativo;
    protected List<Item> bodega;

    public RobotExcavador() {
        this.capacidadCarga = 1000;
        this.operativo = true;
        this.bodega = new ArrayList<>();
    }
    /*
    * Transfiere todos los objetos almacenados en la bodega del robot a la nave del jugador.
    * Sobrescribe el método de Vehiculo.
    * Si el robot no tiene carga, muestra un mensaje de aviso.
    * @param jugador: Jugador que controla la nave receptora.
    * @return void
    */
    @Override
    public void transferirObjetos(Jugador jugador) {
        if (bodega == null || bodega.isEmpty()) {
            Dialogo.error("La bodega del robot está vacía. No hay carga para transferir.");
            return;
        }

        Dialogo.narrar("Iniciando transferencia de carga del robot hacia la nave...");
        for (Item itemRobot : new ArrayList<>(bodega)) { 
            boolean encontrado = false;
            for (Item itemNave : jugador.getNave().getBodega()) {
                if (itemNave.getTipo() == itemRobot.getTipo()) {
                    itemNave.setCantidad(itemNave.getCantidad() + itemRobot.getCantidad());
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                jugador.getNave().getBodega().add(new Item(itemRobot.getTipo(), itemRobot.getCantidad()));
            }
        }

        bodega.clear();
        Dialogo.aviso("Transferencia completada. La bodega del robot ahora está vacía.");
    }
    /*
     * Retorna la capacidad máxima de carga del robot en unidades de recursos.
     * @return int: capacidad máxima del robot.
     */
    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    /*
     * Establece una nueva capacidad de carga para el robot excavador.
     * @param capacidadCarga: int nueva capacidad en unidades.
     * @return void
     */
    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }
    /*
    * Muestra el menú del Robot Excavador (HUD) con las acciones disponibles.
    * Permite excavar, descargar, reparar o mejorar el robot.
    * @param jugador: Jugador que controla el robot.
    * @param sc: Scanner utilizado para las entradas del usuario.
    * @return void
    */
    public void abrirMenuRobot(Jugador jugador, Scanner sc) {
        boolean enRobot = true;

        while (enRobot) {
            Dialogo.aviso("\n-- ROBOT EXCAVADOR --");
            Dialogo.sistema("Capacidad total: " + capacidadCarga + " | Carga actual: " + getCargaActualTotal());

            Dialogo.sistema("1) Excavar recursos");
            Dialogo.sistema("2) Descargar carga en la nave");
            Dialogo.sistema("3) Reparar robot");
            Dialogo.sistema("4) Mejorar capacidad");
            Dialogo.sistema("5) Volver a la nave");
            Dialogo.sistema("6) Ver inventario del robot");

            int opcion = Util.Entradas.leerEnteroEnRango(sc, "Selecciona una opción:", 1, 6);

            switch (opcion) {
                case 1:
                    excavarRecursos(jugador);
                    break;
                case 2:
                    transferirObjetos(jugador);
                    break;
                case 3:
                    repararRobot();
                    break;
                case 4:
                    mejorarCapacidad();
                    break;
                case 5:
                    Dialogo.aviso("Saliendo del modo Robot Excavador...");
                    enRobot = false;
                    break;
                case 6:
                    verBodega();
                    break;
                default:
                    Dialogo.error("Opción inválida, intenta nuevamente.");
            }
        }
    }
    /*
    * Muestra el contenido actual de la bodega del robot excavador.
    * Lista los ítems almacenados o indica si está vacía.
    * @return void
    */
    public void verBodega() {
        Dialogo.aviso("\n-- Inventario del Robot Excavador --");
        if (bodega == null || bodega.isEmpty()) {
            Dialogo.error("La bodega del robot está vacía.");
            return;
        }

        for (Item item : bodega) {
            Dialogo.sistema("- " + item.getTipo() + " x" + item.getCantidad());
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
    /*
    * Agrega una cantidad de un ítem a la bodega del vehículo.
    * Si el ítem ya existe, incrementa su cantidad.
    * @param tipo: ItemTipo tipo del ítem.
    * @param cantidad: int cantidad a agregar.
    * @return void
    */
    protected void agregarItem(ItemTipo tipo, int cantidad) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        bodega.add(new Item(tipo, cantidad));
    }
    /*
    * Mejora la capacidad de carga del robot excavador en un 25%.
    * Si los materiales están disponibles, los consume y aplica la mejora.
    * @return void
    */
    public void mejorarCapacidad() {
        int titanio = contarItem(ItemTipo.TITANIO);
        int cuarzo = contarItem(ItemTipo.CUARZO);

        // Verifica los materiales requeridos
        if (titanio >= 10 && cuarzo >= 20 ) {
            restarItem(ItemTipo.TITANIO, 10);
            restarItem(ItemTipo.CUARZO, 20);

            int nuevaCapacidad = (int) Math.ceil(capacidadCarga * 1.25);
            capacidadCarga = nuevaCapacidad;

            Dialogo.aviso("¡Capacidad del robot mejorada un 25%! Nueva capacidad: " + capacidadCarga + " unidades.");
        } else {
            Dialogo.error("Faltan materiales para mejorar la capacidad del robot.");
            Dialogo.sistema("Requiere 10x titanio y 20x cuarzo.");
        }
    }

    /*
    * Repara el robot excavador utilizando materiales específicos de su bodega.
    * Solo puede ejecutarse si el robot está dañado (operativo = false).
    * Requiere 4x CABLES, 3x PIEZAS_METAL y 5x MAGNETITA.
    * Si los materiales están disponibles, los consume y deja el robot operativo.
    * @return void
    */
    public void repararRobot() {
        if (operativo) {
            Dialogo.aviso("El robot ya está operativo. No requiere reparaciones.");
            return;
        }
        int cables = contarItem(ItemTipo.CABLES);
        int piezasMetal = contarItem(ItemTipo.PIEZAS_METAL);
        int magnetita = contarItem(ItemTipo.MAGNETITA);

        if (cables >= 4 && piezasMetal >= 3 && magnetita >= 5) {
            restarItem(ItemTipo.CABLES, 4);
            restarItem(ItemTipo.PIEZAS_METAL, 3);
            restarItem(ItemTipo.MAGNETITA, 5);

            this.operativo = true;
            Dialogo.aviso("El robot ha sido reparado correctamente. Todos los sistemas operativos están en línea.");
        } else {
            Dialogo.error("Faltan materiales para reparar el robot excavador.");
            Dialogo.sistema("Requiere 4x CABLES, 3x PIEZAS_METAL y 5x MAGNETITA.");
        }
    }
    /*
    * El robot excavador extrae recursos automáticamente desde la zona actual del jugador.
    * Es inmune a calor y presión, y obtiene recursos del conjunto definido en la zona.
    * Agrega los materiales recolectados a la bodega del robot.
    * @param jugador: Jugador que controla el robot.
    * @return void
    */
    public void excavarRecursos(Jugador jugador) {
        if (!operativo) {
            Dialogo.error("El robot no está operativo. Debes repararlo antes de excavar.");
            return;
        }

        ItemTipo[] recursosArray = jugador.getZonaActual().getRecursos().toArray(new ItemTipo[0]);
        ItemTipo recursoElegido = recursosArray[(int) (Math.random() * recursosArray.length)];

        int cantidad = 5 + (int) (Math.random() * 10); 

        agregarItem(recursoElegido, cantidad);

        Dialogo.narrar("El robot desciende a la " + jugador.getZonaActual().nombre + " y comienza a excavar");
        Dialogo.aviso("Ha recolectado " + cantidad + "x " + recursoElegido + " en la " + jugador.getZonaActual().nombre + ".");
        Dialogo.sistema("Los materiales han sido almacenados en la bodega del robot.");

        int carga = getCargaActualTotal();
        if (carga > capacidadCarga) {
            operativo = false;
            Dialogo.error("El robot se ha sobrecargado (" + carga + "/" + capacidadCarga + "). Requiere reparación.");
        }
    }

   /*
    * Calcula la cantidad total de recursos almacenados actualmente en la bodega del robot excavador.
    * Recorre todos los ítems contenidos en la lista 'bodega' y suma sus cantidades individuales.
    * Si la bodega está vacía o no ha sido inicializada, retorna 0.
    * @return int: cantidad total de unidades almacenadas en la bodega del robot.
    */
    private int getCargaActualTotal() {
        int total = 0;
        if (bodega != null) {
            for (Item it : bodega) total += it.getCantidad();
        }
        return total;
    }
}