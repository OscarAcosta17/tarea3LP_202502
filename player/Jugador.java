package player;
import java.util.ArrayList;
import java.util.List;

import Util.Dialogo;

import objetos.Item;
import objetos.ItemTipo;
import entorno.Zona;
import objetos.NaveExploradora;

/*
 * Representa al jugador en el juego
 */
public class Jugador {
    private Oxigeno tanqueOxigeno;
    private List<Item> inventario;
    private Zona zonaActual;
    private int profundidadActual;
    private boolean tienePlanos;
    private boolean trajeTermico;
    private boolean mejoraTanque;
    private NaveExploradora nave;

    public Jugador(int capacidadOxigeno, NaveExploradora naveInicial) {
        this.tanqueOxigeno = new Oxigeno(capacidadOxigeno);
        this.inventario = new ArrayList<>();
        this.profundidadActual = 0;
        this.tienePlanos = false;
        this.trajeTermico = false;
        this.mejoraTanque = false;
        this.nave = naveInicial;
    }

    public void setZonaActual(Zona zona) {
        this.zonaActual = zona;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public int getProfundidadActual() {
        return profundidadActual;
    }

    public void setProfundidadActual(int profundidad) {
        this.profundidadActual = profundidad;
    }

    public Oxigeno getTanqueOxigeno() {
        return tanqueOxigeno;
    }

    public NaveExploradora getNave() {
        return nave;
    }

    public void verEstadoJugador() {
        Dialogo.aviso("=== Estado del Jugador ===");
        
        Dialogo.aviso("Zona actual: "+ zonaActual.nombre + " | Profundidad: " + profundidadActual + " m | O2: "+ tanqueOxigeno.getOxigenoRestante() + "/"+ tanqueOxigeno.getCapacidadMaxima());
    }

    public void agregarItem(ItemTipo tipo, int cantidad) {
        for (Item item : inventario) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // acumula en vez de duplicar
            }
        }
        inventario.add(new Item(tipo, cantidad));
    }

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

    public List<Item> getInventario() {
        return inventario;
    }

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

    public boolean tieneMejoraTanque() {
        return mejoraTanque;
    }
    
}
