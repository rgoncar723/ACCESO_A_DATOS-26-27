package ejercicios1_2FicherosDeTexto;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Ejercicio1 {
    /*
    Crea un método con 3 parámetros:

Path source

Path destination

boolean appended

En dicho método, haz lo siguiente:

Si el destino es un directorio:

Si el origen también es un directorio: lanza la excepción IllegalArgumentException.

Si el origen es un fichero: crea un fichero en el directorio destino que sea una copia del fichero origen. Utiliza el método copy de la clase Files. Muestra por consola el contenido del fichero destino.

Si el destino es un fichero:

Si el origen es un directorio: lanza la excepción IllegalArgumentException.

Si el origen es un fichero, haz diferentes cosas en función del booleano appended:

Si el destino no existe: se crea y se copia el fichero origen cadena a cadena. Utiliza el método writeString de Files. Muestra por consola el fichero destino.

Si el destino existe y appended es false: se sobrescribe su contenido y se copia el fichero origen con todo el contenido en una única operación. Utiliza el método write de Files. Muestra por consola el fichero destino.

Si el destino existe y appended es true: no se sobrescribe sino que se mantiene el contenido del fichero destino y se le añade el contenido del fichero origen. Muestra por consola el fichero destino.
     */

    public void show() {
        try {
            Path dir = Paths.get("files2");
            Path sourceFile = dir.resolve("source.txt");
            Path destFile = dir.resolve("destiny.txt");
            Path subDir = dir.resolve("subDirectory");
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            if (!Files.exists(subDir)) {
                Files.createDirectory(subDir);
            }
            Files.writeString(sourceFile, "Hola línea 1, espero que vayamos aprendiendo juntos\n Hola Línea 2, pq estas encima mío");
            System.out.println("Prueba 1");
            copyWriteFiles(sourceFile, subDir, false); //Copiar fichero a directorio

            System.out.println("Prueba 2");
            Files.deleteIfExists(destFile);
            copyWriteFiles(sourceFile, destFile, false); // Destino no existe
            System.out.println("Prueba 3");
            copyWriteFiles(sourceFile, destFile, false); //Destino existe con append = false (Sobrescribir)
            System.out.println("Prueba 4");
            copyWriteFiles(sourceFile, destFile, true); //Desitno existe con append = true (Añadir al final)

            try {
                System.out.println("Prueba 5");
                copyWriteFiles(subDir, destFile, false);
            } catch (IllegalArgumentException e) {
                System.err.printf("Error : %s", e.getMessage());
            }
        } catch (IOException e) {
            System.err.printf("Erro durante la ejecución: %s", e.getMessage());
        }

    }

    public void copyWriteFiles(Path source, Path destination, boolean appended) {
        try {
            if (Files.isDirectory(destination)) {
                if (Files.isDirectory(source)) {
                    throw new IllegalArgumentException("El origen no puede ser un directorio si el destino lo es.");
                } else {

                    Path newDirectory = destination.resolve(source.getFileName());
                    Files.copy(source, newDirectory, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(Files.readString(newDirectory));
                }
            } else {
                if (Files.isDirectory(source)) {
                    throw new IllegalArgumentException("El origen no puede ser un directorio si el destino es un fichero.");
                }
                if (!Files.exists(destination)) {


                    for (String linea : Files.readAllLines(source)) {
                        Files.writeString(
                                destination,
                                linea + System.lineSeparator(),
                                StandardOpenOption.CREATE,
                                StandardOpenOption.APPEND
                        );
                    }

                    System.out.println(Files.readString(destination));
                } else {


                    if (!appended) {

                        byte[] contenido = Files.readAllBytes(source);

                        Files.write(destination, contenido);

                        System.out.println(Files.readString(destination));
                    } else {

                        String contenido = Files.readString(source);

                        Files.writeString(
                                destination,
                                contenido,
                                StandardOpenOption.APPEND
                        );

                        System.out.println(Files.readString(destination));
                    }

                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.printf("Error de E/S durante la operación con ficheros: %s", e.getMessage());
        }

    }

    public static void main(String[] args) {

        new Ejercicio1().show();
    }
}
