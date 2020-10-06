/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import Entidades.Medico;
import Kit.ListaEnlazada;
import Manejadores.DB.Entidades.IntegradorEntidades;
import Manejadores.DB.ManejadorDB;
import Reservaciones.Cita;
import Reservaciones.CitaMedica;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phily
 */
public class GestorActividades {
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
    
    public boolean agendarCitaMedica(){
    
    }
          
    public CitaMedica[] obtnerHorasOcupadas(Date fecha, Medico medicoDeInteres, int codigoPaciente, String especialidad){//recuerda que el mínimo debería ser la fecha actual...
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
          
}
