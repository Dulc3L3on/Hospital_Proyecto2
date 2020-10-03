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
public class Administrador extends Usuario{        
    
    public Administrador(String elCodigo, String elNombre, DatosPersonales datosPersonales){
        super(elCodigo, elNombre, datosPersonales);
    }
    
    //RECUERDA: COn respecto a los datos personales, solo le enviarás el DPI y la contrasenia... y no olvides qu eeso no afecta al método del Papá...
    
    @Override
     public void registrarse (){//bueno realemente no... porque olvidaron ponerlo xD
    
    }
    
    @Override
    public void iniciarSesion(){
    
    }
    
    @Override
    public void cerrarSesion (){
        
    
    }
    
    @Override
   public void modificarPerfil(String[] datos){
    
    }
    
    @Override
    public void buscar(String tipoBusqueda){
    
    }
    
    @Override
    public void verReportes(String tipoReporte){//aquí deplano que se colocará un switch, para que convergan todos los métodos que implemente la entidad en cuestión...
    
    }       
    
    public void buscarPacientes(){
    
    }
    
}
