package streams_05;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class CopiarByteAByte {

    public static void copiarFichero(Path origen, Path destino) {
        System.out.println("Iniciando copia de: " + origen.getFileName() + " -> " + destino.getFileName());

        /*
         * REQUISITO 1: Abrir simultáneamente los ficheros de origen y destino con 
         * InputStream y OutputStream en el try-with-resources
         */
        try (InputStream in = new FileInputStream(origen.toFile());
             OutputStream out = new FileOutputStream(destino.toFile())) {

            int byteLeido;
            long bytesCopiados = 0;

            /*
             * REQUISITO 2: Crear un bucle secuencial en el que 
             * Leer 1 byte del origen y lo escribimos en el destino
             */
            while ((byteLeido = in.read()) != -1) {
                out.write(byteLeido);
                bytesCopiados++;
            }

            /*
             * REQUISITO 3: Asegurar que 
             * los bytes pendientes en el búfer del SO se graben físicamente
             */
            out.flush();

            System.out.printf("Copia completada con éxito. Total de bytes procesados: %,d bytes.%n", bytesCopiados);

        } catch (IOException e) {
            System.err.println("Error durante el proceso de copia: " + e.getMessage());
        }
    }
}
