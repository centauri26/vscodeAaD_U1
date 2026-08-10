package accesosecuencial_03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class ProcesadorFicheroDeTexto {

    public static void escribirTexto(Path ruta, String contenido) throws IOException {
        // Uso de try-with-resources para garantizar el cierre automático de streams
        try (BufferedWriter writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            writer.write(contenido);
            writer.newLine();
        }
    }

    public static void leerTextoSecuencial(Path ruta) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Línea procesada: " + linea);
            }
        }
    }
}
