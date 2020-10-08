<%-- 
    Document   : agendarCitaMedica
    Created on : 6/10/2020, 00:56:08
    Author     : phily
--%>

<%@page import="Documentacion.Consulta"%>
<%@page import="Reservaciones.CitaMedica"%>
<%@page import="Kit.Herramienta"%>
<%@page import="Kit.Nodo"%>
<%@page import="Kit.ListaEnlazada"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Entidades.Medico"%>
<%@page import="Manejadores.DB.Registro"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page import="Extras.GestorActividades"%>
<%@page import="Entidades.Paciente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssPaciente.css"
        <%!Paciente paciente;%><!--cuando agerguemos lo de las sesiones [cuando la DB esté correcta] se hará la llamada a la búsqueda, a partir del id que fue enviado por el setAttribute...[como en el perfil del ADMIN]-->
        <%!GestorActividades manager = new GestorActividades();%>
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();%>
        <%!Herramienta herramienta = new Herramienta();%>
        <%!Registro registrador = new Registro();%>
        <%!Medico[] especialistas;%>        
        <%!int numero=-1;%>
    </head>
    <body>
        <div>
            <form method="POST" action="AgendarCitaMedica.jsp" id="comandos"><!--puesto que solo aquí en esta pag se sabe como manejar los datos...-->                                              
                <%!ListaEnlazada<Consulta> tipoConsultas = buscador.darConsultas();
                Nodo<Consulta> nodoAuxiliar = tipoConsultas.obtnerPrimerNodo();
                %>                
                <select id="acciones" name="listadoEspecialidades" required>
                <%for(int especialidadActual = 1; especialidadActual <= tipoConsultas.darTamanio(); especialidadActual++){%>
                    <option value="${nodoAuxiliar.contenido.darNombre()}">${nodoAuxiliar.contenido.darNombre()}  $."${nodoAuxiliar.contenido.darCosto()}"</option>
                    <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                 }%>               
                </select>                
                <!--por esta condición será posible ver como es que sucede lo del request de un btn, puesto que al no estar vacío y el paciente selccionar "ver" es redireccionado a otra pág... entonces, yo creo que ya no se mostrará este listado al hacerlo... pues no es la misma sesión...[al menos por ahora]-->
                 <%if(request.getParameter("listadoEspecialidades")!=null){%><!--sería bueno que se probara qué es lo que sucede cuando se pide un valor al listado de médicos cuando aún.... nop, si va a devolver un null, puesto que se "activa" hasta que se selecciona...-->
                    <%especialistas= buscadorMinucioso.buscarMedicosPorEspecialidad(request.getParameter("listadoEspecialidades"));%>
                 <%}%><!--si, pues si no ha seleccionado nada no tendría por qué mostrar algo... o si?-->
                <input id="acciones" type="submit" name="buscar" value="BUSCAR">
            </form>             
            
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                                
            <%if(request.getParameter("numero")!=null && especialistas!=null){%><!--solo con lo del número basta puesto que si no hay listado desplegado, no tien oportunidad de seleccionar 1#; de tal forma que la var, siga siendo nula [en el request]...-->
                <%numero = Integer.parseInt(request.getParameter("numero"));                  
            }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
            <%!String colegiado=(numero!=-1)?especialistas[numero].darColegiado():"";%>            
            <%!String nombreMedico=(numero!=-1)?especialistas[numero].darNombre():"";%>
            <!--ahí miras si declaras aquí lo de las especialidades, p hasta la parte donde se muestran... de todos modos ya tienes la que te interesa...lo cual está en el parám del request...o no? com se redirige despueés de haber seleccionado, tal vez debería quitarle el submi y solo...mmmm nop sino no podría usar el request...deplano que lo tendré que establecer comouna varibale, PERO de SESIÓN!!!-->
            <%!String horaInicio = (numero!=-1)?String.valueOf(especialistas[numero].darHoraInicio()):"";%>
            <%!String horaFin = (numero!=-1)?String.valueOf(especialistas[numero].darHoraFin()):"";%>            
            <%!String fechaIncorporacion=(numero!=-1)?especialistas[numero].darFechaIncorporacion().toString():java.time.LocalDate.now().toString();%>
            <%!String correo=(numero!=-1)?especialistas[numero].datosPersonales.darCorreo():"";%>
            <%!String codigo=(numero!=-1)?String.valueOf(especialistas[numero].darCodigo()):"";%>            
            <%!String DPI=(numero!=-1)?especialistas[numero].darDatosaPersonales().darDPI():"";%><!--como de todos modos los input dan y reciben String...-->
            <%!String telefono=(numero!=-1)?especialistas[numero].datosPersonales.darTelefono():"";%>                                   
            <%!ListaEnlazada<String> titulos=(numero!=-1)?especialistas[numero].darTitulos():new ListaEnlazada();%>
                
            <%if(especialistas!=null){%><!--por medio de esto es posible mostrar el listado de los médicos que poseen título de la especialidad seleccionada...-->
               <%for(int numeroEspecialista=0; numeroEspecialista<especialistas.length; numeroEspecialista++){%>                
                    <p><%especialistas[numeroEspecialista].darNombre();%><a href="AgendarCitaMedica.jsp?numero=${numeroEspecialista}?especialidad=${request.getParameter("listadoEspecialidades")}">ver</a></p><!--de esta manera preservo la especialidad seleccionada para cuando se redireccione a la sig pág...[lo cual tb podría hacerse haciendo uso de la sesión...-->
                     <br/>                
                <%}%>
           <%}%>              
            </div>   
              <div id="areaInformacion">
                    <p>
                        Colegiado: <%=colegiado%><br/><br/>
                        Nombre: <%=nombreMedico%><br/>
                        Especialidades
                        <ol>
                           <%Nodo<String>nodoAuxiliar = titulos.obtnerPrimerNodo();
                           for(int nodoActual = 1; nodoActual <= titulos.darTamanio(); nodoActual++){%>  
                                <li>"${nodoAuxiliar.contenido}"</li>
                                <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                           }%>
                        </ol>                        
                        Horario de atención
                        <ul>
                            <li type="circle">Inicio: <%=horaInicio%>   </li>
                            <li type="circle">Inicio: <%=horaFin%>   </li><br/>                            
                        </ul>
                        correo: <%=correo%> 
                    </p>
                </div>            
        </div><!--fin de la vita del lado iquierdo-->
        <div id='calendario'>            
            <form name="disponibilidadMedico" action="servletGestorActividades.java" method="GET"><!--puesto que el servlet tiene a un doGet.-->                
                <input type='date' name="fecha" min="<%=java.time.LocalDate.now()%>" value="<%=java.time.LocalDate.now()%>" required>
                <input type="submit" name="aceptarFecha" value="ACEPTAR">
                
                <div id="horasDisponibles" name="horasDisponibles">
                <%if(request.getParameter("numero")!=null && request.getParameter("fecha")!=null){
                    CitaMedica[] citasDisponibles = manager.obtenerHorasOcupadas(herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(request.getParameter("fecha")).getTime()), especialistas[Integer.parseInt(request.getParameter("numero"))], paciente.codigo, request.getParameter("especialidad"));                                                                                    
                            
                    if(citasDisponibles!=null){%>                                                            
                    <!--aquí va a ir la asignación del ARREGLO ENTERO de las citas para que el doGet del servlet pueda emplearlo...-->
                            
                   <%for(int citaActual=0; citaActual<citaActual;citaActual++){%>  
                        <%if(citasDisponibles[citaActual]!=null){%>
                               <button name="agendarCita" type="submit" value="${citaActual}"><%=String.valueOf(citasDisponibles[citaActual].darHora())%> : <%=String.valueOf(citasDisponibles[citaActual].darHora()+1)%></button> 
                       <%}%>
                  <%}%><!--de esta manera puedo enviar el número de cita que escogió del arreglo y redirigirlo al servlet donde procede a registrar la cita y a mostrar lo sucedido...-->                                                
                  <%}%>                                                                            
                <%}%>
                    </div>
            </form>
        </div>
        
        
    </body>
</html>


<!--recuerda que ahorita lo que harás es, establecer en el valor del bBx el objeto consulta como tal, de manera que cuando lo recuperers con el request, puestas tener acceso a los demás atriutos [en este caso solamente precio], si lo quieres recvertir, entonces
en el valor de los OPT del cbBox cambialos a  .darNOmbre() y listo, y para los getParamenters, solo quitales las llamadas a darCosto...-->