/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class PokemonFuego extends Pokemon{
    private static final long serialVersionUID = 1L;

    public PokemonFuego(String nombre, int nivel, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.FUEGO, nivel, ps, ataque, defensa, velocidad);
    }

    @Override
    public String getNombreHabilidadEspecial() {
        return "Llamarada Ígnea";
    }

    @Override
    public String getDescripcionHabilidad() {
        return "Potencia 20, dano elevado de fuego sin efecto secundario!";
    }

    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int danio = calcularDanio(20, objetivo);
        objetivo.recibirDanio(danio);
        String efectividad = getTipo().obtenerMensajeEfectividad(objetivo.getTipo());
        return getNombre()+" lanza "+getNombreHabilidadEspecial()+ " contra " + objetivo.getNombre()+" y causa " +danio+" puntos de daño " + efectividad;
    }
}
