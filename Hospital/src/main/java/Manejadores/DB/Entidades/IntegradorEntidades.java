/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB.Entidades;

import Documentacion.Consulta;
import Documentacion.Documento;
import Documentacion.Estructura;
import Documentacion.Examen;
import Entidades.Administrador;
import Entidades.Laboratorista;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Extras.DatosPersonales;
import Manejadores.DB.BusquedaGeneral;
import Reservaciones.Cita;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class IntegradorEntidades {
    BusquedaGeneral buscador = new BusquedaGeneral();
        
      public Usuario formarEntidad(ResultSet resultado, String tipo){ 
        Usuario usuario=null;
          
        try {                        
            switch(tipo){
                case "Medico"://la misma estructura que será aplicada a médico aquí en esta clase, la misma tendrán las demás entidades y también los documentos...
                    usuario = formarMedico(buscador.buscarDatosPersonales(resultado.getString(6)), null, resultado);//falta crear el método para crear los títulos... es de búsqueda general...
                    break;
                    
                case "Laboratorista":
                    usuario = formarLaboratorista(buscador.buscarDatosPersonales(resultado.getString(5)), null, resultado);
                    break;
                    
                case "Paciente":
                    usuario = formarPaciente(buscador.buscarDatosPersonales(resultado.getString(7)), resultado);
                    break;
                    
                case "Administrador":
                    usuario = formarAdministrador(new DatosPersonales(resultado.getString(3), null, resultado.getString(4), null ), resultado);
                    break;
            }                                
        } catch (SQLException ex) {
            usuario = null;
        }       
        return usuario;
    }/*terminado*/      

    private Medico formarMedico(DatosPersonales datosPersonales, String[] titulos, ResultSet resultado){
        Medico medico=null;//puest si sale bien la cosa se sutituirá su val, sino pues volverá a ser nulo...
        
        if(datosPersonales!=null && titulos!=null){
            try{
                medico = new Medico(resultado.getString(1), resultado.getString(2), resultado.getString(3), 
                      resultado.getInt(4), resultado.getInt(5), datosPersonales, resultado.getDate(7), titulos);
            }catch(SQLException sqlE){  
                System.out.println("surgió un error al formar al médico -> "+ sqlE.getMessage());
                medico=null;//pues de esta manera se podrá omitir esa malformación... en el método que requiere de este dato...
            }            
        }        
        return medico;
    }/*terminado*/
    
    private Laboratorista formarLaboratorista(DatosPersonales datosPersonales, boolean[] horario, ResultSet resultado){
        Laboratorista laboratorista = null;
        
        if(datosPersonales!=null && horario!=null){
            try{
                laboratorista = new Laboratorista(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getInt(4), resultado.getDate(5), datosPersonales, horario);
            }catch(SQLException sqlE){
                System.out.println("surió un error al formar al laboratorista -> " + sqlE.getMessage());
                laboratorista= null;
            }
        }                
        return laboratorista;
    }/*terminado*/
   
   private Paciente formarPaciente(DatosPersonales datosPersonales, ResultSet resultado){
       Paciente paciente = null;
       
       if(datosPersonales!=null){
           try{
               paciente = new Paciente(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getDate(4), resultado.getString(5), resultado.getString(6), datosPersonales);
           }catch(SQLException sqlE){
               System.out.println("srgió un error al formar al paciente -> "+ sqlE.getMessage());
               paciente = null;
           }          
       }       
       return paciente;
   }/*terminado*/
   
   private Administrador formarAdministrador(DatosPersonales datosPersonales, ResultSet resultado){
       Administrador administrador=null;
       
       if(datosPersonales!=null){
           try{
               administrador = new Administrador(resultado.getString(1), resultado.getString(2), datosPersonales);
           }catch(SQLException sqlE){
               System.out.println("surgió un error al formar al administrador -> "+ sqlE.getMessage());//aunque este método creo que no se empleará... ya que no te piden crear a un administrador... si te da timepo lo haces...
               administrador = null;
           }           
       }       
       return administrador;       
   }/*terminado*/
   
   public Estructura formarEstructura(ResultSet resultado, String tipo){
       Estructura estructura = null;
       
       switch(tipo){
           case "Examen":
               estructura = formarExamen(resultado);
           break;
           
           case "Consulta":
               estructura = formarConsulta(resultado);
           break;
       }       
       return estructura;       
   }/*terminado*/
   
   private Examen formarExamen(ResultSet resultado){
        Examen examen = null;
        
        try{
            examen = new Examen(resultado.getInt(1), resultado.getString(2), Boolean.parseBoolean(resultado.getString(3)), resultado.getString(4), resultado.getString(5), resultado.getString(6));//aquí no m epreocupo por la conversión, puesto que yo soy quien manda los datos así que... xD
        }catch(SQLException sqlE){
            System.out.println("surgió un error al formar el examen -> "+ sqlE.getMessage());
            examen = null;
        }        
        return examen;        
    }/*terminado*/
   
    private Consulta formarConsulta(ResultSet resultado){
        Consulta consulta = null;
        
        try{
            consulta = new Consulta(resultado.getString(1), resultado.getString(2));
        }catch(SQLException sqlE){
            System.out.println("surgió un error al formar la consulta -> "+sqlE.getMessage());
            consulta = null;
        }
        return consulta;
    }/*terminado*/
   
    public Documento formarDocumento(ResultSet resultado, String tipo){
        Documento documento = null;
        
        switch(tipo){
            case "Informe"://estos ya son exlucsiovos del doc... [pues el admin de estos solo reqiere del número...
            break;
            
            case "Orden":
            break;            
        }                   
        return documento;//ya sabes del otro lado deberás convertir y además verificar que no sea null, si lo es entonces... lo que harás para no complicarte es no dejar espacios en blanco así fijo podrás enviar los datos con el SET_ATTRIBUTE sin confusión alguna... además de aviasar que no todo lo solicitado pudo recuperarse bien...
    }
          
    public Cita formarCita(ResultSet resultado, String tipo){
        Cita cita = null;
        
        switch(tipo){
            case "CitaMedica":
            break;
            
            case "CitaExamen":
            break;
        }
        
        return cita;
    }
    
  
    
}
