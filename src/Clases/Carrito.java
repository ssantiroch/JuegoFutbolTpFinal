package Clases;

import Exceptions.ExcepcionesCarrito;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Carrito implements Serializable {

    private File archivoCarrito;
    private double total;

    public Carrito(String rutaCarrito) {
        this.archivoCarrito = new File(rutaCarrito);
        this.total = 0;
    }

    public Carrito() {
    }

    public void iniciarCarrito()
    {
        if(!archivoCarrito.exists())
        {
            try
            {
                archivoCarrito.createNewFile();
            }catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public File getArchivoCarrito() {
        return archivoCarrito;
    }

    public void setArchivoCarrito(File archivoCarrito) {
        this.archivoCarrito = archivoCarrito;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void menuCarrito(Usuario usuario)
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Carrito: ");
        System.out.println("1) Ver carrito");
        System.out.println("2) Realizar compra");
        System.out.println("3) Eliminar un jugador");
        System.out.println("4) Vaciar carrito");
        int op = teclado.nextInt();
        switch (op)
        {
            case 1:
                verCarrito(usuario);
                break;
            case 2:
                realizarCompra(usuario);
                break;
            case 3:
                eliminarUnJugador(usuario);
                break;
            case 4:
                vaciarCarrito(usuario);
                break;
            default:
                System.out.println("Opcion inexistente!");
                break;
        }

    }

    public void agregarAlCarrito(Jugador nuevo)
    {
        if(archivoCarrito.exists())
        {
            ObjectMapper mapper = new ObjectMapper();
            List<Jugador> listaCarrito= new ArrayList<>();
            try {
                listaCarrito = mapper.readValue(archivoCarrito,new TypeReference<ArrayList<Jugador>>(){});
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
            boolean jugadorExistenteFlag = false;
            for (Jugador e : listaCarrito)
            {
                if(e.getNombreJugador().compareTo(nuevo.getNombreJugador()) == 0)
                    jugadorExistenteFlag = true;

            }
            if(!jugadorExistenteFlag)
            {
                listaCarrito.add(nuevo);
                try {
                    ObjectMapper mapperWrite = new ObjectMapper();
                    mapperWrite.writeValue(archivoCarrito,listaCarrito);
                } catch (IOException e) {
                    System.out.println("Error al escribir en el archivo: " + e.getMessage());
                }
                this.total = this.total + nuevo.getCostoJugador();
                System.out.println("Has agregado a "+nuevo.getNombreJugador()+" al carrito!");
            }
            else
            {
                System.out.println(nuevo.getNombreJugador()+" ya esta en el carrito!");
            }
        }
        else
        {
            System.out.println("El carrito no esta creado");
        }
    }

    public void realizarCompra(Usuario usuario)
    {
        if(usuario.getFichas()>=total )
        {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Jugador> listaCarrito= new ArrayList<>();
            try {
                listaCarrito = mapper.readValue(archivoCarrito,new TypeReference<ArrayList<Jugador>>(){});
            } catch (ExcepcionesCarrito e)
            {
                System.out.println(e.getMessage());

            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
            for(Jugador a : listaCarrito)
            {
                usuario.equipoDeUsuario.agregarJugador(a);
                System.out.println(a.getNombreJugador()+" agregado al equipo!");
            }
            usuario.setFichas(usuario.getFichas()- total);
            total = 0;
            vaciarCarrito(usuario);
        }
        else
        {
            System.out.println("Fichas insuficientes.");
            menuCarrito(usuario);
        }
    }

    public void verCarrito(Usuario usuario)
    {
        if(archivoCarrito.exists())
        {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Jugador> listaCarrito= new ArrayList<>();
            try {
                listaCarrito = mapper.readValue(archivoCarrito,new TypeReference<ArrayList<Jugador>>(){});
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
            for(Jugador a : listaCarrito)
                System.out.println(a.toString());
        }
        else
        {
            System.out.println("Carrito inexistente.");
        }
    }

    public void eliminarUnJugador(Usuario usuario)
    {
        Scanner teclado = new Scanner(System.in);
        verCarrito(usuario);
        System.out.println("Que jugador desea eliminar?");
        String eliminar = teclado.nextLine();

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        try
        {
            listaJugadores = mapper.readValue(archivoCarrito, new TypeReference<ArrayList<Jugador>>(){});
        }catch (IOException e)
        {
            System.out.println("Problemas encontrando jugador."+e.getMessage());
        }
        for(Jugador a : listaJugadores)
        {
            if(a.getNombreJugador().equals(eliminar)) {
                listaJugadores.remove(a);
                return;
            }
        }
        System.out.println("Jugador no encontrado en el carrito.");
    }

    public void vaciarCarrito(Usuario usuario)
    {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        try
        {
            listaJugadores = mapper.readValue(archivoCarrito, new TypeReference<ArrayList<Jugador>>(){});
        }catch (IOException e)
        {
            System.out.println("Problemas encontrando jugador."+e.getMessage());
        }
        listaJugadores.clear();

        try
        {
            mapper.writeValue(archivoCarrito, listaJugadores);
            System.out.println("Carrito vaciado exitosamente!");
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }


    }

}
