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
        String contraseniaEncriptada=null;
        
        try {
            MessageDigest digestor = MessageDigest.getInstance("MD5");
            byte[] bitesEncriptados = digestor.digest(contrasenia.getBytes());
            BigInteger superEntero = new BigInteger(1, bitesEncriptados);//hemos convertido a un hash MD5 :3 xD
            
            contraseniaEncriptada = superEntero.toString(16);
            
            while(contraseniaEncriptada.length()<32){//Se rellenan espacios faltantes :0 xD
                contraseniaEncriptada = "0"+contraseniaEncriptada;
            }                       
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("surió un error con el algoritmo de encriptacion");
        }catch(Exception e){
            System.out.println("surgio un error al encriptar la contrasenia");
        }        
        return contraseniaEncriptada;        
    }     
    
    public String desencriptarContrasenia(String encriptada){
        String desencriptada=":3";
        
        return desencriptada;
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
            return "HomeAdministrador.jsp";
        }
        
        return "";//aquí la página del laboratirista...
    }
    
    public String darPaginaAlAdministrador(String tipo){
      if(tipo!=null){
        switch(tipo){
            case "Medico":
                return "CustomizacionMedico.jsp";                        
            case"Laboratorista":
                 return "CustomizacionLaboratorista.jsp";                        
            case"Examen":
                 return "CustomizacionExamen.jsp";            
            case"Consulta":
                 return "CustomizacionConsulta.jsp";            
            case"Paciente":
                return "CustomizacionPaciente.jsp";                   
        }        
      }        
            return "perfilAdministrador.jsp";
    }
    
    public boolean esRedundante(ListaEnlazada<String> listaAComparar, String comparado){
        Nodo<String> nodoAuxiliar = listaAComparar.obtnerPrimerNodo();
        
        for (int elementoActual = 1; elementoActual < listaAComparar.darTamanio(); elementoActual++) {
            if(comparado.equals(nodoAuxiliar.contenido)){
                return true;
            }
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }        
        return false;
    }
    
    /**
     * Convierte el listado de días específicos en lso que trabaja
     * en un horario con los días de labor, marcados...
     * @param diasTrabajo
     * @return
     */
    public boolean[] darDiasTrabajoLaboratoristas(String[] diasTrabajo){//este arreglo solo posee los días en los que trabaja... [el cual puede obtenerse desde el XML y de la interfaz... de la DB se obtienen los 7 días, marcando aquellos en los que se encuentra de turno...]
        boolean[] diasDeTRabajo = new boolean[diasTrabajo.length];
        String[] dias = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
        
        for (int diaARevisar = 0; diaARevisar < diasTrabajo.length; diaARevisar++) {
            for (int dia = 0; dia < dias.length; dia++) {
                if(diasTrabajo[diaARevisar].equalsIgnoreCase(dias[dia])){
                    diasDeTRabajo[dia]=true;//pues todos los espacios están en false, por defecto...
                }
            }                    
        }        
        return diasDeTRabajo;
    }
    
    public String[] darDiasTrabajoLaboratorista(boolean[] diasTrabajo){
        String[] diasDeTrabajo = new String[7];
        
        for (int dia = 0; dia < diasDeTrabajo.length; dia++) {//talvez agreguen otro día a la semana :v xD
            diasDeTrabajo[dia]= String.valueOf(diasTrabajo[dia]);
        }        
        return diasDeTrabajo;//fijo fijo no surge error alguno... pues los vals los paso yopi xD 
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
