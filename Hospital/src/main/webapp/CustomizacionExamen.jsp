<%-- 
    Document   : Examen
    Created on : 4/10/2020, 20:54:00
    Author     : phily
--%>

<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Manejadores.DB.CreacionAdministrada"%>
<%@page import="Documentacion.Examen"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Documentacion.Estructura"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssAdmin.css">     
        <%!BusquedaGeneral buscador = new BusquedaGeneral();%>
        <%!Estructura[] estructura = buscador.buscarEstructuras("Examen");%>
        <%!Examen[] examen;%>
        <%!Modificacion modificador = new Modificacion();%>
        <%!CreacionAdministrada creacion = new CreacionAdministrada();%>
        <%!int numero=-1;%>
    </head>
    <body>
       
        <div>              
            <div id="listaElementos"><!--aquí le definirás su ancho, puesto que su largo dependerá del iframe..., además del colorcito xD o al menos el borde xD-->                                
                <%if(request.getParameter("numero")!=null && estructura!=null){%><!--si pues la var con el mismo nombre será siempre -1 a menos que le asigne otro valor...-->
                    <%numero = Integer.parseInt(request.getParameter("numero"));
                     examen=(Examen[])estructura;
                 }%><!--esta condición es suficiente, puesto que si no hay usuarios, no habrá posibilidad de generar un número...-->
                <%!String codigo=(numero!=-1)?String.valueOf(examen[numero].darCodigo()):"";%>
                <%!String nombre=(numero!=-1)?estructura[numero].darNombre():"";%>                
                <%!String requiereOrden=(numero!=-1)?String.valueOf(examen[numero].darRequerimiento()):"";%>                
                <%!String descripcion=(numero!=-1)?examen[numero].darDescripcion():"";%>                
                <%!String costo=(numero!=-1)?estructura[numero].darCosto():"";%>                
                <%!String extensionResultado=(numero!=-1)?examen[numero].darExtensionResultado():"";%>                
                
                <%if(estructura!=null){%>
                    <%for(int numeroEstructura=0; numeroEstructura<estructura.length; numeroEstructura++){%>                
                        <p><%estructura[numeroEstructura].darNombre();%><a href="CustomizacionExamen.jsp?numero=${numeroEstructura}">ver</a></p>
                        <br/>                
                    <%}%>
                <%}%>
            </div>    
            <form method="POST" action="CustomizacionExamen.jsp" id="comandos">                
                <%if(request.getParameter("opcion")!=null && numero!=-1) {
                    if(request.getParameter("opcion").equals("MODIFICAR")){
                        //se llaman al método para hacer la actualización en la tabla...
                        modificador.modificarExamen(Integer.parseInt(codigo), nombre,String.valueOf(requiereOrden), descripcion, costo, extensionResultado);
                    }
                    if(request.getParameter("opcion").equals("CREAR")){
                        creacion.crearExamen(nombre, requiereOrden, descripcion, costo, extensionResultado);
                    }//los contenidos de ambos if, serán reemplazados por los métodos del admin que reune a cada uno en su bloque correspondiente...
                 }%>
                <input type="submit" id="seleccion" name="opcion" value="MODIFICAR">
                <input type="submit" id="seleccion" name="opcion" value="CREAR">
                <input type="submit" id="seleccion" name="opcion" value="ELIMINAR"><!--dependiendo del valor que tenga la petición de estos btn, se hará null las variables que obtienen los datos del médico del arreglo obtenido... ó se mandará a guaradar en la DB...-->
                <!--esto aún no tiene funcionalidad como tal... las queries son sencillas... creo xD, si, solo sería de usar DELETE...-->
            </form><!--pregunta, cuando se redirige a la misma página los datos del request son accesibles para cualquiera de susu componentes sin importar dónde se hallan generado?-->        
            
        </div>
        
        <div id="contenedorInformacion">            
            <h3>CÓDIGO: <%=codigo%></h3>
            <form method="POST" action="CustomizacionExamen.jsp"><!--pienso que no habrá problemas con el hecho de que estén en diferente for los btn y los campos, puesto que están las variables, las cuales son de la página entera, no de un form o div en particular,y al ser empleadoas por ambos form, existe una conexión entre ellos...-->
                <img src="img/reporteMedico.png" id="fotografia" alt="iconoExamen">
                <center>
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datos"  name="informacion" placeholder="nombre" value ="<%=nombre%>" required><br/>
                            </th>                         
                            <th>
                                <label for="datosEstructura">$. </label><input type="number" id="datos" name="informacion" placeholder="$precio" value ="<%=costo%>" required><br/>                                                             
                            </th>                        
                            <th>
                                <select id="listado" name="requerimiento" required>
                                    <option value="true">si</option>
                                    <option value="false">no</option>                                    
                                </select>
                            </th>                        
                        </tr> 
                        <tr>
                            <th colspan="2">
                                <textarea required placeholder="descripcion" rows="15" colum="35"><%=descripcion%></textarea>
                            </th>                            
                            <th>
                               <select id="listado" name="requerimiento" required>
                                    <option value="img">img</option>
                                    <option value="pdf">PDF</option>                                    
                                </select> 
                            </th>
                        </tr>
                    </table>                                            
                    <input type="submit" name="aceptar" value="ACEPTAR"><!--no se para que tengo este btn, puesto que con lso de modificar y crear basta, además bforman un solo form...-->
                </center>                
            </form>            
        </div>
    </body>
</html>
