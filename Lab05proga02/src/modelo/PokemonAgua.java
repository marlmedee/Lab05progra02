/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public class PokemonAgua extends Pokemon{
    private static final long serialVersionUID = 1L;
    public PokemonAgua(String nombre,int nivel, int ps, int ataque,int defensa, int velocidad) {
        super(nombre, TipoPokemon.AGUA, nivel, ps, ataque, defensa, velocidad);
    }
    @Override
    public String getNombreHabilidadEspecial(){
        return "Hidropulso vital";
    }
    @Override
    public String getDescripcionHabilidad(){
        return "Potencia 18, recupera el 10% de los PS máximos del usuario";
    }

    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo){
        int danio = calcularDanio(18, objetivo);
        objetivo.recibirDanio(danio);
        int curacion = curar((int) Math.floor(getPsMaximos() * 0.10));
        String efectividad = getTipo().obtenerMensajeEfectividad(objetivo.getTipo());
        return getNombre()+" ejecuta "+getNombreHabilidadEspecial()+" sobre "+objetivo.getNombre()+ " causando " +danio+" de daño"+efectividad+ " y recupera " +curacion+" PS.";
    }
}
