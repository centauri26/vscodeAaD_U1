package streams_05;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class AppReader {
    public static void main(String[] args) throws Exception {
        try (Reader reader = new FileReader("mensaje.txt", StandardCharsets.UTF_8)) {
            int charLeido;
            System.out.print("Texto leído: ");
            while ((charLeido = reader.read()) != -1) {
                //Convertir el código ascii/numérico al carácter
                System.out.print((char) charLeido); 
            }
        }
    }
}