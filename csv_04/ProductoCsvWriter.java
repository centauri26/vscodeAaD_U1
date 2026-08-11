package csv_04;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ProductoCsvWriter {

    private final String separador;

    public ProductoCsvWriter(String separador) {
        this.separador = separador;
    }

    public ProductoCsvWriter() {
        this(";");
    }

    /**
     * Escribe la lista de productos usando la API NIO.2 (Path / Files).
     */
    public void escribirProductos(Path rutaArchivo, List<Producto> productos) throws IOException {
        // Files.newBufferedWriter crea o sobrescribe el archivo en UTF-8 eficientemente
        try (var writer = Files.newBufferedWriter(
                rutaArchivo, 
                StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING)) {
            
            // 1. Cabecera
            writer.write("codigo" + separador + "descripcion" + separador + "precio");
            writer.newLine();

            // 2. Registros
            for (var producto : productos) {
                writer.write(producto.toCsvFila(separador));
                writer.newLine();
            }
        }
    }
}
