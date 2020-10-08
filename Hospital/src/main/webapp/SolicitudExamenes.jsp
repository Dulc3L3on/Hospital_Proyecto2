<%-- 
    Document   : SolicitudExamenes
    Created on : 7/10/2020, 21:22:08
    Author     : phily
--%>

<%@page import="Kit.Nodo"%>
<%@page import="Manejadores.DB.Reportes.ReporteAdministrador"%>
<%@page import="Extras.Reporte"%>
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
        <%!ListaEnlazada<Reporte> listadoExamenesRequeridos;%>
        <%!ReporteAdministrador reportes = new ReporteAdministrador();%>
        <%!Nodo<Reporte> nodoSolicitantes;%>
        <%!Nodo<Reporte> nodoSolicitados;%>
        <%!ListaEnlazada<Reporte> medicosSolicitantes = new ListaEnlazada();%>
        
       
        <%if(request.getParameter("desde")!=null && request.getParameter("hasta")!=null){%>
            <%medicosSolicitantes = reportes.medicosCOnMasExamenesRequeridos(request.getParameter("desde"), request.getParameter("hasta"));%>
        <%}%>
        
        <div id="listaElementos">            
            <ul>
                <%nodoSolicitantes = medicosSolicitantes.obtnerPrimerNodo();%>
                    <li></li>
                <%for(int medicoActual =0; medicoActual <medicosSolicitantes.darTamanio();medicoActual++){%><!--no habrá problemas puesto que se inicializo y por tanto tiene tamanio 0, mas no es null el obj lista como tal...-->
                <li type="circle"><%=nodoSolicitantes.contenido.darNombreUsuario()%> -- <%=nodoSolicitantes.contenido.darNumero()%> <a href="SolicitudExamenes.jsp?codigoMedico=<%nodoSolicitantes.contenido.darCodigo();%>?desde=<%request.getParameter("desde");%>?hasta=<%request.getParameter("hasta");%>">Ver Examenes</a><li/>
            </ul>           
        </div>
        <h4>SOLICITUD EXAMEN</h4>
        <div id="areaBusqueda">
            <%if(request.getParameter("codigoMedico")!=null){%>
                <p><%listadoExamenesRequeridos= reportes.examenesMasRequeridosPorMedicos(Integer.parseInt(request.getParameter("codigoMedico")), request.getParameter("desde"), request.getParameter("hasta"));%></p>
                <h4>EXAMENES SOLICITADOS</h4>
                
                <%nodoSolicitados= listadoExamenesRequeridos.obtnerPrimerNodo();%>
                <ul>
                    <%for(int examenActual=0; examenActual<listadoExamenesRequeridos.darTamanio(); examenActual++){%>
                        <li>
                            <%nodoSolicitados.contenido.darNombreUsuario();%>--<%nodoSolicitados.contenido.darNumero();%>
                        </li>
                    <%}%>
                </ul>
                
        </div>
        
    </body>
</html>
