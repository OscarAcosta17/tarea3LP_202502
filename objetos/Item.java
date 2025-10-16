package objetos;

public class Item {
    private ItemTipo tipo;
    private int cantidad;
    /* (Constructor de Item)
    * Crea un nuevo ítem con un tipo y una cantidad específica.
    * @param tipo: ItemTipo tipo del recurso o ítem.
    * @param cantidad: int cantidad inicial del ítem.
    * @return void
    */
    public Item(ItemTipo tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }
    /* (getTipo)
    * Retorna el tipo de ítem representado por la instancia.
    * @return ItemTipo: tipo de recurso o elemento.
    */
    public ItemTipo getTipo() {
        return tipo;
    }
    /* (getCantidad)
    * Retorna la cantidad actual de unidades del ítem.
    * @return int: cantidad almacenada del ítem.
    */
    public int getCantidad() {
        return cantidad;
    }
    /* (setCantidad)
    * Actualiza la cantidad de unidades del ítem.
    * @param cantidad: int nueva cantidad a establecer.
    * @return void
    */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /* (toString)
    * Devuelve una representación en texto del ítem y su cantidad.
    * @return String: tipo del ítem seguido de su cantidad.
    */
    @Override
    public String toString() {
        return tipo + " x" + cantidad;
    }
}