package ejercicios1_2FicherosDeTexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Ejercicio3 {
    /*
    Realiza este ejercicio utilizando las clases BufferedReader y BufferedWriter: concatena todos tus ficheros .java realizados hasta ahora en los ejercicios en uno solo utilizando código de programación. A continuación, lee dicho fichero y genera dos ficheros:

    Fichero donde estén las declaraciones realizadas en los paréntesis del try-with-resources, cada una en una línea y terminadas con ;. Ejemplo:


    Stream<Path> directoryContents;
    BufferedReader reader;
    BufferedWriter writer;
    Fichero donde estén las declaraciones de las distintas excepciones tratadas, cada una en una línea, terminadas con ; y sin repeticiones, es decir, la excepción que aparezca varias veces que solamente aparezca una vez en el fichero:


    FileNotFoundException e;
    IOException e;
    FileAlreadyExistsException e;
    NoSuchFileException e;
    NotDirectoryException e;

     */
    public void show() {
        Path dir = Paths.get(".");
        Path newFile = Paths.get("outPut.txt");
        Path decTry = Paths.get("tryWithResources.txt");
        Path decExc = Paths.get("exepciones.txt");
        Set<String> excepciones = new HashSet<>();
        Pattern patDec = Pattern.compile("([A-Za-z0-9_<>]+(?:<[A-Za-z0-9_<>]+>)?\\s+[A-Za-z0-9_]+)\\s*=");
        Pattern patCach = Pattern.compile("catch\\s*\\(([^)]+)\\)"); // catch (FileNotFoundException e) -> captura "FileNotFoundException e"
        Matcher matcher;

        boolean enTry = false;

        try (BufferedWriter bw = Files.newBufferedWriter(newFile)) {
            try (Stream<Path> content = Files.walk(dir)) {
                content.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".java"))
                        .forEach(archivo -> {
                                    try (BufferedReader br = Files.newBufferedReader(archivo)) {

                                        String line;
                                        bw.write("Archivo: " + archivo.getFileName().toString());
                                        bw.newLine();

                                        while ((line = br.readLine()) != null) {
                                            bw.write(line);
                                            bw.newLine();
                                        }
                                        bw.newLine();

                                    } catch (IOException e) {
                                        System.err.printf("Error leyendo el archivo: %s...\n%s", archivo.getFileName(), e.getMessage());
                                    }
                                }
                        );
                System.out.println("Fichero concatenado con exito");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Fase 2: try-with-resources");
        try (BufferedReader br = Files.newBufferedReader(newFile);
             BufferedWriter bw = Files.newBufferedWriter(decTry)) {
            String line, lineTrim, clear;
            while ((line = br.readLine()) != null) {
                lineTrim = line.trim();
                if (lineTrim.contains("try") && lineTrim.contains("(")) {
                    enTry = true;
                }
                if (enTry) {
                    matcher = patDec.matcher(lineTrim);
                    while ((matcher.find())) {
                        clear = matcher.group(1).trim();
                        bw.write(clear + ";");
                        bw.newLine();
                    }
                    if (lineTrim.contains(")")) {
                        enTry = false;
                    }
                }

            }
            System.out.println("Fichero de declaraciones creado con exito!");
        } catch (IOException e) {
            System.err.printf("Error en la fase 2: %s", e.getMessage());
        }
        System.out.println("Fase 3, Excepciones");
        try (BufferedReader br = Files.newBufferedReader(newFile)) {
            String line, ctch, excClear;
            String[] part;
            int latSpace;
            while ((line = br.readLine()) != null) {
                matcher = patCach.matcher(line);
                while (matcher.find()) {
                    ctch = matcher.group(1).trim();
                    part = ctch.split("\\|");
                    for (String p : part) {
                        //Separo el nombre de la excepcion del nombre de la variable 'e', ya que generalmente estan separados por espacio
                        latSpace = p.lastIndexOf(' ');
                        excClear = (latSpace != -1) ? p.substring(0, latSpace).trim() : p;

                        if (!excClear.isEmpty()) {
                            excepciones.add(excClear + " e;"); //Para evitar duplicados
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.err.printf("Error leyendo para las excepciones : %s", e.getMessage());
        }
        //Escribo el conjunto de excepciones únicas
        try (BufferedWriter bw = Files.newBufferedWriter(decExc)) {
            for (String e : excepciones) {
                bw.write(e);
                bw.newLine();
            }
            System.out.println("Fichero de excepciones creado con exito!");
        } catch (IOException e) {
            System.err.printf("Error escribiendo el fichero : %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Ejercicio3().show();
    }
}
