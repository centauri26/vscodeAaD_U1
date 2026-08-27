package randomaccess_07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        
        // 1. Definición de los campos para el Registro del Cliente
        // DNI (9 bytes), Nombre (30 bytes), Código Postal (5 bytes) -> Total Registro = 44 bytes
        List<DefinicionCampo> estructuraCliente = new ArrayList<>();
        estructuraCliente.add(new DefinicionCampo("DNI", 9));
        estructuraCliente.add(new DefinicionCampo("NOMBRE", 30));
        estructuraCliente.add(new DefinicionCampo("CP", 5));

        String nombreArchivo = "clientes_db.dat";

        try {
            // Instanciar el gestor
            GestorFicheroAccesoAleatorio gestor = new GestorFicheroAccesoAleatorio(nombreArchivo, estructuraCliente);
            
            System.out.println("--- GESTOR DE FICHERO ALEATORIO INICIALIZADO ---");
            System.out.println("Tamaño por registro: " + gestor.getTamanoRegistroBytes() + " bytes.");
            System.out.println("Registros existentes al abrir: " + gestor.getNumRegistros());

            // 2. Crear datos del Cliente 1
            Map<String, String> cliente1 = new HashMap<>();
            cliente1.put("DNI", "12345678A");
            cliente1.put("NOMBRE", "Juan Pérez García");
            cliente1.put("CP", "28001");

            // Insertar cliente 1 al final (posición 0)
            gestor.insertar(cliente1);
            System.out.println("Cliente 1 insertado al final.");

            // 3. Crear datos del Cliente 2
            Map<String, String> cliente2 = new HashMap<>();
            cliente2.put("DNI", "87654321B");
            cliente2.put("NOMBRE", "Ana Gómez López");
            cliente2.put("CP", "41002");

            // Insertar cliente 2 al final (posición 1)
            gestor.insertar(cliente2);
            System.out.println("Cliente 2 insertado al final.");

            // 4. Modificar o sobrescribir directamente la Posición 0 (Cliente 1)
            Map<String, String> clienteModificado = new HashMap<>();
            clienteModificado.put("DNI", "12345678A");
            clienteModificado.put("NOMBRE", "Juan Pérez (Actualizado)");
            clienteModificado.put("CP", "28002");

            gestor.insertar(clienteModificado, 0); // Sobrescribe posición 0
            System.out.println("Cliente en la Posición 0 modificado con éxito.");

            System.out.println("Número final de registros en fichero: " + gestor.getNumRegistros());

        } catch (IOException e) {
            // El programa principal captura la excepción según los requisitos
            System.err.println("Ocurrió un error en las operaciones I/O de acceso aleatorio: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
