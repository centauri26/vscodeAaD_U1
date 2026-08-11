package csv_04;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class AppProductoCSV {

    // Abstracción moderna con Path.of(...) en lugar de new File(...)
    private static final Path RUTA_ARCHIVO = Path.of("productos.csv");

    public static void main(String[] args) {
        
        // Uso de List.of() (Lista inmutable) y sintaxis limpia con 'var'
        var productosOriginales = List.of(
            new Producto(101, "Teclado Mecánico", 29.99),
            new Producto(102, "Ratón Inalámbrico Ergonomico", 45.50),
            new Producto(103, "Monitor 27 IPS 144Hz", 249.90),
            new Producto(104, "Auriculares Gaming 7.1", 69.00),
            new Producto(105, "Alfombrilla XL", 15.25)
        );

        System.out.println("=== 1. GUARDANDO PRODUCTOS (JDK 21 + java.nio.file) ===");
        var writer = new ProductoCsvWriter(";");
        try {
            writer.escribirProductos(RUTA_ARCHIVO, productosOriginales);
            System.out.println("✔ Fichero '" + RUTA_ARCHIVO.toAbsolutePath() + "' guardado correctamente.\n");
        } catch (IOException e) {
            System.err.println("❌ Error en la escritura: " + e.getMessage());
            return;
        }

        System.out.println("=== 2. LEYENDO PRODUCTOS (JDK 21 + Streams) ===");
        var reader = new ProductoCsvReader(";");
        try {
            List<Producto> productosLeidos = reader.leerProductos(RUTA_ARCHIVO);
            
            System.out.println("Recuperados " + productosLeidos.size() + " productos del disco:");
            System.out.println("------------------------------------------------------------------");
            productosLeidos.forEach(p -> 
                System.out.printf("Producto [Código=%d, Descripción='%-28s', Precio=%.2f €]%n", 
                        p.codigo(), p.descripcion(), p.precio())
            );
            System.out.println("------------------------------------------------------------------");

        } catch (IOException e) {
            System.err.println("❌ Error en la lectura: " + e.getMessage());
        }
    }
}
