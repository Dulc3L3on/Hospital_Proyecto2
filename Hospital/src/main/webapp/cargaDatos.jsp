<%-- 
    Document   : cargaDatos
    Created on : 28/09/2020, 18:00:07
    Author     : phily
--%>

<%@page import="Manejadores.Archivos.ManejadorXML"%>
<%--A esta página será dirigido el usuario para que pueda llenar
la DB, siempre y cuando esté vacía... esto por medio del if--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LlenadoDB</title>
        <script src = "acciones.js"></script>
        
        <!--Aquí debería hacer la recpeción del objeto conexion... para mandarselo al manejadorXML quien será el único en importarse, puesto que dentro de sí tiene a las clases que tiene todo lo que requiere :3-->
       
    </head>
    <body>    
        <%if(request.getParameter("aceptacion").equalsIgnoreCase("ACEPTADO")){%><!--a mi parecer, como aún no se ha creado el btn entonces al tener "ACEPTAR" como la comparación no habrá problema, puesto que aún no existe el componente...-->
        
        
        <%}else{%>
        
            <center>
                <h1>CARGA DE DATOS</h1>
                <h4>Presione aceptar para abastecer el sistema hospitalario
                    </br>con la información necesaria para su funcionamiento</h4>
                <br/>            
            </center>        
             <form method="POST" action="cargaDatos.jsp">
                <center>
                    <input type="submit" name="aceptacion" value="ACEPTAR" onclick="cambiarValorParaCarga()">
                </center>            
            </form>   
        

    </body>
</html>
