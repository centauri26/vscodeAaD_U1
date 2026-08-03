package introficheros_01;

import java.io.File;

//Comentario inicial
public class MuestraInfoFichero {
    public static void main(String[] args) {
        String ruta= args.length > 0 ? args[0] : ".";

        File fichero = new File(ruta);
        if (!fichero.exists()){
            System.out.printf("El fichero %s no existe\n", ruta);
            return;
        }
        if (fichero.isFile()){
            System.out.printf("El fichero %s es un fichero\n", ruta);
        } else if (fichero.isDirectory()){
            System.out.printf("El fichero %s es un directorio que contien los ficheros siguientes:\n", ruta);
            //Como es un directorio, listo su contenido
            File[] ficheros = fichero.listFiles();
            for (File f : ficheros){
                System.out.printf("  %s\n", f.getName());
                if (f.isDirectory()){
                    System.out.printf("/");
                }
                System.out.printf("%s es un directorio\n", f.getName());
                }
            }
        }
    }
