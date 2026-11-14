/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
import java.io.Serializable;
public interface Movimiento extends Serializable{
    String obtenerNombre();
    String ejecutar(Pokemon usuario, Pokemon objetivo);
}
