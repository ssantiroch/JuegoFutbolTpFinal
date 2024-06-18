package Clases;

import Interfaces.Estadisticas;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

public class Usuario implements Serializable, Estadisticas {

    public String nombreUsuario;
    private long idUsuario;
    private String contraseñaUsuario;
    private boolean administrador;
    private String email;
    private Carrito carrito;
    public Equipo equipoDeUsuario;
    private double fichas;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String contra) {
        this.nombreUsuario = nombre;
        this.email = email;
        this.contraseñaUsuario = contra;
        this.carrito = new Carrito("Archivos/Carritos/"+nombre+"Carrito.json");
        this.fichas = 750;
        this.idUsuario = (int)Math.random();
        this.equipoDeUsuario = new Equipo();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContraseñaUsuario() {
        return contraseñaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contraseñaUsuario = contraseñaUsuario;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public void setEquipoDeUsuario(Equipo equipoDeUsuario) {
        this.equipoDeUsuario = equipoDeUsuario;
    }

    public void setFichas(double fichas) {
        this.fichas = fichas;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public Equipo getEquipoDeUsuario() {
        return equipoDeUsuario;
    }

    public double getFichas() {
        return fichas;
    }

    @Override
    public String toString() {
        return  nombreUsuario + '\'' +
                email;
    }

    public boolean comprobarEquipo()
    {
        Scanner teclado = new Scanner(System.in);
        boolean f = flagEquipo();
        if(!f)
        {
            equipoDeUsuario.setDtEquipo(nombreUsuario);
            if(equipoDeUsuario.nombreEquipo!=null)
            {
                System.out.println("Ingrese el nombre de su equipo: ");
                equipoDeUsuario.setNombreEquipo(teclado.nextLine());
            }
            return false;
        }
        return true;
    }

    private boolean flagEquipo()
    {
        if(this.equipoDeUsuario!=null && this.equipoDeUsuario.jugadoresEquipo.length>11)
            return true;

        return false;
    }

    @Override
    public double porcentajeVictorias() {
        return equipoDeUsuario.totalPartidos()/equipoDeUsuario.getVictorias();
    }

    @Override
    public double porcentajeDerrotas() {
        return equipoDeUsuario.totalPartidos()/equipoDeUsuario.getDerrotas();
    }

    @Override
    public double porcetajeEmpates() {
        return equipoDeUsuario.totalPartidos()/equipoDeUsuario.getEmpates();
    }
}

//FALTA: validacion de casi todos los atributos, comprobar repetidos de NombreUsuario, ID, Email
//Buscar la manera de determinar si alguien puede ser admin o usuario}
