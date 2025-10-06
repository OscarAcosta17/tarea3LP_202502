package objetos;

public class Item {
    private ItemTipo tipo;
    private int cantidad;

    public Item(ItemTipo tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public ItemTipo getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return tipo + " x" + cantidad;
    }
}
