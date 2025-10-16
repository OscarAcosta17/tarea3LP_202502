package player;
import java.util.ArrayList;
import java.util.List;

import Util.Dialogo;

import objetos.Item;
import objetos.ItemTipo;
import entorno.Zona;
import objetos.NaveExploradora;

public class Jugador {
    private Oxigeno tanqueOxigeno;
    private List<Item> inventario;
    private Zona zonaActual;
    private int profundidadActual;
    private boolean tienePlanos;
    private boolean trajeTermico;
    private boolean mejoraTanque;
    private NaveExploradora nave;
    /* (Constructor de Jugador)
    * Crea un nuevo jugador con un tanque de oxígeno inicial y su nave exploradora.
    * @param capacidadOxigeno: int capacidad máxima inicial del tanque de oxígeno.
    * @param naveInicial: NaveExploradora nave asignada al jugador al inicio.
    * @return void
    */
    public Jugador(int capacidadOxigeno, NaveExploradora naveInicial) {
        this.tanqueOxigeno = new Oxigeno(capacidadOxigeno);
        this.inventario = new ArrayList<>();
        this.profundidadActual = 0;
        this.tienePlanos = false;
        this.trajeTermico = false;
        this.mejoraTanque = false;
        this.nave = naveInicial;
    }
    /* (setZonaActual)
    * Asigna la zona actual en la que se encuentra el jugador.
    * @param zona: Zona instancia correspondiente al entorno actual.
    * @return void
    */
    public void setZonaActual(Zona zona) {
        this.zonaActual = zona;
    }
    /* (getZonaActual)
    * Retorna la zona actual en la que se encuentra el jugador.
    * @return Zona: zona donde el jugador está ubicado.
    */
    public Zona getZonaActual() {
        return zonaActual;
    }
    /* (getProfundidadActual)
    * Retorna la profundidad actual del jugador dentro de la zona.
    * @return int: profundidad actual en metros.
    */
    public int getProfundidadActual() {
        return profundidadActual;
    }
    /* (setProfundidadActual)
    * Actualiza la profundidad actual del jugador dentro de la zona.
    * @param profundidad: int nueva profundidad en metros.
    * @return void
    */
    public void setProfundidadActual(int profundidad) {
        this.profundidadActual = profundidad;
    }
    /* (getTanqueOxigeno)
    * Devuelve el tanque de oxígeno asociado al jugador.
    * @return Oxigeno: objeto del tanque de oxígeno.
    */
    public Oxigeno getTanqueOxigeno() {
        return tanqueOxigeno;
    }
    /* (getNave)
    * Retorna la nave exploradora asociada al jugador.
    * @return NaveExploradora: nave del jugador.
    */
    public NaveExploradora getNave() {
        return nave;
    }
    /* (verEstadoJugador)
    * Muestra por pantalla la información general del jugador: zona, profundidad y oxígeno.
    * @return void
    */
    public void verEstadoJugador() {
        Dialogo.aviso("=== Estado del Jugador ===");
        
        Dialogo.aviso("Zona actual: "+ zonaActual.nombre + " | Profundidad: " + profundidadActual + " m | O2: "+ tanqueOxigeno.getOxigenoRestante() + "/"+ tanqueOxigeno.getCapacidadMaxima());
    }
    /* (agregarItem)
    * Añade un nuevo ítem al inventario o incrementa su cantidad si ya existe.
    * @param tipo: ItemTipo tipo del recurso o ítem.
    * @param cantidad: int cantidad del ítem a agregar.
    * @return void
    */
    public void agregarItem(ItemTipo tipo, int cantidad) {
        for (Item item : inventario) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // acumula en vez de duplicar
            }
        }
        inventario.add(new Item(tipo, cantidad));
    }
    /* (verInventario)
    * Muestra el inventario actual del jugador, listando todos los ítems almacenados.
    * @return void
    */
    public void verInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Inventario vacío.");
            return;
        }
        System.out.println("=== Inventario ===");
        for (Item item : inventario) {
            System.out.println("- " + item);
        }
    }
    /* (getInventario)
    * Devuelve la lista completa de ítems almacenados en el inventario del jugador.
    * @return List<Item>: lista con los ítems del jugador.
    */
    public List<Item> getInventario() {
        return inventario;
    }
    /* (aplicarMejoraTanque)
    * Duplica la capacidad del tanque de oxígeno y activa resistencia a presión.
    * @return void
    */
    public void aplicarMejoraTanque() {
        if (!mejoraTanque) {
            mejoraTanque = true;

            int capacidadBase = tanqueOxigeno.getCapacidadMaxima();
            tanqueOxigeno.setCapacidadMaxima(capacidadBase * 2);  // +100%

            Dialogo.aviso("¡Has mejorado tu tanque! Capacidad de O₂ duplicada y resistencia a presión activada.");
        } else {
            Dialogo.error("Tu tanque ya está mejorado.");
        }
    }
    /* (tieneMejoraTanque)
    * Indica si el jugador posee la mejora de tanque instalada.
    * @return boolean: true si tiene la mejora, false en caso contrario.
    */
    public boolean tieneMejoraTanque() {
        return mejoraTanque;
    }
    /* (setMejoraTanque)
    * Asigna manualmente el estado de la mejora de tanque (usado para testeo).
    * @param valor: boolean estado de la mejora.
    * @return void
    */
    public void setMejoraTanque(boolean valor) { //test
        this.mejoraTanque = valor;
    }
    /* (setTrajeTermico)
    * Define si el jugador cuenta con el traje térmico equipado.
    * @param valor: boolean estado del traje térmico.
    * @return void
    */
    public void setTrajeTermico(boolean valor) {
        this.trajeTermico = valor;
    }
    
    /* (tieneTrajeTermico)
    * Indica si el jugador posee un traje térmico activo.
    * @return boolean: true si tiene traje térmico, false en caso contrario.
    */
    public boolean tieneTrajeTermico() {
        return trajeTermico;
    }
    /* (tieneItem)
    * Verifica si el jugador posee un ítem específico en su inventario.
    * @param tipo: ItemTipo tipo de ítem a buscar.
    * @return boolean: true si el ítem existe, false si no.
    */
    public boolean tieneItem(ItemTipo tipo) {
        return inventario.stream().anyMatch(item -> item.getTipo() == tipo);
    }
    /* (setTienePlanos)
    * Define si el jugador ha obtenido los planos de reparación de la nave.
    * @param valor: boolean estado de posesión de los planos.
    * @return void
    */
    public void setTienePlanos(boolean valor) {
        this.tienePlanos = valor;
    }
    /* (tienePlanos)
    * Indica si el jugador posee los planos de reparación de la nave estrellada.
    * @return boolean: true si los posee, false si no.
    */
    public boolean tienePlanos() {
        return tienePlanos;
    }
    /* (perderInventario)
    * Elimina todos los ítems del inventario del jugador y muestra un mensaje de pérdida.
    * @return void
    */
    public void perderInventario() {
        inventario.clear();
        Dialogo.error("Has perdido todo tu inventario actual.");
    }
}