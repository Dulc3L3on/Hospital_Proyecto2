<%-- 
    Document   : perfilAdministrador
    Created on : 4/10/2020, 00:37:59
    Author     : phily
--%>

<%@page import="Kit.Herramienta"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page import="Manejadores.DB.BusquedaGeneral"%>
<%@page import="Entidades.Administrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body> 
        <!--aquí NUNCA sucederá lo del nullpnter por el hecho de que en el home, se verificó que existriera un id [y que cumpliera con los req dados] para que pueda acceder a cualquiera de la cascada de páginas dependientes del HOme...-->
        <!--pero como por el momento no lo tengo implementado, entonces si lo da xD-->
        <%!BusquedaEspecifica buscador = new BusquedaEspecifica();%>
        <%!Modificacion modificador = new Modificacion();%>
        <%!Herramienta herramienta = new Herramienta();%>
        <%!int id = -1;%>
        <%//id =Integer.parseInt(request.getParameter("id"));%>
        <%!Administrador administrador = (Administrador)buscador.buscarUnUsuario("Administrador", id);%>
        <!--aquí en base al id recibido por medio de la sesión, se hace la búsqueda del admin en este caso y se obtiene susu valores, detal forma que su existencia en java como en la DB estén de acuerdo...-->
        <center>  
        <h1>BIENVENIDO :3</h1><br/>
        <h4>CODIGO: </h4><br/>        
       <!--aquí irían los if para hacer según el btn seleccionado...-->
       <%if(request.getParameter("accion")!=null){
            if(request.getParameter("accion").equals("MODIFICAR")){
                modificador.modificarAdministrador(id, request.getParameter("nombre"), request.getParameter("DPI"), herramienta.encriptarContrasenia(request.getParameter("contrasenia")));
                //Se va a buscar al admin nuevamente [esto si te das cuentas de que las var no agarran los val que cb en los input...
           }else{
            //Se redirige a la página d eincio, luego de haber hecho el logout...
           }
         }%>
       
             <%!String nombre=(administrador!=null)?administrador.darNombre():"";%><!--reucerda que la comparación la coloqué por el hecho de que ahorita no tengo implementado el método poara verificar las sesiones... aunque de todos modos, creo que me resultaría util por el hecho de que puede que falle la búsuqeda aunque se tenga los datos necesarios-->
             <%!String contrasenia=(administrador!=null)?herramienta.desencriptarContrasenia(administrador.datosPersonales.darContrasenia()):"";%>
             <%!String DPI = (administrador!=null)?administrador.datosPersonales.darDPI():"";%>
             <form method="POST" action="perfilAdministrador">
                <img src="img/administrador.png" id="fotografiaUsuario" alt="iconoMedico">
            
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="nombre" placeholder="nombre" value="<%=nombre%>" required><br/>
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="password" placeholder="contrasenia" value="<%=contrasenia%>" required><br/>                                 
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="datosUsuario" name="DPI" placeholder="DPI" maxlength="13" value="<%=DPI%>"required><br/> 
                            </th><!--pregunta.. cuando se coloca una var para que el input reciba un valor, cuando a este último le es cb su dato, implica que tb a la var???-->
                    </table>     <!--SI NO llegara a ser así, entoces deberás buscar nuevamente al administrador para tener los datos correctos, esto luego de haber realizado la actualización y de haber salido todo nice xD-->
                    <input type="submit" name="accion" value="MODIFICAR">    
                    <input type="submit" name="accion" value="CERRAR SESION">
             </form>
         </center>
    </body>
</html>
