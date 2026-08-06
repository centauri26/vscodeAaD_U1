package excepciones_02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MuestraInfoFicheroNIO {
public static void main(String[] args) {
        String rutaStr = args.length > 0 ? args[0] : ".";
        Path ruta = Path.of(rutaStr);

        if (Files.notExists(ruta)) {
            System.out.printf("El fichero %s no existe%n", rutaStr);
            return;
        }

        if (Files.isRegularFile(ruta)) {
            System.out.printf("El fichero %s es un fichero%n", rutaStr);
        } else if (Files.isDirectory(ruta)) {
            System.out.printf("El fichero %s es un directorio que contiene los ficheros siguientes:%n", rutaStr);
            compruebaDirectorioYficheros(ruta);
        }
    }

    static void compruebaDirectorioYficheros(Path directorio) {
        /* NOTA 1
         * Hay que capturar la excepción IOException que puede lanzar 
         * el método newDirectoryStream.
         * ¡LO ESTUDIAREMOS DESPACIO EN UN EJERCICIO POSTERIOR!
         * en el que estudiaremos el try-with-resources
         */

        /*
         * NOTA 2
         * Ver en la API26 de java.nio.file"Interface DirectoryStream<T>"
         * <This is an object to iterate over the entries in a directory. 
         * A directory stream allows for the convenient use of the 
         * for-each construct to iterate over a directory>
         * Hay que cerrar el stream al terminar de usarlo, para liberar recursos.
         */
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directorio)) {
            for (Path entrada : stream) {
                Path nombre = entrada.getFileName();

                if (Files.isDirectory(entrada)) {
                    // Si es un directorio, mostramos su cabecera y descendemos recursivamente
                    System.out.printf("El fichero %s es un directorio que contiene los ficheros siguientes:%n", nombre);
                    compruebaDirectorioYficheros(entrada);
                } else {
                    // Solo imprimimos como fichero si NO es un directorio (usando else)
                    System.out.printf("%s%n", nombre);
                }
            }stream.close(); //Cerrado cumpliendo con la NOTA 2, para liberar recursos. Se puede hacer con try-with-resources, pero lo dejamos para un ejercicio posterior.
        } catch (IOException e) {
            System.err.printf("Error al acceder al directorio %s: %s%n", directorio, e.getMessage());
        }
    }
}
