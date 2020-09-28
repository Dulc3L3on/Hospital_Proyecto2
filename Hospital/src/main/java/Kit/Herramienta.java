/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author phily
 */
public class Herramienta {//aquí iran las herramientas que no encajen con las clases, y sean necesarias...
    int[] ultimosIDCreados;//al iniciar el programa este obtendrá sus valores por medio del método de manejadorArch con el cual se leen las líneas de los ID... para la primera ejecució, solo será necesario para ExAt, puesto que los demás son brindados por el XML...
    
           
    public String encriptarContrasenia(String contrasenia){
        String contraseniaEncriptada="";
        
        return contraseniaEncriptada;
        
    }
    
    public void autogenerarID(String tipoEntidad){//este parámetro será útil para que puedas saber qué línea es la que te interesa extraer del documento de últimosINdices...
        
    }
    
    
    
    
    public java.sql.Date devolverSQLDate(long fecha){//es long porque al obtner la fecha actual se obtiene en iliseg los cuales son de tipo long [porque son muuuy largos]
        return new java.sql.Date (fecha);               
    }
    
    public java.util.Date convertirStringAUtilDate(String fecha){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            return formatoFecha.parse(fecha);
     
        } catch (ParseException ex) {
            System.out.println("error al convetir la fecha");
        }
        
        return null;
        
    }
    
    public void estblecerUltimosIDnumericos(int[] ultimosID){
        ultimosIDCreados=ultimosID;
    }
    
}
