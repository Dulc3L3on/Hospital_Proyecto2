<%-- 
    Document   : HomePaciente
    Created on : 5/10/2020, 22:01:31
    Author     : phily
--%>

<%@page import="Kit.Herramienta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paciente</title>
        <link rel ="stylesheet" href="cssPaciente.css">        
        <%!Herramienta herramienta = new Herramienta();
           String pagina;%>
    </head>        
    <body>        
        
        <%pagina = herramienta.darPaginaAlPaciente(request.getParameter("opcion"));%>              
         <form method="POST" accion="HomePaciente">
            <input type="submit" id="acciones" name="opcion" value="AgendarCitaMedica" >
            <input type="submit" id="acciones" name="opcion" value="AgendarExamen">
            <input type="submit" id="acciones" name="opcion" value="VerHistorial">            
            <a href="HomePaciente.jsp"><img src="img/iconoPerfil3.png" width="50" height="50" id="perfil"></a>
            <br/>                
            <hr>
        </form><!--te recuerdas de app un color tenue a esta barra quizá celeste o salmon xD-->                
        
        <iframe src="<%=pagina%>" title="customizacion" id="framePaciente">           
        </iframe>
        
    </body>
</html>
