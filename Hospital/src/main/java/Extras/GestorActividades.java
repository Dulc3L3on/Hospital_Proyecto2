/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import Entidades.Medico;
import Manejadores.DB.Entidades.IntegradorEntidades;
import Manejadores.DB.ManejadorDB;
import Reservaciones.Cita;
import Reservaciones.CitaMedica;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class GestorActividades implements Serializable{
    Connection conexion;
    IntegradorEntidades integrador = new IntegradorEntidades();
    boolean procesoObtenecionExitoso=true;
    
    public GestorActividades(){
        conexion = ManejadorDB.darConexion();
    }
        
    public Cita verCitasDelDia(String tipoCita, int codigoPaciente, Date fecha){//quisiera agregarle la hora para que fuera más exacto...
        String buscar ="SELECT * FROM "+tipoCita+" WHERE codigoPaciente = ? AND fecha = ?";
        Cita cita = null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigoPaciente);
            instruccion.setDate(2, fecha);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                cita = integrador.formarCita(resultado, tipoCita);                
            }            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\nlas citas médicas del día");
            cita=null;
        }
        return cita;
    }                

    public CitaMedica[] obtenerHorasOcupadas(Date fecha, Medico medicoDeInteres, int codigoPaciente, String especialidad){//recuerda que el mínimo debería ser la fecha actual...
        String buscar ="SELECT hora FROM Cita_Medica WHERE codigoMedico = ? AND fecha = ?";        
        CitaMedica[] citasDisponibles = new CitaMedica[medicoDeInteres.darHoraFin()-medicoDeInteres.darHoraInicio()];              
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, medicoDeInteres.codigo);
            instruccion.setDate(2, fecha);
            
            ResultSet resultado = instruccion.executeQuery();            
            resultado.first();
            
            for (int horaActual = medicoDeInteres.darHoraInicio(); horaActual < (medicoDeInteres.darHoraFin()-medicoDeInteres.darHoraInicio()); horaActual++) {//puesto que dobidamente xD la primer hora disponible debería estar en el inicio de su jornada y la última debería ser justo 1hr antes de que finalice... si no no se cumpliría con su horario... además de esta manera aunque cambie de horario, no pasará nada malo xD el proceso puede seguir su curso normal...
               citasDisponibles[horaActual]=integrador.formarCitaAlternativa(resultado, medicoDeInteres.codigo, codigoPaciente, especialidad, fecha, horaActual);               
            }            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\nlas horas ocupadas del médico\n"+sqlE.getMessage());            
            procesoObtenecionExitoso=false;
        }        
        return citasDisponibles;
    }/*terminado :) fiuu xd*///Método que se encarga  de formar el listado de todas las citas con las horas que no concueden con aquellas que aparecen en los registros de las citas agendads y no atendidas...
 
     public CitaMedica[] obtenerHorarioDisponibilidad(Date fecha, Medico medicoDeInteres, int codigoPaciente, String especialidad){//recuerda que el mínimo debería ser la fecha actual...
        String buscar ="SELECT hora FROM Cita_Medica WHERE codigoMedico = ? AND fecha = ?";        
        CitaMedica[] citasDisponibles = integrador.formarCitasAlternativa(medicoDeInteres, codigoPaciente, especialidad, fecha);              
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, medicoDeInteres.codigo);
            instruccion.setDate(2, fecha);
            
            ResultSet resultado = instruccion.executeQuery();            
            resultado.first();
            
            for (int horaActual = medicoDeInteres.darHoraInicio(); horaActual < (medicoDeInteres.darHoraFin()-medicoDeInteres.darHoraInicio()); horaActual++) {//puesto que dobidamente xD la primer hora disponible debería estar en el inicio de su jornada y la última debería ser justo 1hr antes de que finalice... si no no se cumpliría con su horario... además de esta manera aunque cambie de horario, no pasará nada malo xD el proceso puede seguir su curso normal...
                if(horaActual == resultado.getInt(1)){
                    citasDisponibles[horaActual] = null;//puesto que ya está ocupada...
                }            
            }//y de esta manera se cancelan o se eliminan las horas no disponibles del médico...            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\nlas horas ocupadas del médico\n"+sqlE.getMessage());            
            citasDisponibles = null;
        }        
        return citasDisponibles;//Entonces si no existían rgistros par el día seleccionado, es decir estaba libre el día, devolverá el horario completo de disponibilidad y si había salido algo mak entonces devuelve null y así no habrán cosas a medias...
    }/*terminado :) *///si el arrglo == null, entonces hubieron problemas en la recuperación de información horaria xD...
    
}
