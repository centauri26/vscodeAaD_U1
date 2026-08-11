package csv_04;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ProductoCsvReader {

    private final String separador;

    public ProductoCsvReader(String separador) {
        this.separador = separador;
    }

    public ProductoCsvReader() {
        this(";");
    }

    /**
     * Lee el fichero CSV usando Streams de JDK 21 sobre NIO.2.
     */
    public List<Producto> leerProductos(Path rutaArchivo) throws IOException {
        // Importante: Files.lines abre un flujo de I/O subyacente que DEBE cerrarse con try-with-resources
        try (Stream<String> lineas = Files.lines(rutaArchivo, StandardCharsets.UTF_8)) {
            return lineas
                .skip(1)                                // Saltar cabecera
                .map(String::trim)                        // Limpiar espacios
                .filter(linea -> !linea.isEmpty())       // Filtrar líneas vacías
                .map(linea -> Producto.deCsvFila(linea, separador)) // Mapear a objeto Record
                .toList();                              // Método inmutable toList() (Java 16+)
        }
    }
}
