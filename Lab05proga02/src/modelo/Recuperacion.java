/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class Recuperacion implements Movimiento{
    private static final long serialVersionUID = 1L;
    @Override
    public String obtenerNombre() {
        return "Recuperar";
    }
    @Override
    public String ejecutar(Pokemon usuario, Pokemon objetivo){
        if (usuario.getUsosRecuperacion() <= 0) {
            throw new IllegalStateException("No quedan usos de recuperacion");
        }
        if (usuario.getPsActuales() >= usuario.getPsMaximos()){
            throw new IllegalStateException("Los PS ya están al maximo");
        }
        usuario.consumirUsoRecuperacion();
        int cantidad = (int) Math.floor(usuario.getPsMaximos() * 0.25);
        int curadoReal = usuario.curar(cantidad);
        return usuario.getNombre()+" usa "+obtenerNombre()+ " y recupera "+curadoReal+" PS";
    }
}
