/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import Kit.ListaEnlazada;
import Manejadores.DB.BusquedaGeneral;
import Manejadores.DB.Creacion;
import Manejadores.DB.CreacionAdministrada;
import Manejadores.DB.Modificacion;
import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Administrador extends Usuario{            
    BusquedaGeneral buscador = new BusquedaGeneral();
    Modificacion modificador = new Modificacion();
    CreacionAdministrada creacion = new CreacionAdministrada();
    Creacion creador = new Creacion();
    
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

    
    public void modificarMedico(int codigo, String nombre, String contrasenia, String numeroColegiado, String DPI, 
      String telefono, String correo, Date fechaIncorporacion, int horaInicio, int horaFin, ListaEnlazada<String> titulosAntiguos, String[] nuevosTitulos){//mando el valor como es, puesto que no hay pierde... ya que cada cajita tiene sus especificaciones
    
        if(!modificador.modificarDatosPersonales(codigo, correo, contrasenia, telefono, DPI) || 
          !modificador.modificarMedico(codigo, nombre, numeroColegiado, horaInicio, horaFin, fechaIncorporacion)
          || !modificador.modificarTitulario(codigo, titulosAntiguos, nuevosTitulos)){
            JOptionPane.showMessageDialog(null,"", "Sucedió un error en la creacion\nbusque y revise la\ninformacion guardada", JOptionPane.ERROR_MESSAGE);
        }       
    }//solo falta revisar lo de las sesiones para reemplazar lo que está en el JSP...
    
    public void crearMedico(String nombre, String colegiado, String horaInicio, String horaFin, String fechaIncorporacion, String correo, String contrasenia, String telefono, String DPI, String[] titulos){
        int codigoMedico=creacion.crearMedico(nombre, colegiado, horaInicio, horaFin, horaInicio, correo, contrasenia, telefono, DPI);
        
        if(codigoMedico!=0){
            creacion.crearTitulos(codigoMedico, titulos);
        }else{
            JOptionPane.showMessageDialog(null, "", "Surgió un problema en la\ncreación del médico\nrevise los datos", JOptionPane.ERROR_MESSAGE);
        }
    }//solo falta llevarlo a la interfaz...
    
    public void modificarLaboratorista(int codigo, String correo, String contrasenia, String telefono, String DPI, String nombreLaboratorista, String registroMS,
      int codigoExamen, Date fechaIncorporacion, boolean[] diasTrabajo){
        if(modificador.modificarDatosPersonales(codigo, correo, contrasenia, telefono, DPI) ||
            modificador.modificarLaboratorista(codigoExamen, nombreLaboratorista, registroMS, codigoExamen, fechaIncorporacion)
            || modificador.modificarHorarioLaboratorista(codigoExamen, diasTrabajo)){
        
            JOptionPane.showMessageDialog(null, "", "sucedio un error en la modificacion\ndel laboratorista corrobore los\ndatos guardados", codigoExamen);
        }        
    }
    
    public void crearLaboratorista(String nombre, String registroMS, String codigoExamen, String fechaIncorporacion, String correo, String contrasenia, String telefono, String DPI, String[] diasTrabajo){
        int codigoLaboratorista=creacion.crearLaboratorista(nombre, registroMS, telefono, telefono, correo, contrasenia, telefono, DPI, diasTrabajo);
            
        if(codigoLaboratorista!=0){
            creador.crearHorarioLaboratorista(false, codigoLaboratorista, diasTrabajo);
        }    
    }//de igual forma a estos 2 solo les hace falta ser incorporados en la interfaz...
}
