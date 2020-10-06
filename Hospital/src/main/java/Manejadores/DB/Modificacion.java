/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import Kit.ListaEnlazada;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Modificacion {//creo que los métodos solo serán específicos [es decir para 1 solo elemtneo de cualquiera de las entidades existentes...
    Connection conexion = ManejadorDB.darConexion();
    Herramienta herramienta = new Herramienta();
    Creacion creador = new Creacion();
    BusquedaEspecifica buscadorMinucioso = new BusquedaEspecifica();
    
    public void modificar(String tipoAModificar){
        switch(tipoAModificar){
            case "Medico":
                
                
            break;
            
            case "Laboratorista":
            break;
            
            case "Paciente":
            break;
            
            case "Administrador"://este será uno de lo súltimo en ser implementados, puesto que no fue solicitado el proceso...
            break;            
        }
        
    }//vamos a ver si es buena idea hacer este método de convergencia... puesto que debes devolver el código en el listado y 
    
    /*empleado por todos los usuarios...*/
    public boolean modificarDatosPersonales(int codigo, String correo, String contrasenia, String numeroTelefono, String DPI){
        String modificar ="UPDATE Datos_Personales SET correo = ? contrasenia = ? telefono = ? DPI = ? WHERE codigo = ?";
        
        if(herramienta.encriptarContrasenia(contrasenia)!=null){
            try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
                instruccion.setString(1, correo);
                instruccion.setString(2, contrasenia);
                instruccion.setString(3, numeroTelefono);
                instruccion.setString(4, DPI);
                instruccion.setInt(6, codigo);//Este código es el de los datos personales, no lo vayas a aolvidar...
            
                instruccion.executeUpdate();            
                return true;
            }catch(SQLException sqlE){
                System.out.println("surgió un error al modificar los datos personales -> "+ sqlE.getMessage());
                return false;
            }
        }                
        return false;
    }/*terminado*///será llamado sismpre que se modifiquen los datos de un médico, ya sea que cb o no los atributos que esta tabla contiene...
    
    public boolean modificarAdministrador(int codigo, String nuevoNombre, String nuevoDPI, String nuevoContraseniaEncriptada ){
        String modificar="UPDATE Administrador SET (nombre, DPI, contrasenia) VALUES(?,?,?) WHERE codigo = ?";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            instruccion.setString(1, nuevoDPI);
            instruccion.setString(1, nuevoDPI);
            
            instruccion.executeUpdate();            
        }catch(SQLException sqlE){
            System.out.println("surgió un error al modificar\nal administrador");
            return false;
        }
        return true;
    }
    
    /*RECUERDA: que recibe los datos de un onjeto MEDICO [1 de un arr o 1 obj en particular debido a la sesion...*/
    public boolean modificarMedico(int codigo, String nombre, String numeroColegiado, int horaInicio, int horaFin, Date fechaIncorporacion){//va a ser el mismo para el ADMIN y el MEDICO
        //independientemente de como recoja la info [con arr, con un obj o indiv [aunque esto último lo dudo] el metodo no tiene afección alguna
        //esto por el hecho de que siempre le terminarán pasando los datos en las posiciones que corresponden, sin importar dónde los halla tenido almacenados el enviaddor de la info... [arr, obj, o campo indiv xD]
        String modificar ="UPDATE Medico SET nombre = ? numeroColegiado = ? horaInicio = ? horaFin= ? WHERE codigo = ?";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){            
            instruccion.setString(1, nombre);
            instruccion.setString(2, numeroColegiado);
            instruccion.setString(3, numeroColegiado);
            instruccion.setInt(4, horaInicio);
            instruccion.setInt(5, horaFin);
            instruccion.setDate(6, fechaIncorporacion);
            instruccion.setInt(7, codigo);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al modificar al médico -> ");
            return false;
        }
        return true;        
    }/*terminado*/   
    
    public boolean modificarTitulario(int codigoMedico, ListaEnlazada<String> titulosAntiguos, String[] nuevosTitulos){
        boolean procesoExitoso=true;
        
        if(nuevosTitulos!=null){//si es null es porque no seleccionó nada y por ello el proceso salió bien :v xD         
            for (int tituloActual = 0; tituloActual < nuevosTitulos.length; tituloActual++) {
                if(creador.crearEspecialidadMedico(false, codigoMedico, buscadorMinucioso.buscarCodigoTitulo(nuevosTitulos[tituloActual]))){
                    titulosAntiguos.anadirAlFinal(nuevosTitulos[tituloActual]);
                }else{
                    procesoExitoso=false;
                }            
            }            
        }        
        return procesoExitoso;
    }/*terminado*///Es empleado cuando los títulso solo peuden ser añadidos de 1 en 1...       
    
    //empleado por el ADMIN y el PACIENTE
    public boolean modificarPaciente(int codigoPaciente, String nombre, String sexo, Date birth, String peso, String tipoSangre){
        String modificar="UPDATE Paciente nombre = ? sexo = ? birth = ? peso = ? tipoSangre = ? WHERE codigo = ?";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            instruccion.setString(1, nombre);
            instruccion.setString(2, sexo);
            instruccion.setDate(3, birth);            
            instruccion.setString(4, peso);
            instruccion.setString(5, tipoSangre);
            instruccion.setInt(6, codigoPaciente);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al modificar al paciente -> "+sqlE.getMessage());
            return false;
        }        
        return true;
    }/*terminado*///no sé si debería poder hacer este cb el admin, pero so si acaso... xD
    
    public boolean modificarLaboratorista(int codigoLaboratorista, String nombre, String registroMS, int codigoExamenAsignado, Date fechaIncorporacion){
        String modificar ="UPDATE Laboratorista nombre = ? registroMS = ? codigoExamenAsignado = ? fechaIncorporacion = ? WHERE codigo = ?";
            
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            instruccion.setString(1, nombre);
            instruccion.setString(2, registroMS);
            instruccion.setInt(3, codigoExamenAsignado);
            instruccion.setDate(4, fechaIncorporacion);
            instruccion.setInt(5, codigoLaboratorista);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("surgio un error al modificar al laboratorista");
            return false;
        }
        return true;        
    }/*terminado*/
    
    public boolean modificarHorarioLaboratorista(int codigoLaboratorista, boolean[] horario){//Este el 
        String modificar ="UPDATE Horario_Laboratorista domingo = ? lunes = ? martes =  ? miercoles = ? jueves = ? viernes = ? sabado = ? WHERE codigoLaboratorista = ?";
    
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            String[] horarioTrabajo = herramienta.darDiasTrabajoLaboratorista(horario);
            
            instruccion.setString(1, horarioTrabajo[0]);
            instruccion.setString(2, horarioTrabajo[1]);
            instruccion.setString(3, horarioTrabajo[2]);
            instruccion.setString(4, horarioTrabajo[3]);
            instruccion.setString(5, horarioTrabajo[4]);
            instruccion.setString(6, horarioTrabajo[5]);
            instruccion.setString(7, horarioTrabajo[6]);
            instruccion.setInt(8, codigoLaboratorista);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("sucedio un error al modificar el horario del laboratorista");
            return false;
        }
        return true;        
    }/*terminado*///recuerda que no agregue este ni las especialdades a la modificaión general de labo y méd, puesto que ello no puende modificar por sí solos los 
    //valores que en dichas tablas se encuentren...
 
    public boolean modificarExamen(int codigo, String nombre, String requerimientoOrden, String descripcion, String costo, String tipoEtension){
        String modificar ="UPDATE Examen nombre = ? requiereOrden = ? descripcion = ? costo = ? tipoExtensionResultado WHERE codigo = ?";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            instruccion.setString(1, nombre);
            instruccion.setString(2, requerimientoOrden);
            instruccion.setString(3, descripcion);
            instruccion.setString(4, costo);
            instruccion.setString(5, tipoEtension);
            instruccion.setInt(6, codigo);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al modificar el examen -> "+ sqlE.getMessage());
            return false;
        }        
        return true;
    }/*terminado*/
    
    public boolean modificarConsulta(String tipo, String costo){
        String modificar = "UPDATE Consulta tipo = ? costo = ?";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(modificar)){
            instruccion.setString(1, tipo);
            instruccion.setString(2, costo);
            
            instruccion.executeUpdate();
        }catch(SQLException sqlE){
            System.out.println("surgió un error al modifcar la consulta -> "+ sqlE.getMessage());
            return false;
        }
        return true;
    }/*terminado*/
    
}
