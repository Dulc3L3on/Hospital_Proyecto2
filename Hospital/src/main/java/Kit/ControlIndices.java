/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ControlIndices {//Este será empleado por todas aquellas entidades que aparecen en el txt, puesto que su FK es # o tiene parte de él, y como no sabes como volver autoincrementable luego de eso y aunque supieras no para todas funciona [más que todo en el caso de las citas, puesto que son traspasadas xD a la tabla de las atendidas...
    String path ="noBorrar.txt";
    int ultimosIndicesCreados[] = new int[8];//este se llenará con el contenido de los números que se encuentran en el archivo correspondiente... pero en el caso de la carga de datos, esto no será necesario, al menos al cargar luego de haberlo cargado sí será necesario... al igual que despueś de haber inicizdo secsión una determinada entidad
    //0-> citaExamen, 1-> citaMedica, 2-> MEDICO, 3-> LABORATORISTA, 4-> PACIENTE, 5-> ADMINISTRADOR, 6-> Examen, 7-> Resultado
    
    public void recuperarUltimosIndices(){
        try(FileReader lector = new FileReader(new File("path"))){
            BufferedReader buffer = new BufferedReader(lector);
            
            for (int numeroLinea = 0; numeroLinea < 8; numeroLinea++) {
                ultimosIndicesCreados[numeroLinea ]=Integer.parseInt(buffer.readLine());
            }//y así obtengo los último índices xD
            
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog( null, "No se encontró el archivo\npara recuperacion de indices\nsolicite ayuda de su superior", "error indice", JOptionPane.ERROR_MESSAGE);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Ocurrio un error al recuperar\nlos ultimos indices creados\n"
                    + "cierre y vuelva a iniciar\nsi el problema persiste\nsolicite ayuda de su encargado","error indice", JOptionPane.ERROR_MESSAGE);
        }                
    }//y así 
    
    public void registrarUltimosINdices(){
        try(FileWriter escritor = new FileWriter(new File("noBorrar.txt"))){
            PrintWriter impresora = new PrintWriter(escritor);
            
            for (int numeroLinea = 0; numeroLinea < 8; numeroLinea++) {
                impresora.println(String.valueOf(ultimosIndicesCreados[numeroLinea]));
            }
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Surgió un error al almacenar\nlos últimos sindices creados", "error indice", JOptionPane.ERROR_MESSAGE);
        }
        
    }//y así mando a guardar los últimos ínidces xD
    
    public void establecerUltimoIndice(int tipoUltimoIndice, int nuevoUltimo){
        ultimosIndicesCreados[tipoUltimoIndice]=nuevoUltimo;
    }
    
    public int obtenerULtimoIndice(int tipoUltimoIndice){
        return ultimosIndicesCreados[tipoUltimoIndice];
    }
    
   
}
