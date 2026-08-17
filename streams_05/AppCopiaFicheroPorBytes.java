package streams_05;

import java.io.File;

public class AppCopiaFicheroPorBytes {
    public static void main(String[] args){
        File origen= new File(".\\streams_05\\cercedilla.txt");
        File destino= new File(".\\streams_05\\cercedillaBKtxt");
        CopiarByteaByte.copiarFichero(origen.toPath(), destino.toPath());
    }
}
