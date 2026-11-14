/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class AtaqueBasico implements Movimiento{
    private static final long serialVersionUID = 1L;
    private static final int POTENCIA_BASICA = 10;
    @Override
    public String obtenerNombre() {
        return "Ataque Basico";
    }
    @Override
    public String ejecutar(Pokemon usuario, Pokemon objetivo){
        int danio = usuario.calcularDanio(POTENCIA_BASICA, objetivo);
        objetivo.recibirDanio(danio);
        String ef =
        usuario.getTipo().obtenerMensajeEfectividad(objetivo.getTipo());
        return usuario.getNombre()+"usa"+obtenerNombre()
        + " contra " + objetivo.getNombre()
        + " y causa " + danio + " puntos de daño" + ef;
    }
}
