/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.ControlIndices;
import Kit.Herramienta;
import Kit.ListaEnlazada;
import Kit.Nodo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class CreadorDeCarga implements Serializable{//si hay algo que pueda generalizar, será colocado aquí, de lo contrario, irá en su clase específica de la entidad ó si miras que no serán tantos los metodos, entonces aquí estarán los métodos por entidad, a sabiendas de que solo haBrá 1 [no creo que hayan más, pero si sí, sería max 2 [o talvez 3 xD]
    private static Connection conexion;
    private Herramienta herramienta = new Herramienta();
    private Creacion creacion;
    private ControlIndices controladorIndices;
    private ListaEnlazada<String> listaEspecialidades = new ListaEnlazada();   
    
    //si la cosa sale mal al crear entonces aquí se queda el error, por lo tanto no se crea nada de nada... pero eso no implica que en el exterior suceda eso...
//    public CreadorDeCarga(Connection conexionDB){
//        conexion=conexionDB;        
//        creacion = new Creacion(conexion);
//    }//recuerda que la instanciacion de esta clase y de sus hermanas accionistas en la DB xd, deberá hacerse en el punto donde se haga el logueo, luego de haber instanciado el manejadorDB...
//    
    
    public CreadorDeCarga(){
        conexion = ManejadorDB.darConexion();
        creacion = new Creacion();
    }
    
    public void crearAdministrador(String codigo, String nombre, String DPI, String contrasenia){
        String crear = "INSERT INTO Administrador (?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            String contraseniaEncriptada=herramienta.encriptarContrasenia(contrasenia);
            int parteNumerica= herramienta.extraerParteNumerica(5, codigo);
            
            //no se si agregar lo de la parte numérica... pero de todos modos debería cimplir con el patrón así que... lo agergaré xD
            if(!contraseniaEncriptada.isBlank() && parteNumerica!=0){//te RECUERDAS de cb la condi para verificar que todo haya salido bien, si es que el método no devuelve un string...                
                
                instruccion.setInt(1, parteNumerica);
                instruccion.setString(2, nombre);
                instruccion.setString(3, DPI);
                instruccion.setString(4, contraseniaEncriptada);
                
                instruccion.executeUpdate();
                controladorIndices.establecerUltimoIndice(5, parteNumerica);//y de esta manera ya en el proceso normal, no habrán problemas con el código xD... de todos modos SÍ se alamcenará con su parte en string... así como lo diern... xD
            }else{
                
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
            }//a menos que halles la manera en que puedas saber cuando cualquiera de los 2 a fallado, las llamadas a la concatenación de los errores deberá estar en ambos lugares...
            
            
        }catch(ClassCastException | SQLException sqlE){
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error            
            
            //aquí va el if para que se exe solo cuando se esté creando de a 1... al menos en una sesión...
            System.out.println("error al cargar Admin -> " + sqlE.getMessage());
            JOptionPane.showMessageDialog(null, "Surgió un error al registrar\nal nuevo administrador", "error de creacion", JOptionPane.ERROR_MESSAGE);
        }                               
    }/*terminado*///al menos lo que te solicitaron...
    
    public void crearPaciente(String codigo, String nombre, String sexo, String birth, String DPI, String telefono, String peso, String tipoSangre, String correo, String contrasenia){//acordemos que las contras van al final de los paráms de creación...
        String crear ="INSERT INTO Paciente (?,?,?,?,?,?,?)";
        
        int codigoDatosPersonales= creacion.crearDatosPersonales(true, correo, contrasenia, telefono, DPI);
        
        if(codigoDatosPersonales>0){
            try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                int paciente = Integer.parseInt(codigo);
                java.sql.Date fechaNacimiento =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(birth).getTime());//así cuando lanze un null la conversión str->utilD me llevará al catch u n ose habrán dejado alguno scampos con info y otros sin nada...                
                
                instruccion.setInt(1, paciente);
                instruccion.setString(2, nombre);
                instruccion.setString(3, sexo);
                instruccion.setDate(4, fechaNacimiento);
                instruccion.setString(5, peso);
                instruccion.setString(6, tipoSangre);
                instruccion.setInt(7, codigoDatosPersonales);  
                
                instruccion.executeUpdate();
                controladorIndices.establecerUltimoIndice(4, paciente);
                
            }catch(ClassCastException | SQLException e){
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
                
                //if para mostrar el JOP
                 System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Surgió un error al registrarle\n", "error de registro", JOptionPane.ERROR_MESSAGE);//lo digo de esta manera, puesto que el paciente se registra por su cuenta en la página...                
            }
        }else{
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
        }                   
    }/*terminado*///solo por el objeto con el cual se podrán mostrar los errores...
    
    public void crearMedico(String codigo, String nombre, String numeroColegiado, String DPI, String telefono,
            String[] titulos, String correo, int horaInicio, int horaFin, String fechaIncorporacion, String contrasenia){//deoendiendo al final de como se pueda obtner a los títulos, o seguirá teniendo en los paráms a un arreglo o al mapeo, para convertirlo a string...
        
        String crear ="INSERT INTO Medico (?,?,?,?,?,?,?)";        
        int codigoDatosPersonales= creacion.crearDatosPersonales(true, correo, contrasenia, telefono, DPI);//puesto que esta entidad [médico] requiere de este código        
        int parteNumerica = herramienta.extraerParteNumerica(4, codigo);// MED-
        
        if(codigoDatosPersonales>0 && parteNumerica!=0){//Puesto que Médico SÍ DEPENDE de este valor para crear correctamente su registro, por lo ual si falla, NO DEBERÁ  de registrarse al médico al menos no automáticamenente, sino manual, esto por medio de la creación que puede hacer el administrador...
            try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                java.sql.Date fechaInicio=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime());                
                
                instruccion.setInt(1, parteNumerica);
                instruccion.setString(2, nombre);
                instruccion.setString(3, numeroColegiado);
                instruccion.setInt(4, horaInicio);
                instruccion.setInt(5, horaFin);
                instruccion.setInt(6, codigoDatosPersonales);
                instruccion.setDate(7, fechaInicio);                        
                
                instruccion.executeUpdate();                
                controladorIndices.establecerUltimoIndice(2, parteNumerica);
                agregarTitulos(parteNumerica, titulos);//ya se que tenfo el código, pero no tendría chiste que se crearan los títulos cuando el médico no. :v xD duh! xD
            }catch(NullPointerException | ClassCastException | SQLException e){//el null, debido al casteo el cual devulve null de string a utilDate cuando no pudo hacer la conversión...
                //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
                
                //y aquí el if para solo mostrar en la carga el JOP                               
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Surgió un error al registrar\nal nuevo médico", "error de creacion", JOptionPane.ERROR_MESSAGE);                
            }        
        }else{
            //mandarás a llamar al objeto que se encarga de concatenar el tipo de categoría y código del que surgió el error
        }                       
    }
   
    private void agregarTitulos(int codigoMedico, String[] titulos){
        for (int numeroTitulo = 0; numeroTitulo < titulos.length; numeroTitulo++) {
            int codigoEspecialidad = agregarEspecialidad(titulos[numeroTitulo]);
            
            if(codigoEspecialidad!=0){//puesto que JAMÁS NUNCA dará # menores a 0...
                creacion.crearEspecialidadMedico(true, codigoMedico, codigoEspecialidad);//WARNING! por el hecho de no corroborar que se add la espMed, es posible que hay una especialidad flotando, es decir que exista pero que ningún médico la posea...
            }
        }//solo te hace falta colocar las condis cuando suceda un error, sin importar
        //En cual de los 2 métodos de registro sucdio...
    }/*terminado*/   
    
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
        Nodo nodoCOincidente = listaEspecialidades.darNodoCoincidente(listaEspecialidades, titulo);
        
        if(listaEspecialidades.darNumeroNodoCoincidente()==0){//si, pues no existe...
            if(creacion.crearEspecialidad(true, titulo)){
                if(listaEspecialidades.estaVacia()){
                    listaEspecialidades.anadirAlFinal(titulo);
                }else{
                    listaEspecialidades.agregarNuevoSiguiente(nodoCOincidente, titulo);                                        
                }//al momento de guadar las consultas, ahora que recuerdo, no reviso si ya se habría agergado una con el mismo nombre... pero de todos modos no habrá fallos puesto que el nombre es su PK y yo lo pasp a ínus... xD :) :D                                                 
                nodoCOincidente.establecerAtributoExtra("0");//esto me hace pensar que talvez si sería útil agregar una clase para Especialidad u otra, pero qu debería addse 1... pero es que después no se usará tanto, y eso no me parece...
                return listaEspecialidades.darTamanio();
                //aL FINAL de cuentas ya no incluí esta revisión en las consultas, puesto que si no existe en la DB 
            }else{
               return 0;
            }
        }
        
        return listaEspecialidades.darNumeroNodoCoincidente();        
    }/*terminado*/
  
    public void crearExamen(String codigo, String nombre, String requiereOrden, String descripcion, String costo, String tipoExtensionResultado){//quedemos de acuerdo con que los parseos se harán aquí para que de esa manera no surga un error en el exterior y dichos parseos no serán agregados de manera directa al campo, para evitar que se deje a medias el trabajo...
        String crear = "INSERT INTO Examen (?,?,?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            int codigoExamen = Integer.parseInt(codigo);            
            
            instruccion.setInt(1, codigoExamen);
            instruccion.setString(2, nombre);
            instruccion.setString(3, requiereOrden);
            instruccion.setNString(4, descripcion);//supongo que esto será útil para un tinyChar...
            instruccion.setString(5, costo);
            instruccion.setString(6, tipoExtensionResultado);       
            
            instruccion.executeUpdate();
            controladorIndices.establecerUltimoIndice(6, codigoExamen);
            
        }catch(Exception e){
            //mandas a llamar al método para agregar a la lista de errores...
            System.out.println("error al crear examen"+ e.getMessage());
        }        
    }/*terminado*/
    
    public void crearLaboratorista(String codigo, String nombre, String registroMS, String DPI, String telefono, String codigoExamenAsignado, 
            String[] diasTrabajo, String correo, String fechaIncorporacion, String contrasenia){
        
        String crear = "INSERT INTO Laboratorista (?,?,?,?,?,?)";
        int codigoDatosPersonales= creacion.crearDatosPersonales(true, correo, contrasenia, telefono, DPI);           
        int parteNumerica = herramienta.extraerParteNumerica(4, codigo);//LAB-
        
        if(codigoDatosPersonales>0 && parteNumerica!=0){
           try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
                java.sql.Date fechaInicio=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaIncorporacion).getTime());//si se atrapa el error
                int codigoExamen = Integer.parseInt(codigoExamenAsignado);                        
                      
                instruccion.setInt(1, parteNumerica);
                instruccion.setString(2, nombre);
                instruccion.setString(3, registroMS);                        
                instruccion.setInt(4, codigoExamen);
                instruccion.setDate(5, fechaInicio);                                
                instruccion.setInt(6, codigoDatosPersonales);                
            
                instruccion.executeUpdate();
                controladorIndices.establecerUltimoIndice(3, parteNumerica);                
                if(!creacion.crearHorarioLaboratorista(true, parteNumerica, diasTrabajo)){
                    //llmarás al método para asignar a la lista de errores...
                }
            }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){ //me están dando ganas de colocar exception, puesto que no especificaré el tipo de error, o sí???
                //mandas a llamar al método para agregar al listado de errores, en este caso sería porque no pudo crearse al labo...
                System.out.println("error al cargar el laboratorista -> "+ e.getMessage());//creo que esto...nop, el usario no lo entendería mucho, o sí? o con esto estaría delantando el proceso :O  eso si no xD
            }
        }else{
            //mandas a llamar al método para agregar al listado de errores, en este caso sería porque no pudo crearse al labo...
        }                                    
    }/*terminado*///solo hace falta que se agregue el control de indices...es decir implementarlo y agregar lo de las excepciones...
    
    public void crearConsultaAtendida(String codigoPaciente, String codigoMedico,  String fecha, String hora, String codigoInforme, String informe){
    //Su codigo lo obtnedrá del método para los ID... por el momento tengo pensado que se haga desde 
    //la carga de datos, para que se mande de una vez el argu aquí como int, pero la cuestión es... y si falla el proceso... por ello debería revisarlo para saber si debo hacer o no la consulta y para ello mejor lo hago desde aquí...
        String crear ="INSERT INTO Consulta_Atendida (codigo, codigoPaciente, codigoMedico, fecha, hora) VALUES (?,?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){            
            int paciente = Integer.parseInt(codigoPaciente);
            java.sql.Date fechaInicio=herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fecha).getTime());
            int horaRealizacion = Integer.parseInt(hora);            
            int codigoDeInforme = Integer.parseInt(codigoInforme);
            int parteNumerica = herramienta.extraerParteNumerica(4, codigoMedico);
            //se manda a llamar al método que se encarga de generar los códigos a partir del número máximo hallado en el txt
            int codigoConsultaAtendida = controladorIndices.darIndiceCorrespondiente(1);
            
            instruccion.setInt(1, codigoConsultaAtendida);
            instruccion.setInt(2, paciente);
            instruccion.setInt(3, parteNumerica);            
            instruccion.setDate(5, fechaInicio);//PUESTO QUE tipo de consultas en la carga, es null
            instruccion.setInt(6, horaRealizacion);  
            
            instruccion.executeUpdate();
            
            if(creacion.crearInforme(codigoDeInforme, codigoConsultaAtendida, informe)){//lo coloco aquí por el hecho de que podría generarse una excepción al hacer al converesión del código de informe)
                controladorIndices.establecerUltimoIndice(8, codigoDeInforme);
            }                        
        }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){        
            //Agregas a la lista de excepciones...
            System.out.println("error al cargar la consulAt ->"+e.getMessage());
        }                        
    }/*terminado*/
    
    //método de convergencia para la creación de un resultado a partir de la carga d e datos...
    public void crearDatosExamenAtendido(String codigoResultado, String codigoPaciente,  String codigoExamen, String codigoLaboratorista,
    String pathOrden, String pathResultado, String fechaRealizacion, String horaRealizacion){//si al final de cuentas lo cb a un campo normal,entonces establecerás un valor null a codMed para que no halla problemas, de todos modos en este punto ya se le habrá notificado...
        
        int codigoExamenAtendido = controladorIndices.darIndiceCorrespondiente(0);//aquí se manda a llamar al método para obtner el índice corresp, sip , deberá ser llamado aquí              
        int parteNumerica = herramienta.extraerParteNumerica(4, codigoLaboratorista);
        
        if(crearExamenAtendido(codigoExamenAtendido, parteNumerica, codigoExamen, codigoPaciente, fechaRealizacion, horaRealizacion)){//puede fallar ya sea por casteo [debido al codigoExamen o porque surgió algo en su interior...
            int numeroOrden= creacion.crearOrdenSinMedico(pathOrden);//ya que no te lo dan y como de todos modos aunque hicieras otro método, al final de cuentas tendrías la col en null... a menos que colocaras algo como "si registros" apra hacer ref a que no se indicó el médico en la carga...
            
            if(numeroOrden!=0){
                crearResultadoConOrden(codigoResultado, codigoExamenAtendido, pathResultado, numeroOrden);//falta lo de los paths, puesto que no has leido
            }else if(pathOrden==null){
               crearResultadoSinOrden(codigoResultado, codigoExamenAtendido, pathResultado);
            }                           
        }//no debes add a la lista, pueto que el método de exAt, ya lo hace...           
    }//aquí en este método se mandará a llamar al que se encarga de darle el id a exAt... dew esta manera se podrá detectar el error si es que hubiera uno...
    
    public boolean crearExamenAtendido(int codigoExamenAtendido, int codigoLaboratorista, String codigoExamen, String codigoPaciente, String fechaDeRealizacion, String hora){
        //por el hecho de ir a trar su código con el método que acude la rimera vez al arch y luego emplea al métooo para hacer el incre... creo que el parám de código
        //deberá desaparecer... puesto que aquí dentro se invocará al método para el incre y en el exterior en la clase a la que se acuda al iniciar el program, se
        //invocará el metodo para acceder al txt...
        
        String crear = "INSERT INTO Examen_Atendido (?,?,?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            int examen = Integer.parseInt(codigoExamen);
            int paciente = Integer.parseInt(codigoPaciente);
            java.sql.Date fechaRealizacion =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fechaDeRealizacion).getTime());
            int horaRealizacion = Integer.parseInt(hora);
            
            instruccion.setInt(1, codigoExamenAtendido);
            instruccion.setInt(2, codigoLaboratorista);
            instruccion.setInt(3, examen);
            instruccion.setInt(4, paciente);
            instruccion.setDate(5, fechaRealizacion);
            instruccion.setInt(6, horaRealizacion);
            
            instruccion.executeUpdate();//recuerad que estos métodos que no se volverán autoincre, no deben establecer a cada ratos el ínidce para reemplazar si es que ahora tiene un valor > o dejar el que tenía en caso contrario... esto lo digo por el hecho de que estos comienzan a aprtir de 1 y siempre incre de 1 en 1, así no es nec...            
            
        }catch(NumberFormatException | NullPointerException | ClassCastException | SQLException e){
            //Agregas a la lista de excepciones... [donde sería bueno especificaras el tipo...]
            System.out.println("error al cargar el exAt ->"+e.getMessage());            
            return false;
        }      
        
        return true;
    }/*terminado*/
      
 //NO OLVIDES QUE Cuando el resultado no requiera orden NO PUEDES mandarle un valor 0, puesto que hace referencia a una tabla  no es cualquier atributo... de lo contrario sí se podría tener 1 solo método...
      public void crearResultadoConOrden(String codigoResultado, int codigoExamenAtendido, String path, int numeroOrden){//falta el path... y al mandarle el código del examen, se permite que puedan corregirse los errores manualmente, si es que este no va a ser generado por ti misma...
         String crear="INSERT INTO Resultado VALUES (?,?,?,?)";//te recuerda que lo del path está aśi, puesto que guardarás la dirección, entonces es indiferente...         
         
         try(PreparedStatement instruccion = conexion.prepareStatement(crear)){    
             int codigo = Integer.parseInt(codigoResultado);
             
             instruccion.setInt(1, codigo);
             instruccion.setInt(2, codigoExamenAtendido);
             instruccion.setString(3, path);
             instruccion.setInt(4, numeroOrden);
             
             instruccion.executeUpdate();
             controladorIndices.establecerUltimoIndice(7, codigo);             
         }catch(SQLException sqlE){
             //Mandas a llamar al método para añadir el error...
         }                           
     }
     
     public void crearResultadoSinOrden(String codigoResultado,int codigoExamenAtendido, String path){//Esto s 2 si solo son para creación...
         String crear ="INSERT INTO Resultado (codigo,codigoExamenAtendido,pathResultado) VALUES (?,?,?)";
         
         try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
             int codigo = Integer.parseInt(codigoResultado);
             
             instruccion.setInt(1, codigo);  
             instruccion.setInt(2, codigoExamenAtendido);
             instruccion.setString(3, path);             
             
             instruccion.executeUpdate();
             controladorIndices.establecerUltimoIndice(7, codigo);//puesto que después se volverá autoincre, la tabla completa...
         }catch(SQLException sqlE){
             //Mandas a llamar al método para añadir el error...
         }
     }
    
    public void crearCita(String codigoCita, String codigoPaciente, String codigoMedico, String tipoConsulta, String fecha, String hora){
        String crear ="INSERT INTO Cita_Medica VALUES (?,?,?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            int numeroCita = Integer.parseInt(codigoCita);
            int paciente = Integer.parseInt(codigoPaciente);
            java.sql.Date fechaRealizacion =herramienta.devolverSQLDate(herramienta.convertirStringAUtilDate(fecha).getTime());
            int horaRealizacion = Integer.parseInt(hora);
            int parteNumerica = herramienta.extraerParteNumerica(4, codigoMedico);
            
            instruccion.setInt(1, numeroCita);
            instruccion.setInt(2, paciente);            
            instruccion.setInt(4, parteNumerica);
            instruccion.setString(5, tipoConsulta);
            instruccion.setDate(6, fechaRealizacion);
            instruccion.setInt(7, horaRealizacion);
            
            instruccion.executeUpdate();
            controladorIndices.establecerUltimoIndice(1, numeroCita);//Esto es para que se pueda trabajar de forma correcta con el ID para la citaAtendida...            
        }catch(SQLException sqlE){
            //se manda a llmar al método para que sea agregada la excepcion al listado...
            System.out.println("error al cargar la cita -> "+ sqlE.getMessage());
        }        
    }/*terminado*/

    public void crearConsulta(String tipo, String costo){
        listaEspecialidades.darNodoCoincidente(listaEspecialidades, tipo);
        
        if(listaEspecialidades.darNumeroNodoCoincidente()!=0){
            creacion.crearConsulta(tipo, costo);
        }else{
            //se agrega a la lista de errores, puesto que esa especialidad no existe en la tabla correspondiente
        }               
    }
     
    public void establecerControladorIndices(ControlIndices controlador){
        controladorIndices = controlador;
    }
    
    //recuerda que con respecto a los índices, los que tienen cols que dependen de los índices ant estableceidos por el método que crea la tabla que le precede, obtienen y registran en la var... y todos al finalizar su proceso, registran en el arch...
    
     /*
    Lo bueno es que estas clases CRUD, no requieren ser la misma instancia en las demás clases,puesto 
    que solo exe axn, mas no almacenan nada en su interior... o al menos por ahora xD
    */
}
