/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Entidades.Medico;
import Entidades.Usuario;
import Kit.ListaEnlazada;
import Manejadores.DB.Entidades.IntegradorEntidades;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Esta clase está destinada a buscar los datos de 1 único elemento de 
 * cualquiera de las entidades existentes en la DB * 
 * @author phily
 */
public class BusquedaEspecifica implements Serializable{
    Connection conexion = ManejadorDB.darConexion(); 
    IntegradorEntidades integrador;
    
    public BusquedaEspecifica(){
        integrador = new IntegradorEntidades();
        integrador.establecerBUsquedaEspecifica(this);
    }
    
    public int buscarIDdelLogueado(String entidad, String contrasenia, String correo){//Aquí ya se le habrá pasado la cencriptación de la contrasenia ingresada...
        int codigo=0;        
        
        if(entidad!=null){
            String buscar ="SELECT codigo FROM "+ entidad+"WHERE datos_Personales = (SELECT codigo FROM "
            + "Datos_Personales WHERE contrasenia = ? AND correo = ?)";           
        
            try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
                instruccion.setString(1, contrasenia);
                instruccion.setString(2, correo);
            
                ResultSet resultado = instruccion.executeQuery();
                resultado.first();
                codigo = resultado.getInt(1);
                
            }catch(SQLException sqlE){
                System.out.println("surgió un error al\ncorroborar identificador\ndel solicitante\n"+sqlE.getMessage());
                codigo=0;
            }
        }        
        return codigo;
    }
    
    /**
     * Método útil pra hacer las búsquedas para mostrar la información
     * del usuario logueado en cuestión..
     * @param tipoUsuario
     * @param codigo
     * @return
     */
    public Usuario buscarUnUsuario(String tipoUsuario, int codigo){
        String buscar ="SELECT * FROM "+tipoUsuario+" WHERE codigo = ?";
        Usuario usuario =null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigo);
            
            ResultSet resultado = instruccion.executeQuery();
            resultado.first();//puesto que no se recorre con un ciclo, requiero ubicarlo de forma manual...
            usuario = integrador.formarEntidad(resultado, tipoUsuario);
            
        }catch(NullPointerException | SQLException e){
            System.out.println("surgió un error al buscar al "+tipoUsuario);
            usuario = null;
        }
        return usuario;        
    }/*terminado*///útil para los momentos en que se estaá concetrado...
    
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
                resultado.next();
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
            resultado.first();
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
            resultado.first();//así me ubico en el primer y único registro [al menos para este caso...]
            
            nombre= resultado.getString(1);
            
            while(resultado.next()){
                nombre = resultado.getString(1);//como se supone no habrán más..
            }                        
        }catch(SQLException sqlE){
            System.out.println("surgió un error al\nrecuperar el nombre del examen\n"+sqlE.getMessage());
            nombre=null;
        }
        return nombre;
    }        
    
    //>>> Propios de PACIENTE!!!
    
    public Medico[] buscarMedicosPorEspecialidad(String nombreEspecialidad){
        String buscar="SELECT * FROM Medico Where codigo = (SELECT codigoMedico FROM Especialidad_Medico"
           + "WHERE codigoEspecialidad = ?)";
        Medico medicos[]=null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setString(1, nombreEspecialidad);
            
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
            
            medicos = new Medico[resultado.getRow()];
            resultado.first();
            
            for (int medicoActual = 0; medicoActual < medicos.length; medicoActual++) {
                medicos[medicoActual] = (Medico)integrador.formarEntidad(resultado, "Medico");                
                resultado.next();
            }//recuerda que si es null, no deberás mostrarlo... porque surgió un problema en su formación...
        }catch(SQLException sqlE){
            System.out.println("surgió un problema en la búsqueda\ndel listado de lso especialistas\n"+sqlE.getMessage());            
        }//Aquí no hago null, puesto que de eso se encarga el formador de entidades, por lo cual lo único qu edeberá hacer para tener bien la información,es saltarte dichos null...        
        return medicos;
    }/*terminado*///seá empleado en el apartado de agendación CONSULTAS_MÉDICAS!!!
    
    public Date buscarFechaPrimeraConsulta(int codigoPaciente){//Si no funciona lo borras y ya xd, lo importarnte es que no dejes que se pase de la fecha actual...
        String buscar ="SELECT fecha FROM Consulta_Atendida WHERE codigoPaciente = ? ORDER BY"
            + "fecha ASC LIMIT 1";
        Date primeraConsulta = null;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            instruccion.setInt(1, codigoPaciente);
            
            ResultSet resultado = instruccion.executeQuery();
            
            while(resultado.next()){//Aunque solo será 1 resultado :v
                primeraConsulta = resultado.getDate(1);
            }            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al obtener\nla fecha de la primer consulta realizada\n"+sqlE.getMessage());
            primeraConsulta=null;
        }
        return primeraConsulta;                
    }
    
}
