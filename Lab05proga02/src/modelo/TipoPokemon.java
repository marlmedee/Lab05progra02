/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public enum TipoPokemon {
    FUEGO("Fuego"),
    AGUA("Agua"),
    PLANTA("Planta"),
    ELECTRICO("Eléctrico");
    private final String nombreVisible;
    TipoPokemon(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }
    public String getNombreVisible() {
        return nombreVisible;
    }
    public double obtenerMultiplicador(TipoPokemon defensor) {
        switch (this) {
            case FUEGO:
            if (defensor == PLANTA){
                return 2.0;
            }
            if (defensor == FUEGO || defensor == AGUA){
                return 0.5;
            }
            return 1.0;
            case AGUA:
            if (defensor == FUEGO){
                return 2.0;
            }
            if (defensor == AGUA || defensor == PLANTA){
                return 0.5;
            }
            return 1.0;
            case PLANTA:
            if (defensor == AGUA){
                return 2.0;
            }
            if (defensor == FUEGO || defensor == PLANTA){
                return 0.5;
            }
            return 1.0;
            case ELECTRICO:
            if (defensor == AGUA){
                return 2.0;
            }
            if (defensor == PLANTA || defensor == ELECTRICO){
                return 0.5;
            }
            return 1.0;
            default:
                return 1.0;
            }
        }
        public String obtenerMensajeEfectividad(TipoPokemon defensor) {
        double mult = obtenerMultiplicador(defensor);
        if (mult == 2.0){
            return " ¡Es muy eficaz!";
        }
        if (mult == 0.5){
            return " No es muy eficaz...";
        }
            return "";
    }
    @Override
    public String toString() {
        return nombreVisible;
    }
}
