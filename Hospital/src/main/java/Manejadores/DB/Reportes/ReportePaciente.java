/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB.Reportes;

import Extras.Reporte;
import Kit.Herramienta;
import Kit.ListaEnlazada;
import Manejadores.DB.ManejadorDB;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class ReportePaciente implements Serializable {
    Connection conexion;
    Herramienta herramienta = new Herramienta();
    ListaEnlazada<Reporte> listadoReportes = new ListaEnlazada();
    Reporte[] reportes;
    
    public ReportePaciente(){
        conexion = ManejadorDB.darConexion();
    }/*terminado*/
    
    public Reporte[] ultimos5Examenes(int codigoPaciente){
        String reportar="SELECT (Examen.nombre, Laboratorista.nombre,fecha, hora) FROM Examen_Atendido INNER JOIN Examen ON"
             + "Examen_Atendido.codigoExamen = Examen.codigo WHERE codigoPaciente = ?, "
             + "ORDER BY fecha DESC LIMIT 5";
        reportes=null;        
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setInt(1, codigoPaciente);
            
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
            
            reportes = new Reporte[resultado.getRow()];
            resultado.first();
            
            for (int reporteActual = 0; reporteActual < reportes.length; reporteActual++) {
               reportes[reporteActual] = new Reporte(resultado.getString(1), resultado.getString(2), resultado.getDate(3), resultado.getInt(4));//y saLudables xD :)
               resultado.first();
            }                                    
        }catch(SQLException sqlE){
            System.out.println("surgió un error en la\nbusqueda de los reportes\n"+sqlE.getMessage());
        }        
        return reportes;
    }/*terminado*/
    
    public ListaEnlazada<Reporte> examenesDeUnTipoRealizados(int codigoExamen, int codigoPaciente, String desde, String hasta){
        String reportar="SELECT (nombre, fecha, hora) FROM Examen_Atendido INNER JOIN Laboratorista ON"
             + "Examen_Atendido.codigoLaboratorista = Laboratorista.codigo WHERE codigoExamen = ?"
             + "AND codigoPaciente = ? AND fecha BETWEEN ? AND ?";
        listadoReportes.limpiar();//puesto que al ser global, mantendrá los valores... y eso no lo requiero... al menos no aquí ni ahora xD
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){                        
            instruccion.setInt(1, codigoExamen);
            instruccion.setInt(2, codigoPaciente);
            instruccion.setDate(3, herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(desde).getTime()));                
            instruccion.setDate(4, herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(hasta).getTime()));              
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                listadoReportes.anadirAlFinal(new Reporte("Examen", resultado.getString(1), resultado.getDate(2), resultado.getInt(3)));//y saLudables xD :)
            }        
        }catch(SQLException sqlE){
            System.out.println("surgió un error en la\nbusqueda de los reportes\n"+sqlE.getMessage());
        }        
        return listadoReportes;
    }/*terminado*/    
    
    public Reporte[] ultimas5Consultas(int codigoPaciente){
        String reportar = "SELECT (tipoConsulta, nombre,fecha, hora) FROM Consulta_Atendida INNER JOIN Medico "
                + "ON Consulta_Atendida.codigoMedico = Medico.codigo WHERE codigoPaciente = ? ORDER BY fecha DESC"
                + "LIMIT 5";
        reportes = null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setInt(1, codigoPaciente);
            
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
            reportes= new Reporte[resultado.getRow()];
            resultado.first();
            
            for (int reporteActual = 0; reporteActual < reportes.length; reporteActual++) {               
                reportes[reporteActual] =new Reporte(resultado.getString(1), resultado.getString(2), resultado.getDate(3), 
                resultado.getInt(4));                                                
                
                resultado.next();
            }                        
        }catch(SQLException sqlE){
            System.out.println("surgió un error en la busqueda\nde las ultimos 5 consultas realizadas\n"+sqlE.getMessage());
        }
        
        return reportes;
    }/*terminado*/
    
    public ListaEnlazada<Reporte> consultasRealizadasConMedicoEspecifico(int codigoMedico, int codigoPaciente, String desde, String hasta){
        String reportar ="SELECT (tipoConsulta, fecha, hora) FROM Consulta_Atendida WHERE codigoMedico= ? codigoPaciente= ? AND fecha"
                + "BETWEEN ? AND ?";
        listadoReportes.limpiar();
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setInt(1, codigoMedico);
            instruccion.setInt(2, codigoPaciente);
            instruccion.setDate(3, herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(desde).getTime()));                
            instruccion.setDate(4, herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(hasta).getTime()));          
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                listadoReportes.anadirAlFinal(new Reporte("Consulta",resultado.getString(1), resultado.getDate(2), resultado.getInt(3)));                                                
            }                 
        }catch(SQLException sqlE){
            System.out.println("surgió un error en la búsque de las\nconsultas realizadas con un medico especifica\n"+sqlE.getMessage());
        }
        return listadoReportes;
    }/*terminado*/    
    
}
