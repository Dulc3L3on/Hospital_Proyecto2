<%-- 
    Document   : perfilAdministrador
    Created on : 4/10/2020, 00:37:59
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <center>  
        <h1>BIENVENIDO :3</h1><br/>
        <h4>CODIGO: </h4><br/>        
       
             <form method="POST" action="perfilAdministrador">
                <img src="img/administrador.png" id="fotografiaUsuario" alt="iconoMedico">
            
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="informacion" placeholder="nombre" required><br/>
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="informacion" placeholder="contrasenia" required><br/>                                 
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="datosUsuario" name="informacion" placeholder="DPI" maxlength="13"required><br/> 
                            </th>                            
                    </table>
                    <input type="submit" name="accion" value="ACEPTAR">    
                    <input type="submit" name="accion" value="CERRAR SESION">
             </form>
         </center>
    </body>
</html>
