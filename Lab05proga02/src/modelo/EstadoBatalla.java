/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
public enum EstadoBatalla {
    PREPARACION("Preparación"),
    ESPERANDO_ACCION("Esperando acción del entrenador"),
    RESOLVIENDO_RONDA("Resolviendo ronda"),
    ELEGIR_REEMPLAZO("Eligiendo reemplazo"),
    VICTORIA("Victoria"),
    DERROTA("Derrota"),
    EMPATE("Empate");
    private final String descripcion;
    EstadoBatalla(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
