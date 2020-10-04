/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import java.sql.Date;

/**
 *
 * @author phily
 */
public class Paciente extends Usuario{    
    private String genero;
    private Date birth;
    private String peso;
    private String tipoSangre;   
    
    public Paciente(int elCodigo, String nombre, String sexo, Date cumpleanios, String weight, String tipoDeSangre, DatosPersonales datosPersonales){
        super(elCodigo, nombre, datosPersonales);
        
        genero = sexo;
        birth = cumpleanios;
        peso = weight;
        tipoSangre = tipoDeSangre;
    }
    
     public void reestablecerDatosPersonales(DatosPersonales nuevosDatosPersonales, String nuevoPeso, String tipoSangreCorrecto){//pues la únicac manera en la que se debería cambiar sería por una confusión...
        datosPersonales = nuevosDatosPersonales;
        peso = nuevoPeso;
        tipoSangre = tipoSangreCorrecto;
    }
     
    @Override
     public void verReportes(String tipoReporte){//aquí deplano que se colocará un switch, para que convergan todos los métodos que implemente la entidad en cuestión...
         //por supuestisimo xD aquí deberán etar las implementaciones correspondientes a las que el paciente deba hacer... :) ya sabes... son los reportes que te solicita el doc...         
    }     
    
    
}
