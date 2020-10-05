<%-- 
    Document   : dashboardAdmin
    Created on : 3/10/2020, 20:28:29
    Author     : phily
--%>

<%@page import="Manejadores.DB.CreacionAdministrada"%>
<%@page import="Entidades.Administrador"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Kit.Nodo"%>
<%@page import="Kit.ListaEnlazada"%>
<%@page import="Entidades.Medico"%>
<%@page import="Kit.Herramienta"%>
<%@page import="Entidades.Usuario"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--no le pongo título, pues es una página interna...-->
        <link rel="stylesheet" href="cssAdmin.css">
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!Modificacion modificador = new Modificacion();%>
        <%!CreacionAdministrada creacion = new CreacionAdministrada();%>
        <%!Herramienta herramienta = new Herramienta();%>
        <%!Usuario[] usuarios=buscador.buscarUsuarios("Medico");%>
        <%!Medico medico[];%>
        <%!Administrador administrador;%><!--esto mientras no revise lo de las seciones... pues puede que se haga como había pensado... transportar el codigo y aquí hacer la búsque, o recibir el objeto completo, ó como lo hizo el auxi xd... [lo cual aún no he visto :1 xD-->
        <%!ListaEnlazada<String> especialidades = buscador.darEspecialidades();%>
        <%!int numero=-1;%>
    </head>
    <body>              
        
        <div>             
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                                
                <%if(request.getParameter("numero")!=null && usuarios!=null){%><!--si pues la var con el mismo nombre será siempre -1 a menos que le asigne otro valor...-->
                    <%numero = Integer.parseInt(request.getParameter("numero"));
                    medico=(Medico[])usuarios;
                 }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
                <%!String codigo=(numero!=-1)?String.valueOf(usuarios[numero].darCodigo()):"";%>
                <%!String nombreMedico=(numero!=-1)?usuarios[numero].darNombre():"";%>
                <%!String contrasenia=(numero!=-1)?herramienta.desencriptarContrasenia(usuarios[numero].darDatosaPersonales().darContrasenia()):"";%>
                <%!String colegiado=(numero!=-1)?medico[numero].darColegiado():"";%>
                <%!String DPI=(numero!=-1)?usuarios[numero].darDatosaPersonales().darDPI():"";%><!--como de todos modos los input dan y reciben String...-->
                <%!String telefono=(numero!=-1)?usuarios[numero].datosPersonales.darTelefono():"";%>
                <%!String correo=(numero!=-1)?usuarios[numero].datosPersonales.darCorreo():"";%>
                <%!String fechaIncorporacion=(numero!=-1)?medico[numero].darFechaIncorporacion().toString():java.time.LocalDate.now().toString();%>
                <%!String horaInicio = (numero!=-1)?String.valueOf(medico[numero].darHoraInicio()):"";%>
                <%!String horaFin = (numero!=-1)?String.valueOf(medico[numero].darHoraFin()):"";%>
                <%!ListaEnlazada<String> titulos=(numero!=-1)?medico[numero].darTitulos():new ListaEnlazada();%>
                
                <%if(usuarios!=null){%>
                    <%for(int numeroUsuario=0; numeroUsuario<usuarios.length; numeroUsuario++){%>                
                        <p><%usuarios[numeroUsuario].darNombre();%><a href="customizacionMedico.jsp?numero=${numeroUsuario}">ver</a></p>
                        <br/>                
                    <%}%>
                <%}%>
            </div>    
            <form method="POST" action="customizacionMedico.jsp" id="comandos">
                <%if(request.getParameter("opcion")!=null && numero!=-1) {
                    if(request.getParameter("opcion").equals("MODIFICAR")){
                        //se llaman al método para hacer la actualización en la tabla...
                        modificador.modificarDatosPersonales(Integer.parseInt(codigo), correo, contrasenia, telefono, DPI);
                        modificador.modificarMedico(Integer.parseInt(codigo), nombreMedico, colegiado, Integer.parseInt(horaInicio), Integer.parseInt(horaFin), herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime()));
                        modificador.modificarTitulario(Integer.parseInt(codigo), titulos, request.getParameterValues("especialidades"));
                    }
                    if(request.getParameter("opcion").equals("CREAR")){
                        int codigoNuevoMedico = creacion.crearMedico(nombreMedico, colegiado, horaInicio, horaFin, fechaIncorporacion, correo, contrasenia, telefono, DPI);
                        if(codigoNuevoMedico!=0){
                            creacion.crearTitulos(codigoNuevoMedico, request.getParameterValues("especialidades"));                        
                        }                        
                    }//los contenidos de ambos if, serán reemplazados por los métodos del admin que reune a cada uno en su bloque correspondiente...
                 }%>
                <input type="submit" id="seleccion" name="opcion" value="MODIFICAR">
                <input type="submit" id="seleccion" name="opcion" value="CREAR">
                <input type="submit" id="seleccion" name="opcion" value="ELIMINAR"><!--dependiendo del valor que tenga la petición de estos btn, se hará null las variables que obtienen los datos del médico del arreglo obtenido... ó se mandará a guaradar en la DB...-->
                <!--esto aún no tiene funcionalidad como tal... las queries son sencillas... creo xD, si, solo sería de usar DELETE...-->
            </form><!--pregunta, cuando se redirige a la misma página los datos del request son accesibles para cualquiera de susu componentes sin importar dónde se hallan generado?-->        
            
        </div>
        
        <div id="contenedorInformacion">
            <h3>CÓDIGO: <%=codigo%></h3>
            <form method="POST" action="customizacionMedico.jsp"><!--pienso que no habrá problemas con el hecho de que estén en diferente for los btn y los campos, puesto que están las variables, las cuales son de la página entera, no de un form o div en particular,y al ser empleadoas por ambos form, existe una conexión entre ellos...-->
                <img src="img/iconoMedico.png" id="fotografia" alt="iconoMedico">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datos"  name="informacion" placeholder="nombre" value ="<%=nombreMedico%>" required><br/>
                            </th>
                            <th>
                                 <input type="number" id="datos" name="informacion" placeholder="#colegiado" value ="<%=colegiado%>" required><br/>                                                             
                            </th>                        
                            <th>
                                <input type="number" id="datos" name="informacion" placeholder="DPI" maxlength="13" value ="<%=DPI%>" required><br/> 
                            </th>
                            <th rowspan="2">
                                <select id="listado" value="NonSelect"  name="especialidades[]" sizae="3" multiple><!--aún no se si funcione el hecho de nombrarlo igual a la info o mejor que tenga uno propio... mejor la 2da op XD facilita más las cosas xD-->
                                    <option disabled="selected" value="Especialidades">..Especialidades..</option>
                                    <%!Nodo<String> nodoAuxiliar = especialidades.obtnerPrimerNodo();%>
                                    <%for(int especialidadActual=1; especialidadActual<= especialidades.darTamanio(); especialidadActual++){%>
                                        <%if(!herramienta.esRedundante(titulos, nodoAuxiliar.contenido)){%> 
                                            <option value="${nodoAuxiliar.contenido}">"${nodoAuxiliar.contenido}"</option>
                                        <%}%>    
                                        <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                                    }%>
                                </select>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="email" id="datos" name="informacion" placeholder="correo" value ="<%=correo%>" required><br/>                                
                            </th>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="contrasenia" value ="<%=contrasenia%>" required><br/> 
                            </th>
                            <th>
                                 <input type="number" id="datos" name="informacion" placeholder="telefono" maxlength="8" value ="<%=telefono%>" required><br/> 
                            </th>                           
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="InicioTrabajo" value ="<%=fechaIncorporacion%>" required><br/> <!--aquí sería unvalue, que obtendrá el valor de la var respectiva, quien a su vez obtiene los datos del melemento [médico] seleccionado...-->
                            </th>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="hora inicio" value ="<%=horaInicio%>" required><br/>  
                            </th>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="hora fin" value ="<%=horaFin%>" required><br/>  
                            </th>
                             <th>
                                <select name="titulos">
                                    <%!Nodo<String> nodoTitulos = titulos.obtnerPrimerNodo();%>
                                    <%for(int tituloActual=1; tituloActual<=titulos.darTamanio(); tituloActual++){%>
                                        <option value="${nodoTitulos.contenido}">${nodoTitulos.contenido}</option>
                                        <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                                    }%>
                                </select>
                            </th>
                        </tr>                                
                    </table>                                            
                    <input type="submit" name="aceptar" value="ACEPTAR"><!--no se para que tengo este btn, puesto que con lso de modificar y crear basta, además bforman un solo form...-->
                </center>                
            </form>            
        </div>
    </body>
</html>
