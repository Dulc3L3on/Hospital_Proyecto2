/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phily
 */
public class ManejadorDB {
    
    private Connection conexion;//se encarga de conectar o desconectar al usuario actual con la DB para interactuar con las estructuras poseedoras de la información
    private Statement instrucciones;//se encarga de aquirir las sentencias correctas para cumplir con la solicitud hecha por el cliente a la DB
    private ResultSet resultadosConsulta;
    private ResultSetMetaData metadatosResultado;
    
    private final String URL_BASEDEDATOS="jdbc:mysql://localhost:3306/INTELAF?useSSL=false";
    private final String NOMBRE_USUARIO="root";
    private final String CONTRASENIA="adminL3on@";//ahi lo revisas mañana xD   
    
    
    public void conectarConDB(){
        try{
            conexion = DriverManager.getConnection(URL_BASEDEDATOS, NOMBRE_USUARIO, CONTRASENIA);                      
        }catch(SQLException sqlE){
        
        }
        
    }
    
    public Connection darConexion(){
        return conexion;        
    }
    
    
}
/*TE RECUERDAS
    -> de ver lo de las contraseñar, mas que todo de como afecta en el manejo de la DB... y las conexiones... [la exncript es otra cosa, una herramienta...
    -> de ver que fue lo que dijo el inge que consumía más recursos al estar creando y matando a un obj de la DB... creo que era conexión...

*/