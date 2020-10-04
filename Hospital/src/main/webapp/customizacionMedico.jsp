<%-- 
    Document   : dashboardAdmin
    Created on : 3/10/2020, 20:28:29
    Author     : phily
--%>

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
        <%!Herramienta herramienta = new Herramienta();%>
        <%!Usuario[] usuarios;%>
        <%!Medico medico;%>
    </head>
    <body>              
        <%usuarios = buscador.buscarUsuarios("Medico");%>
        <div>             
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->
                <%!String nombreMedico="${nombre}}";%>
                <%!String contrasenia="${password}";%>
                <%!String colegiado="${colegiado}}";%>
                <%!String DPI="${DPI}";%><!--como de todos modos los input dan y reciben String...-->
                <%!String telefono="${telefono}";%>
                <%!String correo="${correo}";%>
                <%ListaEnlazada<String> especialidades=request.getParameter("titulos");%>
                <%!int numero=7;%>
                <%!int numeroQueRealmenteDeberiaSerElArrConLasEspRecuperadas=7;%>
                <%for(int numeroUsuario=0; numeroUsuario<usuarios.length; numeroUsuario++){%>
                <%medico=(Medico)usuarios[numeroUsuario];%>
                <p><%usuarios[numeroUsuario].darNombre();%> <a href="customizacionMedico.jsp?nombre=<%usuarios[numeroUsuario].darNombre();%>?password=<%herramienta.desencriptarContrasenia(usuarios[numeroUsuario].darDatosaPersonales().darContrasenia());%>?colegiado=${medico.darColegiado()}?DPI=${usuarios[numeroUsuario].darDatosaPersonales().darDPI()}?telefono=${usuarios[numeroUsuario].darDatosaPersonales().darTelefono()}?correo=${usuarios[numeroUsuario].darDatosaPersonales().darCorreo()}?=titulos<%medico.darTitulos();%>">ver</a></p>
                    <br/>
                    <%numero--;
                }%>
            </div>    
            <form method="POST" action="dashboardAdminMedico.jsp" id="comandos">
                <input type="submit" id="seleccion" name="seleccion" value="MODIFICAR">
                <input type="submit" id="seleccion" name="seleccion" value="CREAR">
                <input type="submit" id="seleccion" name="seleccion" value="ELIMINAR"><!--dependiendo del valor que tenga la petición de estos btn, se hará null las variables que obtienen los datos del médico del arreglo obtenido... ó se mandará a guaradar en la DB...-->
                <!--esto aún no tiene funcionalidad como tal... las queries son sencillas... creo xD, si, solo sería de usar DELETE...-->
            </form><!--pregunta, cuando se redirige a la misma página los datos del request son accesibles para cualquiera de susu componentes sin importar dónde se hallan generado?-->        
            
        </div>
        
        <div id="contenedorInformacion">
            <form method="POST" action="dashboardAdminMedico.jsp">
                <img src="img/iconoMedico.png" id="fotografiaUsuario" alt="iconoMedico">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="informacion" placeholder="nombre" value ="<%=nombreMedico%>" required><br/>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="informacion" placeholder="contrasenia" required><br/> 
                            </th>                        
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="informacion" placeholder="#colegiado" required><br/>                             
                            </th>
                            <th>
                                <input type="number" id="datosUsuario" name="informacion" placeholder="DPI" maxlength="13"required><br/> 
                            </th>
                        </tr>
                        <tr>
                            <th>
                                 <input type="number" id="datosUsuario" name="informacion" placeholder="telefono" maxlength="8" required><br/> 
                            </th>
                            <th>
                                 <input type="text" id="datosUsuario" name="informacion" placeholder="fechaActual" required><br/> <!--aquí sería unvalue, que obtendrá el valor de la var respectiva, quien a su vez obtiene los datos del melemento [médico] seleccionado...-->
                            </th>
                        </tr>        
                         <tr>
                            <th>
                                 <input type="email" id="datosUsuario" name="informacion" placeholder="correo" required><br/> 
                            </th>                            
                        </tr>   
                    </table>
                     <select id="listado" name="especialidades"><!--aún no se si funcione el hecho de nombrarlo igual a la info o mejor que tenga uno propio... mejor la 2da op XD facilita más las cosas xD-->
                        <%while(numeroQueRealmenteDeberiaSerElArrConLasEspRecuperadas>0){%>
                            <option>numeroQueRealmenteDeberiaSerElArrConLasEspRecuperadas--</option>
                        <%numeroQueRealmenteDeberiaSerElArrConLasEspRecuperadas--;}%>
                    </select><br/>                                                                
                    <input type="text" id="especialidades" name="informacion" placeholder="especialidades" required><br/><!--aún no se como agregarlas cuando haga la selección...-->
                
                    <input type="submit" name="aceptar" value="ACEPTAR">
                </center>                
            </form>            
        </div>
    </body>
</html>
