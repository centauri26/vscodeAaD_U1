package accesosecuencial_03;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class ProcesadorFicheroBinario {
    public static void escribirDatosBinarios(Path ruta) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(Files.newOutputStream(ruta)))) {
            
            dos.writeInt(101);             // ID
            dos.writeUTF("Servidor-PROD"); // Nombre
            dos.writeDouble(99.9);         // Uptime %
            dos.writeBoolean(true);        // Activo
        }
    }

    public static void leerDatosBinarios(Path ruta) throws IOException {
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(ruta)))) {
            
            int id = dis.readInt();
            String nombre = dis.readUTF();
            double uptime = dis.readDouble();
            boolean activo = dis.readBoolean();

            System.out.printf("Leído -> ID: %d | Nombre: %s | Uptime: %.1f%% | Activo: %b%n",
                    id, nombre, uptime, activo);
        }
    }
}
