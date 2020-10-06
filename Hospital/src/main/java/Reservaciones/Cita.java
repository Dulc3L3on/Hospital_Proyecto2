/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reservaciones;

import java.sql.Date;

/**
 *
 * @author phily
 */
public class Cita {    
    int codigo;
    int paciente;
    int hora;
    Date fecha;
        
    public Cita(int elCodigo, int codigoPaciente, int horaInicio, Date laFecha){
        codigo = elCodigo;
        paciente = codigoPaciente;
        hora = horaInicio;
        fecha = laFecha;
    }//útil para el paciente...
    
    public Cita(int codigoPaciente, int horaInicio, Date laFecha){        
        paciente = codigoPaciente;
        hora = horaInicio;
        fecha = laFecha;
    }//útil para las citas alternas del paciente...
    
}
