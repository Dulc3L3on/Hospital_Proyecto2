<%-- 
    Document   : Consulta
    Created on : 4/10/2020, 20:54:15
    Author     : phily
--%>

<%@page import="Manejadores.DB.Creacion"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Documentacion.Estructura"%>
<%@page import="Documentacion.Consulta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssAdmin.css">        
        <%!Estructura[] estructura;%>
        <%!Consulta[] consulta;%>
        <%!Modificacion modificador = new Modificacion();%>
        <%!Creacion creador = new Creacion();%>
        <%!int numero=-1;%>
    </head>
    <body>
        
        <div>             
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                                
                <%if(request.getParameter("numero")!=null && estructura!=null){%><!--si pues la var con el mismo nombre será siempre -1 a menos que le asigne otro valor...-->
                    <%numero = Integer.parseInt(request.getParameter("numero"));
                     consulta=(Consulta[])estructura;
                 }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
                <%!String tipo=(numero!=-1)?estructura[numero].darNombre():"";%>
                <%!String costo=(numero!=-1)?estructura[numero].darCosto():"";%>                
                
                <%if(estructura!=null){%>
                    <%for(int numeroEstructura=0; numeroEstructura<estructura.length; numeroEstructura++){%>                
                        <p><%estructura[numeroEstructura].darNombre();%><a href="customizacionConsulta.jsp?numero=${numeroEstructura}">ver</a></p>
                        <br/>                
                    <%}%>
                <%}%>
            </div>    
            <form method="POST" action="customizacionConsulta.jsp" id="comandos">
                <%if(request.getParameter("opcion")!=null && numero!=-1) {
                    if(request.getParameter("opcion").equals("MODIFICAR")){
                        //se llaman al método para hacer la actualización en la tabla...
                        modificador.modificarConsulta(tipo, costo);
                    }
                    if(request.getParameter("opcion").equals("CREAR")){
                        creador.crearConsulta(tipo, costo);
                    }//los contenidos de ambos if, serán reemplazados por los métodos del admin que reune a cada uno en su bloque correspondiente...
                 }%>
                <input type="submit" id="seleccion" name="opcion" value="MODIFICAR">
                <input type="submit" id="seleccion" name="opcion" value="CREAR">
                <input type="submit" id="seleccion" name="opcion" value="ELIMINAR"><!--dependiendo del valor que tenga la petición de estos btn, se hará null las variables que obtienen los datos del médico del arreglo obtenido... ó se mandará a guaradar en la DB...-->
                <!--esto aún no tiene funcionalidad como tal... las queries son sencillas... creo xD, si, solo sería de usar DELETE...-->
            </form><!--pregunta, cuando se redirige a la misma página los datos del request son accesibles para cualquiera de susu componentes sin importar dónde se hallan generado?-->        
            
        </div>
        
        <div id="contenedorInformacion">            
            <form method="POST" action="customizacionConsulta.jsp"><!--pienso que no habrá problemas con el hecho de que estén en diferente for los btn y los campos, puesto que están las variables, las cuales son de la página entera, no de un form o div en particular,y al ser empleadoas por ambos form, existe una conexión entre ellos...-->
                <img src="img/consultaMedica.jpeg" id="fotografia" alt="iconoConsulta">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datos"  name="informacion" placeholder="tipoConsulta" value ="<%=tipo%>" required><br/>
                            </th>                         
                            <th>
                                <label for:"datosEstructutra">$. </label><input type="number" id="datos" name="informacion" placeholder="$precio" value ="<%=costo%>" required><br/>                                                             
                            </th>                        
                        </tr>   
                    </table>                                            
                    <input type="submit" name="aceptar" value="ACEPTAR"><!--no se para que tengo este btn, puesto que con lso de modificar y crear basta, además bforman un solo form...-->
                </center>                
            </form>            
        </div>
    </body>
</html>
