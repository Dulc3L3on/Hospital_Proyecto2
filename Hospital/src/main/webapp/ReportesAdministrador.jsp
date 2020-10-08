<%-- 
    Document   : ReportesAdministrador
    Created on : 7/10/2020, 17:33:12
    Author     : phily
--%>

<%@page import="Kit.Herramienta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">      
        <link rel ="stylesheet" href="cssPaciente.css">  
          <%!Herramienta herramienta = new Herramienta();
           String pagina;%>
    </head>
    <body>       
        <div id="reportes">      
            
            <%pagina = herramienta.darPaginaReporteAlAdmin(request.getParameter("opcion"));%>     
            <form method="POST" action="ReportesAdministrador.jsp"><!--si estableces un valor con el setAttribute, sin importar a que exista un iframe, por estar dentro de la pág... este tb tendrá acceso al valor???-->
                <table>                            
                <tr>
                    <input type="submit" name="opcion" id="acciones" value="IngresosMedico">
                </tr>
                <tr>
                    <input type="submit" name="opcion" id="acciones" value="IngresosPaciente">
                </tr>
                <tr>
                    <input type="submit" name="opcion" id="acciones" value="ProductividadMedica">
                </tr>
                <tr>
                    <input type="submit" name="opcion" id="acciones" value="DemandaExamenes">
                </tr>
                <tr>
                    <input type="submit" name="opcion" id="acciones" value="SolicitudExamenes">
                </tr>                               
                </table>
            </form>
            
            <iframe src="<%=pagina%>" title="customizacion" id="framePaciente"></iframe>
            
        </div>
    </body>
</html>
