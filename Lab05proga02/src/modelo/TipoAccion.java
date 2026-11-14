/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public enum TipoAccion {
    ATACAR("Atacar"),
    HABILIDAD_ESPECIAL("Habilidad especial"),
    RECUPERAR("Recuperar"),
    CAMBIAR("Cambiar Pokémon");
    private final String etiqueta;
    TipoAccion(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    public String getEtiqueta() {
        return etiqueta;
    }
}
