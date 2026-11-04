package ejercicios1_3FicherosBinarios;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio1 {
    /*
    Realiza una agenda telefónica que almacene los siguientes datos en un fichero binario. A continuación, lee el fichero y muestra el contenido por consola:

    Nombre del contacto

    Teléfono

    Dirección

    Código postal (número entero)

    Fecha de nacimiento

    Si le debo dinero (booleano)

    Cuánto le debo(número decimal)
     */
    public void show() {
        Path source = Paths.get("fileBinary", "source.txt");
        Path destination = Paths.get("fileBinary", "source.dat");
        String bytes = read(source);
        txtToBinary(bytes, destination);
        readFile(destination);

    }

    public String read(Path file) {
        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader br = Files.newBufferedReader(file)) {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.printf("Ha ocurrido un problema al leer el fichero: %s", e.getMessage());
        }
        return sb.toString();
    }

    public void readFile(Path file) {
        int totalContacts;
        String name, phoneNumber, address, cp, birthDate;
        boolean oweMoney;
        double money;
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file.toFile()))) {
            // Lee el número total de contactos guardados al principio
            totalContacts = reader.readInt();
            System.out.printf("Leyendo fichero binario...%nTotal de contactos encontrados: %d%n", totalContacts);

            // Itera para leer cada contacto en el mismo orden que fue escrito
            for (int i = 0; i < totalContacts; i++) {
                name = reader.readUTF();
                phoneNumber = reader.readUTF();
                address = reader.readUTF();
                cp = reader.readUTF();
                birthDate = reader.readUTF();
                oweMoney = reader.readBoolean();
                money = reader.readDouble();


                System.out.printf("%n[Contacto %d]%n  Nombre: %s%n  Teléfono: %s%n  Dirección: %s%n  Código postal: %s%n  Fecha de nacimiento: %s%n  ¿Debo dinero?: %s%n  Cuánto le debo: %.2f%n",
                        (i + 1), name, phoneNumber, address, cp, birthDate, (oweMoney ? "Sí" : "No"), money);
            }

        } catch (IOException e) {
            System.err.printf("Error de entrada/salida al leer: %s%n", e.getMessage());
        }
    }

    public void txtToBinary(String str, Path file) {
        String name;
        String phoneNumber;
        String address;
        String pd;
        String birthDate;
        boolean oweMoney;
        double money;
        String[] data;
        String[] args = str.split("\n\n");
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file.toFile()))) {

            writer.writeInt(args.length); // saber cuantos contactos hay
            for (String c : args) {
                // Estructura de la agenda
                data = c.split("\n");
                name = data[0].replace("Nombre de contacto:", "");
                phoneNumber = data[1].replace("Número de telefono: ", "");
                address = data[2].replace("Direccion", "");
                pd = data[3].replace("Código postal", "");
                birthDate = data[4].replace("Fecha de nacimiento", "");
                oweMoney = Boolean.parseBoolean(data[5].replace("Debo dinero: ", ""));
                money = Double.parseDouble(data[6].replace("Cuánto le debo(número decimal): ", ""));
                // Se escribe en el fichero source.dat
                writer.writeUTF(name);
                writer.writeUTF(phoneNumber);
                writer.writeUTF(address);
                writer.writeUTF(pd);
                writer.writeUTF(birthDate);
                writer.writeBoolean(oweMoney);
                writer.writeDouble(money);
            }

            System.out.println("Fichero binario con exito!");
        } catch (Exception e) {
            System.err.printf("Ocurrió un error al escribir el archivo: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Ejercicio1().show();
    }
}
