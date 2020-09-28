/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Archivos;

import Kit.Herramienta;
import Manejadores.DB.CreadorDeCarga;
import java.sql.Connection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author phily
 */
public class CargaDeDatos {
    CreadorDeCarga creadorRegistros;
    Herramienta herramienta = new Herramienta();      
    
    public CargaDeDatos(Connection conexionDB){
         creadorRegistros = new CreadorDeCarga(conexionDB);
    } 
    
    public void clasificar(String categoria, Document documento){//el XML está formado por raiz [hospital], categorias [admin, labo, pac...] y atribitos                          
       leerAdministrador(documento.getElementsByTagName("admin"));//con esto se obtiene a todos los nodos que correspondan a la categoría "admin" en este caso...                                 
       leerPaciente(documento.getElementsByTagName("paciente"));//de esta manera no importará en qué orden puedan venir, puesto que YO soy quien los llama según el orden que requiero...                               
       leerMedico(documento);
       leerExamen(documento.getElementsByTagName("examen"));    
       leerLaboratorista(documento.getElementsByTagName("laboratorista"));                                                                                                                
       leerInforme(documento.getElementsByTagName("reporte"));//informe médico   
       
    }
    
    //toma en cuenta que cada uno de estos métodos emplearán la misma técnica para leer cada una de las etiquetas, pero por la manera de guardarlas en la DB, habrán varias composiciones xD
    private void leerAdministrador(NodeList Administradores){//lo cual viene a ser los nodos, p.ej -> doctor, examen....        
        if(Administradores!=null){
            for(int administradorAcual=0; administradorAcual < Administradores.getLength(); administradorAcual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node administrador = Administradores.item(administradorAcual);//que aquí lo nombraron como item xD
            
                if(administrador.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) administrador;                
                
                    creadorRegistros.crearAdministrador(true, elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), 
                            elemento.getAttribute("DPI"), elemento.getAttribute("PASSWORD"));//no olvides que todo lo devuelve como un string...
                }                        
            }                
        }
        
    }//Y ASÍ XD, ESTE ES EL PROCESO GENERAL QUE DEBERÁS SEGUIR, A LO CUAL LE AGREGARÁS LAS PERSONALIZACIONES, ES DECIR cuando requiere una op extra, pues requiere crear otra tabla, o porque el atributo en la DB es de diferente tipo... pero lo que tienes ahorita, es lo general xD  
    
     public void leerMedico(Document xml){        
        if(xml!=null){
            String titulos[];
            NodeList listaMedicos = xml.getElementsByTagName("doctor");//que aquí lo nombraron como item xD
                
            for(int numeroMedico=0; numeroMedico < listaMedicos.getLength(); numeroMedico++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...[por eje laboratorista...]
                Node medico = listaMedicos.item(numeroMedico);
                NodeList especialidades = xml.getElementsByTagName("ESPECIALIDAD").item(0).getChildNodes();//de esta manera enuentro las especialidades por médico... es decir el listado de todas las etiquetas título que dentro de especialidad estén [puesto que ellos serían sus hijitos...]
                NodeList horas = xml.getElementsByTagName("HORARIO").item(0).getChildNodes();//a partir de esto podría hacer 1 de 2; hacer exactamente lo mismo que con médico [puesto que aquí tengo mi equivalente al listado de médicos [horario...], es decir luego de ubicarme en 1 médico en particular [en este caso horario, el cual es único...] obtner verificar que sea un elemento y si es asíhacer la conversión a ese tipo para luego obtener los atrib, en este caso horaINi, horaFin Ó lo mismísimo que especialidad, es decir obtner el listado de los hijos y luego el valor de sus nodos y como FIJO son 2, entonces no se requiere de un ciclo...
                   
                titulos = new String[especialidades.getLength()];//según el número de títulos que posea [min 1]
                   
                for (int tituloActual = 0; tituloActual < especialidades.getLength(); tituloActual++) {
                    Node especialidad = especialidades.item(tituloActual);//me ubico en una etiqueta título en particular...                                      
                        
                        titulos[tituloActual] = especialidad.getNodeValue();//obtengo el nombre del título [p.ej pediatría]
                }//fin del for por medio del cual obtengo el listado de títulos del médico en cuestión...                      
                
                if(medico.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) medico;                                                    
                    
                   creadorRegistros.crearMedico(true, elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), 
                   elemento.getAttribute("COLEGIADO"), elemento.getAttribute("DPI"), elemento.getAttribute("TELEFONO"),titulos,
                   elemento.getAttribute("CORREO"), Integer.parseInt(horas.item(0).getNodeValue()), Integer.parseInt(horas.item(0).getNodeValue()),//recuerda que debes averiguar como es que se obtienen los datos de las etiquetas anidadas...
                   elemento.getAttribute("TRABAJO"), elemento.getAttribute("PASSWORD"));
               }//aún no está incluido lo de las especialidades, puesto que no sabes exactametne como es que devulve los datos, ó como es que se obtiene el arreglo de dichos títulos, en String!!!                        
            }//fin del for exterior                                              
                
            
        }//fin del if que evita excepciones por no existir la categoría en el archivo dado...                                                   
    }
    
    private void leerLaboratorista(NodeList categoria){
    
    }   
    
    private void leerPaciente(NodeList categoria){
    
    }
    
    private void leerExamen(NodeList categoria){
    
    }
    
    private void leerInforme(NodeList categoria){
    
    }
    
    private void leerResultado(NodeList categoria){
    
    }
    
    private void leerCita(NodeList categoria){
    
    }
    
    private void leerConsulta(NodeList categoria){
    
    }
 
    //para realizar las comparaciones en la lista de especialidades, deberás emplear el ignore case, puesto que solo eso podría cb entre c/u de dichos titulos...
    
}
