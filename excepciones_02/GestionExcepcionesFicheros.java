package excepciones_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestionExcepcionesFicheros {
public static void main(String[] args) {
        System.out.println("--- Inicio del programa ---\n");

        // Intentamos leer un fichero existente y uno inexistente para probar
        procesarFichero("config.txt");
        System.out.println("------------------------------------");
        procesarFichero("fichero_inexistente.txt");

        System.out.println("\n--- Fin del programa ---");
    }

    /**
     * Punto 3: Método que propaga una excepción mediante 'throws'.
     * Delega la responsabilidad de gestionar la IOException a quien lo llame.
     */
    public static void leerFichero(String ruta) throws IOException, IllegalArgumentException {
        if (ruta == null || ruta.isBlank()) {
            // Lanzamos manualmente una excepción runtime si el parámetro es inválido
            throw new IllegalArgumentException("La ruta del fichero no puede estar vacía.");
        }

        /*
         * Punto 5: Try-with-resources (AutoCloseable).
         * BufferedReader e FileReader implementan AutoCloseable.
         * Se cerrarán automáticamente en orden inverso al de creación:
         * 1º BufferedReader, 2º FileReader.
         */
        try (FileReader fr = new FileReader(ruta);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            System.out.println("Contenido del fichero:");
            while ((linea = br.readLine()) != null) {
                System.out.println(" > " + linea);
            }

        } // En este punto se ejecuta el .close() implícito de br y fr.
    }

    public static void procesarFichero(String ruta) {
        /*
         * Punto 2: Múltiples bloques catch.
         * Se deben capturar de la más específica a la más general.
         */
        try {
            System.out.println("Intentando abrir: " + ruta);
            leerFichero(ruta);

        } catch (FileNotFoundException e) {
            // Excepción específica 1: Fichero no encontrado
            System.err.println("[ERROR ESPECÍFICO] El fichero no existe.");
            
            /*
             * Punto 1: Métodos getMessage() y printStackTrace()
             */
            System.out.println("Mensaje corto (getMessage): " + e.getMessage());
            
            System.out.println("\nTraza completa (printStackTrace):");
            e.printStackTrace(); // Imprime por consola dónde ocurrió el fallo

        } catch (IOException e) {
            // Excepción específica 2: Error general de E/S
            System.err.println("[ERROR I/O] Fallo al leer el contenido del fichero: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            // Excepción específica 3: Argumentos no válidos
            System.err.println("[ERROR PARÁMETRO] " + e.getMessage());

        } catch (Exception e) {
            // Catch genérico para cualquier otro error imprevisto
            System.err.println("[ERROR UNKNOWN] Error inesperado: " + e.getMessage());

            /*
             * Punto 1: Inspección de la traza mediante getStackTrace()
             */
            StackTraceElement[] traza = e.getStackTrace();
            if (traza.length > 0) {
                System.out.println("Error originado en la clase: " + traza[0].getClassName() +
                                   ", línea: " + traza[0].getLineNumber());
            }

        } finally {
            /*
             * Punto 4: Bloque finally.
             * Se ejecuta SIEMPRE, haya habido excepción o no.
             */
            System.out.println("[FINALLY] Operación de procesamiento finalizada para: " + ruta);
        }
    }
}
