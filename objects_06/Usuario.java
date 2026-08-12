package objects_06;

import java.io.Serializable;

// Para la versión tradicional necesitábamos 'implements Serializable'
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;
    private int edad;

    // Constructor vacío necesario para Jackson
    public Usuario() {}

    public Usuario(String nombre, String email, int edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    @Override
    public String toString() {
        return "Usuario{" + "nombre='" + nombre + '\'' + ", email='" + email + '\'' + ", edad=" + edad + '}';
    }
}
