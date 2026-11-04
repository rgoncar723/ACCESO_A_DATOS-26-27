package ejercicios1_6FicherosJsonYXml;

import com.fasterxml.jackson.dataformat.xml.annotation.*;

import java.util.List;

@JacksonXmlRootElement(localName = "Contacts")
public class ContactList {
    @JacksonXmlElementWrapper(useWrapping = false) // Elimina el nodo "ArrayList"
    @JacksonXmlProperty(localName = "Contact") // Etiqueta para cada elemento. Si no se pone, coge el nombre del atributo: vehicles

    private List<ContactXML> contactXMLS;

    public ContactList(){}

    public ContactList(List<ContactXML> contactXMLS){
        this.contactXMLS=contactXMLS;
    }
    public List<ContactXML> getContactXMLS(){
        return contactXMLS;
    }
    public void setContactXMLS(List<ContactXML> contactXMLS){
        this.contactXMLS=contactXMLS;
    }
}
