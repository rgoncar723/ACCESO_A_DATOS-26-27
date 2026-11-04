package ejercicios1_4FicherosBinariosDeObjetos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio1 {
    /*
    Ejercicio 1
Realiza la agenda telefónica del ejercicio anterior pero almacenando objetos en el fichero binario. Realiza el siguiente menú:

Consultar

Sobrescribir

Añadir

En la opción 1, se lee el fichero y se muestra por consola.
En la opción 2, se sobrescribe el fichero.
En la opción 3, se mantienen los objetos que hubiera en el fichero y se añaden objetos nuevos.
     */
    Scanner kb = new Scanner(System.in);

    public void show() {

        int option;
        List<Contacto> contactos = generateContacts();
        Path file = Paths.get("fileBinary", "ej1.dat");

        try {
            Files.createDirectories(file.getParent());
            if (!Files.exists(file)) {
                write(file, contactos); // Solo se generan por defecto si el fichero no existe
            }
        } catch (IOException e) {
            System.err.printf("Error al inicializar el directorio: %s", e.getMessage());
        }
        do {
            System.out.println("Escoja una opción del menu");
            menu();
            option = kb.nextInt();
            switch (option) {
                case 1 -> consultar(file);
                case 2 -> overWrite(file);
                case 3 -> askNewContact(file);
                case 0 -> System.out.print("Saliendo...");
                default -> System.out.println("Opción no valida");
            }
        } while (option != 0);


    }


    // 1 Consultar
    public void consultar(Path file) { // Leer el fichero
        Contacto contacto;
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            while (true) {
                contacto = (Contacto) reader.readObject();
                System.out.println(contacto);
            }
        } catch (EOFException e) {
            System.out.println("Lectura del fichero finalizada");
        } catch (ClassNotFoundException e) {
            System.err.printf("Clase no encontrada: %s", e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s", e.getMessage());
        }

    }

    // 2 Sobreescribir
    public void overWrite(Path file) {
        List<Contacto> newContacts = writeContacts("Sobreescribir: Se borrarán los contactos anteriores");
        write(file, newContacts);
        System.out.println("Fichero sobreescrito con exito");

    }

    // 3 Añadir contacto
    public void addNewContact(Path file, List<Contacto> contactos) {
        try (FileOutputStream fos = new FileOutputStream(file.toFile(), true);
             ObjectOutputStream writer = new AppendableObjectOutputStream(fos)) {

            for (Contacto c : contactos) {
                writer.writeObject(c);
            }

        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s\n", e.getMessage());
        }

    }

    public void menu() {
        System.out.print("1.Consultar\n2.Sobrescribir\n3.Añadir\n0.Salir\n");
    }

    // Crea contactos
    private List<Contacto> generateContacts() {
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(new Contacto("Sara Rojas", 666777111, 11002, "Clle Thai 23 Piso 2", "9-7-2005", false, 0.0));
        contactos.add(new Contacto("Santiago Ragel", 666773121, 11302, "Clle Yu 12 Portal 5G", "23-10-2007", false, 0.0));
        contactos.add(new Contacto("Nuria Carrillo", 661273121, 11363, "Clle Sevilla 89 Piso 6B", "10-7-1975", true, 134.65));
        contactos.add(new Contacto("Eva Macías", 611273121, 10063, "Clle San Felipe 20 Piso 8A", "3-2-1990", true, 500.65));
        contactos.add(new Contacto("Jaime González", 666023121, 11363, "Clle Sevilla 89 Piso 6B", "29-05-2009", false, 0.0));
        return contactos;
    }

    // Escribe en el fichero
    private void write(Path file, List<Contacto> contactos) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            for (Contacto c : contactos) {
                writer.writeObject(c);//Escribe un objeto contacto en el fichero
            }
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s\n", e.getMessage());
        }
    }

    private List<Contacto> writeContacts(String message) {
        List<Contacto> newContacts = new ArrayList<>();
        char answer;
        String name, birthDate, address;
        int phoneNumber, cp;
        boolean ownMoney;
        double money;

        System.out.println(message);
        do {
            kb.nextLine(); //Limpieza del buffer
            System.out.println("Nombre y apellidos: ");
            name = kb.nextLine();

            System.out.println("Numero de contacto: ");
            phoneNumber = kb.nextInt();

            System.out.println("Código postal: ");
            cp = kb.nextInt();

            kb.nextLine(); // Limpia el ENTER después de leer el cp 
            System.out.println("Dirección: ");
            address = kb.nextLine();

            System.out.println("Fecha de nacimiento (ej: 8-21-2007): ");
            birthDate = kb.nextLine();

            System.out.println("¿Debes dinero? (true/false): ");
            ownMoney = kb.nextBoolean();

            System.out.println("Deuda: (ej: 998.34): ");
            money = kb.nextDouble();

            newContacts.add(new Contacto(name, phoneNumber, cp, address, birthDate, ownMoney, money));

            System.out.println("¿Deseas añadir otro contacto para escribir? (s/n): ");
            answer = kb.next().toLowerCase().charAt(0);


        } while (answer == 's');

        return newContacts;
    }

    private void askNewContact(Path file) {
        List<Contacto> contactosNuevos = writeContacts("Añadir: Se mantendrán los contactos anteriores");
        addNewContact(file, contactosNuevos);
        System.out.println("Contacto(s) añadido(s) con éxito.");
    }


    public static void main(String[] args) {
        new Ejercicio1().show();
    }
}
