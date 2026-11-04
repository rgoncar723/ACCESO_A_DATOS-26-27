package ejercicios1_1Directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class Ejercicio5 {
    /*
    Borra un directorio que contenga ficheros y subdirectorios (que también contengan ficheros y subdirectorios).
     */
    public void show() {
        Path directory = Paths.get("filesExercise2");
        try (Stream<Path> contents = Files.walk(directory)) {
            contents.sorted(Comparator.reverseOrder()) //Se pone en orden inverso
                    .forEach(p -> { // Se van borrando uno a uno 
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            System.err.printf("Error al borrar: %n\n", e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.printf("Error de Entrada/Salida: %s\n", e.getMessage());
        }

    }

    public static void main(String[] args) {

        new Ejercicio4().show();
    }
}
