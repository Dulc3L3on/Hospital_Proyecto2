<%-- 
    Document   : index
    Created on : 28/09/2020, 22:25:39
    Author     : phily
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="Manejadores.DB.Registro"%>
<%@page import="Manejadores.DB.Sesion"%>
<%@page import="Kit.Herramienta"%>
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
        <%! Herramienta herramienta = new Herramienta();%>        
        <%!Sesion sesion = new Sesion(manejadorDB.darConexion());%>        
        <%LocalDate hoy = LocalDate.now();%>
    </head>
    <body>      
          <%if(verificador.debeLlenarse()){%>        
                <%--redirecciono a la página de carga...--%>          
                <meta http-equiv="refresh" content="1"; url="cargaDatos.jsp">        <!--tambien podría hacerse directamente con un response.senRedirect("a donde se redirijirá...")-->                  
          <%}else{
                if(request.getParameter("aceptacion").equals("INGRESAR") && verificador.verificarLogueo(request.getParameter("tipoUsuario"), request.getParameterValues("logueo"))!=null){
                    //se manda el objeto tipo usuario generado...
                    response.sendRedirect(herramienta.darPaginaPerfil(request.getParameter("tipoUsuario")));//se obtiene el usario del cbBox... que tiene por default paciente...       

                %>
                <%}if(request.getParameter("aceptacion").equals("REGISTRAR") && verificador.verificarRegistro("Paciente", request.getParameterValues("registro"))){%><%--lo mismo digo aquí...--%>                                     
                    <!--se manda a llamar al método para hacer el registro-->
                    
                    
                <%}%>
            <form method="POST" action="index.jsp">
                    <select name="tipoUsuario">
                        <option name="Paciente">Paciente</option>
                        <option name="Administrador">Administrador</option>
                        <option name="Medico">Medico</option>
                        <option name="Laboratorista">Laboratorista</option>                        
                    </select>
                    <input type="text" name="logueo" placeholder="email" required><%--vamos a probar agrupando los btn... no creo que de problemas puesto que todo lo trata como str... sino pues vuelve a separarlos...--%>
                    <input type="password" name="logueo" placeholder="password" required>
                    <input type="submit" name="aceptacion" value="INGRESAR">                      
                        <%--no sería necesario un script... pues podrías revisar con un mpetodo de verficación para el logueo si sale bien, redireccionar con el response y si no regresar a la página y mostrar un msje indicando que no ingresó correctamente los datos...--%>
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
                                            <input type="text" name="registro" placeholder="Nombre" required>
                                        </th>                                    
                                        <th>
                                            <h6>tel: </h6>
                                            <input type="tel" name="registro" placeholder="telefono" min="0" required><!--recuerda, si quieres especificarle tamaño deberá ser en el CSS-->
                                        </th>                                    
                                    </tr>
                                    <tr>
                                        <th>
                                            <h6>cumpleaños</h6>
                                        </th>                                                                   
                                    </tr>
                                    <tr>
                                        <th>
                                             <input type="date" name="registro" value <%=hoy%> max <%=hoy%>><%--sería bueno que se le pudiera cb el límite según el número de mes, pero eso ya es algo terciario... --%>                                                                                       
                                        </th>
                                        <th>
                                             <input type="number" name="registro" placeholder="DPI" min="0" required>
                                        </th><!--si da error en esta línea es por la forma en que asigne...-->     
                                        
                                    </tr>                                    
                                    <tr>
                                        <th>
                                           <select name="registro"><%--si da error, a de ser porque tiene el mismo nombre y no puede ser tratado como los input, para agrupar y escoger...--%>
                                                <option name="femenino">femenino</option>
                                                <option name="masculino">masculino</option>                                                
                                            </select>
                                        </th>                                                                     
                                        <th>
                                            <input type="number" name="registro" placeholder="peso" min="5" required><h6>kg</h6>
                                        </th>                                                                              
                                        <th>
                                            <legend>tipo de sangre</legend>>
                                            <select name="registro"><%--si llegara a dar error es porque este es un listado y por ello hay que explicitar que se quiere una seleccion...--%>
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
                                            <input type="email" name="registro" palceholder="tucorreo@algo.com" required>
                                        </th>
                                        <th>
                                             <input type="password" name="registro" required>
                                        </th>
                                    </tr>                                 
                                    <tr>
                                        <input type="submit" name="aceptacion" value="REGISTRAR">                                      
                                        
                                        <%--para este de registro, salga bien o no debo mantenerlo
                                        en esta página para que después decida loguearse :3 --%>
                                        
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
