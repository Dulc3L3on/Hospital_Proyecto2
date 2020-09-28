/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
*/
                        //además de tener a los métodos con los que crearán los registros hechos por el administrador también irán todos los métodos para creación que independientemente de dónde sean llamados
public class Creacion {//harán lo mismo puesto que la clase que requiere de una pequeña variación del proceso, se encargará de ello... estos son identificacbles puesto que poseen el atrib esCarga, para saber si debe o no mostrar el msje de error
      private Connection conexion;
      private Herramienta herramienta = new Herramienta();
                
      public Creacion(Connection conexionDB){
          conexion = conexionDB;
      } 
      
      public int crearDatosPersonales(boolean esCarga, String correo, String contrasenia, String telefono, String DPI){
        int idDelCreado=0;
        String crear = "INSERT INTO Datos_Personales(?,?,?,?,?)";            
        String contraseniaEncriptada=herramienta.encriptarContrasenia(contrasenia);//por el proceso de encriptacion y desencriptación, asumo que esta valor solo estará mientras se esté creando, puesto que después se procederá a "traducirse" lo que en la DB está...
                
        try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){                       
            
            if(contraseniaEncriptada!=null){//si existe un fallo, solo se enterará de ello el método de encriptación en cuestión, por ello deberé encerrar en un if, para que no se ingresen los datos incompletos o malos a la tabla...
                instruccion.setString(1, correo);
                instruccion.setString(2, contrasenia);//dependiendo de qué devuelva una encriptación la llamada al métodoserá desde aquí o desde afuerita...
                instruccion.setString(3, telefono);
                instruccion.setString(4, DPI);
            
                instruccion.executeUpdate();
            
                ResultSet resultado=instruccion.getGeneratedKeys();
                idDelCreado=resultado.getInt(1);
            }//Aquí no colocarás un else, puesto que no es una tabla exterior, como médico, paciente, labo...                             
            
        }catch(SQLException sqlE){//no debe mostrar nada puesto que es una tabla que depende de otras... [además para la edición, donde prácticamente sería indep, no se emplea este método, así que...                      
            
        }finally{
            return idDelCreado;//tienes que colocar una condición de que si devulve 0, entonces no puede crearse a la entidad correspondiente
        }//media vez devuelve 0, es porque surgió un error...          
    }
      
      
    public boolean crearEspecialidad(boolean esCarga, String nombre){//puesto que es autoincreReal xD
        String crear = "INSERT INTO Especialidad (nombre) VALUES (?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            instruccion.setString(1, nombre);
            
        }catch(SQLException ex){                                               
           return false;
        }
        
        return true;
    }
    
    public boolean crearEspecialidadMedico(boolean esCarga, String codigoMedico, int codigoEspecialidad){
        String crear = "INSERT INTO Especialidad_Medico";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
        
        }catch(SQLException sqlE){
            return false;
        }
        
        return true;
    }
    
    
}
