<%-- 
    Document   : index
    Created on : 28/09/2020, 22:25:39
    Author     : phily
--%>

<%@page import="Manejadores.DB.ManejadorDB"%>
<%@page import="Verificadores.VerificadorDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Home</title>
        <%! ManejadorDB manejadorDB  = new ManejadorDB();%>
        <% manejadorDB.conectarConDB();%>
        <%! VerificadorDB verificador = new VerificadorDB(manejadorDB.darConexion());%>
        
    </head>
    <body>      
          <%if(verificador.debeLlenarse()){%>        
                <%--redirecciono a la página de carga...--%>          
                <meta http-equiv="refresh" content="1"; url=cargaDatos.jsp">          
          <%}else{%>
            <form method="POST" action="index.jsp">
                    <input type="text" name="nombreUsuario" value="email">
                    <input type="password" name="contraseña" value="password">
                    <input type="submit" name="ingresar" value="INGRESAR">
                        <%--aquí el script para exe el método para corroboara los datos y redirigir, si es que todo salió bien en la verificación de los datos--%>
                    </br>
                    <hr>      
                    <br/>
                </form>              
            <div>
                <center>
                    <h1>HOSPITAL CARE</h1>                
                </center>            
            </div>        
        
            <div>
                <table>
                    <tr>
                        <th>
                            <img src ="img/doctorFondo.jpg" alt ="backgroundPresentation" height="500" width="1300">
                        </th>            
                        <th>
                            <form method="POST" action="index.jsp"><%--reccuerda que aún no le he colocado el action, puesto que si están llenos entoces rxn con la DB como corresponde y redirecciono a la página correspondiente...--%>
                                <table>
                                    <tr>
                                        <th>
                                            <input type="text" name="nombreReal" value="Nombre">
                                        </th>                                    
                                    </tr>
                                    <tr>
                                        <th>
                                            <h6>cumpleaños</h6>
                                        </th>                                                                   
                                    </tr>
                                    <tr>
                                        <th>
                                            <input type="number" name="diaCumple" value="01" min="1" max="31"><%--sería bueno que se le pudiera cb el límite según el número de mes, pero eso ya es algo terciario... --%>
                                        </th>     
                                        <th>
                                            <input type="number" name="mesCumple" value="01" min="1" max="12">
                                        </th>     
                                        <th>
                                            <input type="number" name="anioCumple" value="2020" min="1930" max="2020">
                                        </th>     
                                    </tr>
                                    <tr>
                                        <th>
                                            <h6>sexo</h6>
                                        </th>                                                                     
                                    </tr>
                                    <tr>
                                        <th>
                                            <input type="radio" name="sexo" value="mujer">
                                        </th>   
                                        <th>
                                            <input type="radio" name="sexo" value="hombre">
                                        </th>                                       
                                    </tr>
                                    <tr>                                   
                                        <th>
                                            <input type="number" name="peso" value="60" min="5"><h6>kg</h6>
                                        </th>                                                                              
                                        <th>
                                            <legend>tipo de sangre</legend>>
                                            <select name="tipoSanger">
                                                <option name="A+">A+</option>
                                                <option name="A-">A-</option>
                                                <option name="B+">B+</option>
                                                <option name="B-">B-</option>
                                                <option name="AB+">AB+</option>
                                                <option name="AB-">AB-</option>
                                                <option name="O+">O+</option>
                                                <option name="O-">O-</option>
                                            </select>
                                        </th>           
                                    </tr>
                                    <tr>                                   
                                        <th>
                                             <input type="email" name="correo" value="tucorreo@algo.com">
                                        </th>
                                        <th>
                                             <input type="password" name="constrasenia" value="tucontra">
                                        </th>
                                    </tr>                                 
                                    <tr>
                                        <input type="submit" name="registrar" value="REGISTRAR">
                                        <%--aquí el script para redirigir si es que todo salió bien en el registro de los datos
                                        de tal manera que al estar correcto todo, rediriga a la página correspondiente y de esa
                                        manera se "anule" la acción del form [es decir el lugar al que se dirige luego de haber 
                                        hecho lo que correspondía con los datos...--%>
                                        
                                    </tr>
                                </table>
                            </form>
                        
                        </th>
                    </tr>        
                </table>
            
            </div>
       <%}%><%--fin del else...--%>
    </body>
</html>
