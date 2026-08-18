package randomaccess_07;

public class DefinicionCampo {
    private String nombre;
    private int longitudBytes;

    public DefinicionCampo(String nombre, int longitudBytes) {
        this.nombre = nombre;
        this.longitudBytes = longitudBytes;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLongitudBytes() {
        return longitudBytes;
    }
}
