/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Sesion {
    Connection conexion;
    
    public Sesion(Connection conexionDB){
        conexion=conexionDB;
    }
                                                            //aquí ya se recibe la contrasenia encriptada...puesto que en la DB se almacenó de esa manera...
    public String[] loguearAdministrador(String codigo, String contrasenia){//puesto que no le agregaron un correo :v xD entonces que ingrese el código xD
        String loguear="SELECT (nombre, DPI) FROM Administrador WHERE codigo= ? AND contrasenia = ?";        
        String datosAdministrador[] = new String[2];
        
        try(PreparedStatement instruccion = conexion.prepareStatement(loguear)){
            instruccion.setString(1, codigo);
            
            ResultSet resultado = instruccion.executeQuery();
             
            while(resultado.next()){//Aunque la verdad esto no es necesario, porque si app este método es porque sé que no hay más registros que cumplan con lo anterior
                for (int columnaActual = 0; columnaActual < datosAdministrador.length; columnaActual++) {
                    datosAdministrador[columnaActual]=resultado.getString(columnaActual+1);                                
                }//fin del for por medio del cual obtnego los datos de las columnas...                
            }                        
        }catch(SQLException sqlE){
            return null;
        }        
        return datosAdministrador;        
    }//Estos serían los datos que deberían mandarse a la página que corresponde al tipo de usuario... pero aún no se enviar de esa manera objetos...
    
    
    public String[] loguearMedico(String correo, String contrasenia){//a menos que le permita cambiar su contraseña, la extraeré... pero eso implicaría que la tenga que decodificar :1 jajjaja, aún así para la comparación, app el método para codificar en la contra ingresada en el área para loguearse...
        String loguear="SELECT (nombre, numeroColegiado, horaInicio, horaFin, fechaIncorporacion, correo, telefono, DPI, contrasenia) FROM Datos_Personales "
             + "INNER JOIN Medico ON Datos_Personales.codigo = Medico.datosPersonales WHERE correo= ? AND contrasenia = ?";        
        String datosAdministrador[] = new String[2];
        
        try(PreparedStatement instruccion = conexion.prepareStatement(loguear)){
            instruccion.setString(1, correo);
            instruccion.setString(2, contrasenia);
            
            ResultSet resultado = instruccion.executeQuery();
             
            //recuerda que aquí deberás llmar al método para desencriptar la contraseña...
            
            while(resultado.next()){//en lugar de asignar los datos a un arreglo, de una vez lo asignaré al objeto del tipo correspondiente y lo devolveré en su forma más general, para que pueda convergerse en un solo método [es decir usuario...]
                for (int columnaActual = 0; columnaActual < datosAdministrador.length; columnaActual++) {
                    datosAdministrador[columnaActual]=resultado.getString(columnaActual+1);                                
                }//fin del for por medio del cual obtnego los datos de las columnas...                
            }                        
        }catch(SQLException sqlE){
            return null;
        }        
        return datosAdministrador;//Recuerda QUE DEVOVLERÁS UN OBJETO!!! pero por el momento como no está bien defi la herencia, está así con el arreglo...                
    }
    
    
    public String[] loguearPaciente(String correo, String contrasenia){
        String loguear="SELECT (nombre, sexo, birth, tipoSangre, peso, correo, telefono, DPI, contrasenia) FROM Datos_Personales "
             + "INNER JOIN Paciente ON Datos_Personales.codigo = Paciente.datosPersonales WHERE correo= ? AND contrasenia = ?";        
        String datosPaciente[] = new String[2];
        
        try(PreparedStatement instruccion = conexion.prepareStatement(loguear)){
            instruccion.setString(1, correo);
            instruccion.setString(2, contrasenia);
            
            ResultSet resultado = instruccion.executeQuery();
             
            while(resultado.next()){//en lugar de asignar los datos a un arreglo, de una vez lo asignaré al objeto del tipo correspondiente y lo devolveré en su forma más general, para que pueda convergerse en un solo método [es decir usuario...]
                for (int columnaActual = 0; columnaActual < datosPaciente.length; columnaActual++) {
                    datosPaciente[columnaActual]=resultado.getString(columnaActual+1);                                
                }//fin del for por medio del cual obtnego los datos de las columnas...                
            }                        
        }catch(SQLException sqlE){
            return null;
        }        
        return datosPaciente;                          
    }
    
    public String[] loguearLaboratorista(String correo, String contrasenia){//quiero colocar el nombre del examen pero no se si puedo refernciar antes de indicar el INNER JOIN a la tabla que posee dicho atrib...
      String loguear="SELECT (nombre, registroMS, codigoExamenAsignado, fechaIncorporacion, correo, telefono, DPI, contrasenia) FROM Datos_Personales "
             + "INNER JOIN Laboratorista ON Datos_Personales.codigo = Laboratorista.datosPersonales WHERE correo= ? AND contrasenia = ?";        
        String datosPaciente[] = new String[2];
        
        try(PreparedStatement instruccion = conexion.prepareStatement(loguear)){
            instruccion.setString(1, correo);
            instruccion.setString(2, contrasenia);
            
            ResultSet resultado = instruccion.executeQuery();
             
            while(resultado.next()){//en lugar de asignar los datos a un arreglo, de una vez lo asignaré al objeto del tipo correspondiente y lo devolveré en su forma más general, para que pueda convergerse en un solo método [es decir usuario...]
                for (int columnaActual = 0; columnaActual < datosPaciente.length; columnaActual++) {
                    datosPaciente[columnaActual]=resultado.getString(columnaActual+1);                                
                }//fin del for por medio del cual obtnego los datos de las columnas...                
            }                        
        }catch(SQLException sqlE){
            return null;
        }        
        return datosPaciente;  
    }
    
}
