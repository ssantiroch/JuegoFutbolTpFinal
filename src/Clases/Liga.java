package Clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Liga{
    private List<Equipo> equipos;
    private Equipo equipoUsuario;
    private List<Partido> partidos;
    private int partidoActual;

    public Liga(List<Equipo> equipos, Equipo equipoUsuario) {
        this.equipos = equipos;
        this.equipoUsuario = equipoUsuario;
        this.partidos = new ArrayList<>();
        this.partidoActual = 0;
        generarCalendario();
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\nMenú de Liga:");
            System.out.println("1. Jugar partido");
            System.out.println("2. Ver tabla de posiciones");
            System.out.println("3. Ver calendario");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    jugarPartido();
                    break;
                case 2:
                    mostrarTablaDePosiciones();
                    break;
                case 3:
                    mostrarCalendario();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public void generarCalendario() {
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Equipo equipoLocal = equipos.get(i);
                Equipo equipoVisitante = equipos.get(j);
                partidos.add(new Partido(equipoLocal, equipoVisitante, equipoUsuario));
            }
        }
    }

    public void jugarPartido() {
        if (partidoActual < partidos.size()) {
            Partido partido = partidos.get(partidoActual);
            partido.jugarPartido();
            partidoActual++;
        } else {
            System.out.println("No hay más partidos para jugar.");
        }
    }

    public void mostrarTablaDePosiciones() {
        Collections.sort(equipos, Comparator.comparingInt(Equipo::getPuntos).reversed());

        System.out.println("Tabla de posiciones:");
        for (Equipo equipo : equipos) {
            System.out.println(equipo.nombreEquipo + " - Puntos: " + equipo.getPuntos());
        }
    }
    public void mostrarCalendario() {
        System.out.println("Calendario de Partidos:");
        for (int i = 0; i < partidos.size(); i++) {
            Partido partido = partidos.get(i);
            System.out.println((i + 1) + ". " + partido.equipoLocalPartido.nombreEquipo + " vs " + partido.equipoVisitantePartido.nombreEquipo);
        }
    }
    public boolean verificarAscenso() {
        Collections.sort(equipos, Comparator.comparingInt(Equipo::getPuntos).reversed());
        if(equipos.get(0).equals(equipoUsuario)){
            return true;
        }
        return false;
    }

    public void reiniciarLiga() {
        for (Equipo equipo : equipos) {
            equipo.sumarPuntos(-equipo.getPuntos());
            equipo.setVictorias(0);
            equipo.setEmpates(0);
            equipo.setDerrotas(0);
        }
        partidos.clear();
        partidoActual = 0;
        generarCalendario();
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}