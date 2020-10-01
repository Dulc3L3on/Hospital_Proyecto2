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
    
    private Connection conexion;//se encarga de conectar o desconectar al usuario actual con la DB para interactuar con las estructuras poseedoras de la información
    private DataSource dataSource;
    
    private final String URL_BASEDEDATOS="jdbc:mysql://localhost:3306/HOSPITAL?useSSL=false";
    private final String NOMBRE_USUARIO="root";
    private final String CONTRASENIA="adminL3on@";//ahi lo revisas mañana xD       
    
    public ManejadorDB(){
        conectarConDB();
    }
    
    public void conectarConDB(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/HOSPITAL");
            conexion = dataSource.getConnection();
            
            System.out.println("SI ME METO AQUI :0");
        } catch (NamingException exe) {
            System.out.println("error al establecer la fuente de datos");
        } catch (SQLException ex) {
            System.out.println("error al obtener la conexion");
        }
    }
    
   /* public void conectarConDB(){
        try{
            conexion = DriverManager.getConnection(URL_BASEDEDATOS, NOMBRE_USUARIO, CONTRASENIA);                      
        }catch(SQLException sqlE){
            System.out.println("error al conectar con la DB: "+ sqlE.getMessage());
        }
       
        System.out.println("");
    }*/
    
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al cerrar la DB -> "+sqlE.getMessage());
        }
    }
    
//    public DataSource darDataSource(){
//        return dataSource;
//    }
    
    public Connection darConexion(){
        return conexion;        
    }
    
    
}
/*TE RECUERDAS
    -> de ver lo de las contraseñar, mas que todo de como afecta en el manejo de la DB... y las conexiones... [la exncript es otra cosa, una herramienta...
    -> de ver que fue lo que dijo el inge que consumía más recursos al estar creando y matando a un obj de la DB... creo que era conexión...

*/