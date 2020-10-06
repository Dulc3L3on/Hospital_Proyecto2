<%-- 
    Document   : adminLaboratorista
    Created on : 3/10/2020, 23:26:13
    Author     : phily
--%>

<%@page import="Manejadores.DB.Creacion"%>
<%@page import="Entidades.Laboratorista"%>
<%@page import="Entidades.Usuario"%>
<%@page import="Manejadores.DB.CreacionAdministrada"%>
<%@page import="Kit.Herramienta"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--no le pongo título, pues es una página interna...-->
        <link rel="stylesheet" href="cssAdmin.css">
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%><!--desaparecera-->
        <%!Modificacion modificador = new Modificacion();%><!--desaparecera-->
        <%!Creacion creador = new Creacion();%><!--desaparecera-->
        <%!CreacionAdministrada creacion = new CreacionAdministrada();%><!--desaparecera-->
        <%!Herramienta herramienta = new Herramienta();%><!--creo que tambien...-->
        <%!Usuario[] usuarios=buscador.buscarUsuarios("Laboratorista");%>
        <%!Laboratorista[] laboratorista;%>             
        <%!int numero=-1;%>
    </head>
    <body>   
        
        <div>
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                
                <%if(request.getParameter("numero")!=null && usuarios!=null){%><!--si pues la var con el mismo nombre será siempre -1 a menos que le asigne otro valor...-->
                    <%numero = Integer.parseInt(request.getParameter("numero"));
                    laboratorista=(Laboratorista[])usuarios;
                }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
                <%!String codigo=(numero!=-1)?String.valueOf(usuarios[numero].darCodigo()):"";%>
                <%!String nombreLaboratorista=(numero!=-1)?usuarios[numero].darNombre():"";%>
                <%!String contrasenia=(numero!=-1)?herramienta.desencriptarContrasenia(usuarios[numero].darDatosaPersonales().darContrasenia()):"";%>
                <%!String registroMS=(numero!=-1)?laboratorista[numero].darRegistroMS():"";%>
                <%!String DPI=(numero!=-1)?usuarios[numero].darDatosaPersonales().darDPI():"";%><!--como de todos modos los input dan y reciben String...-->
                <%!String telefono=(numero!=-1)?usuarios[numero].datosPersonales.darTelefono():"";%>
                <%!String codigoExamen = (numero!=-1)?String.valueOf(laboratorista[numero].darCodigoExamenAsignado()):"";%>
                <%!String correo=(numero!=-1)?usuarios[numero].datosPersonales.darCorreo():"";%>
                <%!String fechaIncorporacion=(numero!=-1)?laboratorista[numero].darFechaIncorporacion().toString():java.time.LocalDate.now().toString();%>                
                <%!boolean[] horario = (numero!=-1)?laboratorista[numero].darHorario():new boolean[7];%><!--puesto que el # de días no cambia...-->
                
                <%if(usuarios!=null){%>
                    <%for(int numeroUsuario=0; numeroUsuario<usuarios.length; numeroUsuario++){%>                
                        <p><%usuarios[numeroUsuario].darNombre();%><a href="customizacionMedico.jsp?numero=${numeroUsuario}">ver</a></p>
                        <br/>                
                    <%}%>
                <%}%>
            </div>    
            <form method="POST" action="customizacionLaboratorista.jsp" id="comandos">
                <%if(request.getParameter("opcion")!=null && numero!=-1) {
                    if(request.getParameter("opcion").equals("MODIFICAR")){
                        //se llaman al método para hacer la actualización en la tabla...
                        modificador.modificarDatosPersonales(Integer.parseInt(codigo), correo, contrasenia, telefono, DPI);
                        modificador.modificarLaboratorista(Integer.parseInt(codigo), nombreLaboratorista, registroMS, Integer.parseInt(codigoExamen), herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime()));
                        modificador.modificarHorarioLaboratorista(Integer.parseInt(codigo), herramienta.darDiasTrabajoLaboratoristas(request.getParameterValues("horario[]")));//no se si lso [] son parte del nombre o es un extra que solo le es útil al traductor...
                    }
                    if(request.getParameter("opcion").equals("CREAR")){
                        int codigoNuevoLaboratorista = creacion.crearLaboratorista(nombreLaboratorista, registroMS, codigoExamen, fechaIncorporacion, correo, contrasenia, telefono, DPI, herramienta.darDiasTrabajoLaboratorista(herramienta.darDiasTrabajoLaboratoristas(request.getParameterValues("horario"))));//croe que cb los rBtn a un cbBox... así trabajamos igual...
                        if(codigoNuevoLaboratorista!=0){
                            creador.crearHorarioLaboratorista(false, Integer.parseInt(codigo), herramienta.darDiasTrabajoLaboratorista(herramienta.darDiasTrabajoLaboratoristas(request.getParameterValues("horario[]"))));
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
            <form method="POST" action="customizacionLaboratorista.jsp">
                <img src="img/laboratorista.png" id="fotografia" alt="iconoLaboratorista">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datos"  name="informacion" placeholder="nombre" required><br/>
                            </th>
                            <th>
                                <input type="number" id="datos" name="informacion" placeholder="#registroMS" required><br/>                                                             
                            </th>                        
                            <th>
                                <input type="number" id="datos" name="informacion" placeholder="DPI" maxlength="13"required><br/> 
                            </th>
                             <th>
                                <select id="diasTrabajo" value="NonSelect"  name="horario" size="3" onlyread><!--aún no se si funcione el hecho de nombrarlo igual a la info o mejor que tenga uno propio... mejor la 2da op XD facilita más las cosas xD-->
                                    <option disabled="selected" value="..Horario..">..Horario..</option>                                    
                                    <%for(int dia=0; dia< horario.length; dia++){%>                                        
                                          <option value="${horario[dia]}">"${horario[dia]}"</option><!--aquí todo se coloca como string sin importar que tipo halla sido originalmente...-->
                                    <%}%>
                                </select>                                                                
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                <input type="email" id="datos" name="informacion" placeholder="correo" required><br/> 
                            </th>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="contrasenia" required><br/> 
                            </th>
                             <th>
                                 <input type="number" id="datos" name="informacion" placeholder="telefono" maxlength="8" required><br/> 
                            </th>
                            <th>
                               <select id="listado" name="horario[]" size="3" multiple><!--aún no se si funcione el hecho de nombrarlo igual a la info o mejor que tenga uno propio... mejor la 2da op XD facilita más las cosas xD-->                                                                                                                                         
                                    <option value="domingo">Domingo</option>
                                    <option value="lunes">Lunes</option>
                                    <option value="martes">Martes</option>
                                    <option value="miercoles">Miercoles</option>
                                    <option value="jueves">Jueves</option>
                                    <option value="viernes">Viernes</option>
                                    <option value="sabado">Sábado</option>
                                </select>                                                                     
                            </th>
                        </tr>                                     
                        <tr>
                            <th>
                                <input type="text" id="datos" name="informacion" placeholder="fechaInicio" required><br/> <!--aquí sería unvalue, que obtendrá el valor de la var respectiva, quien a su vez obtiene los datos del melemento [médico] seleccionado...-->
                            </th>
                            <th>
                                <input type="number" id="datos" name="informacion" placeholder="codigoExamen" required><br/>
                            </th>
                            
                        </tr>
                    </table>                             
                    <input type="submit" name="aceptar" value="ACEPTAR"><!--insisto, no es necesario...-->
                </center>                
            </form>            
        </div>
    </body>
</html>
