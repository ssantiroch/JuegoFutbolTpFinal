package Clases;

import Interfaces.AccionesJugador;

import javax.accessibility.Accessible;
import java.io.Serializable;

public class Jugador implements Serializable, Comparable<Jugador>, AccionesJugador {

    private String nombreJugador;
    private Posiciones posicionJugador;
    private float costoJugador;
    private int ataqueJugador;
    private int defensaJugador;
    private int golesJugador;
    private float costoVenta;
    private int valoracionTotal;

    enum Posiciones implements Serializable{
        Arquero,
        Defensor,
        Mediocampista,
        Delantero,
    }

    public Jugador() {
    }

    public int getAtaqueJugador() {
        return ataqueJugador;
    }

    public void setAtaqueJugador(int ataqueJugador) {
        this.ataqueJugador = ataqueJugador;
    }

    public float getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(float costoVenta) {
        this.costoVenta = costoVenta;
    }

    public int getValoracionTotal() {
        return valoracionTotal;
    }

    public void setValoracionTotal(int valoracionTotal) {
        this.valoracionTotal = valoracionTotal;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Enum getPosicionJugador() {
        return posicionJugador;
    }

    public void setPosicionJugador(Posiciones posicionJugador) {
        this.posicionJugador = posicionJugador;
    }

    public float getCostoJugador() {
        return costoJugador;
    }

    public void setCostoJugador(float costoJugador) {
        this.costoJugador = costoJugador;
    }

    public double getAtaqueJuegador() {
        return ataqueJugador;
    }

    public void setAtaqueJuegador(int ataqueJuegador) {
        this.ataqueJugador = ataqueJuegador;
    }

    public double getDefensaJugador() {
        return defensaJugador;
    }

    public void setDefensaJugador(int defensaJugador) {
        this.defensaJugador = defensaJugador;
    }

    public int getGolesJugador() {
        return golesJugador;
    }

    public void setGolesJugador(int golesJugador) {
        this.golesJugador = golesJugador;
    }

    @Override
    public String toString() {
        return  nombreJugador+" "+
                posicionJugador +
                ", Precio: " + costoJugador +
                ", Ataque: " + ataqueJugador +
                ", Defensa: " + defensaJugador +
                ", Valoracion: " + valoracionTotal;
    }

    @Override
    public int compareTo(Jugador otroJugador)
    {
        return this.nombreJugador.compareTo(otroJugador.nombreJugador);
    }


    public double calcularValoracionTotal() {
        // Ponderaciones para cada atributo
        double ponderacionCosto = 0.25; // 25%
        double ponderacionAtaque = 0.35; // 35%
        double ponderacionDefensa = 0.25; // 25%
        double ponderacionGoles = 0.15; // 15%

        // Normalización de los atributos (opcional, depende de tus necesidades)
        double costoNormalizado = costoJugador / 100; // Suponiendo que el costo está en millones
        double ataqueNormalizado = ataqueJugador / 100; // Ataque en una escala de 0 a 100
        double defensaNormalizada = defensaJugador / 100; // Defensa en una escala de 0 a 100
        double golesNormalizados = golesJugador / 50; // Suponiendo que 50 goles es un valor máximo razonable

        // Cálculo de la valoración total ponderada
        double valoracionTotal = (costoNormalizado * ponderacionCosto) +
                (ataqueNormalizado * ponderacionAtaque) +
                (defensaNormalizada * ponderacionDefensa) +
                (golesNormalizados * ponderacionGoles);

        // Escalar la valoración total a una escala de 0 a 100 (opcional)
        valoracionTotal *= 100;

        return valoracionTotal;
    }

    @Override
    public void correr() {
        System.out.println("estoy corriendo");
    }

    @Override
    public void disparar() {
        System.out.println("estoy disparando al arco");

    }

    @Override
    public void pasarBalon() {
        System.out.println("estoy pasando el balon");

    }

    @Override
    public void defender() {
        System.out.println("estoy defendiendo");

    }
}
