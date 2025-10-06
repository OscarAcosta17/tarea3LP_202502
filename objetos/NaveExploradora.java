package objetos;
import Util.Dialogo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import player.Jugador;

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {
    private int profundidadSoportada;

    public NaveExploradora() {
        this.profundidadSoportada = 500; // base
        this.bodega = new ArrayList<>();
    }

    public int getProfundidadSoportada() {
        return profundidadSoportada;
    }

    private int profundidadAnclaje = 0;  // anclaje actual de la nave

    public int getProfundidadAnclaje() {
        return profundidadAnclaje;
    }

    public void anclarNave(int nuevaProfundidad) {
        if (nuevaProfundidad < 0 || nuevaProfundidad > profundidadSoportada) {
            Dialogo.error("No puedes anclar más allá de la profundidad soportada (" + profundidadSoportada + " m).");
            return;
        }
        this.profundidadAnclaje = nuevaProfundidad;
        Dialogo.aviso("Anclaje ajustado a " + profundidadAnclaje + " m.");
    }

    public void abrirMenuNave(Jugador jugador, Scanner sc) {
        boolean enNave = true;

        while (enNave) {
            Dialogo.aviso(  "\n-- NAVE EXPLORADORA --");
            Dialogo.aviso("Zona actual: " + jugador.getZonaActual().nombre + " | Anclaje: "+ profundidadAnclaje +"m | Inventario nave: " + (bodega != null ? bodega.size() : 0));

            Dialogo.sistema("1) Ajustar profundidad de nave (anclaje)");
            Dialogo.sistema("2) Guardar todos los objetos en la nave");
            Dialogo.sistema("3) Crear objetos (no implementado x.x)");
            Dialogo.sistema("4) Moverse a otra zona");
            Dialogo.sistema("5) Salir de la nave");
            Dialogo.sistema("6) Ver inventario de la nave");
            Dialogo.sistema("7) Recargar oxigeno");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    Dialogo.aviso("Nueva profundidad de anclaje (0 - " + profundidadSoportada + "): ");
                    int nuevaProfundidad = sc.nextInt();
                    sc.nextLine();
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
                    Dialogo.sistema("0) Cancelar");

                    int eleccion = sc.nextInt();
                    sc.nextLine();

                    switch (eleccion) {
                        case 1:
                            crearMejoraTanque(jugador);
                            break;
                        case 2:
                            //crearTrajeTermico(jugador);
                            break;
                        case 3:
                            
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
                    Dialogo.sistema("0) Cancelar");

                    int opcionZona = sc.nextInt();
                    sc.nextLine();

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
                        Dialogo.error("Tu nave no puede alcanzar esa profundidad (mínimo " + profundidadDestino + " m).");
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
                default:
                    Dialogo.error("Opción no válida.");
            }
        }
    }
    private int contarItem(ItemTipo tipo) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                return item.getCantidad();
            }
        }
        return 0;
    }

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

    private void agregarItemNave(ItemTipo tipo, int cantidad) {
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        bodega.add(new Item(tipo, cantidad));
    }

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

    @Override
    public boolean puedeAcceder(int requerido) {
        return requerido <= profundidadSoportada;
    }

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


    // Clase anidada para el módulo de profundidad
    public class ModuloProfundidad {
        private int profundidadExtra = 1000;

        public void aumentarProfundidad() {
            profundidadSoportada += profundidadExtra;
        }
    }
}
