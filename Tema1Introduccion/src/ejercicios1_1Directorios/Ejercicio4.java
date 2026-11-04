package ejercicios1_1Directorios;

import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Ejercicio4 {
    /*
    Muestra todos los archivos con extensión .java que se encuentren en un directorio y sus subdirectorios.
    */
    public void show() {
        Path directory = Paths.get(".");


        try (Stream<Path> directoryContents = Files.walk(directory)) {
            directoryContents.filter(p -> !Files.isDirectory(p)) // Si es un directorio
                    .filter(c -> c.toString().endsWith(".java")) //Para comprobar que terminan en .java
                    .forEach(System.out::println);

        } catch (NotDirectoryException e) {
            System.err.printf("El directorio no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error al mostrar el contenido del directorio: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {

        new Ejercicio4().show();
    }
}
