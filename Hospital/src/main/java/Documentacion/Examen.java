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
public class Examen extends Estructura{
    private int codigo;
    private boolean requiereOrden;
    private String descripcion;    
    private String tipoExtensionResultado;
    
    public Examen(int elCodigo, String nombre, boolean requerimientoOrden, String laDescripcion, String elCosto, String extensionResultado){
        super(nombre, elCosto);
        codigo=elCodigo;
        requiereOrden = requerimientoOrden;
        descripcion = laDescripcion;        
        tipoExtensionResultado = extensionResultado;
    }
    
    public void redefinirRequerimientoOrden(boolean nuevoRequerimiento){
        requiereOrden = nuevoRequerimiento;        
    }
    
    public void redefinirDescripcion(String nuevaDescripcion){
        descripcion = nuevaDescripcion;
    }
  
    public void redefinirExtension(String nuevaExtension){
        tipoExtensionResultado = nuevaExtension;
    }
    
    public int darCodigo(){
        return codigo;
    }
    
    public boolean darRequerimiento(){
        return requiereOrden;
    }
    
    public String darDescripcion(){
        return descripcion;
    }
    
    public String darExtensionResultado(){
        return tipoExtensionResultado;
    }
    
}
