package com.biblioteca.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.biblioteca.modelos.Multa;
import com.biblioteca.modelos.Prestamo;

public class Fecha {
    public static String convertirFormatoSQL(String date) {
        String[] format = date.split(("/"));
        String day = format[0];
        String mes = format[1];
        String year = format[2];
        return year + "-" + mes + "-" + day;
    }

    public static void comprobarMulta() {
        Prestamo p = new Prestamo();
        Multa m = new Multa();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate hoy = LocalDate.now();
        for (Prestamo prestamo : p.obtenerAtrasos()) {
            LocalDate fechaPrestamo = LocalDate.parse(prestamo.getFecha_devolucion(), formato);
            long diferencia = ChronoUnit.DAYS.between(fechaPrestamo, hoy);
            if (diferencia > 30)
                m.setCosto_multa(30 * 20);
            else m.setCosto_multa(diferencia * 20);

            m.setId_prestamo_user(prestamo.getId_prestamo_user());
            m.setId_user(prestamo.getId_user());
            if (m.comprobarMultaPrestamo()) {
                m.setCosto_multa(20 * 30);
                m.actualizarCostoPrestamo();

            } else {
                m.setEstatus("Sin pagar");
                m.agregarMulta();
            }
        }
    }
}
