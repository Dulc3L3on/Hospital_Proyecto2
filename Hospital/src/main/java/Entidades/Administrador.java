/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Extras.DatosPersonales;
import Extras.Reporte;
import Kit.Herramienta;
import java.io.Serializable;
import Kit.ListaEnlazada;
import Kit.Nodo;
import Manejadores.DB.BusquedaGeneral;
import Manejadores.DB.Creacion;
import Manejadores.DB.CreacionAdministrada;
import Manejadores.DB.Modificacion;
import Manejadores.DB.Reportes.ReporteAdministrador;
import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Administrador extends Usuario implements Serializable{            
    BusquedaGeneral buscador = new BusquedaGeneral();
    Modificacion modificador = new Modificacion();
    CreacionAdministrada creacion = new CreacionAdministrada();
    Creacion creador = new Creacion();
    ReporteAdministrador reporte = new ReporteAdministrador();
    Herramienta herramienta = new Herramienta();
    
    public Administrador(int elCodigo, String elNombre, DatosPersonales datosPersonales){
        super(elCodigo, elNombre, datosPersonales);                
    }
    
    //RECUERDA: COn respecto a los datos personales, solo le enviarás el DPI y la contrasenia... y no olvides qu eeso no afecta al método del Papá...
    
    @Override
     public void registrarse (){//bueno realemente no... porque olvidaron ponerlo xD
    
    }
    
    @Override
    public void iniciarSesion(){
    
    }
    
    @Override
    public void cerrarSesion (){
        
    
    }
    
    @Override
   public void modificarPerfil(String[] datos){
    
    }
    
    @Override
    public void buscar(String tipoBusqueda){
    
    }
    
   public ListaEnlazada<String[]> contabilizarIngresosPorCliente(String desde, String hasta){      
       ListaEnlazada<Reporte> listaPorConsultas  = reporte.ingresosPorPacienteDebidoConsultas(desde, hasta);
       ListaEnlazada<Reporte> listaPorExamenes = reporte.ingresosPorPacienteDebidoExamenes(desde, hasta);               
               
       if(!listaPorConsultas.estaVacia() && !listaPorExamenes.estaVacia()){
           int resultado[] = herramienta.darElMayor(listaPorConsultas.darTamanio(), listaPorExamenes.darTamanio());
           String[][] valoresNumericos = new String[resultado[2]][3];
           String[] resultados;
           ListaEnlazada<Reporte> listaMayor;
           ListaEnlazada<Reporte> listaMenor;
           
           if(resultado[0]==1){
               listaMayor=listaPorConsultas;
               listaMenor= listaPorExamenes;
           }else{
               listaMayor=listaPorExamenes;
               listaMenor= listaPorConsultas;
           }                      
           
           Nodo<Reporte> nodoMayor = listaMayor.obtnerPrimerNodo();           
           
           for(int nodoActual=0; nodoActual< listaMayor.darTamanio(); nodoActual++){
              if(listaMenor.darTamanio()>0){//puesto que las coiincidencias se irán eliminando...
                  resultados= fusionarPagos(nodoMayor.contenido.darNumero(), listaMenor);
                  valoresNumericos[nodoActual][0]=resultados[0];
                  valoresNumericos[nodoActual][1]=resultados[1];
                  valoresNumericos[nodoActual][2]=resultados[2];
              }else{
                  
              }
           }
           
       }
       
       return null;
   }//se quedará así puesto que aunque sea pequeña, puede no coincidir en lo absoluto y por lo tanto hbría que agregar una condición má... así que deplanom a mostrar los arreglos inidividuales...
   
   public String[] fusionarPagos(int valorABuscar, ListaEnlazada<Reporte> listadoARevisar){
       int resultados[] = new int[3];
       
       for (int examenActual = 1; examenActual <= listadoARevisar.darTamanio(); examenActual++) {
           if(1==1){
           
           }
           
           
       }
       return null;       
   }
    
    
    
//    public void buscar(String buscado){
//        if(!buscado.equals("Examen") && !buscado.equals("Consulta")){
//            buscador.
//        }
//        
//        
//    }//esto deplano que mañana, porque será necesario tener la interfaz para saber al final como se hará para manejar la infor dada...

    
    public void modificarMedico(int codigo, String nombre, String contrasenia, String numeroColegiado, String DPI, 
      String telefono, String correo, Date fechaIncorporacion, int horaInicio, int horaFin, ListaEnlazada<String> titulosAntiguos, String[] nuevosTitulos){//mando el valor como es, puesto que no hay pierde... ya que cada cajita tiene sus especificaciones
    
        if(!modificador.modificarDatosPersonales(codigo, correo, contrasenia, telefono, DPI) || 
          !modificador.modificarMedico(codigo, nombre, numeroColegiado, horaInicio, horaFin, fechaIncorporacion)
          || !modificador.modificarTitulario(codigo, titulosAntiguos, nuevosTitulos)){
            JOptionPane.showMessageDialog(null,"", "Sucedió un error en la creacion\nbusque y revise la\ninformacion guardada", JOptionPane.ERROR_MESSAGE);
        }       
    }//solo falta revisar lo de las sesiones para reemplazar lo que está en el JSP...
    
    public void crearMedico(String nombre, String colegiado, String horaInicio, String horaFin, String fechaIncorporacion, String correo, String contrasenia, String telefono, String DPI, String[] titulos){
        int codigoMedico=creacion.crearMedico(nombre, colegiado, horaInicio, horaFin, horaInicio, correo, contrasenia, telefono, DPI);
        
        if(codigoMedico!=0){
            creacion.crearTitulos(codigoMedico, titulos);
        }else{
            JOptionPane.showMessageDialog(null, "", "Surgió un problema en la\ncreación del médico\nrevise los datos", JOptionPane.ERROR_MESSAGE);
        }
    }//solo falta llevarlo a la interfaz...
    
    public void modificarLaboratorista(int codigo, String correo, String contrasenia, String telefono, String DPI, String nombreLaboratorista, String registroMS,
      int codigoExamen, Date fechaIncorporacion, boolean[] diasTrabajo){
        if(modificador.modificarDatosPersonales(codigo, correo, contrasenia, telefono, DPI) ||
            modificador.modificarLaboratorista(codigoExamen, nombreLaboratorista, registroMS, codigoExamen, fechaIncorporacion)
            || modificador.modificarHorarioLaboratorista(codigoExamen, diasTrabajo)){
        
            JOptionPane.showMessageDialog(null, "", "sucedio un error en la modificacion\ndel laboratorista corrobore los\ndatos guardados", codigoExamen);
        }        
    }
    
    public void crearLaboratorista(String nombre, String registroMS, String codigoExamen, String fechaIncorporacion, String correo, String contrasenia, String telefono, String DPI, String[] diasTrabajo){
        int codigoLaboratorista=creacion.crearLaboratorista(nombre, registroMS, telefono, telefono, correo, contrasenia, telefono, DPI, diasTrabajo);
            
        if(codigoLaboratorista!=0){
            creador.crearHorarioLaboratorista(false, codigoLaboratorista, diasTrabajo);
        }    
    }//de igual forma a estos 2 solo les hace falta ser incorporados en la interfaz...
}
