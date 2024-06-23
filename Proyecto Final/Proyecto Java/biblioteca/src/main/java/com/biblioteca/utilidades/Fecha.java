package com.biblioteca.utilidades;

public class Fecha {
    public static String convertirFormatoSQL(String date){
        String[] format = date.split(("/"));
        String day = format[0];
        String mes = format[1];
        String year =  format[2];
        return year + "-" + mes + "-" + day;
    }
}
