/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class HabilidadEspecial implements Movimiento{
    private static final long serialVersionUID = 1L;
    @Override
    public String obtenerNombre() {
        return "Habilidad Especial";
    }
    @Override
    public String ejecutar(Pokemon usuario, Pokemon objetivo) {
        if (usuario.getUsosHabilidad() <= 0) {
            throw new IllegalStateException("No quedan usos de habilidad especial.");
        }
        usuario.consumirUsoHabilidad();
        return usuario.ejecutarHabilidadEspecial(objetivo);
    }
}
