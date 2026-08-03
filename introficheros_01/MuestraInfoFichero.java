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
            System.out.printf("El fichero %s es un directorio que contiene los ficheros siguientes:\n", ruta);
            //Como es un directorio, listo su contenido
            File[] ficheros = fichero.listFiles();
            for (File f : ficheros){
                compruebaDirectorioYficheros(f);
                }
            }
        }

        static void compruebaDirectorioYficheros(File fichero){
            if (fichero.isDirectory()){
                System.out.printf("El fichero %s es un directorio que contiene los ficheros siguientes:\n", fichero.getName());
                //Como es un directorio, hay que listar su contenido
                File[] ficheros = fichero.listFiles();
                for (File f : ficheros){
                    if (f.isDirectory()){
                        System.out.printf("/");
                        compruebaDirectorioYficheros(f);
                    }
                    System.out.printf("%s\n", f.getName());
                }
            }else{ //Es sólo un fichero
                System.out.printf("%s\n", fichero.getName());
            }
        }
    }
