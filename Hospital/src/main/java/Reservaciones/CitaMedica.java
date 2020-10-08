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
public class CitaMedica extends Cita implements Serializable{        
    int codigoMedico;    
    String tipoConsulta;       
               
    public CitaMedica(int codigo, int paciente, int medicoEncargado, 
       String tipoDeConsulta, Date fechaAcordada, int horaAcordada){
        
       super(codigo, paciente,  horaAcordada, fechaAcordada);
       tipoConsulta = tipoDeConsulta;
       codigoMedico = medicoEncargado;
    }    
    
    public CitaMedica(int paciente, int medicoEncargado, 
       String tipoDeConsulta, Date fechaAcordada, int horaAcordada){
        
       super(paciente,  horaAcordada, fechaAcordada);
       tipoConsulta = tipoDeConsulta;
       codigoMedico = medicoEncargado;
    }//este es para la citaAlternativa... para cuando está haciendo una reservación el paciente en cuestión...
    
    public void establecerCodigo(int codigoCorrespondiente){
        codigo = codigoCorrespondiente;
    }
    
    public int darCodigoMedicoACargo(){
        return codigoMedico;
    }
    
    public String darTipoConsulta(){
        return tipoConsulta;
    }   
    
    
}
