<%-- 
    Document   : paginaAdministrador
    Created on : 1/10/2020, 20:50:13
    Author     : phily
--%>

<%@page import="Kit.Herramienta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador</title>
        <link rel ="stylesheet" href="cssAdmin.css">        
        <%!Herramienta herramienta = new Herramienta();
           String pagina;%>
    </head>        
    <body>        
   
        <%pagina = herramienta.darPaginaAlAdministrador(request.getParameter("opcion"));%>     
        <form method="POST" accion="HomeAdministrador">
            <input type="submit" id="opcionesEntidades" name="opcion" value="Medico" >
            <input type="submit" id="opcionesEntidades" name="opcion" value="Paciente" >
            <input type="submit" id="opcionesEntidades" name="opcion" value="Laboratorista" >
            <input type="submit" id="opcionesEntidades" name="opcion" value="Examen" >
            <input type="submit" id="opcionesEntidades" name="opcion" value="Consulta" >            
            <a href="HomeAdministrador.jsp"><img src="img/iconoPerfil3.png" width="50" height="50" id="perfil"></a><!--aquí irá la dirección a la página en la que podrán ver sus perfiles...-->
            <br/>
            <hr>                   
        </form><!--te recuerdas de app un color tenue a esta barra quizá celeste o salmon xD-->
        
        <iframe src="<%=pagina%>" title="customizacion" id="frameAdministrador">           
        </iframe>
        
    </body>
</html>
