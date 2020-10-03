/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import Kit.ListaEnlazada;
import java.sql.Date;

/**
 *
 * @author phily
 */
public class Medico extends Usuario{
    String codigo;
    String numeroColegiado;    
    ListaEnlazada<String> titulos;//estos se volverán un obj...
    int horaInicio;
    int horaFin;
    Date fechaIncorporacion;//pues no haré nada con ella... y si no, pues entonces la cb a Date xD O A sql Date xD para no hacer tanta conversión xD     
        
    public Medico(String elCodigo, String elNombre, String elNumeroColegiado, int hrInicio, int hrFin, DatosPersonales losDatosPersonales, Date fecha, ListaEnlazada<String> especialidades){
        super( elNombre, losDatosPersonales);
        codigo = elCodigo;
        numeroColegiado=elNumeroColegiado;
        titulos=especialidades;//esto se obtnedrá en el switch... puesto que ada una tiene sus epecilidaddes
        horaInicio=hrInicio;
        horaFin = hrFin;
        fechaIncorporacion= fecha;
    }
    
    public void reestablecerNombre(){
    
    }
    
    public void reestablecerNumeroColegiado(){//yo sabía que no se podía xD
    
    }
    
    public void reestablecerDatosPersonales(){
    
    }            
    
    @Override
    public void registrarse (){//vamos a ver si no desaparece, por el proceso que tienes actualmetne para exe dicha axn...
    
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
    
    public void reagendarCita(){
    
    }
    
    public void agregarInforme(){
    
    }
    
    public void verTrabajo(){
        verTrabajoActual();
        verTrabajoDelDia();
        verTrabajoFuturo();
    }
    
    private void verTrabajoActual(){
    
    }
    
    private void verTrabajoDelDia(){
    
    }
    
    private void verTrabajoFuturo(){
    
    }
    
    public void atenderConsulta(){
    
    }
    
    public void verHistorialPacientes(){//esto se hará llamando el mismo método (o métodos) del paciente
        verConsultasPaciente();
        verExamenesPaciente();        
    }
    
    private void verConsultasPaciente(){
    
    }
    
    private void verExamenesPaciente(){
    
    }
    
    public void subirOrden(){
    
    }
    
    public void solicitarExamen(){
    
    }//esto será llamado cuando en el formulario para describir el examen tenga una solicitud 
    //de examen, lo cual se verá al tener un item de la lista de examenes a solicitar, diferente
    //de -ninguno-
    
    
    
}
