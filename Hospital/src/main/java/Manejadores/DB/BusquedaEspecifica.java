/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.ListaEnlazada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Esta clase está destinada a buscar los datos de 1 único elemento de 
 * cualquiera de las entidades existentes en la DB * 
 * @author phily
 */
public class BusquedaEspecifica {
    Connection conexion = ManejadorDB.darConexion();         
    
    public int buscarIDdelLogueado(String entidad, String contrasenia, String correo){//Aquí ya se le habrá pasado la cencriptación de la contrasenia ingresada...
        int codigo=0;        
        
        if(entidad!=null){
            String buscar ="SELECT codigo FROM "+ entidad+"WHERE datos_Personales = (SELECT codigo FROM "
            + "Datos_Personales WHERE contrasenia = ? AND correo = ?)";           
        
            try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
                instruccion.setString(1, contrasenia);
                instruccion.setString(2, correo);
            
            }catch(SQLException sqlE){
                System.out.println("surgió un error al\ncorroborar identificador\ndel solicitante\n"+sqlE.getMessage());
                codigo=0;
            }
        }        
        return codigo;
    }
    
    public boolean[] buscarHorario(int codigoLaboratorista){
        String buscar="SELECT * FROM Horario_Laboratorista WHERE codigoLaboratorista = ?";
        boolean[] horario =null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigoLaboratorista);
            
            ResultSet resultado=instruccion.executeQuery();            
            resultado.last();
            horario = new boolean[resultado.getRow()];//bueno.. aunque fijo son 7 los daots que irá a traer xD :v
            resultado.first();
            
            for (int dia = 0; dia < horario.length; dia++) {
                horario[dia] = Boolean.parseBoolean(resultado.getString(dia+1));
            }            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al obtener el horario del laboratorista -> "+ sqlE.getMessage());
            return null;
        }        
        return horario;
    }    
    
     public ListaEnlazada<String> buscarTitulos(int codigoMedico){
        String buscar ="SELECT nombre FROM Especialidad WHERE codigo = (SELECT codigoEspecialidad FROM Especialidad_Medico WHERE codigoMedico = ?)";
        ListaEnlazada<String> listadoTitulos = new ListaEnlazada();
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigoMedico);
            
            ResultSet resultado = instruccion.executeQuery();           
            
            while(resultado.next()){
                listadoTitulos.anadirAlFinal(resultado.getString(1));//siempre será 1, puesot que por cad registro que tenga, hay un solo código...                
            }
        }catch(SQLException sqlE){
            System.out.println("surgió un error al buscar los titulos -> "+ sqlE.getMessage());
            listadoTitulos.limpiar();
        }
        return listadoTitulos;
    }
    
    /**
     * Método empleado cuadno al médico le sean agregados títulos al sistema
     * esto porque el médico tiene pero un arreglod e títulos de tipo String...
     * @param nombreTitulo
     * @return
     */
    public int buscarCodigoTitulo(String nombreTitulo){
        String buscar ="SELECT codigo FROM Especialidad WHERE nombre = ?";
        int codigoTitulo = 0;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setString(1, nombreTitulo);
            
            ResultSet resultado = instruccion.executeQuery();//no habría alguna situación en la que al ir a buscar el título este no exista, así que no habrá problema con exe el getInt de una sola vez...
            codigoTitulo = resultado.getInt(1);
        }catch(SQLException sqlE){
            System.out.println("surgio un error al recuperar el codigo del titulo -> "+ sqlE.getMessage());
            codigoTitulo=0;//aqunque creo que cuando llegara a fallar, la variable se quedaría con un valor 0, ya sea porque no le llegó nada o porqu eel método mismo devulve 0 cuando falla...
        }
        return codigoTitulo;
    }
    
    public String buscarNombreExamen(int codigo){        
        String buscar ="SELECT (nombre) FROM Examen WHERE codigo = ?";
        String nombre=null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigo);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){
                nombre = resultado.getString(1);//como se supone no habrán más..
            }                        
        }catch(SQLException sqlE){
            System.out.println("surgió un error al\nrecuperar el nombre del examen\n"+sqlE.getMessage());
            nombre=null;
        }
        return nombre;
    }
    
}
