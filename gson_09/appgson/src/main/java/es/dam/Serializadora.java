package es.dam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class Serializadora {

    private final Gson gson;

    public Serializadora() {
        // Usamos GsonBuilder para generar un JSON formateado ("Pretty Printing")
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Serializa un producto a una cadena de texto en formato JSON.
     */
    public String serializarAString(Producto producto) {
        return this.gson.toJson(producto);
    }

    /**
     * Serializa un producto y lo escribe directamente en un fichero de texto.
     */
    public void serializarAFichero(Producto producto, String rutaFichero) throws IOException {
        try (FileWriter writer = new FileWriter(rutaFichero)) {
            this.gson.toJson(producto, writer);
        }
    }
}
