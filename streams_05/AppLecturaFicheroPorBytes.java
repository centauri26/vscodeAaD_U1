package streams_05;



public class AppLecturaFicheroPorBytes {
    public static void main(String[] args){
        /*
         * He creado el método leerFichero como static 
         * para no necesitar instanciar su clase para usar el método.
        */
        LecturaByteaByte.leerFichero(".\\streams_05\\cercedilla.txt");
    }
}
