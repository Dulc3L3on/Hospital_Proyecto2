/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Documentacion.Consulta;
import Documentacion.Documento;
import Documentacion.Estructura;
import Documentacion.Examen;
import Entidades.Laboratorista;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Extras.DatosPersonales;
import Manejadores.DB.Entidades.IntegradorEntidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class BusquedaGeneral {
    private static Connection conexion;
//    ListaEnlazada<Usuario> listaUsuarios;
    Usuario[] usuarios;
    Documento[] documentos;
    Estructura[] estructuras;
    
    IntegradorEntidades integrador = new IntegradorEntidades();
    
    //estos métodos son útiles sin importar el número de coincidencias que vayan a hallarse... puesto que si hubiera 1, entonces sería un arreglo de 1 coincidencia :v
    public BusquedaGeneral(){
        conexion = ManejadorDB.darConexion();
    }
    
    public DatosPersonales buscarDatosPersonales(String codigoDatos){//esto es para todos los usuarios menos los médicos...
        DatosPersonales datosPersonales;
        
        String crear = "SELECT * FROM Datos_Personales WHERE codigo = (?)";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(crear)){            
            ResultSet resultado = instrucciones.executeQuery();
            datosPersonales = new DatosPersonales(resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5));                        
        }catch(SQLException e){
            datosPersonales = null;
            System.out.println("Surgió un problema al buscar los datos personales");
        }
        
        return datosPersonales;       
    }//esto es para una persona en específico... entonces no puede ser app a la búsuqeda general sino a la especíica...
    
    /**
     *Método empleado por ADMINISTRADOR para realizar las búsquedas
     * de los elementos [users] a modificar/crear desde
     * la interfaz web... creo que va a ser incorporado en los métodos
     * de la clase ADMIN para que se axn la accón xD de dicha entidad...
     * 
     * @param buscado
     * @return
     */
    public Usuario[] buscarUsuario(String buscado){
        String buscar ="SELECT * FROM "+ buscado;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){                       
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
    
            usuarios = new Usuario[resultado.getRow()];
            resultado.first();
            
            for (int numero = 0; numero < usuarios.length; numero++) {
                usuarios[numero] = integrador.formarEntidad(resultado, buscado);//a pesar de incluir a los documentos, aquí solo serán tomados los usuarios...
                resultado.next();
            }            
            
        }catch(SQLException e){
            System.out.println("surgio un error al buscar al "+ buscado);
        }             
        
        return usuarios;
    }//este sería el método de convergencia... puesto que se adapta a todos los usuarios, a diferencia de DocumentoS
    
    /**
     * empleado por el ADMIN de forma directa... para hacer las
     * busquedasd de las estructuras a crear/modif o visualizar...
     * @param tipoEstructura
     * @return
     */
    public Estructura[] buscarEstructura(String tipoEstructura){//puesto que no debo hacer nada diferente en el proceso... así como en users...
        String buscar ="SELECT * FROM "+ tipoEstructura;//ahi miras porqué colocaste un switch para los docs... [ya se que cb pero por si las msocas hay algo importante a tomar en cuenta, de ese método...
        
         try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){                       
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
    
            estructuras = new Estructura[resultado.getRow()];
            resultado.first();
            
            for (int numero = 0; numero < estructuras.length; numero++) {
                estructuras[numero] = integrador.formarEstructura(resultado, tipoEstructura);//a pesar de incluir a los documentos, aquí solo serán tomados los usuarios...
                resultado.next();
            }            
            
        }catch(SQLException e){
            System.out.println("surgio un error al buscar al "+ tipoEstructura);
        }             
        
         return estructuras;
    }
    
    public Documento[] buscarDocumentos(String tipoDocumento){
        switch(tipoDocumento){
            case "Examen":
               buscarExamen();
            break;
            
            case "Consulta":
               buscarConsultas();
            break;
            
            case "Informe":
            break;
            
            case "Orden":
            break;
        }
        
        return documentos;
    }
    
    public void buscarExamen(){
        String buscar ="SELECT (codigo, nombre) FROM Examen";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
            
            documentos = new Documento[resultado.getRow()];
            resultado.first();
            
            for (int numero = 0; numero < documentos.length; numero++) {
                formarEntidad(numero, resultado, "Examen");
                resultado.next();
            }
            
        }catch(SQLException e){
            System.out.println("surgio un error al buscar el examen ");
        }                
    }
    
    public void buscarConsultas(){
        String buscar ="SELECT (codigo, nombre) FROM Consulta";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
            
            documentos = new Documento[resultado.getRow()];
            resultado.first();
            
            for (int numero = 0; numero < documentos.length; numero++) {
                formarEntidad(numero, resultado, "Consulta");
                resultado.next();
            }
            
        }catch(SQLException e){
            System.out.println("surgio un error al buscar la consulta ");
        }                
        
    }  
 
    
}
