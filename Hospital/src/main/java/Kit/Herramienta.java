/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        
        try {
            MessageDigest digestor = MessageDigest.getInstance("MD5");
            byte[] bitesEncriptados = digestor.digest(contrasenia.getBytes());
            BigInteger superEntero = new BigInteger(1, bitesEncriptados);//hemos convertido a un hash MD5 :3 xD
            
            String cadenaEncriptada = superEntero.toString(16);
            
            while(cadenaEncriptada.length()<32){//Se rellenan espacios faltantes :0 xD
                cadenaEncriptada = "0"+cadenaEncriptada;
            }                       
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("surió un error con el algoritmo de encriptacion");
        }catch(Exception e){
            System.out.println("surgio un error al encriptar la contrasenia");
        }
        
        return contraseniaEncriptada;        
    }     
    
    public int extraerParteNumerica(int inicioExtraccion, String cadena){
        String parteExtraida = cadena.substring(inicioExtraccion);
        int parteNumerica=0;
        
        try{
            parteNumerica = Integer.parseInt(parteExtraida);
        }catch(NumberFormatException e){
            System.out.println("La parte extraida no puede ser convertida a un número");
        }        
        
        return parteNumerica ;                  
    }
    
    public String darPaginaPerfil(String tipoUsuario){
        if(tipoUsuario.equals("Paciente")){
            return "";//El perfil del paciente :v xD
        }if(tipoUsuario.equals("Medico")){
            return "";
        }if(tipoUsuario.equals("Administrador")){
            return "";
        }
        
        return "";//aquí la página del laboratirista...
    }
    
    public boolean[] darDiasTrabajoLaboratoristas(String[] diasTrabajo){
        boolean[] diasDeTRabajo = new boolean[diasTrabajo.length];
        String[] dias = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
        
        for (int dia = 0; dia < diasTrabajo.length; dia++) {
            if(diasTrabajo[dia].equalsIgnoreCase(dias[dia])){
                diasDeTRabajo[dia]=false;
            }
        }
        
        return diasDeTRabajo;
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
