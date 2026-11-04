package ejercicios1_1Directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Ejercicio3 {
    /*
    Muestra el nombre y tipo (fichero o directorio) de los ficheros y
    subdirectorios contenidos en un directorio solicitado al usuario.
    Muestra también el contenido de todos los subdirectorios y si éstos contienen subdirectorios también...
    y así sucesivamente hasta mostrar todo el contenido de dicho directorio.
    No se puede utilizar el método walk de la clase Files.
    */
    public void show() {
        Scanner kb = new Scanner(System.in);
        Path pathToComprobe;
        System.out.println("Introduzca el path a combrobar");
        pathToComprobe = Paths.get(kb.nextLine());
        showFiles(pathToComprobe);
        showDirectories(pathToComprobe);
    }

    public void showFiles(Path userPath) {
        try (Stream<Path> filesContents = Files.list(userPath)) {
            Stream<Path> files = filesContents.filter(Files::isRegularFile);
            Iterable<Path> iterable = files::iterator;
            for (Path p : iterable) {
                System.out.printf("Archivo : %s\n", p);
            }
        } catch (IOException e) {
            System.err.printf("Error de Entrada/Salida", e.getMessage());
        }
    }

    public void showDirectories(Path userPath) {
        try (Stream<Path> filesContents = Files.list(userPath)) {
            Stream<Path> files = filesContents.filter(Files::isDirectory);
            Iterable<Path> iterable = files::iterator;
            for (Path p : iterable) {
                System.out.printf("Directorio : %s\n", p);
            }
        } catch (IOException e) {
            System.err.printf("Error de Entrada/Salida", e.getMessage());
        }
    }

    public static void main(String[] args) {

        new Ejercicio3().show();
    }
}
