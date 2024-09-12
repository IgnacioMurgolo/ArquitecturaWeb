package dto;

public class ProductoDTO {
    private String nombre;
    private float valor;
    private float recaudacion;

    public ProductoDTO(String nombre, float valor, float recaudacion) {
        this.nombre = nombre;
        this.valor = valor;
        this.recaudacion = recaudacion;
    }

    public String getNombre() {
        return nombre;
    }

    public float getValor() {
        return valor;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    @Override
    public String toString() {
        return "nombre= " + nombre +
                ", valor= " + valor +
                ", recaudacion= " + recaudacion;
    }
}
