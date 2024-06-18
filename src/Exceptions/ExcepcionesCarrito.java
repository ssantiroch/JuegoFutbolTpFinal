package Exceptions;

import java.io.IOException;

public class ExcepcionesCarrito extends IOException{

    public ExcepcionesCarrito() {
    }

    public ExcepcionesCarrito(String message) {
        super(message);
    }
}
