/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.Servlets;

import Manejadores.Archivos.ManejadorArchivo;
import Manejadores.Archivos.ManejadorXML;
import Manejadores.DB.ManejadorDB;
import Verificadores.VerificadorDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author phily
 */

@WebServlet("/procesoSubida")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class servletSubidorArchivo extends HttpServlet{
    ManejadorXML manejadorXML = new ManejadorXML();
    ManejadorDB manejadorDB= new ManejadorDB();
    VerificadorDB verificador = new VerificadorDB();
    ManejadorArchivo manejadorArchivo = new ManejadorArchivo();    
     
        
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        
        Part filePart = request.getPart("cargaArchivo");
        String fileName = getFileName(filePart);        
        String path = "/"+fileName;
        
        //manejadorArchivo.guardarArchivo(filePart, fileName);
        filePart.write(path);
        manejadorXML.leerXML(verificador.darListadoVacios(), manejadorArchivo.darPathReciente());
        
        
        String mimeType = filePart.getContentType();
        System.out.println("type: " + mimeType);
        System.out.println("file name: " + fileName);
        //System.out.println("Stored at: " + path);        
    }

    private String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    for (String content : part.getHeader("content-disposition").split(";")) {
        if (content.trim().startsWith("filename")) {
            return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return null;
}
    
    
    
}






























 //aquí irían todo lo que tendría que vver con los métodos para 
    // -> hacer put, get, prop, delete... creo que tendrá que haber 1 por página
    //si es así entonces aquí convergerián para no tener que estar importando 
    //tanta clase...


