package accesosecuencial_03;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;


public class ProcesadorFicheroCifrado {
    private static final String ALGORITMO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12; // 12 bytes recomendados para GCM

    public static void cifrarYGuardar(Path rutaSalida, byte[] datos, byte[] clave16Bytes) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv); // Generar Vector de Inicialización seguro

        SecretKey key = new SecretKeySpec(clave16Bytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

        try (OutputStream fos = Files.newOutputStream(rutaSalida);
             // Guardamos primero el IV al inicio del fichero
             OutputStream out = new BufferedOutputStream(fos)) {
            
            out.write(iv); 

            try (CipherOutputStream cos = new CipherOutputStream(out, cipher)) {
                cos.write(datos);
                cos.flush();
            }
        }
    }

    public static byte[] descifrarYLeer(Path rutaEntrada, byte[] clave16Bytes) throws Exception {
        try (InputStream fis = Files.newInputStream(rutaEntrada);
             InputStream in = new BufferedInputStream(fis)) {

            // Leer los primeros 12 bytes correspondientes al IV
            byte[] iv = new byte[IV_LENGTH];
            if (in.read(iv) != IV_LENGTH) {
                throw new IllegalArgumentException("Fichero corrupto o IV incompleto.");
            }

            SecretKey key = new SecretKeySpec(clave16Bytes, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

            try (CipherInputStream cis = new CipherInputStream(in, cipher);
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                
                byte[] data = new byte[1024];
                int bytesRead;
                while ((bytesRead = cis.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                return buffer.toByteArray();
            }
        }
    }
}