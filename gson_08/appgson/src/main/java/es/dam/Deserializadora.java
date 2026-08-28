package gson_08.appgson.src.main.java.es.dam;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Deserializadora {

    private final Gson gson;

    public Deserializadora() {
        this.gson = new Gson();
    }

    /**
     * Deserializa una cadena JSON a un objeto Producto.
     */
    public Producto deserializarDesdeString(String jsonText) {
        return this.gson.fromJson(jsonText, Producto.class);
    }

    /**
     * Deserializa datos JSON desde un fichero a un objeto Producto.
     */
    public Producto deserializarDesdeFichero(String rutaFichero) throws IOException {
        try (FileReader reader = new FileReader(rutaFichero)) {
            return this.gson.fromJson(reader, Producto.class);
        }
    }
}
