package randomaccess_07;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GestorFicheroAccesoAleatorio {

    private File archivo;
    private List<DefinicionCampo> estructuraRegistro;
    private int tamanoRegistroBytes;
    private long numRegistros;

    /**
     * Constructor del gestor de fichero aleatorio.
     * 
     * @param rutaFichero Nombre o ruta del fichero a gestionar.
     * @param estructuraRegistro Lista con el nombre y longitud de cada campo.
     * @throws IOException Si ocurre un error de E/S.
     */
    public GestorFicheroAccesoAleatorio(String rutaFichero, List<DefinicionCampo> estructuraRegistro) throws IOException {
        this.archivo = new File(rutaFichero);
        this.estructuraRegistro = estructuraRegistro;

        // 1. Calcular el tamaño total en bytes que ocupa un solo registro
        this.tamanoRegistroBytes = 0;
        for (DefinicionCampo campo : estructuraRegistro) {
            this.tamanoRegistroBytes += campo.getLongitudBytes();
        }

        // 2. Si el fichero existe, calculamos cuántos registros contiene actualmente
        if (this.archivo.exists()) {
            this.numRegistros = this.archivo.length() / this.tamanoRegistroBytes;
        } else {
            this.numRegistros = 0;
            // Al abrirlo en modo "rw" más adelante, se creará físicamente si no existe.
        }
    }

    /**
     * Inserta un registro al final del fichero.
     * 
     * @param registro Mapa con los pares (NombreCampo, ValorTexto).
     * @throws IOException
     */
    public void insertar(Map<String, String> registro) throws IOException {
        // La posición del registro a insertar al final es igual al número actual de registros
        insertar(registro, this.numRegistros);
    }

    /**
     * Inserta un registro en una posición específica del fichero (basada en índice 0).
     * 
     * @param registro Mapa con los pares (NombreCampo, ValorTexto).
     * @param posicion Registro donde insertar (0, 1, 2...).
     * @throws IOException
     */
    public void insertar(Map<String, String> registro, long posicion) throws IOException {
        // Abrimos el recurso dentro del bloque o aseguramos su uso en modo "rw"
        try (RandomAccessFile raf = new RandomAccessFile(this.archivo, "rw")) {

            // 1. Calcular el desplazamiento (offset) exacto en bytes
            long offsetBytes = posicion * this.tamanoRegistroBytes;

            // 2. Posicionar el puntero
            raf.seek(offsetBytes);

            // 3. Escribir cada campo asegurando longitud fija en UTF-8
            for (DefinicionCampo campo : estaEstructura()) {
                String valor = registro.getOrDefault(campo.getNombre(), "");
                byte[] bytesCampo = ajustarTextoABytes(valor, campo.getLongitudBytes());
                raf.write(bytesCampo);
            }

            // 4. Actualizar el contador total de registros si hemos añadido al final o más allá
            long registrosActualesEnFichero = raf.length() / this.tamanoRegistroBytes;
            if (registrosActualesEnFichero > this.numRegistros) {
                this.numRegistros = registrosActualesEnFichero;
            }
        }
    }

    /**
     * Método auxiliar para ajustar o rellenar cadenas de texto en bytes UTF-8 de tamaño fijo.
     */
    private byte[] ajustarTextoABytes(String texto, int longitudRequerida) {
        byte[] bytesTexto = texto.getBytes(StandardCharsets.UTF_8);
        byte[] resultado = new byte[longitudRequerida];

        // Rellenar previamente con espacios en blanco (byte 32 en ASCII/UTF-8)
        Arrays.fill(resultado, (byte) ' ');

        // Copiar los bytes del texto hasta el máximo permitido por la longitud del campo
        int bytesACopiar = Math.min(bytesTexto.length, longitudRequerida);
        System.arraycopy(bytesTexto, 0, resultado, 0, bytesACopiar);

        return resultado;
    }

    // Getters auxiliares
    public List<DefinicionCampo> estaEstructura() {
        return estructuraRegistro;
    }

    public long getNumRegistros() {
        return numRegistros;
    }

    public int getTamanoRegistroBytes() {
        return tamanoRegistroBytes;
    }
}