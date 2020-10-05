/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Documentacion.Documento;
import Documentacion.Estructura;
import Entidades.Usuario;
import Extras.DatosPersonales;
import Kit.ListaEnlazada;
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
    
    IntegradorEntidades integrador;
    
    //estos métodos son útiles sin importar el número de coincidencias que vayan a hallarse... puesto que si hubiera 1, entonces sería un arreglo de 1 coincidencia :v
    public BusquedaGeneral(){
        conexion = ManejadorDB.darConexion();
        integrador =new IntegradorEntidades();
        integrador.establecerBuscadorGeneral(this);
    }
    
    public DatosPersonales buscarDatosPersonales(String codigoDatos){//esto es para todos los usuarios menos los médicos...
        DatosPersonales datosPersonales;
        
        String crear = "SELECT * FROM Datos_Personales WHERE codigo = (?)";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(crear)){            
            ResultSet resultado = instrucciones.executeQuery();
            datosPersonales = new DatosPersonales(resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5));                        
        }catch(SQLException e){
            datosPersonales = null;
            System.out.println("Surgió un problema al buscar los datos personales"+"/n"+e.getMessage());
        }
        
        return datosPersonales;       
    }//esto es para una persona en específico... entonces no puede ser app a la búsuqeda general sino a la especíica...
    
    public ListaEnlazada<String> darEspecialidades(){
        String buscar="SELECT (nombre) FROM Especialidad";
        ListaEnlazada<String> especialidades = new ListaEnlazada();
    
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){
            ResultSet resultado = instruccion.executeQuery();                                    
            
            while(resultado.next()){
                especialidades.anadirAlFinal(resultado.getString(1));
            }
            
        }catch(SQLException e){
            System.out.println("surgió un error al recuperar las especialidades"+"/n"+e.getMessage());                        
        }        
        return especialidades;//De esta manera, no hanrá problema cuando no tenga nada, puesto que dará un valor 0...
    }
    
    /**
     *Método empleado por ADMINISTRADOR para realizar las búsquedas
     * de los elementos [users] a modificar/crear desde
     * la interfaz web... creo que va a ser incorporado en los métodos
     * de la clase ADMIN para que se axn la accón xD de dicha entidad...
     * 
     * @param buscado
     * @return
     */
    public Usuario[] buscarUsuarios(String buscado){
        String buscar ="SELECT * FROM "+ buscado;
        
        try(PreparedStatement instruccion = conexion.prepareStatement(buscar)){                       
            ResultSet resultado = instruccion.executeQuery();
            resultado.last();
    
            usuarios = new Usuario[resultado.getRow()];//creo que cuando no tiene registros devulve -1... eso quiere decir que se provocaría un indexOutBoudException...
            resultado.first();
            
            for (int numero = 0; numero < usuarios.length; numero++) {
                usuarios[numero] = integrador.formarEntidad(resultado, buscado);//a pesar de incluir a los documentos, aquí solo serán tomados los usuarios...                
                resultado.next();
            }            
            
        }catch(SQLException e){
            System.out.println("surgio un error al buscar al "+ buscado+"\n"+e.getMessage());
        }             
        
        return usuarios;
    }//este sería el método de convergencia... puesto que se adapta a todos los usuarios, a diferencia de DocumentoS
    
    /**
     * empleado por el ADMIN de forma directa... para hacer las
     * busquedasd de las estructuras a crear/modif o visualizar...
     * @param tipoEstructura
     * @return
     */
    public Estructura[] buscarEstructuras(String tipoEstructura){//puesto que no debo hacer nada diferente en el proceso... así como en users...
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
            System.out.println("surgio un error al buscar al "+ tipoEstructura+"/n"+e.getMessage());
        }                   
         return estructuras;
    }
    
    public Documento[] buscarDocumentos(String tipoDocumento){//DEBE SER IGUAL PERO A LOS DE USUERS/ESTRUC...
        switch(tipoDocumento){    
            case "Informe":
            break;
            
            case "Orden":
            break;
        }
        
        return documentos;
    }
    
   
 
    
}
