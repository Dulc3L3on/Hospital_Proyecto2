/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import java.io.Serializable;
import Extras.Reporte;
import Kit.ListaEnlazada;
import Manejadores.DB.Registro;
import Manejadores.DB.Reportes.ReportePaciente;
import Reservaciones.CitaMedica;
import java.sql.Date;

/**
 *
 * @author phily
 */
public class Paciente extends Usuario implements Serializable{    
    private String genero;
    private Date birth;
    private String peso;
    private String tipoSangre;     
    private ReportePaciente reportes = new ReportePaciente();
    private Registro registro = new Registro();
    
    public Paciente(){
    
    }
    
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
 
    public boolean agendarCita(CitaMedica citaAAgendar){
        return registro.agendarCitaMedica(citaAAgendar);
    }//Creo que tendrá que quedarse en la clase de registro solamente...     
    
    
    
    public Reporte[] ver5Recientes(String tipo){//Este lo recibirás de la seelcción del select del menú...
        if(tipo.equals("Consulta")){
            return verReporte5Consultas();
        }if(tipo.equals("Examen")){
            return verReporte5Examenes();
        }
            return null;//de tal manera que cuando llegue al for no exe nada... y así se eviten problemas...
    }
    
    private Reporte[] verReporte5Consultas(){
        return reportes.ultimas5Consultas(codigo);
    }
    
    private Reporte[] verReporte5Examenes(){
        return reportes.ultimos5Examenes(codigo);
    }
    
    public ListaEnlazada<Reporte> verCitasEspecificas(String tipo, int codigoRequerido, String desde, String hasta){
        if(tipo.equals("Consulta")){
           verConsultasEspecificas(codigoRequerido, desde, hasta);
        }
        if(tipo.equals("Examen")){
            verExamenesEspecificos(codigoRequerido, desde, hasta);
        }
        return new ListaEnlazada<>();
    }
    
    private ListaEnlazada<Reporte> verConsultasEspecificas(int codigoMedico, String desde, String hasta){
        return reportes.consultasRealizadasConMedicoEspecifico(codigoMedico, codigo, desde, hasta);    
    }//recuerda que si la búsqueda falló, será porque 
    
    private ListaEnlazada<Reporte> verExamenesEspecificos(int codigoExamen, String desde, String hasta){
        return reportes.examenesDeUnTipoRealizados(codigoExamen, codigo, desde, hasta);
    }
    
     public String darGenero(){
         return genero;
     }
     
     public Date darBirth(){
         return birth;
     }
     
     public String darPeso(){
         return peso;
     }
     
     public String darTipoSangre(){
         return tipoSangre;
     }
     
    
}
