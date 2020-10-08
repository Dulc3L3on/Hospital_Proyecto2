/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phily
 */
public class CreacionAdministrada implements Serializable{
    Connection conexion = ManejadorDB.darConexion();
    Creacion creador = new Creacion();
    Herramienta herramienta = new Herramienta();
    BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();
    
    public int crearMedico(String nombre, String numeroColegiado, String horaInicioLabor, String horaFinLabor, String fechaInicio, String correo, String contrasenia, String telefono, String DPI){
        String crear ="INSERT INTO Medico (nombre, numeroColegiado, horaInicio, horaFin, datosPersonales, fechaIncorporacion) VALUES(?,?,?,?,?,?)";
        int codigoMedico=0;
        int codigoDatosPersonales= creador.crearDatosPersonales(false, correo, contrasenia, telefono, DPI);//puesto que esta entidad [médico] requiere de este código                    
        
        if(codigoDatosPersonales>0){//Puesto que Médico SÍ DEPENDE de este valor para crear correctamente su registro, por lo ual si falla, NO DEBERÁ  de registrarse al médico al menos no automáticamenente, sino manual, esto por medio de la creación que puede hacer el administrador...            
        
            try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){         
                int horaInicio = Integer.parseInt(horaInicioLabor);
                int horaFin = Integer.parseInt(horaFinLabor);                               
                java.sql.Date fecha=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaInicio).getTime());                
                                
                instruccion.setString(1, nombre);
                instruccion.setString(2, numeroColegiado);
                instruccion.setInt(3, horaInicio);
                instruccion.setInt(4, horaFin);
                instruccion.setInt(5, codigoDatosPersonales);
                instruccion.setDate(6, fecha);                        
                
                 ResultSet resultado= instruccion.executeQuery();                                                           
                 codigoMedico = resultado.getInt(1);            
            }catch(NumberFormatException | SQLException e){
                System.out.println("surgió un error al crear al médico -> "+ e.getMessage());
                codigoMedico =0;
            }
        }
        return codigoMedico;
    }/*terminado*/
    
    public boolean crearTitulos(int codigoMedico, String[] titulos){//se empleará mientras el codigo del médico no sea 0
        int codigoEspecialidad;
        boolean procesoCorrecto=true;
        
        for (int tituloActual = 0; tituloActual < titulos.length; tituloActual++) {
            codigoEspecialidad = buscadorMinucioso.buscarCodigoTitulo(titulos[tituloActual]);
            
            if(codigoEspecialidad!=0){
                creador.crearEspecialidadMedico(false, codigoMedico, codigoMedico);        
            }else{
                procesoCorrecto=false;
            }
        }                
        return procesoCorrecto;
    }/*terminado*/
    
    public int crearLaboratorista(String nombre, String registroMS, String exmamenAsignado, String fechaInicio, String correo, String contrasenia, String telefono, String DPI, String[] diasTRabajo){//estos son los días en los que se encuentra en el hospital...
        String crear ="INSERT INTO Laboratorista (nombre, registroMS, codigoExamenAsignado, fechaIncorporacion, DatosPersonales) VALUES (?,?,?,?,?)";
         int codigoDatosPersonales= creador.crearDatosPersonales(false, correo, contrasenia, telefono, DPI);                   
         int codigoLaboratorista=0;
        
        if(codigoDatosPersonales>0){
            try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){ 
                java.sql.Date fecha=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaInicio).getTime());//si se atrapa el error
                int codigoExamen = Integer.parseInt(exmamenAsignado);                                            
                
                instruccion.setString(1, nombre);
                instruccion.setString(2, registroMS);                        
                instruccion.setInt(3, codigoExamen);
                instruccion.setDate(4, fecha); 
                instruccion.setInt(5, codigoDatosPersonales);                
            
                ResultSet resultado= instruccion.executeQuery();     
                codigoLaboratorista = resultado.getInt(1);
                
                creador.crearHorarioLaboratorista(false, codigoLaboratorista, herramienta.darDiasTrabajoLaboratorista(herramienta.darDiasTrabajoLaboratoristas(diasTRabajo)));
            }catch(NumberFormatException | SQLException e){
                System.out.println("surgió un error al crear al laboratorista -> "+e.getMessage());
                codigoLaboratorista=0;
            }               
        }
        return codigoLaboratorista;
    }/*terminado*/
    
   public int crearPaciente(String nombre, String sexo, String birth, String peso, String sangre, String correo, String contrasenia, String telefono, String DPI){
       String crear ="INSERT INTO Paciente (nombre, sexo, birth, peso, tipoSangre, datosPersonales) VALUES (?,?,?,?,?,?)";
       int codigoDatosPersonales = creador.crearDatosPersonales(false, correo, contrasenia, telefono, DPI);
       int codigoPaciente=0;
   
       if(codigoDatosPersonales!=0){
           try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){                
                java.sql.Date fechaNacimiento =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(birth).getTime());//así cuando lanze un null la conversión str->utilD me llevará al catch u n ose habrán dejado alguno scampos con info y otros sin nada...                
                                
                instruccion.setString(1, nombre);
                instruccion.setString(2, sexo);
                instruccion.setDate(3, fechaNacimiento);
                instruccion.setString(4, peso);
                instruccion.setString(5, sangre);
                instruccion.setInt(6, codigoDatosPersonales);  
                
                instruccion.executeUpdate();               
           }catch(NumberFormatException | SQLException e){
               System.out.println("surgio un error al crear al paciente -> "+e.getMessage());
               codigoPaciente=0;
           }           
       }
       return codigoPaciente;
   }/*terminado*/
   
   public int crearExamen(String nombre, String requiereOrden, String descripcion, String costo, String tipoExtensionResultado){  
     String crear ="INSERT INTO Paciente (nombre, requiereOrden, descripcion, costo, tipoExtensionResultado) VALUES (?,?,?,?,?)";
     int codigoExamen=0;
       
     try(PreparedStatement instruccion = conexion.prepareStatement(crear)){                                    
            instruccion.setString(2, nombre);
            instruccion.setString(3, requiereOrden);
            instruccion.setNString(4, descripcion);//supongo que esto será útil para un tinyChar...
            instruccion.setString(5, costo);
            instruccion.setString(6, tipoExtensionResultado);       
            
            ResultSet resultado = instruccion.executeQuery();
            codigoExamen = resultado.getInt(1);            
             
        }catch(Exception e){            
            System.out.println("error al crear examen"+ e.getMessage());
            codigoExamen =0;
        }             
     return codigoExamen;
   }/*terminado*/
   
   //la creación de la consutla no es necesaria, puessto que es NO TIENE VARIACIÓN, sin importar porqué o quien la cree...
}
