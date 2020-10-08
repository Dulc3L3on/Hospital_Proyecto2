/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phily
 */
public class Laboratorista extends Usuario implements Serializable{    
    private String registroMS;
    private int examenAsignado;
    private Date fechaIncorporacion;
    private boolean[] horario;
    
    public Laboratorista(int elCodigo, String elNombre, String elRegistroMS, int examen, Date fechaEntrada, DatosPersonales datosPersonales, boolean[] diasTrabajo){
        super(elCodigo, elNombre, datosPersonales);        
        registroMS = elRegistroMS;
        examenAsignado = examen;
        fechaIncorporacion = fechaEntrada;
        horario= diasTrabajo;        
    }
    
    public void reestablecerCodigoExamenAsignado(int nuevoCodigo){
        examenAsignado= nuevoCodigo;
    }//bueno aqunque esto no me será útil, puesto que esto lo hace el admin y los rrestablecimientos , son de aspectos que la persona dueña de su perfil, puede modificar...
    
    //lo mismo para la fecha... y el horario... es decir todos los atrib que aquí se encuentran...
    
    public void subirResultado(){
    
    }
    
    @Override
      public void verReportes(String tipoReporte){//aquí deplano que se colocará un switch, para que convergan todos los métodos que implemente la entidad en cuestión...
          //aquí harás las llamadas a los métodos de aquí mismo que invocan a los que se encargan de hacer las búsquedas y luego formar los reporetes busacados con los métodos de aquí ó
          //hacer lo mismo que con el admin para buscar a las entidades [users/documentacion...] es decir un método conver luego cada uno de los inidv que llama a los métodos SQL según requiera para devolver el objeto [o arreglo,ya veremos] de interés...
    }     
      
    public String darRegistroMS(){
        return registroMS;
    }
    
    public int darCodigoExamenAsignado(){
        return examenAsignado;
    }
    
    public Date darFechaIncorporacion(){
        return fechaIncorporacion;
    }
    
    public boolean[] darHorario(){
        return horario;
    }
}
