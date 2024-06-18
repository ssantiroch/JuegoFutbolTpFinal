package Clases;

import Exceptions.ExcepcionesUsuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema implements Serializable {

    private File rutaUsuarios;
    public Tienda tienda;
    public Usuario usuario;

    public Sistema(String rutaUsuarios, String rutaJugadoresTienda) {
        this.rutaUsuarios = new File(rutaUsuarios);
        this.tienda = new Tienda(rutaJugadoresTienda);
    }

    public void inicio ()
    {
        Scanner teclado = new Scanner(System.in);
        int op;
        System.out.println("1) Iniciar sesion");
        System.out.println("2) Registrasre");

        op = teclado.nextInt();
        switch (op)
        {
            case 1:
                if(iniciarSesion()) {
                    System.out.println("Sesion iniciada!");
                    menu();
                }
                else{
                    System.out.println("Error en inicio de sesion.");
                }
                break;
            case 2:
                registrarUsuario();
                menu();
                break;

            default:
                System.out.println("Error. Opción inexistente.");
                break;
        }
    }

    public void menuTienda()
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("1) Tienda");
        System.out.println("2) Carrito");
        int op = teclado.nextInt();
        switch (op)
        {
            case 1:
                tienda.mostrarTienda();
                System.out.println("1) Filtrar");
                System.out.println("2) Comprar jugador");
                op = teclado.nextInt();
                switch (op)
                {
                    case 1:
                        tienda.filtrarTienda(usuario);
                        break;
                    case 2:
                        tienda.comprarJugador(usuario);
                        break;
                    default:
                        System.out.println("Opcion inexistente!");
                }
                menu();
                break;
            case 2:
                usuario.getCarrito().menuCarrito(usuario);
                menu();
                break;
            default:
                System.out.println("Opcion inexistente!");
                menu();
                break;
        }
    }

    public void menu()
    {
        Scanner teclado = new Scanner(System.in);
        int op;
        System.out.println("1) Tienda");
        System.out.println("2) Jugar");
        op = teclado.nextInt();
        switch (op)
        {
            case 1:
                menuTienda();
                break;
            case 2:
                while(!usuario.comprobarEquipo())
                {
                System.out.println("Debe cargar mas jugadores a su equipo!");
                menuTienda();
                }
                menuJugar();
                break;
            case 42:
                System.out.println("Admin: ");
                System.out.println("1) Si");
                System.out.println("2) No");
                op = teclado.nextInt();
                switch (op)
                {
                    case 1:
                        if(usuario.isAdministrador())
                        {
                            System.out.println("Ingresaste modo Administrador.");
                            System.out.println("1) Eliminar Usuario");
                            System.out.println("2) Cargar nuevo Jugador a Tienda");
                            op = teclado.nextInt();
                            switch (op)
                            {
                                case 1:
                                    eliminarUsuario();
                                    break;
                                case 2:
                                    //cargarJugadoresNuevos
                                    break;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Ingrese contraseña de admin: ");
                        teclado.nextInt();
                        String contraseña = teclado.nextLine();
                        if(contraseña.equals("utn123"))
                            usuario.setAdministrador(true);
                }
                break;
            default:
                System.out.println("Error. Opción inexistente.");
                break;
        }
    }

    public boolean iniciarSesion()
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese nombre de usuario: ");
        String nombreBuscar = teclado.nextLine();
        Usuario aux = chequearUsusario(nombreBuscar);
        if(aux!=null)
        {
            System.out.println("Ingrese contrase単a: ");
            String contraBuscar = teclado.nextLine();
            if(contraBuscar.equals(aux.getContraseñaUsuario()))
            {
                this.usuario = aux;
                this.usuario.getCarrito().iniciarCarrito();
                return true;
            }

            System.out.println("Contraseña incorrecta.");
        }
        System.out.println("Usuario inexitente");
        return false;
    }

    private Usuario chequearUsusario(String nombreBuscar)
    {
        Gson gson = new Gson();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> listaUsuarios = mapper.readValue(rutaUsuarios, new TypeReference<List<Usuario>>(){});

            for (Usuario usuario : listaUsuarios) {
                if (usuario.getNombreUsuario().equals(nombreBuscar)) {
                    return usuario;
                }
            }
        } catch (IOException e)
        {
            new ExcepcionesUsuario("error en el chequeo de usuario");

        }
        return null;
    }

    public void registrarUsuario()
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese nombre de usuario: ");
        String nombreElegido = teclado.nextLine();
        Usuario aux = chequearUsusario(nombreElegido);
        if(aux==null)
        {
            System.out.println("Ingrese email: ");
            String email = teclado.nextLine();
            System.out.println("Ingrese contrase単a");
            String contra = teclado.nextLine();
            Usuario nuevo = new Usuario(nombreElegido, email, contra);
            this.usuario = nuevo;
            this.usuario.getCarrito().iniciarCarrito();
            cargaUsuarioArchivo(nuevo);
        }
        else
        {
            System.out.println("Error en el registro. Nombre ya elegido por otro usuario.");
        }

        menu();
    }

    private void cargaUsuarioArchivo(Usuario nuevo)
    {
        // leemos la lista entera en el archivo y la copiamos a una auxiliar
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try {

            listaUsuarios = mapper.readValue(rutaUsuarios,new TypeReference<ArrayList<Usuario>>(){});

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        // agregamos nuevo usuario a la lista auxiliar
        listaUsuarios.add(nuevo);

        try {
            ObjectMapper mapperWrite = new ObjectMapper();
            mapperWrite.writeValue(rutaUsuarios,listaUsuarios);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
        System.out.println("Bienvenido "+nuevo.nombreUsuario+"!");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void eliminarUsuario()
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese nombre de usuario a eliminar: ");
        String usuarioEliminar = teclado.nextLine();
        Usuario eliminado = chequearUsusario(usuarioEliminar);
        System.out.println(eliminado);

        eliminacion(eliminado);
        System.out.println("Usuario eliminado exitosamente. Nueva lista de usuarios: ");

        verListaUsuarios();
    }

    private void verListaUsuarios()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> listaUsuarios = mapper.readValue(rutaUsuarios,  new TypeReference<List<Usuario>>(){});
            for (Usuario usuario : listaUsuarios) {
                System.out.println(usuario.toString());
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void eliminacion(Usuario borrar) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> listaUsuarios = mapper.readValue(rutaUsuarios, new TypeReference<List<Usuario>>() {});
            listaUsuarios.remove(borrar);
            reescribirUsuarios(listaUsuarios);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void reescribirUsuarios(List<Usuario> listaUsuarios) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(rutaUsuarios, listaUsuarios);
            System.out.println("Lista de usuarios actualizada en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }


    public void menuJugar()
    {
        List<Equipo> ligaArgentina = cargarLiga("Archivos/Ligas/ligaArgentina.json");
        List<Equipo> ligaEspañola = cargarLiga("Archivos/Ligas/ligaEspanola.json");
        List<Equipo> ligaInglesa = cargarLiga("Archivos/Ligas/ligaInglesa.json");

        ligaArgentina.add(usuario.equipoDeUsuario);

        Liga ligaActual = new Liga(ligaArgentina, usuario.equipoDeUsuario);
        while (true) {
            ligaActual.mostrarMenu();
            if (ligaActual.verificarAscenso()) {
                if (ligaActual.getEquipos() == ligaArgentina) {
                    System.out.println("¡Felicitaciones! Has ascendido a La Liga.");
                    ligaEspañola.add(usuario.equipoDeUsuario);
                    ligaActual = new Liga(ligaEspañola, usuario.equipoDeUsuario);
                } else if (ligaActual.getEquipos() == ligaEspañola) {
                    System.out.println("¡Felicitaciones! Has ascendido a la Premier League.");
                    ligaInglesa.add(usuario.equipoDeUsuario);
                    ligaActual = new Liga(ligaInglesa, usuario.equipoDeUsuario);
                } else {
                    // Si se verifica el ascenso en ligaInglesa, ya no hay más ligas a las que ascender
                    System.out.println("¡Has ganado la Premier League! No hay más ligas a las que ascender.");
                    break;
                }
                // Mostrar el menú después de ascender a la siguiente liga
                ligaActual.mostrarMenu();
            } else {
                System.out.println("No lograste ascender. Jugarás otra temporada en la misma liga.");
                ligaActual.reiniciarLiga();
            }
        }
    }

    public List<Equipo> cargarLiga(String rutaLiga)
    {
        File archivoLiga = new File(rutaLiga);
        List<Equipo> ligaRetornar = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ligaRetornar = mapper.readValue( archivoLiga, new TypeReference<ArrayList<Equipo>>() {});
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (Equipo a : ligaRetornar)
            System.out.println(a.toString());

        return ligaRetornar;
    }


    /*
    private void eliminacion(Usuario borrar)
    {
        Gson gson = new Gson();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> listaUsuarios = mapper.readValue(rutaUsuarios,  new TypeReference<List<Usuario>>(){});
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getNombreUsuario().equals(borrar.getNombreUsuario())) {
                    boolean removido = listaUsuarios.remove(borrar);
                    if(removido)
                        System.out.println("Removidoooooooooooooooooooooooooooo");

                }
                System.out.println("recorriendo archivo: "+usuario.getNombreUsuario());
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

     */

}
