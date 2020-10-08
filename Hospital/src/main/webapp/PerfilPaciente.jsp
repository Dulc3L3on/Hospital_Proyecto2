<%-- 
    Document   : perfilPaciente
    Created on : 5/10/2020, 22:04:16
    Author     : phily
--%>

<%@page import="Entidades.Paciente"%>
<%@page import="Kit.Herramienta"%>
<%@page import="Manejadores.DB.Modificacion"%>
<%@page import="Manejadores.DB.BusquedaEspecifica"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body> 
        <%!BusquedaEspecifica buscador = new BusquedaEspecifica();%>
        <%!Modificacion modificador = new Modificacion();%>
        <%!Herramienta herramienta = new Herramienta();%>
        <%!int id = -1;%>
        <%//id =Integer.parseInt(request.getParameter("id"));%>
        <%!Paciente paciente = (Paciente)buscador.buscarUnUsuario("Paciente", id);%>
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
       
             <%!String nombre=(paciente!=null)?paciente.darNombre():"";%><!--reucerda que la comparación la coloqué por el hecho de que ahorita no tengo implementado el método poara verificar las sesiones... aunque de todos modos, creo que me resultaría util por el hecho de que puede que falle la búsuqeda aunque se tenga los datos necesarios-->
             <%!String DPI = (paciente!=null)?paciente.datosPersonales.darDPI():"";%>
             <%!String birth = (paciente!=null)?String.valueOf(paciente.darBirth()):"";%>             
             <%!String telefono = (paciente!=null)?paciente.darDatosaPersonales().darTelefono():"";%>
             <%!String correo = (paciente!=null)?paciente.darDatosaPersonales().darCorreo():"";%>
             <%!String contrasenia=(paciente!=null)?herramienta.desencriptarContrasenia(paciente.datosPersonales.darContrasenia()):"";%>
             <%!String peso = (paciente!=null)?paciente.darPeso():"";%>
             <%!String genero = (paciente!=null)?paciente.darGenero():"";%>
             <%!String tipoSangre = (paciente!=null)?paciente.darTipoSangre():"";%>
             <form method="POST" action="perfilPaciente">
                <img src="img/administrador.png" id="fotografiaUsuario" alt="iconoPaciente">
            
                    <table cellspacing="25">
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="nombre" placeholder="nombre" value="<%=nombre%>" required><br/>
                            </th>                            
                            <th>
                                <input type="number" id="datosUsuario" name="DPI" placeholder="DPI" maxlength="13" value="<%=DPI%>"required><br/> 
                            </th>
                            <th>
                                <input type="date" id="datosUsuario" name="birth" placeholder="cumpleanios" value="<%=birth%>"required><br/> 
                            </th>                             
                        </tr>
                        <tr>
                            <th>
                                <input type="tel" id="datosUsuario" name="telefono" placeholder="#telefono" maxlength="8" value="<%=telefono%>"required><br/> 
                            </th>
                            <th>                                
                                <input type="email" id="datosUsuario" name="correo" placeholder="correo@correo.com"  value="<%=correo%>" required><br/> 
                            </th>
                             <th>
                                <input type="text" id="datosUsuario" name="password" placeholder="tucontrasenia" value="<%=contrasenia%>"required><br/> 
                            </th>                                                                                   
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="datosUsuario" name="peso" placeholder="peso en Kg" value="<%=peso%>" required><br/>                                 
                            </th> 
                            <th>
                                <input type="texto" id="datosUsuario" name="sexo" placeholder="genero" value="<%=genero%>" required><br/>                                 
                            </th> 
                            <th>
                                <input type="texto" id="datosUsuario" name="tipoSangre" placeholder="Grupo sanguineo" value="<%=tipoSangre%>" required><br/>                                 
                            </th> 
                        </tr>
                           <!--pregunta.. cuando se coloca una var para que el input reciba un valor, cuando a este último le es cb su dato, implica que tb a la var???-->
                    </table>     <!--SI NO llegara a ser así, entoces deberás buscar nuevamente al administrador para tener los datos correctos, esto luego de haber realizado la actualización y de haber salido todo nice xD-->
                    <input type="submit" name="accion" value="MODIFICAR">    
                    <input type="submit" name="accion" value="CERRAR SESION">
             </form>
         </center>
    </body>
</html>
