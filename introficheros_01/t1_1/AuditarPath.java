package introficheros_01.t1_1;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class AuditarPath {
    /*
     * REQUISITO 1:
     * Es interesante conocer el package java.time.format, 
     * que nos permite formatear fechas y horas de manera flexible.
     */
    private static final DateTimeFormatter FORMATO_FECHA = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
     * REQUISITO 2: 
     * Reutilizo el código de la clase MuestraInfoFicheroNIO 
     * como indica el enenuciado de esta tarea
    */
    public static void main(String[] args) {
        String rutaStr = args.length > 0 ? args[0] : ".";
        Path ruta = Path.of(rutaStr);

        // REQUISITO 4: Compruebo si la ruta existe antes de auditarla
        if (Files.notExists(ruta)) {
            System.err.printf("Error: La ruta '%s' no existe.%n", rutaStr);
            return;
        }
        /*
         * REQUISITO 3: 
         * Utilizar toAbsolutePath() paramostrar la ruta completa en la auditoría.
         * Si el path ya es completo(absoluto) no cambia nada, pero si es relativo,
         * consigue mostrar la ruta completa.
        */
        System.out.println("=========================================================================================");
        System.out.printf("AUDITORÍA DE LARUTA: %s%n", ruta.toAbsolutePath());
        System.out.println("=========================================================================================");
        
        auditarDirectorioRecursivamente(ruta);
    }

    /*
     * REQUISITO 5: 
     * Implementar un método "auditarDirectorioRecursivamente" que debe ser privado y estático.
     * Su funcionalidad será recorrer y revisar el Directorio de forma recursiva.
     * En cada iteración, debe pintar los datos de auditoría del fichero o directorio actual 
     * usando un método "pintarInfoAuditoria" que también hay que implementar.
     */
    private static void auditarDirectorioRecursivamente(Path directorio) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directorio)) {
            for (Path entrada : stream) {
                //REQUISITO 6: Imprimir los datos, requeridos en el enunciado, de la entrada actual archivo o directorio
                pintarInfoAuditoria(entrada);

                //REQUISITO 7: Además, si es un directorio, aplicar recursividad
                if (Files.isDirectory(entrada)) {
                    auditarDirectorioRecursivamente(entrada);
                }
            }
        } catch (IOException e) {
            System.err.printf("Error al auditar el directorio '%s': %s%n", directorio, e.getMessage());
        }
    }

    /*
     * REQUISITO 8: 
     * Implementar un método "pintarInfoAuditoria" que debe ser privado y estático.
     * Su funcionalidad será imprimir los datos de auditoría del fichero o directorio actual.
     */
    private static void pintarInfoAuditoria(Path path) {
        try {
            //REQUISITO 9: Leer todos los atributos básicos del fichero de la forma máseficiente
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            //REQUISITO 10: Componer y formatear el nombre del fichero o directorio
            String nombre = path.getFileName().toString();
            if (attrs.isDirectory()) {
                nombre = "[DIR] " + nombre;
            }

            //REQUISITO 11: Obtener el tamaño en bytes
            long tamanio = attrs.size();

            //REQUISITO 12: Obtener los permisos en formato rwx invocando al método obtenerPermisosRWX
            String permisos = obtenerPermisosRWX(path);

            //REQUISITO 13: Obtener la fecha de última modificación
            LocalDateTime fechaModificacion = LocalDateTime.ofInstant(
                    attrs.lastModifiedTime().toInstant(), 
                    ZoneId.systemDefault()
            );
            String fechaFormateada = fechaModificacion.format(FORMATO_FECHA);

            //REQUISITO 14: Formateo en tabla para presentar la información de forma limpia en la consola
            System.out.printf("%-10s | %-19s | %12d bytes | %s%n", 
                    permisos, fechaFormateada, tamanio, nombre);

        } catch (IOException e) {
            System.err.printf("No se pudo leer la información de '%s': %s%n", path.getFileName(), e.getMessage());
        }
    }

    /**
     * REQUISITO 15: 
     * Implementar un método "obtenerPermisosRWX" que debe ser privado y estático
     * y que devuelve los permisos en formato rwx.
     * Debe soportar en el PASO 1: la lectura POSIX (Linux/macOS) y en el PASO 2: fallback para Windows.
     */
    private static String obtenerPermisosRWX(Path path) {
        try {//PASO 1: Intentar leer permisos estilo POSIX (Linux, macOS, Unix)
            Set<PosixFilePermission> posixPermissions = Files.getPosixFilePermissions(path);
            return PosixFilePermissions.toString(posixPermissions);
        } catch (UnsupportedOperationException e) {
            //PASO 2: Fallback para Windows(Sistemas de archivos sin POSIX como NTFS/FAT32)
            char r = Files.isReadable(path) ? 'r' : '-';
            char w = Files.isWritable(path) ? 'w' : '-';
            char x = Files.isExecutable(path) ? 'x' : '-';
            return String.format("%c%c%c------", r, w, x);
        } catch (IOException e) {
            return "---------";
        }
    }
}