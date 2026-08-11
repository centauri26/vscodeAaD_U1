package csv_04;


/**
 * En JDK 21, representamos entidades de datos inmutables con Records.
 * Genera automáticamente constructores, getters (codigo(), descripcion(), precio()), 
 * equals(), hashCode() y toString().
 */
public record Producto(Integer codigo, String descripcion, Double precio) {

    /**
     * Serializa la instancia a una fila de CSV.
     */
    public String toCsvFila(String separador) {
        return codigo + separador + descripcion + separador + precio;
    }

    /**
     * Factory Method (método estático de fábrica) para deserializar.
     */
    public static Producto deCsvFila(String linea, String separador) {
        String[] campos = linea.split(separador);
        if (campos.length != 3) {
            throw new IllegalArgumentException("Línea CSV mal formada: " + linea);
        }
        return new Producto(
            Integer.parseInt(campos[0].trim()),
            campos[1].trim(),
            Double.parseDouble(campos[2].trim())
        );
    }
}
