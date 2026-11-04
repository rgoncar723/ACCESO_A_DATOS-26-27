package ejercicios1_4FicherosBinariosDeObjetos;

import java.io.Serializable;

public record Contacto(String name, int phoneNumber, int cp, String address, String birthDate, boolean oweMoney,
                       double money) implements Serializable {

}
