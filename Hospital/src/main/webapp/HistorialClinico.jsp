<%-- 
    Document   : HistorialClinico
    Created on : 7/10/2020, 14:36:26
    Author     : phily
--%>

<%@page import="Kit.Nodo"%>
<%@page import="Kit.ListaEnlazada"%>
<%@page import="Documentacion.Examen"%>
<%@page import="Entidades.Medico"%>
<%@page import="Entidades.Paciente"%>
<%@page import="java.sql.Date"%>
<%@page import="Extras.Reporte"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel ="stylesheet" href="cssPaciente.css">       
        <%!Paciente paciente;%>        
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();%>
        <%!ListaEnlazada<Reporte> listadoReportes;%>
        <%!Nodo<Reporte> nodoAuxiliar;%>
        <%!String tipoEstructura;%>
        <%!Medico[] especialistas;%>
        <%!Examen[] examenes;%>
        <%!Reporte reporte;%>
        <%!Reporte[] reportesRecientes;%>
        <%!int numeroReportes=-1;%>
        
    </head>          
    <body>            
        <form method="POST" action="HistorialClinico.jsp">
            <button type="submit" value="Consulta" name="tipoEstructura"><img src="img/agenda.jpg" width="95" height="95"></button>
            <button type="submit" value="Examen" name="tipoEstructura"><img src="img/resultados.png" width="95" height="95"></button>
        </form>
        
        <div id="listaElementos">            
            <table>                            
                <%if(request.getParameter("tipoEstructura")!=null && paciente!=null){%><!--debido al hecho de que -->                    
                    <%tipoEstructura = request.getParameter("tipoEstructura");%>        
                    <!--aquí se hace igual al parámetro almacenado por medio de la sesión...-->
                    
                    <%reportesRecientes = paciente.ver5Recientes(tipoEstructura);%><!--recueda que ambas estructuras son semejantes, por lo tanto solo es necesario saber a cual se estaban refiriendo y por ello no podrás dejarse su valor, como definición general...-->
                
                    <%for(int posicionActual=0; posicionActual<reportesRecientes.length; posicionActual++){%>                    
                        <tr>
                            <th><%reportesRecientes[posicionActual].darNombreServicio();%></th>
                            <th><%reportesRecientes[posicionActual].darNombreUsuario();%></th>
                            <th><%reportesRecientes[posicionActual].darFecha();%></th>
                            <th><%reportesRecientes[posicionActual].darHora();%></th>
                        </tr>
                    <%}%>
                <%}%>
            </table>
            
        </div>
        <div id="areaBusqueda">            
            <center>
                <%!Date fechaMinima;%>
                <form method="POST" action="HistorialClinico.jsp">
                    <table>
                        <tr>
                            <th>
                                <input type="date" value="<%=java.time.LocalDate.now()%>" name="desde" min="<%=java.time.LocalDate.now()%>" max="<%=java.time.LocalDate.now()%>" required>
                            </th>
                            <th>
                                <input type="date" value="<%=java.time.LocalDate.now()%>" name="hasta" min="<%=java.time.LocalDate.now()%>" max="<%=java.time.LocalDate.now()%>" required>
                            </th>
                        </tr>
                        <tr colspan="2">
                            <th>
                                <select id="listado" name="listadoOpciones" required>
                                    <%if(tipoEstructura!=null){
                                        if(tipoEstructura.equals("Consulta")){
                                            especialistas = (Medico[]) buscador.buscarUsuarios("Medico");
                                            
                                            for(int medicoActual=0; medicoActual < especialistas.length; medicoActual++){%>
                                                <option value="<%=especialistas[medicoActual].codigo%>"><%=especialistas[medicoActual].darNombre()%></option>                                            
                                            <%}%>                                            
                                        <%}
                                        
                                         if(tipoEstructura.equals("Examen")){
                                            examenes = (Examen[])buscador.buscarEstructuras("Examen");
                                            for(int examenActual = 0; examenActual <examenes.length; examenActual++){%>
                                                <option value="<%=examenes[examenActual].darCodigo()%>"><%=examenes[examenActual].darNombre()%></option><!--este dependerá de la DB, puesto que la información es cambiante...-->
                                            <%}%>
                                         <%}%>
                                    <%}%>
                                </select>
                            </th>
                        </tr>
                    </table>
                    <input type="submit" value="ACEPTAR" name="aceptar">                    
                </form>
            </center>            
            
            <div id="areaHistorial">                
                <%if(tipoEstructura!=null){%>
                    <%listadoReportes = paciente.verCitasEspecificas(tipoEstructura, Integer.parseInt(request.getParameter("listadoOpciones")), request.getParameter("desde"), request.getParameter("hasta"));%>                                    
                    <table>
                           <tr><!--aquí irán los encabeados...-->
                               <%if(tipoEstructura.equals("Consulta")){%>
                                    <th><h3>Tipo consulta</h3></th><!--esto depende del tipo de búsqueda que se haya decidido hacer-->
                                <%}%>
                                <%if(tipoEstructura.equals("Examen")){%>
                                    <th><h3>Laboratorista encargado</h3></th><!--esto depende del tipo de búsqueda que se haya decidido hacer-->
                                <%}%>
                                <th>Fecha</th>
                                <th>Hora</th>                        
                            </tr>
                            <%nodoAuxiliar = listadoReportes.obtnerPrimerNodo();%>
                            <%for(int posicionActual = 0; posicionActual<5; posicionActual++){%>
                                <tr>
                                    <th>
                                        <%nodoAuxiliar.contenido.darNombreServicio();%>
                                    </th>
                                    <th>
                                        <%nodoAuxiliar.contenido.darFecha();%>
                                    </th>
                                    <th>
                                        <%nodoAuxiliar.contenido.darHora();%>
                                    </th>
                                   <!--aquí se forman las 3 columnas de cada registro almacenado en el arreglo [o listado]-->
                                </tr>
                                <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;%>
                            <%}%>
                    </table>
                 <%}%>
            </div>                       
        </div>                       
    </body>
</html>
