/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
import java.util.Random;
public class PokemonElectrico extends Pokemon{
    int danioFinal;
    String efectividad;
    String textoCritico;
    private static final long serialVersionUID = 1L;
    private static final Random RANDOM = new Random();
    public PokemonElectrico(String nombre, int nivel, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.ELECTRICO, nivel, ps, ataque, defensa, velocidad);
    }
    @Override
    public String getNombreHabilidadEspecial() {
        return "Impactrueno Critico";
    }
    @Override
    public String getDescripcionHabilidad() {
        return "Potencia 17, tiene un 25% mas de probabilidad de golpe critico (x1,5)";
    }
    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int danioBase = calcularDanio(17, objetivo);
        boolean critico = RANDOM.nextDouble() < 0.25;
        if (critico){
            danioFinal = (int) Math.round(danioBase * 1.5);
        }
        else{
            danioFinal = danioBase;
        }
        objetivo.recibirDanio(danioFinal);
        efectividad = getTipo().obtenerMensajeEfectividad(objetivo.getTipo());
        textoCritico = critico ? " Golpe critico!" : "";
        return getNombre() + " descarga "+getNombreHabilidadEspecial()+" sobre "+objetivo.getNombre()+" causando " + danioFinal + " de daño " + textoCritico + efectividad;
    }
}
