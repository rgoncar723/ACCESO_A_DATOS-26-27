package ejercicios1_2FicherosDeTexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio4 {
    /*
    Realiza un programa que dado un fichero de texto,
     se copie en tres ficheros diferentes de tal manera que copie en el primer
     fichero los primeros 5 caracteres, en el segundo, los 5 siguientes y en el tercero
     los 5 siguientes, y así sucesivamente hasta copiar
      todo el fichero. Utilizar lectura sin desplazamiento y
       escrituras con desplazamiento de un buffer de 15 caracteres.
       Después, hacer justamente lo contrario. Dados los 3 ficheros,
       construir uno como el fichero original. Utilizar ahora lecturas con
        desplazamiento y escritura sin desplazamiento de un buffer de 15 caracteres.
        Comprobar por código que ambos son iguales.
        En el caso de la última lectura/escritura,
        si no se llena el buffer, utilizar el desplazamiento para no dejar basura.
     */
    public void show() {

        Path originalFile = Paths.get("file4", "originalFile.txt");
        Path f1 = Paths.get("file4", "f1.txt");
        Path f2 = Paths.get("file4", "f2.txt");
        Path f3 = Paths.get("file4", "f3.txt");
        Path newFile = Paths.get("file4", "newFile.txt");
        char[] character = new char[15];

        final int ZERO = 0;
        final int FIVE = 5;
        final int TEN = 10;
        long mismatch;
        int caracteresLeidos, len1, len2, len3;
        System.out.println("Fase 1: Separación de ficheros");
        try (BufferedReader br = Files.newBufferedReader(originalFile);
             BufferedWriter bw = Files.newBufferedWriter(f1);
             BufferedWriter bw2 = Files.newBufferedWriter(f2);
             BufferedWriter bw3 = Files.newBufferedWriter(f3)) {
            while ((caracteresLeidos = br.read(character)) != -1) {
                len1 = Math.min(caracteresLeidos, FIVE); // El mínimo entre los caracteres leídos totales y 5.
                len2 = Math.clamp(caracteresLeidos - FIVE, ZERO, FIVE); //Limita los caracteres para f2 entre 0 y 5 con Math.clamp, evitando negativos o excesos.
                len3 = Math.clamp(caracteresLeidos - (FIVE * 2), ZERO, FIVE); //// Limita los caracteres para f3 entre 0 y 5 (descontando los 10 anteriores).
                if (len1 > ZERO) {
                    bw.write(character, ZERO, len1);
                }
                if (len2 > ZERO) {
                    bw2.write(character, FIVE, len2);
                }
                if (len3 > ZERO) {
                    bw3.write(character, TEN, len3);
                }
            }
            System.out.println("Fichero dividido con éxito en f1, f2 y f3.");

        } catch (IOException e) {
            System.out.printf("Error a leer / escribir en el fichero: %s", e.getMessage());
        }
        System.out.println("Fase 2: reconstrucción ");
        try (BufferedReader br1 = Files.newBufferedReader(f1);
             BufferedReader br2 = Files.newBufferedReader(f2);
             BufferedReader br3 = Files.newBufferedReader(f3);
             BufferedWriter bw = Files.newBufferedWriter(newFile)) {

            while ((len1 = br1.read(character, ZERO, FIVE)) != -1) {
                len2 = br2.read(character, FIVE, FIVE);
                if (len2 == -1) {
                    len2 = ZERO;
                }
                len3 = br3.read(character, TEN, FIVE);
                if (len3 == -1) {
                    len3 = ZERO;
                }
                caracteresLeidos = len1 + len2 + len3;
                if (caracteresLeidos > ZERO) {
                    bw.write(character, ZERO, caracteresLeidos);
                }

            }
            System.out.println("Fichero reconstruido con exito");
        } catch (IOException e) {
            System.out.printf("Error al recosntruir el fichero: %s", e.getMessage());
        }
        System.out.println("Comprobación de igualdad");
        try {
            mismatch = Files.mismatch(originalFile, newFile);
            if (mismatch == -1) {
                System.out.println("El fichero original y el reconstruido son iguales");
            } else {
                System.out.printf("Los ficheros son diferentes. Primera diferencia en la posicion: %d%n", mismatch);
            }
        } catch (IOException e) {
            System.out.printf("Error al comprobar los ficheros: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Ejercicio4().show();
    }
}
