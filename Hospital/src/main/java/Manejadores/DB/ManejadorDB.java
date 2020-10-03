/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author phily
 */
public class ManejadorDB implements Serializable{
    
    private static Connection conexion;//por el patrón SINGLENTON, solo podrá existir una versión de esta instacia, mietras esté viva...
    private DataSource dataSource;
    private static ManejadorDB manejadorDB;

    public ManejadorDB(){
        conectarConDB();
    }
    
    public void conectarConDB(){
        try {       
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/HOSPITAL");
                conexion = dataSource.getConnection();
                System.out.println("SI ME METO AQUI :0, pues aún no existo :v xD");
       
        } catch (NamingException exe) {
            System.out.println("error al establecer la fuente de datos");
        } catch (SQLException ex) {
            System.out.println("error al obtener la conexion");
        }
    }
 
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al cerrar la DB -> "+sqlE.getMessage());
        }
    }

    
    public static Connection darConexion(){
       if(manejadorDB==null){
           manejadorDB= new ManejadorDB();
       }        
        return conexion;        
    }    
    
}
/*TE RECUERDAS
    -> de ver lo de las contraseñar, mas que todo de como afecta en el manejo de la DB... y las conexiones... [la exncript es otra cosa, una herramienta...
    -> de ver que fue lo que dijo el inge que consumía más recursos al estar creando y matando a un obj de la DB... creo que era conexión...

*/