/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Archivos;

import Verificadores.VerificadorDB;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



/**
 *
 * @author phily
 */
public class ManejadorXML {
    CargaDeDatos clasificador;    
    
    public ManejadorXML(Connection conexionDB){//recuerda que este solo deberá ser ejecutado cuando la DB esté vacía...
        clasificador = new CargaDeDatos(conexionDB);        
    }

//    public ManejadorXML(DataSource dataSource){//recuerda que este solo deberá ser ejecutado cuando la DB esté vacía...
//        clasificador = new CargaDeDatos(dataSource);        
//    }
    
    public void leerXML(boolean listadoALlenar[], String nombreArchivo){             
        
        try{
             DocumentBuilderFactory fabricaDocumento = DocumentBuilderFactory.newInstance();
             DocumentBuilder constructorDocumento = fabricaDocumento.newDocumentBuilder();
             Document documento = constructorDocumento.parse(new File(nombreArchivo));
             
             documento.getDocumentElement().normalize();             
             
             clasificador.clasificar(listadoALlenar, documento.getNodeName(), documento);
            
        }catch(ParserConfigurationException exc){
            JOptionPane.showMessageDialog(null, "No puedo transformarse el archivo", "error de conversion", JOptionPane.ERROR_MESSAGE);
        }   
        
        catch(IOException | SAXException exc){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al\nintentar leer el XML", "error de lectura", JOptionPane.ERROR_MESSAGE);            
        }                             
    }          
    
}
/*
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder;
*/

/*
 <%if(request.getParameter("carga").equalsIgnoreCase("ACEPTADO")){//<!--a mi parecer, como aún no se ha creado el btn entonces al tener "ACEPTAR" como la comparación no habrá problema, puesto que aún no existe el componente...-->        
                            //se manda a llamar al método para guardar el archivo y posterior a ello agarrar ... busca como podrías obtener el nombre del archivo de tal manera que emplees la forma normal...s i no puedes entonces emplea la lib...                            
                            manejadorXML.leerXML(verificador.darListadoVacios(), request.getParameter("cargaArchivo"));
                        }%>
*/