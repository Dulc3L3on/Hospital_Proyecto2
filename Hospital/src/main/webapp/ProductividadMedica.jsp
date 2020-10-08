<%-- 
    Document   : ProductividadMedica
    Created on : 7/10/2020, 21:15:15
    Author     : phily
--%>

<%@page import="Extras.Reporte"%>
<%@page import="Kit.Nodo"%>
<%@page import="Manejadores.DB.Reportes.ReporteAdministrador"%>
<%@page import="Kit.ListaEnlazada"%>
<%@page import="Entidades.Administrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%!Administrador administrador;%>
        <%!ListaEnlazada<Reporte> listadoReportes;%>
        <%!ReporteAdministrador reportes = new ReporteAdministrador();%>
        <%!Nodo<Reporte> nodoAuxiliar;%>
    </head>
    <body>
      <center>
          <h4>>>PRODUCTIVIDAD MÉDICA<<</h4>
            <table>
                <tr>
                    <th>
                        <input type="date" name="desde" max="<%=java.time.LocalDate.now()%>" required>
                    </th>
                    <th>
                        <input type="date" name="hasta" max="<%=java.time.LocalDate.now()%>" required>
                    </th>
                </tr>               
            </table>
        </center>          
        <div id='izquierdo'>
            <%if(request.getParameter("desde")!=null && request.getParameter("hasta")!=null){
                listadoReportes=reportes.medicosConMasInformes(request.getParameter("desde"), request.getParameter("hasta"));%>
                
                <ul>        
                    <%nodoAuxiliar=listadoReportes.obtnerPrimerNodo();%>
                    <%for(int numeroElemento =1; numeroElemento < listadoReportes.darTamanio(); numeroElemento++ ){%>
                        <li><%nodoAuxiliar.contenido.darNombreUsuario();%> --- Q.<%nodoAuxiliar.contenido.darNumero();%></li>
                </ul><!--listo, aquí esta lo de médicos :3-->
           <%}%> 
        </div>            
            
        <div id='derecho'>
            <%if(request.getParameter("desde")!=null && request.getParameter("hasta")!=null){
                listadoReportes=reportes.medicosConMenorCantidadDeCitas(request.getParameter("desde"), request.getParameter("hasta"));%>
                
                <ul>        
                    <%nodoAuxiliar=listadoReportes.obtnerPrimerNodo();%>
                    <%for(int numeroElemento =1; numeroElemento < listadoReportes.darTamanio(); numeroElemento++ ){%>
                        <li><%nodoAuxiliar.contenido.darNombreUsuario();%> --- Q.<%nodoAuxiliar.contenido.darNumero();%></li>
                </ul><!--listo, aquí esta lo de médicos :3-->
           <%}%>                         
        </div>
    </body>
</html>
