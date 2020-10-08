<%-- 
    Document   : AgendarExamen
    Created on : 6/10/2020, 19:44:28
    Author     : phily
--%>

<%@page import="Documentacion.Examen"%>
<%@page import="Entidades.Medico"%>
<%@page import="Manejadores.DB.Registro"%>
<%@page import="Kit.Herramienta"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Extras.GestorActividades"%>
<%@page import="Entidades.Paciente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssPaciente.css">
        <%!Paciente paciente;%><!--cuando agerguemos lo de las sesiones [cuando la DB esté correcta] se hará la llamada a la búsqueda, a partir del id que fue enviado por el setAttribute...[como en el perfil del ADMIN]-->
        <%!GestorActividades manager = new GestorActividades();%>
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();%>
        <%!Herramienta herramienta = new Herramienta();%>
        <%!Registro registrador = new Registro();%>              
        <%!int codigoExamen=-1;%>
    </head>
    <body>
        <div>                                                  
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                                
                <%!Examen[] examenes =(Examen[]) buscador.buscarEstructuras("Examen");%>                                
                <%if(examenes!=null){%>
                    <%for(int examenActual = 0; examenActual < examenes.length; examenActual++){
                        int codigoExamen = examenes[examenActual].darCodigo();%>
                        <p> <%=examenes[examenActual].darNombre()%>  $.<%=examenes[examenActual].darCosto()%><a href="AgendarExamen.jsp?codigoExamen=${codigoExamen}">ver</a></p>                    
                    <%}%><!--no olvides que esta query no provocará problemas, puesto que cada ves que se "llega a este jsp, se recarga el arreglo con los datos al cual le corresponde la unicación llevada, sin importar de qué forma llegue acá... [red o link...]"-->                     
                <%}%>
                <%if(request.getParameter("codigoExamen")!=null && examenes!=null){%><!--solo con lo del número basta puesto que si no hay listado desplegado, no tien oportunidad de seleccionar 1#; de tal forma que la var, siga siendo nula [en el request]...-->
                    <%codigoExamen = Integer.parseInt(request.getParameter("codigoExamen"));     
                    request.setAttribute("codigoExamen", codigoExamen);//de tal form aqu epueda obtener este valor el servlet gestor...
                }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
                        
                <%!String nombre=(codigoExamen!=-1)?examenes[codigoExamen].darNombre():"";%>
                <%!String descripcion=(codigoExamen!=-1)?examenes[codigoExamen].darDescripcion():"";%>                        
                
            </div>   
             <div id="areaInformacion">
                <p>
                    Nombre: <%=nombre%><br/><br/>
                    Descripcion: <%=descripcion%><br/>                        
                </p>
             </div>            
        </div><!--fin de la vita del lado iquierdo-->
        <div id='calendario'>            
            <form name="disponibilidadMedico" action="servletGestorActividades.java" method="GET"><!--puesto que el servlet tiene a un doGet.-->
                <center>
                    <input type='date' name="fecha" min="<%=java.time.LocalDate.now()%>" value="<%=java.time.LocalDate.now()%>" required>
                    <input type="submit" name="aceptarFecha" value="ACEPTAR">
                </center>
                    <div id="horasDisponibles" name="diasDsiponibles">
                        <%if(request.getParameter("numero")!=null){%><!--hago esto pues de esta manera aeguro que no vayana a fastidiar mi código :v xD..l pues al no estar no habrán errores por falta de datos...-->                        
                            <img src="img/agendacionEx.png" id="imagen">
                            <table>
                                <tr>
                                    <th>
                                        <h2>Fecha </h2>
                                    </th>
                                    <th>
                                        <h2>Hora</h2>
                                    </th>
                                </tr>
                                <tr>
                                    <th>
                                        <input id="fecha" type="date" name ="fecha" min="<%=java.time.LocalDate.now().toString()%>">
                                    </th>
                                    <th>
                                        <input type="number" id="hora" name="hora" min ="0" max="24">
                                    </th>
                                </tr>
                            </table>                                                    
                                    <center><input type="submit" name="agendarExamen" value="ACEPTAR" id="acciones"></center>
                        <%}%>
                    </div>
            </form>
        </div>
        
        
    </body>
</html>
