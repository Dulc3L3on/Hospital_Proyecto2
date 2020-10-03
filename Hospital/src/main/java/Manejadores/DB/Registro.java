/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Registro {
    private static Connection conexion;
    Creacion creador;
    Herramienta herramienta = new Herramienta();
    
//    public Registro(Connection conexionDB){
//        conexion=conexionDB;
//        creador = new Creacion(conexionDB);
//    }
    
    public Registro(){
        conexion = ManejadorDB.darConexion();        
    }
    
    public boolean registrarPaciente(String datos[]){//Este será exclusivamente hecho por y para el cliente...
        int codigoDatosPersonales = creador.crearDatosPersonales(false, datos[7], datos[8], datos[1], datos[3]);//Aquí ya se recibió la contraseña encriptada...
        
        if(codigoDatosPersonales!=0){
            String registrar="INSERT INTO Paciente (nombre, sexo, birth, peso, tipoSangre, datosPersonales) VALUES (?,?,?,?,?)";//nombre, telefono, birth, dpi, sexo, peso, tipoSangre
      
            try(PreparedStatement instruccion = conexion.prepareStatement(registrar)){                
               java.sql.Date birth =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(datos[2]).getTime());
                
                instruccion.setString(1, datos[0]);
                instruccion.setString(2, datos[4]);
                instruccion.setDate(3, birth);
                instruccion.setString(4, datos[5]);
                instruccion.setString(5, datos[6]);
                instruccion.setInt(6, codigoDatosPersonales);
                
                return true;
            }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){
                System.out.println(e.getMessage());
                return false;
            }      
        }
        return false;                               
    }
    
    //de aquí hacia abaj serán realizados por el admin nada más...
    public boolean registrarMedico(){//creo que estos podrían ser iguales que los de la carga de datos, puesto que su código es str... a dif de paciente, examen, resultado, citaMedica, informe [aquí solo nombre a los que fueron pedidos por el documento...]
        return false;
    }
    
     public boolean registrarLaboratorista(String datos[]){//Esto ya es por medio del admin... podría recopilarse campo a campo los datos para que fuese solo 1 método... pero de primero veamos como funciona el de cargar datos y luego se cb a esta clase, para ver si funciona = si si entonces se recogerá campo a campo los datos cuando registre el admin...
        
        String crear = "INSERT INTO Laboratorista (?,?,?,?,?,?)";
        /*int codigoDatosPersonales= creador.crearDatosPersonales(true, correo, contrasenia, telefono, DPI);           
        
        if(codigoDatosPersonales>0){
           try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                java.sql.Date fechaInicio=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime());//si se atrapa el error
                int codigoExamen = Integer.parseInt(codigoExamenAsignado);        
                      
                instruccion.setString(1, codigo);
                instruccion.setString(2, nombre);
                instruccion.setString(3, registroMS);                        
                instruccion.setInt(4, codigoExamen);
                instruccion.setDate(5, fechaInicio);                                
                instruccion.setInt(6, codigoDatosPersonales);                
            
                instruccion.executeUpdate();
                
                if(!creador.crearHorarioLaboratorista(true, codigoExamenAsignado, diasTrabajo)){
                    //llmarás al método para asignar a la lista de errores...
                    return false;
                }
                
                return true;
            }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){ //me están dando ganas de colocar exception, puesto que no especificaré el tipo de error, o sí???
                //mandas a llamar al método para agregar al listado de errores, en este caso sería porque no pudo crearse al labo...
                System.out.println(e.getMessage());//creo que esto...nop, el usario no lo entendería mucho, o sí? o con esto estaría delantando el proceso :O  eso si no xD
            }
        } */       
            //mandas a llamar al método para agregar al listado de errores, en este caso sería porque no pudo crearse al labo...
            return false;                       
    }/*terminado*///solo te recuerdas del manejo de ínidices y del de errores [si se tiene que cb la forma en que se recibe los datos del request [es decir a forma indiv, entonces FIJO habrá un solo método para Med, Labo y admin... sino pues esta bien tb, pues no tnedría inmensas líneas para paráms en la interfaz xD
    
                 
    public boolean registrarAdministrador(){//Este fijo no tiene problemas al cb de la clase de allá para la de acá... pero igual depende de como se recibirán los paráms.. arr o indi..
        return false;
    }
    
    
    
    
}
