package ejercicios1_5FicherosDeAccesoAleatorio;

import java.io.Serializable;

public record Contact(String name, int phoneNumber, int cp, String address, String birthDate, boolean oweMoney,
                      double money) implements Serializable {

}
