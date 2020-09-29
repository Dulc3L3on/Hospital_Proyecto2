/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Verificadores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phily
 */
public class VerificadorDB {
    private Connection conexion;
    private boolean debeLlenarse=false;
    boolean estaLlena[];
    
    public VerificadorDB(Connection conexionDB){
        conexion = conexionDB;
    }
    
    private void verificarLlenura(){//todavía no he decidido si llegase a pasar que no se llenan todas sino solo algunas, le voy a dejar que lo ingrese manualmente o le seguiré pidiendo que la llene... si se lo voy a permitir entonces tendría que hacer FALSE La var que indicasi debe llenarse...
        String nombreTablas[] = {"Administrador", "Paciente", "Medico", "Laboratorista", "Consulta_Atendida", "Examen", "Resultado", "Cita_Medica", "Consulta"};
        estaLlena = new boolean[nombreTablas.length];
        int hayUnaVacia=0;
        
        for (int numeroTabla = 0; numeroTabla < nombreTablas.length; numeroTabla++) {
            estaLlena[numeroTabla]= estaVacia(nombreTablas[numeroTabla]);
            
            if(hayUnaVacia==0 &&  estaLlena[numeroTabla]){
                debeLlenarse=true;
            }
        }                   
    }
    
    private boolean estaVacia(String nombreTabla){
        String consulta ="SELECT COUNT(*) FROM" + nombreTabla;
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
        
        return numeroFilas ==0;    
    }
    
    public boolean debeLlenarse(){
        verificarLlenura();
        return debeLlenarse;
    }
    
    public boolean[] darListadoVacios(){
        return estaLlena;
    }
    
}
/*
 <%if(){%><%--lo que tienes que saber es si fue presionado o no, media vez lo hayan hecho y la DB esté vacía entonces se procede a llamar a lector de XML, quien a su vez llama al cargador y este al creacdor y este, cuando lo requiera a creacion normal..--%>

       //puesto que será empleado JavaScript... ya que no sabía cuando exactamente era presionadao el btn, puesto que con getParamenter, sleeccionara o no iba a obtner siempre el value...
           <%!ManejadorXML manejadorXML = new ManejadorXML();%>//puesto que aún no se coo intercamiar objetos entre páginas...
*/