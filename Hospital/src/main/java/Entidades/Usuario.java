/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;

/**
 *
 * @author phily
 */
public class Usuario {        
    public int codigo;
    public String nombre;
    public DatosPersonales datosPersonales;
    
    public Usuario(int elCodigo, String elNombre, DatosPersonales losDatosPersonales){
        codigo = elCodigo;
        nombre= elNombre;        
        datosPersonales = losDatosPersonales;
    }    
    
    public void reestablecerDatosPersonales(DatosPersonales nuevosDatosPersonales){
        datosPersonales = nuevosDatosPersonales;
    }
    
    public void reestablecerNombre(String nuevoNombre){//:v eso no debería poderse... pero en la realidad ya existe :v                
        nombre = nuevoNombre;
    }
    
    public void registrarse (){//vamos a ver si no desaparece, por el proceso que tienes actualmetne para exe dicha axn...
    
    }
    
    public void iniciarSesion(){//propenso a solo ser una invocación al método implementado completamente en otra clase
    
    }
    
    public void cerrarSesion (){//de la misma forma que los 2 anteriores...
        
    
    }
    
    public void modificarPerfil(String[] datos){
    
    }
    
    public void buscar(String tipoBusqueda){
    
    }
    
    public void verReportes(String tipoReporte){//aquí deplano que se colocará un switch, para que convergan todos los métodos que implemente la entidad en cuestión...
    
    }     
    
    public int darCodigo(){
        return codigo;
    }
    
    public String darNombre(){
        return nombre;
    }
    
    public DatosPersonales darDatosaPersonales(){
        return datosPersonales;
    }    
    
}
