<%-- 
    Document   : Historial
    Created on : 7/10/2020, 08:10:00
    Author     : phily
--%>

<%@page import="Kit.Nodo"%>
<%@page import="Kit.ListaEnlazada"%>
<%@page import="Documentacion.Examen"%>
<%@page import="Entidades.Medico"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Entidades.Paciente"%>
<%@page import="Extras.Reporte"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page import="java.sql.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HistorialMedico</title>
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();%>        
        <%!String tipoEstructura;%><!--este valor lo obtendrá del request...puesto que se formará un query string para mandar el parámetro correcto...-->
        <%!ListaEnlazada<Reporte> listadoReportes;%>        
        <%!int numeroReportes=-1;%>
        <%!Nodo<Reporte> nodoAuxiliar;%>        
        <%!Reporte reporte;%>
    </head>
    <body>         
        <%!Paciente paciente;%><!--el objeto se obtendrá por medio de la sesión...-->        
        <%tipoEstructura = request.getParameter("tipoEstructura");%>
        <%!Reporte[] reportesRecientes = paciente.ver5Recientes(tipoEstructura);%>
        
        
        <div id="listaElementos">            
            <ul>                
                <%if(request.getParameter("tipoEstructura").equals("Consulta")){%>
                    <%for(int posicionActual = 0; posicionActual< reportesRecientes.length; posicionActual++){%>
                        <li><%reportesRecientes[posicionActual];%></li>
                    <%}%>
                <%}%>
                <%if(request.getParameter("tipoEstructura").equals("Examen")){%>
                    <%for(int posicionActual = 1; posicionActual<0; posicionActual++){%> 
                        <li>
                            <!--aquí mandarías a llamar a los metodos poara -->
                        </li>
                    <%}%>
                <%}%>
            </ul>                                        
        </div>        
        <div id="areaBusqueda">
            <%!Date fechaMinima;// = buscadorMinucioso.buscarFechaPrimeraConsulta(codigoPaciente);%><!--este codigo lo obtendrás con la sesión...-->            
            <center>
                <form method="POST" action="HistorialMedico.jsp">
                    <table>
                        <tr>
                            <th>
                                <input type="date" value="<%=java.time.LocalDate.now()%>" name="desde" min="<%(fechaMinima!=null)?fechaMinima:"";%>" max="<%=java.time.LocalDate.now()%>" required>
                            </th>
                            <th>
                                <input type="date" value="<%=java.time.LocalDate.now()%>" name="hasta" min="<%(fechaMinima!=null)?fechaMinima:"";%>" max="<%=java.time.LocalDate.now()%>" required>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <select id="listado" name="listadoOpciones" required><!--que dependerá de la opción seleccionada: consulta/examen-->
                                    <%if(tipoEstructura!=null){
                                        if(tipoEstructura.equals("Consulta")){
                                            Medico[] medicos = (Medico[])buscador.buscarUsuarios("Medico");                                    
                                            for(int medicoActual = 0; medicoActual < medicos.length; medicoActual++){%>
                                                <option value="<%=medicos[medicoActual].codigo%>"><%=medicos[medicoActual].darNombre()%></option>
                                            <%}%>
                                        <%}//<!--entonces procedo a buscar a los médicos-->
                                         if(tipoEstructura.equals("Examen")){
                                            Examen[] examenes = (Examen[])buscador.buscarEstructuras("Examen");                                    
                                            for(int examenActual = 0; examenActual < examenes.length; examenActual++){%>
                                                <option value="<%=examenes[examenActual].darCodigo()%>"><%=examenes[examenActual].darNombre()%></option>
                                            <%}%>
                                        <%}%><!--y de esta manera muestro los listados correspondientes xD :) :D :3-->                                
                                    <%}%>                                    
                                </select>
                            </th>
                        </tr>    
                        <tr>
                            <th colspan="2">
                                <input type="submit" name="aceptar" id="acciones" value="ACEPTAR"><!--si no se mira bien, entonces solo lo sacamos y ya xD...-->
                            </th>
                        </tr>
                    </table>
                </form>                
            </center>    
             <%listadoReportes = paciente.verCitasEspecificas(request.getParameter("tipoEstructura"),Integer.parseInt(request.getParameter("listadoOpciones")), request.getParameter("desde"), request.getParameter("hasta"));%>
             <div id="areaHistorial">
                 <table>
                     <%if(listadoReportes.darTamanio()>0){%><!--//lo cual abarcaría el hecho de que sucedió un fallo ó no había nada para mostrar...-->
                          <tr>
                              <th>
                                  <%if(request.getParameter("tipoEstructura").equals("Consulta")){%>
                                        <h3>Tipo consulta</h3>
                                  <%}if(request.getParameter("tipoEstructura").equals("Examen")){%><!--por si acaso llegasen a ingresar algo más...-->
                                         <h3>Laboratorista a cargo</h3>
                                  <%}%>
                              </th>
                              <th>
                                  <h3>Fecha</h3>
                              </th>
                              <th>
                                  <h3>Hora</h3>
                              </th>
                          </tr>
                     
                        <%nodoAuxiliar = listadoReportes.obtnerPrimerNodo();%>
                            <%for(int posicionActual=1; posicionActual<= listadoReportes.darTamanio(); posicionActual++){%>
                                <%reporte = nodoAuxiliar.contenido;%>
                                <tr>
                                    <th>    
                                        <%nodoAuxiliar.contenido;%>
                                        <!--aquí se hará la llamada a los método para dar el nombre, entero y fecha de reporte...[obvidamente en cada columna...]-->
                                    </th> 
                                </tr>
                                <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                            }%>
                     <%}%>
                 </table>
             </div>                                                
        </div>        
    </body>
</html>
