/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reservaciones;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phily
 */
public class CitaExamen extends Cita implements Serializable{
    int codigoExamenSolicitado;
    
    public CitaExamen(int codigo,  int codigoExamen, int codigoPaciente, 
            Date fechaAcordada, int hora){
        
        super(codigo, codigoPaciente, hora, fechaAcordada);
        codigoExamenSolicitado= codigoExamen;
    }
    
}
