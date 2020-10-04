/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import Manejadores.DB.BusquedaGeneral;

/**
 *
 * @author phily
 */
public class Administrador extends Usuario{            
    BusquedaGeneral buscador = new BusquedaGeneral();
    
    public Administrador(int elCodigo, String elNombre, DatosPersonales datosPersonales){
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
    
//    public void buscar(String buscado){
//        if(!buscado.equals("Examen") && !buscado.equals("Consulta")){
//            buscador.
//        }
//        
//        
//    }//esto deplano que mañana, porque será necesario tener la interfaz para saber al final como se hará para manejar la infor dada...
    
    /**
     *Método llamado al ser seleccionada una opcion del grupo de entidades
     *de los radioBUttons
     * @param tipoAModificar
     */
    public void modificarEntidad(String tipoAModificar){
        switch(tipoAModificar){
            case "Medico":
            break;
            
            case "Laboratorista":
            break;
            
            case "Paciente":
            break;
            
            case "Administrador":
            break;
        }//igial mañana hay que ver esto...
        
    }//a mi parrecer este método deberia estar en modificaciones... aśi como con la búsuqueda...
    
}
