<%-- 
    Document   : cargaDatos
    Created on : 28/09/2020, 18:00:07
    Author     : phily
--%>

<%@page import="Manejadores.Archivos.ManejadorDeArchivos"%>
<%@page import="Verificadores.VerificadorDB"%>
<%@page import="Manejadores.DB.ManejadorDB"%>
<%@page import="java.sql.Connection"%>
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
    <body><!--OJO: debes tener cuidado en no nombrar las variables del servlet desde dónde se redirigió a esta pág [con otro servlet definido...] pues si no sobreentenderá que te refieres a la que se encuentra en el lugar de donde salió para llegar aquí...-->    
        <%!ManejadorDB manejador = new ManejadorDB();//vamos a probar creando una nueva conexion... por el hecho de no saber si cuando se trata con sesiones, debe mantenerse la misma desde principio a fin o debe ser la misma hasta que se logue... esto lo digo porque me parece muy bien usar el patrón singleton
        ManejadorXML manejadorXML = new ManejadorXML();
        VerificadorDB verificador = new VerificadorDB(manejador.darConexion());
        ManejadorDeArchivos manejadorArchivos = new ManejadorDeArchivos();
        String nombreArchivoXML;
        boolean permisoCarga=false;
       
        %>                
            <center>
                <h1>CARGA DE DATOS</h1>
                <h4>Presione aceptar para abastecer el sistema hospitalario
                    </br>con la información necesaria para su funcionamiento</h4>
                <br/>            
            </center>        
             <form name="muestraCarga" method="POST" action="cargaDatos.jsp" enctype="multipart/form-data">
                <center>                                        
                    <table>
                        <tr>
                            <th>
                                <input type="file" name="cargaArchivo" accept=".xml" value="XML"required>
                            </th>
                            <th>
                                <button type="submit" name="cargaXML" value="ACEPTAR" ONCLICK="cambiarValorParaCarga()">SUBIR XML</button>
                            </th>
                        </tr>                       
                        
                    </table>                                                            
                    <!--aquí debería ir algo como un txtA no editable, para que pueda mostrar los msjes... respecto a la carga de datos...-->
                    <br/>
                    <br/>
                    <p style="background-color: AliceBlue;">
                        <%if(permisoCarga){                            
                            nombreArchivoXML = request.getParameter("cargaArchivo");
                            //Se manda a llmar al método para guardar
                            manejadorArchivos.guardarArchivosCargados(request);
                            manejadorXML.leerXML(verificador.darListadoVacios(), manejadorArchivos.darRutaXML());                                                         
                        }                            
                            permisoCarga=true;
                        %>                        
                        <%--Aquí se llamaría al método para devolver el listado de errores que surgieron en el proceso de carga--%>
                    </p>                                     
                </center>                               
            </form>   
            <center>
                <%if(!verificador.debeLlenarse()){%><%--lo malo de hacer esto es que con 1 que no tenga mínimo 1 registro, no será perimitido que regrese y aunque volviera a ingresar la URL lo redirigiría a esta página cada vez....--%>                
                    <a href="index.jsp">HOME</a>
                <%}else{%>
                    <a href="cargaDatos.jsp">REINTENTAR</a>
                <%}%>
            </center>             
            <script languaje="JavaScript">
                        function cambiarValorParaCarga(){
                            document.getElementsByName("carga").value="ACEPTADO";                              
                        }    
            </script>   
            
    </body>
</html>
