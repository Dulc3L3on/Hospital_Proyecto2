/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Archivos;

import Kit.ControlIndices;
import Kit.Herramienta;
import Manejadores.DB.CreadorDeCarga;
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
    ControlIndices controladorIndices = new ControlIndices();
    
//    public CargaDeDatos(Connection conexionDB){
//         creadorRegistros = new CreadorDeCarga(conexionDB);
//         controladorIndices.recuperarUltimosIndices();
//         creadorRegistros.establecerControladorIndices(controladorIndices);
//    }     

     public void CargaDatos(){
         creadorRegistros = new CreadorDeCarga();
         controladorIndices.recuperarUltimosIndices();
         creadorRegistros.establecerControladorIndices(controladorIndices);
     }
        
    /**
     *Método llamado en al iniciar la aplicación, siempre y cuando
     * la DB aún esté vacía...
     * @param tiposACargar
     * @param categoria
     * @param documento
     */
    public void clasificar(boolean[] tiposACargar, String categoria, Document documento){//el XML está formado por raiz [hospital], categorias [admin, labo, pac...] y atribitos                          
       leerAdministrador(tiposACargar[0], documento.getElementsByTagName("admin"));//con esto se obtiene a todos los nodos que correspondan a la categoría "admin" en este caso...                                 
       leerPaciente(tiposACargar[1], documento.getElementsByTagName("paciente"));//de esta manera no importará en qué orden puedan venir, puesto que YO soy quien los llama según el orden que requiero...                               
       leerMedico(tiposACargar[2], documento);
       leerExamen(tiposACargar[3], documento.getElementsByTagName("examen"));    
       leerLaboratorista(tiposACargar[4],documento);                           
       leerCita(tiposACargar[5], documento.getElementsByTagName("cita"));//tiene que ir aquí arriba puesto que a partir del código de esta se genrearán los de las consultas atendidas...
       leerInforme(tiposACargar[6], documento.getElementsByTagName("reporte"));//informe médico  [aquí se encuentra la creación de consultaAt]        
       leerResultado(tiposACargar[7], documento.getElementsByTagName("resultado"));//[aquí se encuentra la creación de exAt]
       leerConsulta(tiposACargar[8], documento.getElementsByTagName("consulta"));
       controladorIndices.registrarUltimosIndices();
    }
    
    //toma en cuenta que cada uno de estos métodos emplearán la misma técnica para leer cada una de las etiquetas, pero por la manera de guardarlas en la DB, habrán varias composiciones xD
    private void leerAdministrador(boolean debeCargar, NodeList Administradores){//lo cual viene a ser los nodos, p.ej -> doctor, examen....        
        if(debeCargar && Administradores!=null){
            for(int administradorAcual=0; administradorAcual < Administradores.getLength(); administradorAcual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node administrador = Administradores.item(administradorAcual);//que aquí lo nombraron como item xD
            
                if(administrador.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) administrador;                
                
                    creadorRegistros.crearAdministrador(elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), 
                            elemento.getAttribute("DPI"), elemento.getAttribute("PASSWORD"));//no olvides que todo lo devuelve como un string...
                }                        
            }                
        }        
    }//Y ASÍ XD, ESTE ES EL PROCESO GENERAL QUE DEBERÁS SEGUIR, A LO CUAL LE AGREGARÁS LAS PERSONALIZACIONES, ES DECIR cuando requiere una op extra, pues requiere crear otra tabla, o porque el atributo en la DB es de diferente tipo... pero lo que tienes ahorita, es lo general xD  
    /*terminado*/
    
    private void leerPaciente(boolean debeCargar, NodeList pacientes){
        if(debeCargar && pacientes!=null){                       
            for(int pacienteActual=0; pacienteActual < pacientes.getLength(); pacienteActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node paciente = pacientes.item(pacienteActual);//que aquí lo nombraron como item xD
            
                if(paciente.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) paciente;                
                
                    creadorRegistros.crearPaciente(elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), elemento.getAttribute("SEXO"),
                    elemento.getAttribute("BIRTH"), elemento.getAttribute("DPI"), elemento.getAttribute("TELEFONO"), elemento.getAttribute("PESO"),
                    elemento.getAttribute("SANGRE"), elemento.getAttribute("CORREO"), elemento.getAttribute("PASSWORD"));                                      
                }                        
            }                
        }
    }/*terminado*/
    
     public void leerMedico(boolean debeCargar, Document xml){        
        if(debeCargar && xml!=null){
            String titulos[];
            NodeList listaMedicos = xml.getElementsByTagName("doctor");//que aquí lo nombraron como item xD
                
            for(int numeroMedico=0; numeroMedico < listaMedicos.getLength(); numeroMedico++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...[por eje laboratorista...]
                Node medico = listaMedicos.item(numeroMedico);
                NodeList especialidades = xml.getElementsByTagName("ESPECIALIDAD").item(0).getChildNodes();//de esta manera enuentro las especialidades por médico... es decir el listado de todas las etiquetas título que dentro de especialidad estén [puesto que ellos serían sus hijitos...]
                NodeList horas = xml.getElementsByTagName("HORARIO").item(0).getChildNodes();//a partir de esto podría hacer 1 de 2; hacer exactamente lo mismo que con médico [puesto que aquí tengo mi equivalente al listado de médicos [horario...], es decir luego de ubicarme en 1 médico en particular [en este caso horario, el cual es único...] obtner verificar que sea un elemento y si es asíhacer la conversión a ese tipo para luego obtener los atrib, en este caso horaINi, horaFin Ó lo mismísimo que especialidad, es decir obtner el listado de los hijos y luego el valor de sus nodos y como FIJO son 2, entonces no se requiere de un ciclo...
                   
                titulos = new String[especialidades.getLength()];//según el número de títulos que posea [min 1]
                   
                for (int tituloActual = 0; tituloActual < especialidades.getLength(); tituloActual++) {
                    Node especialidad = especialidades.item(tituloActual);//me ubico en una etiqueta título en particular...                                      
                        
                        titulos[tituloActual] = especialidad.getNodeValue().toLowerCase();//obtengo el nombre del título [p.ej pediatría] y lo establezco en minus para evitar desacuerdos al revisar para las consultas...
                }//fin del for por medio del cual obtengo el listado de títulos del médico en cuestión...                      
                
                if(medico.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) medico;                                                    
                    
                   creadorRegistros.crearMedico(elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), 
                   elemento.getAttribute("COLEGIADO"), elemento.getAttribute("DPI"), elemento.getAttribute("TELEFONO"),titulos,
                   elemento.getAttribute("CORREO"), Integer.parseInt(horas.item(0).getNodeValue()), Integer.parseInt(horas.item(1).getNodeValue()),//recuerda que debes averiguar como es que se obtienen los datos de las etiquetas anidadas...
                   elemento.getAttribute("TRABAJO"), elemento.getAttribute("PASSWORD"));
               }
            }//fin del for exterior                                                                         
        }//fin del if que evita excepciones por no existir la categoría en el archivo dado...                                                   
    }/*terminado*/
    
    private void leerExamen(boolean debeCargar, NodeList examenes){
        if(debeCargar && examenes!=null){
            for(int examenActual=0; examenActual < examenes.getLength(); examenActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node examen = examenes.item(examenActual);//que aquí lo nombraron como item xD
            
                if(examen.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) examen;                
                
                    creadorRegistros.crearExamen(elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), elemento.getAttribute("ORDEN"), 
                    elemento.getAttribute("DESCRIPCION"), elemento.getAttribute("COSTO"), elemento.getAttribute("INFORME"));
                }                        
            }                
        }                
    }/*terminado*/
         
    private void leerLaboratorista(boolean debeCargar, Document xml){
         if(debeCargar && xml!=null){
            String dias[];
            NodeList listaLaboratoristas = xml.getElementsByTagName("laboratorista");//que aquí lo nombraron como item xD
                
            for(int numeroLaboratorista=0; numeroLaboratorista < listaLaboratoristas.getLength(); numeroLaboratorista++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...[por eje laboratorista...]
                Node laboratorista = listaLaboratoristas.item(numeroLaboratorista);
                NodeList diasDeTrabajo = xml.getElementsByTagName("TRABAJO").item(0).getChildNodes();//con esto obtengo de una vez el listado de los días... de forma semejante a como obtnego los atributos del labo... en este caso el padre es horario               
                   
                dias = new String[diasDeTrabajo.getLength()];//según el número de títulos que posea [min 1]
                   
                for (int diaActual = 0; diaActual < diasDeTrabajo.getLength(); diaActual++) {
                    Node dia = diasDeTrabajo.item(diaActual);//me ubico en una etiqueta título en particular...                                      
                        
                        dias[diaActual] = dia.getNodeValue();//obtengo el nombre del título [p.ej pediatría]
                }//fin del for por medio del cual obtengo el listado de títulos del médico en cuestión...                      
                
                if(laboratorista.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) laboratorista;                                                    
                    
                   creadorRegistros.crearLaboratorista(elemento.getAttribute("CODIGO"), elemento.getAttribute("NOMBRE"), 
                   elemento.getAttribute("REGISTRO"), elemento.getAttribute("DPI"), elemento.getAttribute("TELEFONO"),
                   elemento.getAttribute("EXAMEN"), dias, elemento.getAttribute("CORREO"), elemento.getAttribute("TRABAJOF"), elemento.getAttribute("PASSWORD"));
               }//aún no está incluido lo de las especialidades, puesto que no sabes exactametne como es que devulve los datos, ó como es que se obtiene el arreglo de dichos títulos, en String!!!                        
            }//fin del for exterior                                                                         
        }//fin del if que evita excepciones por no existir la categoría en el archivo dado...             
    }/*terminado*/   
    
     private void leerCita(boolean debeCargar, NodeList citas){
        if(debeCargar && citas!=null){
            for(int citaActual=0; citaActual < citas.getLength(); citaActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node cita = citas.item(citaActual);//que aquí lo nombraron como item xD
            
                if(cita.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) cita;                
                
                    creadorRegistros.crearCita(elemento.getAttribute("CODIGO"), elemento.getAttribute("PACIENTE"), elemento.getAttribute("MEDICO"), elemento.getAttribute("ESPECIALIDAD"),
                    elemento.getAttribute("FECHA"), elemento.getAttribute("HORA"));
                }                        
            }                
        }                    
    }
         
    private void leerInforme(boolean debeCargar, NodeList informes){
        if(debeCargar && informes!=null){                       
            
            for(int informeActual=0; informeActual < informes.getLength(); informeActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node informe = informes.item(informeActual);//que aquí lo nombraron como item xD
            
                if(informe.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) informe;                                                 
                    
                    creadorRegistros.crearConsultaAtendida(elemento.getAttribute("PACIENTE"), elemento.getAttribute("MEDICO"),//con respecto al código de la consulta, este será dado por un método que aún no sé si obtntendrá el valor directamente de la var de controlId o del dos, puesto que después estará haciendo eso cada vez que principie... creo que suena mejor esto último...
                    elemento.getAttribute("FECHA"), elemento.getAttribute("HORA"), elemento.getAttribute("CODIGO"), elemento.getAttribute("INFORME"));//RECUERDA que mando a llamar a este método por el hecho de que para que un informe exista debe existir una consulta atendida... pero puse al método informes puesto que eso es lo que nos dan en el XML xD
                }                        
            }                
        }                   
    }
    
    private void leerResultado(boolean debeCargar, NodeList resultados){
        if(debeCargar && resultados!=null){                                   
            for(int resultadoActual=0; resultadoActual < resultados.getLength(); resultadoActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node resultado = resultados.item(resultadoActual);//que aquí lo nombraron como item xD
            
                if(resultado.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) resultado;                
                                    
                    creadorRegistros.crearDatosExamenAtendido(elemento.getAttribute("CODIGO"), elemento.getAttribute("PACIENTE"), elemento.getAttribute("EXAMEN"), elemento.getAttribute("LABORATORISTA"),
                    elemento.getAttribute("ORDEN"), elemento.getAttribute("INFORME"), elemento.getAttribute("FECHA"), elemento.getAttribute("HORA"));
                }                        
            }                
        }           
    }      
    
    private void leerConsulta(boolean debeCargar, NodeList consultas){
       if(debeCargar && consultas!=null){
         for(int consultaActual=0; consultaActual < consultas.getLength(); consultaActual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
            Node consulta = consultas.item(consultaActual);//que aquí lo nombraron como item xD
            
            if(consulta.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                Element elemento = (Element) consulta;                
                
               creadorRegistros.crearConsulta(elemento.getAttribute("TIPO"), elemento.getAttribute("COSTO"));
            }                        
         }//fin el for con el cual se registra cada consulta..
      }                                   
    }
 
    //para realizar las comparaciones en la lista de especialidades, deberás emplear el ignore case, puesto que solo eso podría cb entre c/u de dichos titulos...
    
}
