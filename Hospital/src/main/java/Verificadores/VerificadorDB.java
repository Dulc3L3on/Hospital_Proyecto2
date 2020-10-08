/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Verificadores;

import Entidades.Usuario;
import java.io.Serializable;
import Manejadores.DB.ManejadorDB;
import Manejadores.DB.Registro;
import Manejadores.DB.Sesion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phily
 */
public class VerificadorDB implements Serializable{
    private static Connection conexion;
    Registro registrador;
    Sesion sesion;
    Usuario usuario;//esto es para el logueo... que devolverán los métodos que provienen de la clase con el mismo nombre... y que mandarás a la página que corresponde... a la cual te dirigiras según el tipo de usuario por medio del método para redirigir introducido en el response...
    
    private boolean debeLlenarse=false;
    boolean estaLlena[];
    
    
//    public VerificadorDB(Connection conexionDB){
//        conexion = conexionDB;
//        registrador = new Registro(conexion);
//        sesion = new Sesion(conexion);
//    }
    
    public VerificadorDB(){
        conexion = ManejadorDB.darConexion();
        registrador = new Registro();
        sesion = new Sesion();
    }
    
    private void verificarLlenura(){//todavía no he decidido si llegase a pasar que no se llenan todas sino solo algunas, le voy a dejar que lo ingrese manualmente o le seguiré pidiendo que la llene... si se lo voy a permitir entonces tendría que hacer FALSE La var que indicasi debe llenarse...
        debeLlenarse=false;
        String nombreTablas[] = {"Administrador", "Paciente", "Medico", "Laboratorista", "Consulta_Atendida", "Examen", "Resultado", "Cita_Medica", "Consulta"};
        estaLlena = new boolean[nombreTablas.length];     
        int hayUnaVacia=0;
        
        for (int numeroTabla = 0; numeroTabla < nombreTablas.length; numeroTabla++) {
            estaLlena[numeroTabla]= !estaVacia(nombreTablas[numeroTabla]);//puesto que vacío es != lleno...
            
            if(hayUnaVacia==0 && !estaLlena[numeroTabla]){
                hayUnaVacia=1;
                debeLlenarse=true;
            }
        }                   
    }
    
    private boolean estaVacia(String nombreTabla){
        String consulta ="SELECT COUNT(*) FROM " + nombreTabla;
        int numeroFilas=0;
        
        try(Statement instrucciones = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY)){
        
            ResultSet resultadosHallados= instrucciones.executeQuery(consulta);
            
            if(resultadosHallados.next()){
                numeroFilas= resultadosHallados.getInt(1);
            }                  
        } catch (SQLException ex) {           
            System.out.println("\nerror al corroborar llenura de tablas: "+ex.getMessage());
            return true;
        }        
        return numeroFilas ==0;//pues querría decir que no tiene ninguna fia [registro]    
    }   
    
    /**
     * Este método es el empleado para saber si es necesario llamar al manejadorXML en la pág CargaDeDatos...
     * @return
     */
    public boolean debeLlenarse(){
        verificarLlenura();
        return debeLlenarse;
    }
    
    public boolean[] darListadoVacios(){
        verificarLlenura();
        return estaLlena;
    }
    
  

    
  
    
}
/*
 <%if(){%><%--lo que tienes que saber es si fue presionado o no, media vez lo hayan hecho y la DB esté vacía entonces se procede a llamar a lector de XML, quien a su vez llama al cargador y este al creacdor y este, cuando lo requiera a creacion normal..--%>

       //puesto que será empleado JavaScript... ya que no sabía cuando exactamente era presionadao el btn, puesto que con getParamenter, sleeccionara o no iba a obtner siempre el value...
           <%!ManejadorXML manejadorXML = new ManejadorXML();%>//puesto que aún no se coo intercamiar objetos entre páginas...

       //<%--redirecciono a la página de carga...--%>          
       //<meta http-equiv="refresh" content="1"; url="cargaDatos.jsp">        <!--tambien podría hacerse directamente con un response.senRedirect("a donde se redirijirá...")-->                  

*/