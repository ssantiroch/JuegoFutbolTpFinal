package Clases;

import Interfaces.Estadisticas;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Equipo implements Serializable, Estadisticas {

    public String nombreEquipo;
    public String dtEquipo;
    private float presupuestoEquipo;
    public int puntajeEquipo;
    public Jugador[] jugadoresEquipo = new Jugador[11];
    private boolean ventajaDeEquipo;
    private int victorias;
    private int empates;
    private int derrotas;
    public int puntos;

    public Equipo(){
    }

    public Jugador[] getJugadoresEquipo() {
        return jugadoresEquipo;
    }

    public void setJugadoresEquipo(Jugador[] jugadoresEquipo) {
        this.jugadoresEquipo = jugadoresEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getDtEquipo() {
        return dtEquipo;
    }

    public void setDtEquipo(String dtEquipo) {
        this.dtEquipo = dtEquipo;
    }

    public float getPresupuestoEquipo() {
        return presupuestoEquipo;
    }

    public void setPresupuestoEquipo(float presupuestoEquipo) {
        this.presupuestoEquipo = presupuestoEquipo;
    }

    public int getPuntajeEquipo() {
        return puntajeEquipo;
    }

    public void setPuntajeEquipo(int puntajeEquipo) {
        this.puntajeEquipo = puntajeEquipo;
    }

    public Jugador[] getJuegadoresEquipo() {
        return jugadoresEquipo;
    }

    public void setJuegadoresEquipo(Jugador[] juegadoresEquipo) {
        this.jugadoresEquipo = juegadoresEquipo;
    }

    public boolean isVentajaDeEquipo() {
        return ventajaDeEquipo;
    }

    public void setVentajaDeEquipo(boolean ventajaDeEquipo) {
        this.ventajaDeEquipo = ventajaDeEquipo;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public void agregarJugador(Jugador nuevo)
    {
        for (int i = 0; i < jugadoresEquipo.length; i++) {
            if (jugadoresEquipo[i] == null) {
                jugadoresEquipo[i] = nuevo;
                return;
            }
        }
        System.out.println("Error agregando jugador nuevo.");
    }

    public int getPuntos() {
        return puntos;
    }

    private void calcularPuntajeEquipo(){
        int puntaje=0;
        for(int i = 0; i < jugadoresEquipo.length; i++){
            puntaje += jugadoresEquipo[i].getValoracionTotal();
        }
        this.puntajeEquipo = puntaje;
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }
    public void sumarPresupuesto(int dinero){
        this.presupuestoEquipo += dinero;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "nombreEquipo='" + nombreEquipo + '\'' +
                ", dtEquipo='" + dtEquipo + '\'' +
                ", presupuestoEquipo=" + presupuestoEquipo +
                ", puntajeEquipo=" + puntajeEquipo +
                ", jugadoresEquipo=" + Arrays.toString(jugadoresEquipo) +
                ", ventajaDeEquipo=" + ventajaDeEquipo +
                ", victorias=" + victorias +
                ", empates=" + empates +
                ", derrotas=" + derrotas +
                ", puntos=" + puntos +
                '}';
    }

    @Override
    public double porcentajeVictorias() {
        return 0;
    }

    @Override
    public double porcentajeDerrotas() {
        return 0;
    }

    @Override
    public double porcetajeEmpates() {
        return 0;
    }

    int totalPartidos()
    {
     return victorias+derrotas+empates;
    }

}
