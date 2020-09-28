/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

/**
 *
 * @author phily
 */
public class ListaEnlazada <E>{
    private Nodo<E> primerNodo;//posee el primero objeto puesto que así se sabe de donde partir
    private Nodo<E> ultimoNodo;//obtiene el último elemento, el cual de forma directa ayuda a saber si tiene o no elementos
    private String nombreLista;//podría tener nombre,, solo debes pensar como se lo asignarás
    private int tamanioLista;
    private int tamanioFinal;//Esta var será útil para las propiedades, pues esta contiene el número de elementos totales que contiene un grupo, en este caso almacenado en una lista
    //private Casilla casillaAnadida;
    
    public ListaEnlazada(){
        crearListar();
    }
    
    public ListaEnlazada(String nombreLista){//Este será empleado para las propiedades del jugador
        crearListar();
        establecerNOmbre(nombreLista);
    }
    
    public void insertarAlInicio(E elementoInsertar){
        primerNodo=ultimoNodo= new Nodo<E>(elementoInsertar);
    }      
    
    public void crearListar(){
        //si tine nombre aquí deberías indicarlo
        primerNodo=ultimoNodo=null;//porque no tienen ningun elemento
    }
    
    public void establecerNOmbre(String nombre){
        nombreLista=nombre;
    }
    
    public void establecerTamanioLimite(int limite){
        tamanioFinal=limite;
    }       
    
    public void anadirAlFinal(E elementoInsertar){
        if(estaVacia()){
            insertarAlInicio(elementoInsertar);
            tamanioLista++;
        }        
        else{
             ultimoNodo=ultimoNodo.nodoSiguiente= new Nodo<E>(elementoInsertar);
             tamanioLista++;
        }        
    }
    
      public void eliminarUltimoNodo() {
            if (ultimoNodo!=null) {
                if (tamanioLista == 1)
                {
                    primerNodo = ultimoNodo = null;
                }
                else
                {
                    Nodo<E> nodoAuxiliar = primerNodo;

                    for (int nodoActual = 1; nodoActual < (tamanioLista - 1); nodoActual++)//debe ser -1 puesto que quiero quedarme en el penúltimo nodo para así eliminar al último, además por el hehco de tener antes de comenzar con el ciclo el primer nodo, es necesario que considere el valor al que quiero llegar a sabiendas de que ya llevo adelantado un paso...
                    { //Así cabal se queda en el penúltimo nodo...
                        nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                    }

                    nodoAuxiliar.nodoSiguiente = null;//bye bye xD GRACIAS POR TUS SERVICIOS... XD
                    ultimoNodo = nodoAuxiliar;//Si la lista tiene 1 solo ele cabal 
                }

                tamanioLista--;
           }            
     }
      
    /**
     * Añade un nuevo siguiente al nodo de la lista qué invocó
     * al método [si se ingersa un nodo que no le pertenezca, se
     * generará una inconsistencia con el tamaño...]     
     * 
     * @param nodoBase
     * @param contenido
     */
    public void agregarNuevoSiguiente(Nodo<E> nodoBase, E contenido){//ese problema se resolvería enviando la listaENlazada a la que le pertenece el nodo y el nodo o la lista y la posición donde se add, pero eso no lo quería hacer así porque implicaría recorrer nuevamente la lista, eso último sería para add donde sea no habiendo recorrido dicho listado antes, de preferencia...
         nodoBase.AnadirUnSiguiente(contenido);
         tamanioLista++;
     }
      
     public Nodo<E> darUltimoNodo() {
            return ultimoNodo;
     }

     public E darContenidoUltimoNodo() {
            return ultimoNodo.contenido;
     }
    
     public boolean estaVacia(){
        return primerNodo==null;
    }
    
    public Nodo<E> obtnerPrimerNodo (){
        return primerNodo;
    }
    
    public Nodo<E> obtenerUltimoNodo(){
        return ultimoNodo;
    }   
    
    public int darTamanio(){
        return tamanioLista;
    }
    
    public int darTamanioLimite(){// "límite" para el caso de los grupos de las proiedades
        return tamanioFinal;
    }
    
    public String obtenerNombre(){
        return nombreLista;
    }
}
