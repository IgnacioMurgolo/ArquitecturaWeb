package dto;

public class ClienteDTO {
    private String nombre;
    private String email;
    private float montoFacturado;

    public ClienteDTO(String nombre, String email, float montoFacturado) {
        this.nombre = nombre;
        this.email = email;
        this.montoFacturado = montoFacturado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getMontoFacturado() {
        return montoFacturado;
    }

    public void setMontoFacturado(float montoFacturado) {
        this.montoFacturado = montoFacturado;
    }

    @Override
    public String toString() {
        return "nombre= " + nombre +
                ", email= " + email +
                ", monto facturado= " + montoFacturado;
    }
}
