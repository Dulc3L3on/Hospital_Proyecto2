/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documentacion;

/**
 *
 * @author phily
 */
public class Documento {
    int codigo;//solo la consulta no empleará este atrib...
    String nombre;
    
    public Documento(int elCodigo, String elNombre){
        codigo= elCodigo;
        nombre = elNombre;        
    }
    
    public void redefinirNombre(String nuevoNombre){//igual, consulta no implementará esto... creo que no cabe en al defi de doumenteacion... pues es más una acción, se parece más a las citas...
        nombre = nuevoNombre;
    }
    
    //Aquí las demás acciones que con la documentación en general se peude hacer
    
    public int darCodigo(){
        return codigo;
    }
    
    public String darNombre(){
        return nombre;
    }
    
}
