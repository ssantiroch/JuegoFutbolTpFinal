package Clases;
import Clases.Equipo;
import java.util.Random;
import java.util.Scanner;

public class Partido {
    public Equipo equipoLocalPartido;
    private int golesLocal;
    public Equipo equipoVisitantePartido;
    private int golesVisitante;
    private Equipo equipoUsuario;

    public Partido(Equipo equipoLocalPartido, Equipo equipoVisitantePartido, Equipo equipoUsuario) {
        this.equipoLocalPartido = equipoLocalPartido;
        this.equipoVisitantePartido = equipoVisitantePartido;
        this.equipoUsuario = equipoUsuario;
    }

    public void jugarPartido() {
        if (equipoLocalPartido.equals(equipoUsuario) || equipoVisitantePartido.equals(equipoUsuario)) {
            jugarPartidoConUsuario();
        } else {
            jugarPartidoSimulado();
        }
    }

    private void jugarPartidoSimulado() {
        Random rand = new Random();
        this.golesLocal = rand.nextInt(6);
        this.golesVisitante = rand.nextInt(6);

        // Mostrar resultados del partido
        System.out.println("Resultado del partido simulado: ");
        System.out.println(equipoLocalPartido.nombreEquipo + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitantePartido.nombreEquipo);

        // Actualizar puntos
        actualizarPuntos();
    }

    private void jugarPartidoConUsuario() {
        Random rand = new Random();
        Scanner teclado = new Scanner(System.in);

        int oportunidadesLocal = 5;
        int oportunidadesVisitante = 5;

        this.golesLocal = 0;
        this.golesVisitante = 0;
        if (equipoLocalPartido.puntajeEquipo > equipoVisitantePartido.puntajeEquipo) {
            equipoLocalPartido.setVentajaDeEquipo(true);
            equipoVisitantePartido.setVentajaDeEquipo(false);
        } else {
            equipoVisitantePartido.setVentajaDeEquipo(true);
            equipoLocalPartido.setVentajaDeEquipo(false);
        }
        if(equipoLocalPartido.isVentajaDeEquipo()){
            oportunidadesVisitante = 3;
        }else{
            oportunidadesLocal = 3;
        }
        System.out.println("   Comienza el partido entre "+equipoLocalPartido.getNombreEquipo()+"-"+equipoVisitantePartido.getNombreEquipo());
        while (oportunidadesLocal > 0 || oportunidadesVisitante > 0) {
            int opcion = 0;
            if (oportunidadesLocal > 0) {
                System.out.println(equipoLocalPartido.nombreEquipo + " " + golesLocal + " " + golesVisitante + " " + equipoVisitantePartido.nombreEquipo);
                System.out.println("                Elige donde patear                ");
                System.out.println("   Izquierda(1) -     Centro(2)    - Derecha(3)");
                opcion = teclado.nextInt();
                int iV = rand.nextInt(3) + 1;
                if (opcion != iV) {
                    System.out.println("¡¡GOL DE " + equipoLocalPartido.nombreEquipo + "!!");
                    golesLocal++;
                } else {
                    System.out.println("Atajo el arquero de" + equipoVisitantePartido.nombreEquipo);
                }
            }
            if(oportunidadesVisitante>0){
                System.out.println(equipoLocalPartido.getNombreEquipo()+" "+golesLocal+"-"+golesVisitante+" "+equipoVisitantePartido.getNombreEquipo());
                System.out.println("                Elige donde atajar                ");
                System.out.println("   Izquierda(1) -     Centro(2)    - Derecha(3)");
                opcion = teclado.nextInt();
                int iL = rand.nextInt(3) + 1;
                if (opcion == iL) {
                    System.out.println("¡¡Atajaste!!");
                } else {
                    System.out.println("¡¡Gol de " + equipoVisitantePartido.nombreEquipo + "!!");
                    golesVisitante++;
                }
            }

            oportunidadesLocal--;
            oportunidadesVisitante--;
        }

        System.out.println("Resultado del partido: ");
        System.out.println(equipoLocalPartido.nombreEquipo + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitantePartido.nombreEquipo);

        // Actualizar puntos
        actualizarPuntos();
    }

    private void actualizarPuntos() {
        if (golesLocal > golesVisitante) {
            equipoLocalPartido.sumarPuntos(3);
            equipoVisitantePartido.setDerrotas(+1);
            equipoLocalPartido.setVictorias(+1);
            equipoLocalPartido.sumarPresupuesto(2000000);
        } else if (golesLocal < golesVisitante) {
            equipoVisitantePartido.sumarPuntos(3);
            equipoLocalPartido.setDerrotas(+1);
            equipoVisitantePartido.setVictorias(+1);
            equipoLocalPartido.sumarPresupuesto(500000);
        } else {
            equipoLocalPartido.sumarPuntos(1);
            equipoVisitantePartido.sumarPuntos(1);
            equipoLocalPartido.setEmpates(+1);
            equipoVisitantePartido.setEmpates(+1);
            equipoLocalPartido.sumarPresupuesto(1000000);
        }
        equipoLocalPartido.setVentajaDeEquipo(false);
        equipoVisitantePartido.setVentajaDeEquipo(false);
    }
}