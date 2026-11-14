/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class PokemonPlanta extends Pokemon{
    private static final long serialVersionUID = 1L;
    public PokemonPlanta(String nombre,int nivel,int ps,int ataque,int defensa,int velocidad) {
        super(nombre, TipoPokemon.PLANTA,nivel, ps,ataque,defensa, velocidad);
    }
    @Override
    public String getNombreHabilidadEspecial() {
        return "Drenadoras";
    }
    @Override
    public String getDescripcionHabilidad() {
        return "Potencia 18: absorbe la mitad del daño causado como PS";
    }
    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int danio = calcularDanio(18, objetivo);
        objetivo.recibirDanio(danio);
        int absorbido = curar((int) Math.floor(danio / 2.0));
        String efectividad = getTipo().obtenerMensajeEfectividad(objetivo.getTipo());
        return getNombre() + " usa " + getNombreHabilidadEspecial()+" contra "+objetivo.getNombre()+" causando " + danio + " de daño" + efectividad+" y absorbe " + absorbido + " PS.";
    }
}
