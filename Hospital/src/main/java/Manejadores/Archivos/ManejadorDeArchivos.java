/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Archivos;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author phily    
 */
public class ManejadorDeArchivos {
    String repositorio="Hospital/src/main/resource/CargaXML";
    
    public void guardarArchivosCargados(HttpServletRequest request){
        DiskFileItemFactory fabrica = new DiskFileItemFactory();
        fabrica.setRepository(new File(repositorio));
        ServletFileUpload carga = new ServletFileUpload(fabrica);        
        
        try{
            List<FileItem> itemsSeleccionados = carga.parseRequest(request);
            
            for (FileItem itemsSeleccionado : itemsSeleccionados) {
                File archivo = new File(repositorio, itemsSeleccionado.getName());
            }                        
        }catch(FileUploadException ex){
            System.out.println("Ha surgido un error al cargar los archivos al servidor");
        }
    }
   
    
    public String darRutaXML(){
        return repositorio;
    }
    
}
