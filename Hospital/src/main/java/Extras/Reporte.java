/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import java.sql.Date;

/**
 *
 * @author phily
 */
public class Reporte {
    
    private int codigo;
    private String nombreUsuario;
    private String nombreServicio;//es decir nombre de especialidad o nombre de examen...
    private int dia;
    private Date fecha;
    private int numero;//para sumas, conteos...
    
    public Reporte(int elCodigo, String nombreDelUsuario, int elNumero){
        codigo= elCodigo;
        nombreUsuario = nombreDelUsuario;
        numero = elNumero;
    }
    
    public Reporte(String nombreDelUsuario, int elNumero){
        nombreUsuario = nombreDelUsuario;
        numero = elNumero;
    }//útiles para el administrador [esto se "dispersa" hacaia arriba... 
    
    public Reporte(String nombreDelUsuario, int elNumero, Date laFecha){
        nombreUsuario = nombreDelUsuario;
        numero = elNumero;
        fecha = laFecha;
    }
    
    
}
