/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
import Kit.ListaEnlazada;
import Kit.Nodo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class CreadorDeCarga {//si hay algo que pueda generalizar, será colocado aquí, de lo contrario, irá en su clase específica de la entidad ó si miras que no serán tantos los metodos, entonces aquí estarán los métodos por entidad, a sabiendas de que solo haBrá 1 [no creo que hayan más, pero si sí, sería max 2 [o talvez 3 xD]
    Connection conexion;
    Herramienta herramienta = new Herramienta();
    Creacion creacion;
    ListaEnlazada<String> listaEspecialidades = new ListaEnlazada();
    
    
    //si la cosa sale mal al crear entonces aquí se queda el error, por lo tanto no se crea nada de nada... pero eso no implica que en el exterior suceda eso...
    public CreadorDeCarga(Connection conexionDB){
        conexion=conexionDB;        
        creacion = new Creacion(conexion);
    }//recuerda que la instanciacion de esta clase y de sus hermanas accionistas en la DB xd, deberá hacerse en el punto donde se haga el logueo, luego de haber instanciado el manejadorDB...
    
    
    
    public void crearAdministrador(boolean esCarga, String codigo, String nombre, String DPI, String contrasenia){
        String crear = "INSERT INTO Administrador (?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            String contraseniaEncriptada=herramienta.encriptarContrasenia(contrasenia);
            
            if(contraseniaEncriptada!=null){//te RECUERDAS de cb la condi para verificar que todo haya salido bien, si es que el método no devuelve un string...
                instruccion.setString(1, codigo);
                instruccion.setString(2, nombre);
                instruccion.setString(3, DPI);
                instruccion.setString(4, contraseniaEncriptada);                              
            }else{
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
            }//a menos que halles la manera en que puedas saber cuando cualquiera de los 2 a fallado, las llamadas a la concatenación de los errores deberá estar en ambos lugares...
            
            
        }catch(SQLException sqlE){
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error            
            
            //aquí va el if para que se exe solo cuando se esté creando de a 1... al menos en una sesión...
            JOptionPane.showMessageDialog(null, "Surgió un error al registrar\nal nuevo administrador", "error de creacion", JOptionPane.ERROR_MESSAGE);
        }                               
    }/*terminado*///al menos lo que te solicitaron...
    
    public void crearPaciente(boolean esCarga, String codigo, String nombre, String birth, String DPI, String telefono, String peso, String tipoSangre, String correo, String contrasenia){//acordemos que las contras van al final de los paráms de creación...
        String crear ="INSERT INTO Paciente (?,?,?,?,?)";
        
        int codigoDatosPersonales= creacion.crearDatosPersonales(esCarga, correo, contrasenia, telefono, DPI);
        
        if(codigoDatosPersonales>0){
            try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                java.sql.Date fechaNacimiento =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(birth).getTime());//así cuando lanze un null la conversión str->utilD me llevará al catch u n ose habrán dejado alguno scampos con info y otros sin nada...
                
                instruccion.setInt(1, Integer.parseInt(codigo));
                instruccion.setString(2, nombre);
                instruccion.setDate(3, fechaNacimiento);
                instruccion.setString(4, peso);
                instruccion.setString(5, tipoSangre);
                instruccion.setInt(6, codigoDatosPersonales);                
                
            }catch(SQLException sqlE){
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
                
                //if para mostrar el JOP
                JOptionPane.showMessageDialog(null, "Surgió un error al registrarle\n", "error de registro", JOptionPane.ERROR_MESSAGE);//lo digo de esta manera, puesto que el paciente se registra por su cuenta en la página...                
            }
        }else{
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
        }                   
    }/*terminado*///solo por el objeto con el cual se podrán mostrar los errores...
    
    public void crearMedico(boolean esCarga, String codigo, String nombre, String numeroColegiado, String DPI, String telefono,
            String[] titulos, String correo, int horaInicio, int horaFin, String fechaIncorporacion, String contrasenia){//deoendiendo al final de como se pueda obtner a los títulos, o seguirá teniendo en los paráms a un arreglo o al mapeo, para convertirlo a string...
        
        String crear ="INSERT INTO Medico (?,?,?,?,?,?,?)";        
        int codigoDatosPersonales= creacion.crearDatosPersonales(esCarga, correo, contrasenia, telefono, DPI);//puesto que esta entidad [médico] requiere de este código
        ListaEnlazada<Integer> codigosEspecialidades;
        
        if(codigoDatosPersonales>0){//Puesto que Médico SÍ DEPENDE de este valor para crear correctamente su registro, por lo ual si falla, NO DEBERÁ  de registrarse al médico al menos no automáticamenente, sino manual, esto por medio de la creación que puede hacer el administrador...
            try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                java.sql.Date fechaInicio=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime());
            
                instruccion.setString(1, codigo);
                instruccion.setString(2, nombre);
                instruccion.setString(3, numeroColegiado);
                instruccion.setInt(4, horaInicio);
                instruccion.setInt(5, horaFin);
                instruccion.setInt(6, codigoDatosPersonales);
                instruccion.setDate(7, fechaInicio);                        
            
                agregarTitulos(codigo, titulos);
            }catch(SQLException ex){
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
                
                //y aquí el if para solo mostrar en la carga el JOP                               
                JOptionPane.showMessageDialog(null, "Surgió un error al registrar\nal nuevo médico", "error de creacion", JOptionPane.ERROR_MESSAGE);
            }        
        }else{
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
        }               
        
    }//falta ESPECIALIDAD, puesto que no sabes como es que devolverá los datos por el hecho de haber normalizado [fusionado] es decir si por dicha función devulve los datos como arreglo o separados por ","... 
   
    public void agregarTitulos(String codigoMedico, String[] titulos){
        for (int numeroTitulo = 0; numeroTitulo < titulos.length; numeroTitulo++) {
            int codigoEspecialidad = agregarEspecialidad(titulos[numeroTitulo]);
            
            if(codigoEspecialidad!=0){//puesto que JAMÁS NUNCA dará # menores a 0...
                creacion.crearEspecialidadMedico(true, codigoMedico, codigoEspecialidad);
            }
        }//solo te hace falta colocar las condis cuando suceda un error, sin importar
        //En cual de los 2 métodos de registro sucdio...
    }    
    
    /**
     *Por medio de este método será posible hacer las asignaciones de las especialidades
      sin repetición, para posterirormente por medio del código [numero de nodo xD]
     * que le corresponde a la especialidad registrada de la cual posee un título el médico, 
     * donde si llegara a susceder algún problema, no sucederá nada malo puesto que en 
     * este caso al administrador agregarlos manualmente xD :v jajajadinistrador agregarlos
     * @return  manualmente xD :v jajaja
     * @param titulo
     */
    private int agregarEspecialidad(String titulo){//a especialidad :v y al médico xD
        
        if(!listaEspecialidades.estaVacia()){
            Nodo<String> nodoAuxiliar = listaEspecialidades.obtnerPrimerNodo();
            
            for (int especialidadActual = 1; especialidadActual <= listaEspecialidades.darTamanio(); especialidadActual++) {//recuerda con listasEnlazadas el conteo empieza en 1!!!
                if(titulo.equalsIgnoreCase(nodoAuxiliar.contenido)){
                    return especialidadActual;                    
                    
                }else if(especialidadActual== listaEspecialidades.darTamanio()){
                    if(creacion.crearEspecialidad(true, titulo)){
                        listaEspecialidades.agregarNuevoSiguiente(nodoAuxiliar, titulo);                    
                        nodoAuxiliar.establecerAtributoExtra("0");//esto me hace pensar que talvez si sería útil agregar una clase para Especialidad u otra, pero qu debería addse 1... pero es que después no se usará tanto, y eso no me parece...
                    }else{
                        return 0;//ya sabes cuando recibas este valor, no salió bien la creación xD
                    }                    
                }
                
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
            }//fin del for con el cual se recorre el listado de especialidades...                       
        }else{
            listaEspecialidades.anadirAlFinal(titulo);//Esto pasará una uniquísima vez... xD
            listaEspecialidades.obtnerPrimerNodo().establecerAtributoExtra("0");
        }
        
        return listaEspecialidades.darTamanio();
    }
  
    
    
    
    /*
    Lo bueno es que estas clases CRUD, no requieren ser la misma instancia en las demás clases,puesto 
    que solo exe axn, mas no almacenan nada en su interior... o al menos por ahora xD
    */
}
