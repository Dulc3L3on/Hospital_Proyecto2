<%-- 
    Document   : DemandaExamenes
    Created on : 7/10/2020, 21:19:41
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
        <title>JSP Page</title>
    </head>
    <body>
        <%!Administrador administrador;%>
        <%!ListaEnlazada<Reporte> listadoReportes;%>
        <%!ReporteAdministrador reportes = new ReporteAdministrador();%>
        <%!Nodo<Reporte> nodoAuxiliar;%>
        <center>
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
        <div id='central'>
            <%if(request.getParameter("desde")!=null && request.getParameter("hasta")!=null){
                listadoReportes=reportes.examenesMasDemandados(request.getParameter("desde"), request.getParameter("hasta"));%>
                
                <ul>        
                    <%nodoAuxiliar=listadoReportes.obtnerPrimerNodo();%>
                    <%for(int numeroElemento =1; numeroElemento < listadoReportes.darTamanio(); numeroElemento++ ){%>
                        <li><%nodoAuxiliar.contenido.darNombreUsuario();%> --- Q.<%nodoAuxiliar.contenido.darNumero();%></li>
                </ul><!--listo, aquí esta lo de médicos :3-->
           <%}%> 
        </div>                       
    </body>
</html>
