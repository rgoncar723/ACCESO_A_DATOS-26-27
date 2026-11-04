package ejercicios1_6FicherosJsonYXml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@JacksonXmlRootElement(localName = "ContactXML") // Etiqueta raíz personalizada


public class ContactXML {
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "PhoneNumber")
    private int phoneNumber;

    @JacksonXmlProperty(localName = "PostalCode")
    private int pc;

    @JacksonXmlProperty(localName = "Address")
    private String address;

    @JacksonXmlProperty(localName = "BirthDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @JacksonXmlProperty(localName = "OwnsMoney")
    private boolean oweMoney;

    @JacksonXmlProperty(localName = "Amount")
    private double amount;

    public ContactXML(String name, int phoneNumber, int pc, String address, LocalDate birthDate, boolean oweMoney, double amount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pc = pc;
        this.amount = amount;
        this.oweMoney = oweMoney;
        this.birthDate = birthDate;
        this.address = address;
    }

    public ContactXML() {

    }

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getPC() {
        return pc;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean getOweMoney() {
        return oweMoney;
    }

    public double getAmount() {
        return amount;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setOweMoney(boolean oweMoney) {
        this.oweMoney = oweMoney;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Nombre:%s\nNúmero de teléfono:%d\nCódigo postal:%d\nDirección:%s\nFecha de nacimiento:%s\n¿Le debo dinero?:%s\nCantidad:%.2f"
                , name, phoneNumber, pc, address, formatter.format(birthDate), oweMoney ? "Si" : "No", amount);
    }
}
