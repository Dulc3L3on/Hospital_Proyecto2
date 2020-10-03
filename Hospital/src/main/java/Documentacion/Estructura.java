/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documentacion;

/**
 *
 * @author phily
 */
public class Estructura {
    String nombre;//para el caso de la consutla, esta es su PK...
    String costo;
    
    public Estructura(String elNombre, String elCosto){
        nombre= elNombre;
        costo = elCosto;
    }
    
    //aquí irán los métodos de la aaxn que puede hacer... no se si inlcuir crear, puesto que eso lo hace la clase que tiene que ver con la DB, por medio de la llmaada de la entidad que corresponde al usuario "creador" de esta estructura [administrador...]
    
    public void reestablecerNombre(String nuevoNombre){//para conusltas NO PUEDE SER APP ESTO, puesto que es su PK...
        nombre= nuevoNombre;
    }
    
    public void reestablecerCosto(String nuevoCosto){
        costo = nuevoCosto;
    }
    
    public String darNombre(){
        return nombre;
    }
    
    public String darCosto(){
        return costo;
    }
}
