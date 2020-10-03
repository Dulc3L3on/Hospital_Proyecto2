/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB.Reportes;

import Extras.Reporte;
import Manejadores.DB.ManejadorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class ReporteAdministrador {
    Connection conexion = ManejadorDB.darConexion();
    Reporte reporte;
    
    public Reporte medicosConMasInformes(String desde, String hasta){//10 médicos... SI DA PROBLEMAS es por el hecho de estar comparando un str con el DATE de la DB... si es así solo debes usar lso métodos de la herramienta para convertir y listo xD...
      String reportar = "SELECT COUNT (codigoMedico) AS total, nombre FROM Consulta_Atendida INNER JOIN Medico"
              + "ON Consulta_Atendida.codigoMedico = Medico.codigo WHERE fecha BETWEEN ? AND ? GROUP BY codigoMedico ORDER BY total DESC LIMIT 10";/*correcta*/
      reporte=null;//esto lo hago para que cuando no halle nada con respecto al reporte en particular, entonces se pueda saber este hecho
      //De lado donde se invocó...
      
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));//Debe ser así, puesto que en ese orden fue pedido a la DB
            }                               
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion para el reporte\nde medicos con ># informes"+sqlE.getMessage());
            return null;
        }
        return reporte;        
    }/*terminado*/
    
    public Reporte ingresosObtenidosPorMedico(String desde, String hasta){//por ser en un rango de tiempo, entonces no se debe recibir el código a dif de las fechas...
        String reportar = "SELECT SUM (SELECT costo FROM Consulta WHERE Consulta.tipo = ConsultaAtendida.tipoConsulta) AS ingresos, nombre FROM Consulta_Atendida"
                + " INNER JOIN Medico ON Consulta_Atendida.codigoMedico = Medico.codigo WHERE fecha BETWEEN ? AND ? GROUP BY codigoMedico ORDER BY ingresos DESC";//si no funciona, es por el hecho de no haber incluido esta columna a mostrar, entonces tendría que se con el nombre... pero no se si vaya a dejar puesto que es la tabla con la que se fusionoó...

        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){/*indica aquí si está correcta*/
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion para el reporte de\ningregsos por medico -> "+ sqlE.getMessage());
        }
        return reporte;        
    }
    
    public Reporte medicosConMenorCantidadDeCitas(String desde, String hasta){
        String reportar = "SELECT COUNT(codigoMedico) total, nombre FROM Consulta_Atendida INNER JOIN Medico ON Medico.codigo = Consulta_Atendida.codigoMedico"
                + "WHERE fecha BETWEEN ? AND ? GROUP BY codigoMedico ORDER BY citas ASC LIMIT 5";
        
         try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){/*indica aquí si está correcta*/
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion para el reporte de\ningregsos por medico -> "+ sqlE.getMessage());
        }
        return reporte;        
    }
    
    public Reporte examenesMasDemandados(String desde, String hasta){
        String reportar ="SELECT COUNT(codigoExamen) AS total, nombre, FROM Examen_Atendido INNER JOIN Examen ON Examen_Atendido.codigoExamen =  Examen.codigo"
                + "WHERE fecha BETWEEN ? AND ? GROUP BY codigoExamen ORDER BY total DESC LIMIT 7";
        
         try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){/*indica aquí si está correcta*/
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion para el reporte de\ningregsos por medico -> "+ sqlE.getMessage());
        }
        return reporte;                
    }
    
    public Reporte medicosCOnMasExamenesRequeridos(String desde, String hasta){
        String reportar ="SELECT COUNT(codigoMedico) AS total, codigoMedico, nombre, FROM Solicitud_Examen INNER JOIN Medico ON Solicitud_Examen.codigoMedico =  Medico.codigo"
                + "WHERE fecha BETWEEN ? AND ? GROUP BY codigoMedico ORDER BY total DESC LIMIT 7";
        
         try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){/*indica aquí si está correcta*/
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getString(3), resultado.getInt(1));                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion para el reporte de\ningregsos por medico -> "+ sqlE.getMessage());
        }
        return reporte;                                
    }
    
    /**
     * Empleado luego de haber hecho la selección de uno de los elementos hallados
     * por la query anterior
     * @param codigoMedico
     * @param desde
     * @param hasta
     * @return
     */
    public Reporte examenesMasRequeridosPorMedicos(String codigoMedico, String desde, String hasta){//será empelado luego de haber seleccionadp  a un médico en particular...
        String reportar ="SELECT COUNT(codigoExamen) AS total, nombre, FROM Solicitud_Examen INNER JOIN Examen ON Solicitud_Examen.codigoExamen =  Examen.codigo"
                + "WHERE codigoMedico =  ? fecha BETWEEN ? AND ? GROUP BY codigoExamen ORDER BY total DESC LIMIT 3";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){/*indica aquí si está correcta*/
            instruccion.setString(1, codigoMedico);
            instruccion.setString(2, desde);
            instruccion.setString(3, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformacion de los examenes más\nrequeridos por medico -> "+ sqlE.getMessage());
        }
        return reporte;                
    }
    
    public Reporte ingresosPorPacienteDebidoConsultas(String desde, String hasta){//este deberá ser sumado con el resultado del de los exmanes para obtener el total.. ó puedes hacer la suma que parediste... eso sí no estría detalladito... 
        String reportar ="SELECT SUM(SELECT costo FROM Consulta WHERE Consulta.tipo = Consulta_Atendida.tipoConsulta) AS total, nombrePaciente FROM Consulta_Atendida"
                + "INNER JOIN Paciente ON Consulta_Atendida.codigoPaciente = Paciente.codigo WHERE fecha BETWEEN ? AND ? GROUP BY codigoPaciente ORDER BY total DESC";//aquí se están obteniendo a todos los pacientes... si se quisiera de uno en específico entonces debería agregarse es en la cláusula WHERE...
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformación de los ingresos\npor consulta de cada paciente\n "+sqlE.getMessage());
            reporte = null;
        }
        return reporte;    
    }
    
    public Reporte ingresosPorPacienteDebidoExamenes(String desde, String hasta){//este deberá ser sumado con el resultado del de los exmanes para obtener el total.. ó puedes hacer la suma que parediste... eso sí no estría detalladito... 
        String reportar ="SELECT SUM(SELECT costo FROM Examen WHERE Examen.codigoExamen = Consulta_Atendida.codigoExamen) AS total, nombrePaciente FROM Consulta_Atendida"
                + "INNER JOIN Paciente ON Consulta_Atendida.codigoPaciente = Paciente.codigo WHERE fecha BETWEEN ? AND ? GROUP BY codigoPaciente ORDER BY total DESC";//aquí se están obteniendo a todos los pacientes... si se quisiera de uno en específico entonces debería agregarse es en la cláusula WHERE...
        
        try(PreparedStatement instruccion = conexion.prepareStatement(reportar)){
            instruccion.setString(1, desde);
            instruccion.setString(2, hasta);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                reporte = new Reporte(resultado.getString(2), resultado.getInt(1));
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar\ninformación de los ingresos\npor examen de cada paciente\n "+sqlE.getMessage());
            reporte = null;
        }
        return reporte;    
    }
}
