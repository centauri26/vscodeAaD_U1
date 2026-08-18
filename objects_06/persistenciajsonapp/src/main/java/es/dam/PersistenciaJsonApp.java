package es.dam;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class PersistenciaJsonApp {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File archivo = new File("usuario.json");

        Usuario usuarioOriginal = new Usuario("Carlos Pérez", "carlos@example.com", 30);

        /*
         * REQUISITO 1 DE ESCRITURA: 
         * Guardar objeto como JSON en archivo
         */
        try {//REQUISITO 2 try-catch-with-resources
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, usuarioOriginal);
            System.out.println("Objeto guardado correctamente en JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * REQUISITO 3 DE LECTURA: 
         * Recuperar objeto desde archivo JSON
         */
        try {//REQUISITO 2 try-catch-with-resources
            Usuario usuarioLeido = mapper.readValue(archivo, Usuario.class);
            System.out.println("Objeto leído desde JSON: " + usuarioLeido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}