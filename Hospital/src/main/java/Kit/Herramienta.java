/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author phily
 */
public class Herramienta implements Serializable{//aquí iran las herramientas que no encajen con las clases, y sean necesarias...
    int[] ultimosIDCreados;//al iniciar el programa este obtendrá sus valores por medio del método de manejadorArch con el cual se leen las líneas de los ID... para la primera ejecució, solo será necesario para ExAt, puesto que los demás son brindados por el XML...
    static final String contraseniaMaestra = "proyecto2_Hospital";
               
    public String encriptarContrasenia(String contrasenia){
        String encriptada=null;        
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(contraseniaMaestra.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = contrasenia.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.getEncoder().encode(buf);
            encriptada = new String(base64Bytes);            
        } catch (Exception ex) {
            System.out.println("surgió un error al cifrar\nla contrasenia\n"+ex.getMessage());
            encriptada = null;
        }
        return encriptada;
    }              
    
    public String desencriptarContrasenia(String encriptada){
        String desencriptada=null;        
        
        try {
            byte[] message = Base64.getDecoder().decode(encriptada.getBytes("utf-8"));            
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(contraseniaMaestra.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            desencriptada = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            System.out.println("surgió un error al descifrar\nla contrasenia\n"+ex.getMessage());
            desencriptada = null;
        }
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
            return "HomePaciente.jsp";//El perfil del paciente :v xD
        }if(tipoUsuario.equals("Medico")){
            return "HomeMedico.jsp";
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
            case"Reportes":
                return "ReportesAdministrador.jsp";            
        }        
      }        
            return "PerfilAdministrador.jsp";
    }
    
    public String darPaginaReporteAlAdmin(String tipo){
        if(tipo!=null){
            switch(tipo){                
                case "ProductividadMedica":
                    return "ProductividadMedica.jsp";                
                case "DemandaExamenes":
                    return "DemandaExamenes.jsp";                
                case "SolicitudExamenes":
                    return "SolicitudExamenes.jsp";                
                case "IngresosMedico":
                    return "IngresosPorMedico.jsp";
            }                                    
        }        
        return "IngresosPorPaciente.jsp";
    }
    
    public String darPaginaAlPaciente(String tipo){
      if(tipo!=null){
        switch(tipo){
            case "AgendarCitaMedica":
                return "AgendarCitaMedica.jsp";                        
            case"AgendarExamen":
                 return "AgendarExamen.jsp";                        
            case"VerHistorial":
                 return "HistorialClinico.jsp";                                    
        }        
      }        
       return "PerfilPaciente.jsp";
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
    
    public int[] darElMayor(int numero1, int numero2){
        int resultado[] = {1, numero1};        
        
        if(numero2>numero1){
            resultado[0]= 2;
            resultado[1]= numero2;                        
        }                       
        return resultado;//Entonces puede que devuelva 1 si son ==
    }
    
}
