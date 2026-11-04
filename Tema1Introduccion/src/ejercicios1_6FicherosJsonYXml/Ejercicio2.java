package ejercicios1_6FicherosJsonYXml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio2 {

    public void show() {
        /*
        Crea un fichero xml con los datos de la agenda telefónica.  A continuación, lee el fichero xml y muestra los datos por consola.
         */
        Path file = Paths.get("filesJsonYXml", "contacts.xml");
        ContactList contactList = generateContacts();
        writeXml(file, contactList); // Se escribe el fichero XML
        readXml(file); // Se lee el fichero XML
    }

    private ContactList generateContacts() {
        ContactList contactList;
        List<ContactXML> contacts = new ArrayList<>();
        contacts.add(new ContactXML("Sara Rojas", 666777111, 11002, "Clle Thai 23 Piso 2", LocalDate.of(2005, 7, 9), false, 0.0));
        contacts.add(new ContactXML("Santiago Ragel", 666773121, 11302, "Clle Yu 12 Portal 5G", LocalDate.of(2007, 10, 23), false, 0.0));
        contacts.add(new ContactXML("Eva Macías", 611273121, 10063, "Clle San Felipe 20 Piso 8A", LocalDate.of(1990, 3, 2), true, 500.65));
        contacts.add(new ContactXML("Jaime González", 666023121, 11363, "Clle Sevilla 89 Piso 6B", LocalDate.of(2009, 5, 29), false, 0.0));

        contactList = new ContactList(contacts);
        return contactList;
    }

    private void writeXml(Path file, ContactList contactsList) {
        // Asegurarse de que el directorio padre existe antes de escribir
        try {
            if (file.getParent() != null && !Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
        } catch (IOException e) {
            System.err.printf("Error al crear el directorio: %s%n", e.getMessage());
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        String xml;

        try {
            xml = xmlMapper.writeValueAsString(contactsList); // Devuelve una cadena con los datos en formato xml
            Files.writeString(file, xml); // Se escribe en el fichero la cadena que contiene el xml
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s%n", e.getMessage());
        }
    }

    private void readXml(Path file) {
        List<String> lines;
        String xml;
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        try {
            lines = Files.readAllLines(file); // Se leen las líneas del fichero
            xml = String.join("", lines);

            // Leemos mapeando a ContactList para que coincida con la estructura escrita
            ContactList contactList = xmlMapper.readValue(xml, ContactList.class);

            // Mostramos los contactos por consola (ajusta según el método de tu clase ContactList, ej: getContacts())
            if (contactList != null && contactList.getContactXMLS() != null) {
                contactList.getContactXMLS().forEach(System.out::println);
            }

        } catch (FileNotFoundException e) {
            System.err.printf("El fichero no existe: %s%n", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s%n", e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Ejercicio2().show();
    }
}