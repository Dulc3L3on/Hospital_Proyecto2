/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores.DB;

import Kit.Herramienta;
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
                        //además de tener a los métodos con los que crearán los registros hechos por el administrador también irán todos los métodos para creación que independientemente de dónde sean llamados
public class Creacion {//harán lo mismo puesto que la clase que requiere de una pequeña variación del proceso, se encargará de ello... estos son identificacbles puesto que poseen el atrib esCarga, para saber si debe o no mostrar el msje de error
      private Connection conexion;
      private Herramienta herramienta = new Herramienta();
                
      public Creacion(Connection conexionDB){
          conexion = conexionDB;
      } 
      
      public int crearDatosPersonales(boolean esCarga, String correo, String contrasenia, String telefono, String DPI){
        int idDelCreado=0;
        String crear = "INSERT INTO Datos_Personales(?,?,?,?)";            
        String contraseniaEncriptada=herramienta.encriptarContrasenia(contrasenia);//por el proceso de encriptacion y desencriptación, asumo que esta valor solo estará mientras se esté creando, puesto que después se procederá a "traducirse" lo que en la DB está...
                
        try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){                       
            
            if(contraseniaEncriptada!=null){//si existe un fallo, solo se enterará de ello el método de encriptación en cuestión, por ello deberé encerrar en un if, para que no se ingresen los datos incompletos o malos a la tabla...
                instruccion.setString(1, correo);
                instruccion.setString(2, contrasenia);//dependiendo de qué devuelva una encriptación la llamada al métodoserá desde aquí o desde afuerita...
                instruccion.setString(3, telefono);
                instruccion.setString(4, DPI);
            
                instruccion.executeUpdate();
            
                ResultSet resultado=instruccion.getGeneratedKeys();
                idDelCreado=resultado.getInt(1);
            }//Aquí no colocarás un else, puesto que no es una tabla exterior, como médico, paciente, labo...                             
            
        }catch(SQLException sqlE){//no debe mostrar nada puesto que es una tabla que depende de otras... [además para la edición, donde prácticamente sería indep, no se emplea este método, así que...                      
            
        }finally{
            return idDelCreado;//tienes que colocar una condición de que si devulve 0, entonces no puede crearse a la entidad correspondiente
        }//media vez devuelve 0, es porque surgió un error...          
    }/*terminado*/
      
      
    public boolean crearEspecialidad(boolean esCarga, String nombre){//puesto que es autoincreReal xD
        String crear = "INSERT INTO Especialidad (nombre) VALUES (?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            instruccion.setString(1, nombre);
            
            instruccion.executeUpdate();
            
        }catch(SQLException ex){                                               
           return false;
        }
        
        return true;
    }/*terminado*/
    
    public boolean crearEspecialidadMedico(boolean esCarga, String codigoMedico, int codigoEspecialidad){//como en este caso pasó por un proceso anteiror el codigo de especialidad para que le fuese enviado a este, no habrá problemas y menos con la interfaz, puesto que el número es daod por mi misma xD no por el usuario, así que no hay pierde xD
        String crear = "INSERT INTO Especialidad_Medico (?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            instruccion.setString(1, codigoMedico);
            instruccion.setInt(2, codigoEspecialidad);
            
            instruccion.executeUpdate();
            
        }catch(SQLException sqlE){            
            System.out.println("error al crear espMed-> "+ sqlE.getMessage());
            return false;
        }//ahí decides si con estos métodos harás la add al listado de errores...
        
        return true;
    }/*terminado*/
    
    public boolean crearHorarioLaboratorista(boolean esCarga, String codigoLaboratorista, String[] diasTrabajo){//puesto que así lo recibe la tabla,pero recuerda que cuando obtengas estos datos los converitrás al tipo que corresponden... boolean... creo xD
        String crear ="INSERT INTO Horario_Laboratorista (?,?,?,?,?,?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            instruccion.setString(1, codigoLaboratorista);
            instruccion.setString(2, diasTrabajo[0]);
            instruccion.setString(3, diasTrabajo[1]);
            instruccion.setString(4, diasTrabajo[2]);
            instruccion.setString(5, diasTrabajo[3]);
            instruccion.setString(6, diasTrabajo[4]);
            instruccion.setString(7, diasTrabajo[5]);                                   
            instruccion.setString(8, diasTrabajo[6]);                                   
            
            instruccion.executeUpdate();
            
        }catch(SQLException sqlE){
            System.out.println("error al crear el horario del laboratorista -> "+ sqlE.getMessage());
            return false;
        }//Creoq que esta esCarga me será útil para saber si mando a agregar al listado de errores o no... aunque talvez tenga que seguir empleando esa ventana, en lugar de un solo JOP...
        
        return true;
    }/*terminado*/
    
     public boolean crearInforme(int codigoInforme, int codigoConsulta, String informe){//RECUERDA que sus datos en parte serán obtneridos de la consulta atendida más reciente [esto en la manera normal, lo cual lo lograrás saber porque corresponde a la recientemente atendida o registrada... la otra parte la obtendrá del método para los ID, puestp que es autoINcre "obligada xD" no lo sería si no hubieran problemas...
        String crear = "INSERT INTO Informe (?,?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            
            instruccion.setInt(1, codigoInforme);
            instruccion.setInt(2, codigoConsulta);
            instruccion.setString(3, informe);
            
            instruccion.executeUpdate();
            return true;
        }catch(SQLException sqlE){
            //mandas a llamar al método para insertar en las excepciones para mandar a decir que algo salió mal al estar creando el informe, e indicas el código del informe...            
            System.out.println("surgió un error al crear el informe -> "+sqlE.getMessage());
        }
        
        return false;
    }/*terminado*/
    
     public int crearOrden(String codigoMedico, String path){//aún no he leído bien, pero supondremos que se guarda el path... [pero si realmente llega a ser así, entonces con la forma normal tendría que haber una variación puestoq ue asignará el doc en sí ó nada más tomar el path u no el doc como tal de la selección mencionada...si tienes que agregar el arch entoces creo que tocará buscarlo en la carpeta dada...
         String crear ="INSERT INTO Orden (codigoMedico, pathOrden) VALUES (?,?)";//puesto que es autoincrementable...
         int idDelCreado=0;
         
        if(path!=null){
            try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){
                 instruccion.setString(1, codigoMedico);
                 instruccion.setString(2, path);
             
                 instruccion.executeUpdate();
                          
                 ResultSet resultado=instruccion.getGeneratedKeys();
                 idDelCreado=resultado.getInt(1);                        
            }catch(SQLException sqlE){
                //agregas el error al listado...
                 return 0;
            }                     
        }//Esto lo hago por la carga de datos        
        
        return idDelCreado;//ouesto que es una tabla "dependiente"[o interna, puesto que requiere de algo para ser generada...], la cual no requiere de cb en su actuar sin importar en qué situación se encuentre... entonces debe avisar que sucedió con su trabajo
     }
     
     public int crearResultadoConOrden(int codigoExamenAtendido, String path, int numeroOrden){//falta el path... y al mandarle el código del examen, se permite que puedan corregirse los errores manualmente, si es que este no va a ser generado por ti misma...
         String crear="INSERT INTO Resultado (codigoExamenAtendido, pathResultado, numeroOrden) VALUES (?,?,?)";         
         int IDdelCreado=0;
         
         try(PreparedStatement instruccion = conexion.prepareStatement(crear)){                                    
             instruccion.setInt(1, codigoExamenAtendido);
             instruccion.setString(2, path);
             instruccion.setInt(3, numeroOrden);
             
             instruccion.executeUpdate();
             
             ResultSet resultado = instruccion.getGeneratedKeys();
             IDdelCreado =resultado.getInt(1);
             
         }catch(SQLException sqlE){//recuerda, que si los datos se obtienen de la página, entonces aquí harás las conversiones [así como cuando recibe del XML...]
             //Mandas a llamar al método para añadir el error...
         }   
         
         return IDdelCreado;
     }
     
     public int crearResultadoSinOrden(int codigoExamenAtendido, String path){//Esto s 2 si solo son para creación...
         String crear ="INSERT INTO Resultado (codigoExamenAtendido, pathResultado) VALUES (?,?)";
         int IDdelCreado=0;
         
         try(PreparedStatement instruccion = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){
             instruccion.setInt(1, codigoExamenAtendido);            
             instruccion.setString(2, path);             
             
             instruccion.executeUpdate();
             
             ResultSet resultado = instruccion.getGeneratedKeys();
             IDdelCreado =resultado.getInt(1);
         }catch(SQLException sqlE){//recuerda, que si los datos se obtienen de la página, entonces aquí harás las conversiones [así como cuando recibe del XML...]
             //Mandas a llamar al método para añadir el error...
         }
         
         return IDdelCreado;
     }
     
    public void crearConsulta(String tipo, String costo){
        String crear="INSERT INTO Consulta (?,?)";
        
        try(PreparedStatement instruccion = conexion.prepareStatement(crear)){
            instruccion.setString(1, tipo.toLowerCase());//de esta manera se evitarán agergar una misma especialidd solo porque no tenía el mksmo caso...
            instruccion.setString(2, costo);
            
            instruccion.executeUpdate();
            
        }catch(SQLException sqlE){
             //se manda a llmar al método para que sea agregada la excepcion al listado...
        }        
    }
     
     //lo mismo sucede con orden, ocn respecto al archivo... pero aqui está más feito, puesto que no tendrías como una manera para indicarle que arch add, ya que no tienes mucha info...
     //y si guardas el oath piensas por el momento que te tocaría ir a buscar en la carpeta para ver cual coin con el path... mejor investiga!!!
     
     
}
