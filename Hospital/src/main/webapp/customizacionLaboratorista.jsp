<%-- 
    Document   : adminLaboratorista
    Created on : 3/10/2020, 23:26:13
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--no le pongo título, pues es una página interna...-->
        <link rel="stylesheet" href="cssAdmin.css">
    </head>
    <body>      
        <div>
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->
                <%!int numero=7;%>
                <%!int numeroQueRealmenteDeberiaSerElArrConLasEspRecuperadas=7;%>
                <%while(numero>0){%>
                    <p>nombre entidad <a href="">link con query string</a></p>
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
                <img src="img/pseudoLaboraatorista.png" id="fotografiaUsuario" alt="iconoMedico">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="informacion" placeholder="nombre" required><br/>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="informacion" placeholder="contrasenia" required><br/> 
                            </th>                        
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="datosUsuario" name="informacion" placeholder="#registroMS" required><br/>                             
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
                    <div id="listado">
                        <input type="radio" value="domingo">
                        <input type="radio" value="lunes"><br/>
                        <input type="radio" value="martes">
                        <input type="radio" value="miercoles"><br/>
                        <input type="radio" value="jueves">
                        <input type="radio" value="viernes"><br/>
                        <input type="radio" value="sabado">
                    </div>                    
                    <input type="submit" name="aceptar" value="ACEPTAR">
                </center>                
            </form>            
        </div>
    </body>
</html>
