package es.dam;

import java.io.IOException;

public class Appgson {
    public static void main(String[] args) {
        String archivoPrueba = "producto.json";

        // Instanciar objetos de prueba
        Producto laptop = new Producto(101, "Portátil Gaming 16GB", 1250.99);
        Serializadora serializadora = new Serializadora();
        Deserializadora deserializadora = new Deserializadora();

        try {
            System.out.println("=== 1. SERIALIZACIÓN ===");
            // Serialización a String
            String jsonOutput = serializadora.serializarAString(laptop);
            System.out.println("JSON en String:\n" + jsonOutput);

            // Serialización a Fichero
            serializadora.serializarAFichero(laptop, archivoPrueba);
            System.out.println("Objeto guardado con éxito en: " + archivoPrueba);

            System.out.println("\n=== 2. DESERIALIZACIÓN ===");
            // Deserialización desde String
            Producto productoDesdeString = deserializadora.deserializarDesdeString(jsonOutput);
            System.out.println("Objeto recuperado desde String: " + productoDesdeString);

            // Deserialización desde Fichero
            Producto productoDesdeFichero = deserializadora.deserializarDesdeFichero(archivoPrueba);
            System.out.println("Objeto recuperado desde Fichero: " + productoDesdeFichero);

        } catch (IOException e) {
            System.err.println("Error durante el procesamiento I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
}