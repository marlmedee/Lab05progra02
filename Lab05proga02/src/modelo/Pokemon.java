/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon implements Cloneable, Serializable{
    private static final long serialVersionUID = 1L;
    public static final int USOS_HABILIDAD_MAX = 3;
    public static final int USOS_RECUPERACION_MAX = 2;
    private final String nombre;
    private final TipoPokemon tipo;
    private final int nivel;
    private final int psMaximos;
    private int psActuales;
    private final int ataque;
    private final int defensa;
    private final int velocidad;
    private int usosHabilidad;
    private int usosRecuperacion;
    private List<Movimiento> movimientos;
    public Pokemon(String nombre, TipoPokemon tipo, int nivel, int psMaximos,int ataque, int defensa, int velocidad){
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.psMaximos = psMaximos;
        this.psActuales = psMaximos;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.usosHabilidad = USOS_HABILIDAD_MAX;
        this.usosRecuperacion = USOS_RECUPERACION_MAX;
        this.movimientos = crearMovimientosPorDefecto();
    }
    private List<Movimiento> crearMovimientosPorDefecto() {
        List<Movimiento> lista = new ArrayList<>();
        lista.add(new AtaqueBasico());
        lista.add(new HabilidadEspecial());
        lista.add(new Recuperacion());
        return lista;
    }
    public int calcularDanio(int potencia, Pokemon objetivo) {
        double mult = this.tipo.obtenerMultiplicador(objetivo.getTipo());
        double base = (this.ataque+this.nivel+potencia-(objetivo.getDefensa()/2.0)) * mult;
        return Math.max(1, (int) Math.round(base));
    }
    public void recibirDanio(int danio) {
        this.psActuales = Math.max(0, this.psActuales - Math.max(0, danio));
    }

    public int curar(int cantidad) {
        int antes = this.psActuales;
        this.psActuales = Math.min(this.psMaximos, this.psActuales + Math.max(0, cantidad));
        return this.psActuales - antes;
    }
    public boolean estaDebilitado() {
        return this.psActuales <= 0;
    }

    public void reiniciarEstadoBatalla() {
        this.psActuales = this.psMaximos;
        this.usosHabilidad = USOS_HABILIDAD_MAX;
        this.usosRecuperacion = USOS_RECUPERACION_MAX;
    }
    public void consumirUsoHabilidad() {
        if (usosHabilidad > 0) usosHabilidad--;
    }
    public void consumirUsoRecuperacion() {
        if (usosRecuperacion > 0) usosRecuperacion--;
    }

    public abstract String getNombreHabilidadEspecial();
    public abstract String getDescripcionHabilidad();
    public abstract String ejecutarHabilidadEspecial(Pokemon objetivo);

    @Override
    public Pokemon clone() {
        try {
            Pokemon copia = (Pokemon) super.clone();
            copia.movimientos = crearMovimientosPorDefecto();
            copia.reiniciarEstadoBatalla();
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error al clonar el Pokémon", e);
        }
    }

    public String getNombre(){
        return nombre;
    }
    public TipoPokemon getTipo(){
        return tipo;
    }
    public int getNivel(){
        return nivel;
    }
    public int getPsMaximos(){
        return psMaximos;
    }
    public int getPsActuales(){
        return psActuales;
    }
    public int getAtaque(){
        return ataque;
    }
    public int getDefensa(){
        return defensa;
    }
    public int getVelocidad(){
        return velocidad;
    }
    public int getUsosHabilidad(){
        return usosHabilidad;
    }
    public int getUsosRecuperacion(){
        return usosRecuperacion;
    }
    public List<Movimiento> getMovimientos(){
        return movimientos;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo.getNombreVisible() + " | Nv." + nivel + ")";
    }
}
