package ejercicios1_6FicherosJsonYXml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ejercicios1_4FicherosBinariosDeObjetos.Contacto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 {
    public void show() {
        /*
            Crea un fichero json con los datos de la agenda telefónica.  A continuación, lee el fichero json y muestra los datos por consola.

         */
        Path file = Paths.get("filesJsonYXml", "contacts.json");
        List<Contact> contacts = generateContacts();
        writeJson(file, contacts);//Se escribe el fichero Json
        readJson(file);//Se lee el fichero Json
    }

    private List<Contact> generateContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Sara Rojas", 666777111, 11002, "Clle Thai 23 Piso 2", LocalDate.of(2005, 7, 9), false, 0.0));
        contacts.add(new Contact("Santiago Ragel", 666773121, 11302, "Clle Yu 12 Portal 5G", LocalDate.of(2007, 10, 23), false, 0.0));
        contacts.add(new Contact("Nuria Carrillo", 661273121, 11363, "Clle Sevilla 89 Piso 6B", LocalDate.of(1975, 7, 10), true, 134.65));
        contacts.add(new Contact("Eva Macías", 611273121, 10063, "Clle San Felipe 20 Piso 8A", LocalDate.of(1990, 3, 2), true, 500.65));
        contacts.add(new Contact("Jaime González", 666023121, 11363, "Clle Sevilla 89 Piso 6B", LocalDate.of(2009, 5, 29), false, 0.0));
        return contacts;
    }

    private void writeJson(Path file, List<Contact> contacts) {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            json = mapper.writeValueAsString(contacts); // //Devuelve una cadena con los datos de los contactos en formato json
            Files.writeString(file, json); ////Se escribe en el fichero la cadena que contiene el json
        } catch (IOException e) {
            System.err.printf("Error de Entrada/Salida: %s", e.getMessage());
        }
    }

    private void readJson(Path file) {
        List<String> lines;
        String json;
        List<Contact> contacts;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            lines = Files.readAllLines(file); // Se leen las líneas del fichero.
            json = String.join("", lines); // Une todas las líneas del fichero.
            contacts = mapper.readValue(json, new TypeReference<List<Contact>>() {
            }); //Lee la cadena que contiene el json y devuelve una lista con los contactos leídos
            contacts.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Ejercicio1().show();
    }
}
