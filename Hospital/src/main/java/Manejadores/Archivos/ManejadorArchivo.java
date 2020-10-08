/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Archivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Part;

/**
 *
 * @author phily
 */
public class ManejadorArchivo {
    public static final String BASE="/";
    String pathReciente;
    
    public void guardarArchivo(Part archivoXML, String pathArchivo) {
        File path = new File(BASE);        
        File archivo = new File(path, pathArchivo);
        
        pathReciente= path+pathArchivo;
        
        try ( InputStream input = archivoXML.getInputStream()) {
            Files.copy(input, archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (FileNotFoundException e) {
            System.out.println("no fue posible encontrar el archivo");
        } catch (IOException ex) {
            System.out.println("surgio un error en\nla escritura del archivo");
        }
    }
    
    public String darPathReciente(){
        return pathReciente;
    }
    
}
