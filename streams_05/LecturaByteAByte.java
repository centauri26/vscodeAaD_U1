package streams_05;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LecturaByteaByte {
    public static void leerFichero(String rutaArchivo) {
        System.out.println("--- Inicio de lectura byte a byte ---");
        /*
         * REQUISITO 1: Abrir el fichero con FileInputStream
         * REQUISITO 2:Utilizar try-with-resources 
         * para garantizar el cierre automático del flujo
         */
        try (InputStream input = new FileInputStream(rutaArchivo)) {
            int byteLeido;
            int totalBytes = 0;

            /*
             * REQUISITO 3: leer el fichero byte a byte
             * hasta llegar al final del fichero (EOF) usando read() que devuelve
             * el byte en formato int (0 a 255) o -1 si llega al EOF
             */
            while ((byteLeido = input.read()) != -1) {
           // Imprimimos el valor numérico del byte y su representación ASCII/Char
                System.out.printf("Byte %d: Valor decimal = %3d | que convertido a char es = '%c'%n", 
                        totalBytes + 1, byteLeido, (char) byteLeido);
                totalBytes++;
            }

            System.out.println("-------------------------------------");
            System.out.println("Lectura finalizada. Total bytes leídos: " + totalBytes);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
