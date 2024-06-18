import Clases.Sistema;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema juego = new Sistema("Archivos/usuarios.json", "Archivos/jugadoresTienda2.json");
        juego.inicio();
    }
}