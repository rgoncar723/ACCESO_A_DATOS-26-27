package ejercicios1_2FicherosDeTexto;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Ejercicio2 {
    /*
    Realiza un programa para encriptar y desencriptar los datos de un fichero de texto.
     Se introduce una cadena por teclado que será la clave a aplicar para la encriptación y desencriptación.
     A cada carácter del fichero de texto original se le sumará una letra de la clave (el código Unicode),
     cuando se hayan acabado las letras de la palabra clave y aún no se hayan acabado los caracteres del fichero,
      se volverá al principio de la cadena para seguir aplicando la encriptación.
      Los datos encriptados se escribirán en un fichero destino, que será usado como origen para desencriptar.
      Para desencriptar se aplicará la fórmula a la inversa. Por ejemplo, si el fichero origen contiene “abcdef”
      y la palabra clave es “rosa”, en el fichero destino se escribirán los siguientes caracteres: ”ÓÑÖÅ×Õ”.
      Es decir, Ó es el resultado de sumar a y r, Ñ es el resultado de sumar b y o, y así sucesivamente.
     */
    public void show() {
        Scanner kb = new Scanner(System.in);
        int choice, caracterLeido, caracterEncriptado, caracterDesencriptado;
        int i = 0; //Contador
        Path dir = Paths.get("file3");
        Path source = Paths.get("file3", "source.txt");
        Path destination = dir.resolve("destination.txt");
        Path desencriptado = dir.resolve("desencriptado.txt");
        System.out.println("¿Quieres encriptar, desencriptar? (1/2/)");
        String password;
        char letraClave;
        do {
            choice = kb.nextInt();
            kb.nextLine();
            if (choice != 1 && choice != 2) {
                System.out.println("Opción incorrecta, debes introducir entre 1 y 2");
            }
        } while (choice != 1 && choice != 2);

        do {
            System.out.println("Escribe una clave de encriptación");
            password = kb.nextLine();
        } while (password.isEmpty());

        if (choice == 1) {
            System.out.println("Encriptando archivo...");
            try (BufferedReader br = Files.newBufferedReader(source);
                 BufferedWriter bw = Files.newBufferedWriter(destination)) {
                while ((caracterLeido = br.read()) != -1) {
                    letraClave = password.charAt(i % password.length());
                    caracterEncriptado = caracterLeido + letraClave;
                    bw.write((char) caracterEncriptado);
                    i++;

                }
            } catch (Exception e) {
                System.out.printf("Error: %s", e.getMessage());
            }
        } else if (choice == 2) {
            System.out.println("Desencriptando archivo...");
            i = 0;
            try (BufferedReader br = Files.newBufferedReader(destination);
                 BufferedWriter bw = Files.newBufferedWriter(desencriptado)) {
                while ((caracterLeido = br.read()) != -1) {
                    letraClave = password.charAt(i % password.length());

                    caracterDesencriptado = caracterLeido - letraClave;

                    bw.write((char) caracterDesencriptado);
                    i++;
                }
                System.out.println("El archivo ha sido desencriptado correctamente");
            } catch (Exception e) {
                System.out.printf("Error: %s", e.getMessage());
            }
        }


    }

    public static void main(String[] args) {
        new Ejercicio2().show();
    }
}
