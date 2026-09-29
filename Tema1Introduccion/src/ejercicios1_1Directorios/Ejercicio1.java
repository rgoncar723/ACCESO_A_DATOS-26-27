package ejercicios1_1Directorios;

import java.nio.file.*;
import java.io.IOException;

public class Ejercicio1 {
    public void show() {
    /*
    Dado un fichero que se encuentre en una subcarpeta de tu directorio actual,
    muestra su nombre, si es un ejecutable, si está oculto, la ruta relativa,
    la ruta absoluta y el tamaño.
     */
        Path file, absolutePath;
        try {
            file = Paths.get("files", "ejercicio1.txt");

            System.out.println("Nombre del archivo:");
            System.out.println(file.getFileName());

            System.out.printf("¿Es ejecutable? %s\n", Files.isExecutable(file) ? "Si" : "No");

            System.out.printf("¿Esta oculto? %s\n", Files.isHidden(file) ? "Si" : "No");

            System.out.println("Ruta relativa:");
            System.out.println(file);

            System.out.println("Ruta absoluta:");
            absolutePath = file.toAbsolutePath();
            System.out.println(absolutePath);

            System.out.printf("Tamaño del fichero: %d bytes\n", Files.size(file));


        } catch (FileAlreadyExistsException e) {
            System.err.printf("El fichero ya existe: %s", e.getMessage());
        } catch (NoSuchFileException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s", e.getMessage());
        }


    }

    public static void main(String[] args) {

        new Ejercicio1().show();
    }

}
