/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import Reservaciones.CitaMedica;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Registro implements Serializable{
    private static Connection conexion;
    Creacion creador;
    Herramienta herramienta = new Herramienta();
    
//    public Registro(Connection conexionDB){
//        conexion=conexionDB;
//        creador = new Creacion(conexionDB);
//    }
    
    public Registro(){
        conexion = ManejadorDB.darConexion();  
        creador = new Creacion();
    }
    
    //creo que voy a atener que dejar a cada elemento con su nombre, a menos que se pueda obtener de él, un único componenete
    public boolean registrarPaciente(String datos[]){//Este será exclusivamente hecho por y para el cliente...
        int codigoDatosPersonales = creador.crearDatosPersonales(false, datos[7], datos[8], datos[1], datos[3]);//Aquí ya se recibió la contraseña encriptada...
        
        if(codigoDatosPersonales!=0){
            String registrar="INSERT INTO Paciente (nombre, sexo, birth, peso, tipoSangre, datosPersonales) VALUES (?,?,?,?,?,?)";//nombre, telefono, birth, dpi, sexo, peso, tipoSangre
      
            try(PreparedStatement instruccion = conexion.prepareStatement(registrar)){                
               java.sql.Date birth =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(datos[2]).getTime());
                
                instruccion.setString(1, datos[0]);
                instruccion.setString(2, datos[4]);
                instruccion.setDate(3, birth);
                instruccion.setString(4, datos[5]);
                instruccion.setString(5, datos[6]);
                instruccion.setInt(6, codigoDatosPersonales);
                
                instruccion.executeUpdate();
                return true;
            }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){
                System.out.println(e.getMessage());
                return false;
            }      
        }
        return false;                               
    }
    
   public boolean agendarCitaMedica(CitaMedica citaAAgendar){
       String registrar ="INSERT INTO Cita_Medica (codigoPaciente, codigoMedico, tipoConsulta, fecha, hora) VALUES (?,?,?,?,?)";

       try(PreparedStatement instruccion = conexion.prepareStatement(registrar)){
           instruccion.setInt(1, citaAAgendar.darCodigoPaciente());
           instruccion.setInt(2, citaAAgendar.darCodigoMedicoACargo());
           instruccion.setString(3, citaAAgendar.darTipoConsulta());
           instruccion.setDate(4, citaAAgendar.darFecha());
           instruccion.setInt(5, citaAAgendar.darHora());
           
           instruccion.executeUpdate();
       }catch(SQLException sqlE){
           System.out.println("surgió un error al\n registrar la citaMedia"+sqlE.getMessage());
           return false;
       }
       return true;       
   }/*terminado*///util para el paciente :v dobi xD
           
   public boolean agendarExamen(int codigoExamen, int codigoPaciente, String fecha, int hora){//puesto que los datos son dados por una fuente confiable, entonces es pisble hacer la conversión de una sola vez... pero por si las moscas xD...
       String registrar= "INSERT INTO Cita_Examen (codigoExamenSolicitado, codigoPaciente, fecha, hora) VALUES(?,?,?,?)";
       
       try(PreparedStatement instruccion = conexion.prepareStatement(registrar)){
           java.sql.Date fechaAgenda =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fecha).getTime());                
           
           instruccion.setInt(1, codigoExamen);
           instruccion.setInt(2, codigoPaciente);
           instruccion.setDate(3, fechaAgenda);
           instruccion.setInt(4, hora);
           
           instruccion.executeUpdate();           
       }catch(ClassCastException | SQLException e){
           System.out.println("surgió un error al registrar\nla cita para el examen\n" + e.getMessage());
           return false;
       }
       return true;
   }//empleado en el servletGestor... 
   
    public boolean registrarAdministrador(){//Este fijo no tiene problemas al cb de la clase de allá para la de acá... pero igual depende de como se recibirán los paráms.. arr o indi..
        return false;
    }//Creo que no me será útil...
    
    
    
    
}
