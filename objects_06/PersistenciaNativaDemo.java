package objects_06;

import java.io.*;

public class PersistenciaNativaDemo {

    public static void main(String[] args) {
        File archivo = new File("usuario.bin");
        Usuario usuarioOriginal = new Usuario("Ana Gómez", "ana@example.com", 25);

        // Escritura con ObjectOutputStream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarioOriginal);
            System.out.println("Objeto guardado (Serialización nativa).");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lectura con ObjectInputStream
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Usuario usuarioLeido = (Usuario) ois.readObject();
            System.out.println("Objeto leído (Deserialización nativa): " + usuarioLeido);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}