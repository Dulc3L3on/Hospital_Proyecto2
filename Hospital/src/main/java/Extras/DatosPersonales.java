/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

/**
 *
 * @author phily
 */
public class DatosPersonales {  
    private int codigo;//esto para cuando se realicen las actualizaciones... o aún eliminaciones...
    private String correo;
    private String contrasenia;
    private String telefono;
    private String DPI;
    
    public DatosPersonales(String email, String password, String numeroTelefono, String elDPI){
        DPI = elDPI;
        correo = email;
        contrasenia = password;
        telefono = numeroTelefono;
    }
    
    public void reestablecerCorreo(String nuevoCorreo){
        correo = nuevoCorreo;
    }
    
    public void reestablecerPassword(String nuevaContrasenia){
        contrasenia = nuevaContrasenia;
    }
    
    public void reestablecerTelefono(String nuevoTelefono){
        telefono = nuevoTelefono;
    }    

    public int darCodigo(){
        return codigo;
    }
    
    public String darCorreo(){
        return correo;
    }
    
    public String darContrasenia(){
        return contrasenia;
    }
    
    public String darTelefono(){
        return telefono;
    }
    
    public String darDPI(){
        return DPI;
    }            
}
