package player;

/*
 * Maneja la capacidad y cantidad de oxígeno del jugador
 */
public class Oxigeno {
    private int oxigenoRestante;
    private int capacidadMaxima;

    public Oxigeno(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.oxigenoRestante = capacidadMaxima;
    }

    public int getOxigenoRestante() {
        return oxigenoRestante;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void consumirO2(int unidades) {
        oxigenoRestante -= unidades;
        if (oxigenoRestante < 0) {
            oxigenoRestante = 0;
        }
    }

    public void recargarCompleto() {
        oxigenoRestante = capacidadMaxima;
    }

    // 🔹 fórmulas de costo
    public static int costoExplorar(double d, int presion) {
        return (int) Math.ceil(12 + 10*d + presion);
    }

    public static int costoRecolectar(double d, int presion) {
        return (int) Math.ceil(10 + 6*d + presion);
    }

    public static int costoMover(double d, int deltaZ) {
        return (int) Math.ceil((3 + 3*d) * (Math.abs(deltaZ) / 50.0));
    }
}
