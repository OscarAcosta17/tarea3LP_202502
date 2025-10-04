package player;
import java.util.ArrayList;
import java.util.List;

import Util.Dialogo;

import objetos.Item;
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

    public void verEstadoJugador() {
        Dialogo.aviso("=== Estado del Jugador ===");
        
        Dialogo.aviso("Oxígeno: " + tanqueOxigeno.getOxigenoRestante() + "/" + tanqueOxigeno.getCapacidadMaxima());
        Dialogo.aviso("Profundidad actual: " + profundidadActual);
        Dialogo.aviso("Zona actual: " + (zonaActual != null ? zonaActual.nombre : "Ninguna"));
        Dialogo.aviso("Planos obtenidos: " + tienePlanos);
        Dialogo.aviso("Traje térmico: " + trajeTermico);
        Dialogo.aviso("Mejora de tanque: " + mejoraTanque);
    }
}
