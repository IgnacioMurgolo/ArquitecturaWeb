package model;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String email;

    public Cliente(int idCliente, String nombre, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
    }

    public int getIdCliente() {
        return idCliente;
    }


    public String getNombre() {
        return nombre;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "idCliente=" + idCliente +
                ", nombre=" + nombre +
                ", email=" + email;
    }
}
