package ejercicios1_1Directorios;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class Ejercicio2 {
    /*
    Crea un directorio en el directorio actual,
    luego crea tres ficheros en dicho directorio donde uno se borre y otro se renombre.
    Crear también un subdirectorio con un fichero dentro.
    Después mostrar la ruta absoluta de ambos directorios y sus contenidos.
     */
    public void show() {
        Path newDirectory, file1, file2, file3, file4, subDirectory, file1txt, new3, old3;
        newDirectory = Paths.get("filesExercise2");
        try {

            Files.createDirectory(newDirectory);
            file1 = Paths.get("filesExercise2", "file1.txt"); //Se borra
            file2 = Paths.get("filesExercise2", "file2.txt");
            file3 = Paths.get("filesExercise2", "file3.txt"); // Se cambia de nombre
            Files.createFile(file1);
            Files.createFile(file2);
            Files.createFile(file3);


            subDirectory = newDirectory.resolve("filesExercise2_1");
            Files.createDirectories(subDirectory);
            file4 = subDirectory.resolve(Paths.get("subFile.txt"));
            Files.createFile(file4);
            //Borrar un archivo
            file1txt = newDirectory.resolve("file1.txt");
            Files.deleteIfExists(file1txt);
            System.out.printf("El archivo '%s' ha sido eliminado correctamente\n", file1txt);
            // Renombrar
            old3 = newDirectory.resolve("file3.txt");
            new3 = newDirectory.resolve("I'mJustAMan.txt");
            Files.move(old3, new3);
            System.out.printf("El archivo '%s' ha sido movido correctamente a '%s'\n", old3, new3);
            /*
                Files.delete(file2);
                Files.delete(file4);
                Files.delete(new3);
                Files.delete(newDirectory);
                Files.delete(subDirectory);
            */
            try (Stream<Path> directoryContents = Files.walk(newDirectory)) {
                directoryContents.forEach(c -> {
                    System.out.printf("%s\n", c.toAbsolutePath());
                });
            } catch (IOException e) {
                System.err.printf("I/O error: %s", e.getMessage());
            }


        } catch (FileAlreadyExistsException e) {
            System.err.printf("El fichero ya existe: %s", e.getMessage());
        } catch (NoSuchFileException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (NotDirectoryException e) {
            System.err.printf("El directorio no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error al mostrar el contenido del directorio: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {

        new Ejercicio2().show();
    }
}
