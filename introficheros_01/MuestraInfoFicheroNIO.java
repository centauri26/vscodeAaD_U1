package introficheros_01;

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
            }
        } catch (IOException e) {
            System.err.printf("Error al acceder al directorio %s: %s%n", directorio, e.getMessage());
        }
    }
}
