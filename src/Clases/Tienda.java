package Clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

public class Tienda implements Serializable {

    List<Jugador> listaJugadoresTienda;

    public Tienda(String rutaJugadores){
        listaJugadoresTienda = cargarListaJugadores(rutaJugadores);
    }

    public List<Jugador> cargarListaJugadores(String rutaJugadores)
    {
        Gson gson = new Gson();
        try(FileReader fileReader = new FileReader(rutaJugadores))
        {
            Type tipoDeLista = new TypeToken<List<Jugador>>() {}.getType();
            return gson.fromJson(fileReader, tipoDeLista);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void mostrarTienda( )
    {
        for(Jugador a : listaJugadoresTienda)
        {
            System.out.println(a.toString());
        }
    }

    public void filtrarTienda(Usuario usuario)
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("1) Alfabeticamente");
        System.out.println("2) Posicion");
        System.out.println("3) Valoracion");
        int op = teclado.nextInt();
        switch (op)
        {
            case 1:
                alfabeticamente();
                break;
            case 2:
                //posicion();
                break;
            case 3:

        }
        System.out.println("1) Agregar jugador al carrito");
        System.out.println("2) Filtrar nuevamente");
        op = teclado.nextInt();
        switch (op)
        {
            case 1:
                comprarJugador(usuario);
                break;
            case 2:
                filtrarTienda(usuario);
                break;
        }
    }

    public void comprarJugador(Usuario usuario)
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el nombre del jugador deseado: ");
        String jugadorBuscar = teclado.nextLine();
        Jugador comprar = buscarJugadorPorNombre(jugadorBuscar);
            if(comprar!=null)
            {
                System.out.println("Precio de "+comprar.getNombreJugador()+": "+comprar.getCostoJugador());
                System.out.println("Fichas disponibles: "+usuario.getFichas());
                System.out.println("Enviar al carrito: ");
                System.out.println("1) Aceptar");
                System.out.println("2) Volver al menu");
                    int op = teclado.nextInt();
                    switch (op)
                    {
                        case 1:
                            usuario.getCarrito().agregarAlCarrito(comprar);
                            usuario.getCarrito().verCarrito(usuario);
                            break;

                        case 2:

                    }
            }
            else
                System.out.println("Jugador inexistente");
    }

    private Jugador buscarJugadorPorNombre(String nombreBuscar)
    {
        for(Jugador a : listaJugadoresTienda)
        {
            if(a.getNombreJugador().equals(nombreBuscar))
                return a;
        }
        return null;
    }

    private void alfabeticamente()
    {
        List<Jugador> aux = listaJugadoresTienda;
        Collections.sort(listaJugadoresTienda);
        mostrarTienda();
    }

}
