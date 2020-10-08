/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Manejadores.DB.BusquedaEspecifica;
import Manejadores.DB.Registro;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author phily
 */
@WebServlet("/ResultadoReserva")
public class servletGestorActividades extends HttpServlet{
    Registro registrador = new Registro();
    BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //Aquí ya tienes a tu disponibilidad el arreglo entero de citas y el número correspondiente el cual puedes obtenerlo con el request.GetParameter puesto que se lo estableciste como valor a los btn...
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();     
        boolean procesoExitoso=true;
        
        if(request.getParameter("agendarCita")!=null){
            //procesoExitoso = registrador.agendarCitaMedica(/*aqui obtendras el arreglo de las citas por medio de la sesión y el número por meio del valor del botón...*/);
        }
        else if(request.getParameter("agendarExamen")!=null){
            //procesoExitoso = registrador.agendarExamen(/*la citaExamen creada a partir de la información escogida desde la interfaz [fecha y hora... además de la especialidad y el código del paciente, donde este último es obtenido por la sesión...]*/);
        }
        
        //if(){//recuerda que en este objeto ya va el paciente, el médico , la fech y todo lo que requieres :)
            //Aquí las etiquetas para decir que el proceso salió bien          
            //Medico medico = buscadorMinucioso.buscarUnUsuario("Medico", /*aquí el numero de codigo del médico, que se encuentra en el objeto cita formado...*/);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>RESULTADO EXITOSO</title></head>");           
            out.println("<body>");            
            out.println("<div><center>");
            out.println("<h1>RESERVA EXITOSA</h1><br/>");
            out.println("<table>");            
            out.println("<tr>");
            out.println("<th><h3>Tipo Consulta: </h3></th>");
            out.println("<th><p>  </p></th>");//ahí colocaría el nombre del tipo 
            out.println("<th><h3>Fecha: </h3></th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th><h3>Medico: </h3></th>");            
            out.println("<th><p>  </p></th>");//ahí colocaría el nombre del tipo 
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th><h3>Fecha: </h3></th>");
            out.println("<th><p> </p></th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th><h3>Hora: </h3></th>");//ahí colocaría el nombre del tipo 
            out.println("<th><p> </p></th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th><h3>Costo: </h3></th>");
            out.println("<th><p> </p></th>");//aquí el precio
            out.println("</tr>");
            out.println("</table>");
            out.println("</center></div>");
            out.println("</body>");
            out.println("</html>");
            
            out.close();
        //}else{
            //Aquí las etiquetas para informar que no        
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Resultado Fallido</title></head>");           
            out.println("<body>");            
            out.println("<div><center>");
            out.println("<h1>RESERVA FALLIDA :(</h1><br/>");
            out.println("<h3>Intente nuevamente</h3><br/>");
            out.println("</center></body>");            
            out.println("</head>");                    
            out.println("</html>");            
        
            out.close();
        //ni siquiera tendrías que hacer un response si es que aquí mismo implemetnarás
        //las etiquetas del html, si no harás eso entonces iría la dirección de la pág que 
        //rxn según el valor enviado :) y para regresar no te preocupes, puesto que tienes el menú gneeral :) :3
        
    }
    
    
}
