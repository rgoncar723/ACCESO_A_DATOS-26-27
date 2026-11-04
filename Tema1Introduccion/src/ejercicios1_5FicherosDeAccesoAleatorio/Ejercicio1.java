package ejercicios1_5FicherosDeAccesoAleatorio;

import ejercicios1_4FicherosBinariosDeObjetos.Contacto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ejercicio1 {
    /*
    Realiza la agenda telefónica en un fichero de acceso aleatorio. Realiza el siguiente menú:

Consultar el fichero
Consultar un contacto
Añadir un contacto
Modificar un contacto
Modificar dinero

En la opción 1, se muestra por consola el contenido del fichero.
En la opción 2, se le pide al usuario el identificador del contacto a mostrar.
En la opción 3, se le piden los datos al usuario y se añade el contacto por el final.
En la opción 4, se modifica el contacto entero por otro elegido por el usuario.
En la opción 5, se modifica si le debo dinero y la cantidad.

Cuando se le pida al usuario el identificador del contacto que desea, hay que mostrarle el rango de identificadores válidos.
     */

    /*
        int id -> 4 bytes
        int phoneNumber -> 4 bytes
        String name -> 30 caracteres -> 60 bytes
        String address -> 50 caracteres -> 100 bytes
        int cp -> 4 bytes
        String birthDate -> 10 caracteres -> 20 bytes
        boolean ownMoney -> 1 byte
        double amount -> 8 bytes
        Total: 4 + 60 + 4 + 4 + 100 + 20 + 1 + 8 = 211 bytes
         */

    public static final int NUM_BYTES_CONTACT = 211;
    public static final int LEN_NAME = 30;
    public static final int LEN_ADDRESS = 50;
    public static final int LEN_BIRTHDATE = 10;
    public static final int OFFSET_OWE_MONEY = 202;

    Scanner kb = new Scanner(System.in);

    public void show() {
        Path contactList = Paths.get("fileBinary", "ejAgendaAleatoria.dat");
        int option;
        do {
            System.out.println("Escoge una opción");
            menu();
            option = kb.nextInt();
            switch (option) {
                case 0 -> System.out.println("Saliendo...");
                case 1 -> consultFile(contactList);
                case 2 -> consultContact(contactList);
                case 3 -> addContact(contactList);
                case 4 -> modifyContact(contactList);
                case 5 -> modifyMoney(contactList);

            }
        } while (option != 0);
    }

    // 1. Consultar
    public void consultFile(Path file) {
        try {
            if (Files.size(file) > 0) {
                System.out.println("Agenda:");
                try (RandomAccessFile reader = new RandomAccessFile(file.toFile(), "r")) {
                    while (reader.getFilePointer() < reader.length()) {
                        System.out.printf("Nombre: %s\n Telefono: %d\n Direccion: %s\n Codigo Postal: %d\n Cumpleaños: %s\n ¿Debo dinero?: %s\n Dinero: %2.f\n",
                                readString(reader, LEN_NAME), reader.readInt(), readString(reader, LEN_ADDRESS), reader.readInt(), readString(reader, LEN_BIRTHDATE), reader.readBoolean() ? "YES" : "NO", reader.readDouble());
                    }
                }
            } else {
                System.out.println("El fichero esta vacío");
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    // 2. Consultar contacto
    public void consultContact(Path file) {
        long numberOfContacts, position;
        int idContact;
        try {
            if (Files.size(file) > 0) {
                try (RandomAccessFile reader = new RandomAccessFile(file.toFile(), "r")) {
                    numberOfContacts = reader.length() / NUM_BYTES_CONTACT;
                    System.out.printf("Introduzca el id del contacto que desea consultar: 1 - %d\n", numberOfContacts);
                    idContact = kb.nextInt();
                    position = (long) (idContact - 1) * NUM_BYTES_CONTACT;
                    reader.seek(position);
                    System.out.printf("Nombre: %s\n Telefono: %d\n Direccion: %s\n Codigo Postal: %d\n Cumpleaños: %s\n ¿Debo dinero?: %s\n Dinero: %2.f\n",
                            readString(reader, LEN_NAME), reader.readInt(), readString(reader, LEN_ADDRESS), reader.readInt(), readString(reader, LEN_BIRTHDATE), reader.readBoolean() ? "YES" : "NO", reader.readDouble());
                }
            } else {
                System.out.println("El fichero está vacío, debes añadir un contacto");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 3. Añadir contacto
    public void addContact(Path file) {
        String name, address, birthDate;
        int phoneNumber, postalCode;
        boolean oweMoney;
        double amount;
        try (RandomAccessFile writer = new RandomAccessFile(file.toFile(), "rw")) {
            writer.seek(writer.length()); // final de fichero.
            System.out.println("Datos para el nuevo contacto");
            System.out.println("Nombre: ");
            name = kb.nextLine();
            System.out.println("Telefono: ");
            phoneNumber = kb.nextInt();
            System.out.println("Direccion: ");
            address = kb.nextLine();
            System.out.println("Cumpleaños: ");
            birthDate = kb.nextLine();
            System.out.println("Codigo postal");
            postalCode = kb.nextInt();
            System.out.println("¿Debes dinero? (true/false)");
            oweMoney = kb.nextBoolean();
            if (oweMoney) {
                System.out.println("¿Cuanto debes?: ");
                amount = kb.nextDouble();
            } else {
                amount = 0.0;
            }
            writeString(writer, name, LEN_NAME);
            writer.writeInt(phoneNumber);
            writeString(writer, address, LEN_ADDRESS);
            writer.writeInt(postalCode);
            writeString(writer, birthDate, LEN_BIRTHDATE);
            writer.writeBoolean(oweMoney);
            writer.writeDouble(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menu() {
        System.out.printf("1. Consultar fichero%n2. Consultar un contacto%n3. Añadir un contacto%n4. Modificar un contacto%n5. Modificar dinero%n0. Salir%n");
    }

    // 4. Modificar un contacto
    public void modifyContact(Path file) {
        String name, address, birthDate;
        int phoneNumber, postalCode;
        boolean oweMoney;
        double amount;
        long numberOfContacts, position;
        int idContact;
        try {
            if (Files.size(file) > 0) {
                try (RandomAccessFile writer = new RandomAccessFile(file.toFile(), "rw")) {
                    numberOfContacts = writer.length() / NUM_BYTES_CONTACT;
                    System.out.printf("Introduzca el ID del contacto que desea modificar: 1- %d\n", numberOfContacts);
                    idContact = kb.nextInt();
                    position = (long) (idContact - 1) * NUM_BYTES_CONTACT;
                    writer.seek(position);
                    System.out.println("Introduzca los datos del nuevo contacto: ");
                    System.out.println("Nombre: ");
                    name = kb.nextLine();
                    System.out.println("Telefono: ");
                    phoneNumber = kb.nextInt();
                    System.out.println("Direccion: ");
                    address = kb.nextLine();
                    System.out.println("Cumpleaños: ");
                    birthDate = kb.nextLine();
                    System.out.println("Codigo postal");
                    postalCode = kb.nextInt();
                    System.out.println("¿Debes dinero? (true/false)");
                    oweMoney = kb.nextBoolean();
                    if (oweMoney) {
                        System.out.println("¿Cuanto debes?: ");
                        amount = kb.nextDouble();
                    } else {
                        amount = 0.0;
                    }
                    writeString(writer, name, LEN_NAME);
                    writer.writeInt(phoneNumber);
                    writeString(writer, address, LEN_ADDRESS);
                    writer.writeInt(postalCode);
                    writeString(writer, birthDate, LEN_BIRTHDATE);
                    writer.writeBoolean(oweMoney);
                    writer.writeDouble(amount);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 5 Cambiar dinero
    public void modifyMoney(Path file) {
        int idContact;
        long position, numberofContacts;
        boolean oweMoney;
        double amount;
        try {
            if (Files.size(file) > 0) {
                try (RandomAccessFile writer = new RandomAccessFile(file.toFile(), "rw")) {
                    numberofContacts = writer.length() / NUM_BYTES_CONTACT;
                    System.out.printf("Introduzca el ID del contacto que quiere modificar 1 - %d", numberofContacts);
                    idContact = kb.nextInt();
                    position = (long) (idContact - 1) * NUM_BYTES_CONTACT;

                    writer.seek(position + OFFSET_OWE_MONEY);
                    System.out.println("¿Debes dinero? (true/false)");
                    oweMoney = kb.nextBoolean();
                    if (oweMoney) {
                        System.out.println("¿Cuanto debes?");
                        amount = kb.nextDouble();
                    } else {
                        amount = 0.0;
                    }
                    writer.writeBoolean(oweMoney);
                    writer.writeDouble(amount);
                    System.out.println("Datos modificados correctamente");
                }
            } else {
                System.out.println("Fichero esta vacío");
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    private String readString(RandomAccessFile file, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(file.readChar());
        }
        return sb.toString();
    }

    private void writeString(RandomAccessFile file, String text, int length) throws IOException {
        if (text.length() > length) {
            text = text.substring(0, length);
        }
        for (int i = 0; i < length; i++) {
            if (i < text.length()) {
                file.writeChar(text.charAt(i));
            } else {
                file.writeChar(' ');
            }
        }
    }

    public static void main(String[] args) {
        new Ejercicio1().show();
    }
}