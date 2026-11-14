
package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modelo.EstadoBatalla;
import modelo.Pokemon;

/**
 *
 * @author gigiw
 */
public class MotorBatalla {
    public static final int LIMITE_RONDAS = 50;

    private final String nombreJugador;
    private final String nombreRival;

    private final List<Pokemon> equipoJugador;
    private final List<Pokemon> equipoRival;

    private int indiceActivoJugador;
    private int indiceActivoRival;

    private int rondaActual;
    private EstadoBatalla estado;

    private final List<String> bitacora;
    private final Random random;

    public MotorBatalla(Entrenador entrenador, List<Pokemon> catalogo) {
        if (entrenador == null) {
            throw new IllegalArgumentException("El entrenador no puede ser nulo.");
        }

        if (catalogo == null || catalogo.isEmpty()) {
            throw new IllegalArgumentException("El catálogo de Pokémon está vacío.");
        }

        this.nombreJugador = entrenador.getNombre();
        this.nombreRival = "Rival CPU";

        this.equipoJugador = new ArrayList<>();
        this.equipoRival = new ArrayList<>();

        for (Pokemon pokemon : entrenador.getEquipo()) {
            if (pokemon != null) {
                this.equipoJugador.add(pokemon.clone());
            }
        }

        if (equipoJugador.isEmpty()) {
            throw new IllegalArgumentException("El entrenador necesita al menos un Pokémon.");
        }

        this.random = new Random();
        this.bitacora = new ArrayList<>();

        this.indiceActivoJugador = 0;
        this.indiceActivoRival = 0;
        this.rondaActual = 1;

        generarEquipoRival(catalogo);

        this.estado = EstadoBatalla.ESPERANDO_ACCION;

        registrar("¡Comienza la batalla entre " + nombreJugador + " y " + nombreRival + "!");
        registrar(nombreJugador + " envía a " + getPokemonActivoJugador().getNombre() + ".");
        registrar(nombreRival + " envía a " + getPokemonActivoRival().getNombre() + ".");
    }

    private void generarEquipoRival(List<Pokemon> catalogo) {
        List<Pokemon> disponibles = new ArrayList<>(catalogo);
        int cantidad = Math.min(6, disponibles.size());
        for (int i = 0; i < cantidad; i++) {
            int posicion = random.nextInt(disponibles.size());
            Pokemon seleccionado = disponibles.remove(posicion);
            equipoRival.add(seleccionado.clone());
        }
    }

    public Pokemon getPokemonActivoJugador() {

        if (indiceActivoJugador < 0
                || indiceActivoJugador >= equipoJugador.size()) {
            return null;
        }

        return equipoJugador.get(indiceActivoJugador);
    }

    public Pokemon getPokemonActivoRival() {
        if (indiceActivoRival < 0
                || indiceActivoRival >= equipoRival.size()) {
            return null;
        }
        return equipoRival.get(indiceActivoRival);
    }

    public void atacar() {
        if (!puedeContinuar()) {
            return;
        }
        Pokemon atacante = getPokemonActivoJugador();
        Pokemon objetivo = getPokemonActivoRival();
        int danio = calcularDanio(atacante, objetivo, 1.0);
        aplicarDanio(objetivo, danio);

        registrar(atacante.getNombre() + " utilizó un ataque básico contra " + objetivo.getNombre() + " causando " + danio + " puntos de daño.");
        verificarEstadoCombate();
    }

    public void usarHabilidadEspecial() {
        if (!puedeContinuar()) {
            return;
        }

        Pokemon atacante = getPokemonActivoJugador();
        Pokemon objetivo = getPokemonActivoRival();

        int danio = calcularDanio(atacante, objetivo, 1.5);

        aplicarDanio(objetivo, danio);

        registrar(atacante.getNombre() + " utilizó su habilidad especial contra " + objetivo.getNombre() + " causando " + danio + " puntos de daño.");
        verificarEstadoCombate();
    }

    public void recuperar() {
        if (!puedeContinuar()) {
            return;
        }

        Pokemon pokemon = getPokemonActivoJugador();
        int psAntes = pokemon.getPsActuales();
        int recuperacion = (int) (pokemon.getPsMaximos() * 0.25);
        int nuevosPS = Math.min(pokemon.getPsMaximos(), psAntes + recuperacion);

        pokemon.setPsActuales(nuevosPS);

        int recuperado = nuevosPS - psAntes;

        registrar(pokemon.getNombre() + " utilizó recuperación y restauró " + recuperado + " PS.");
        verificarEstadoCombate();
    }

    public boolean cambiarPokemon(int nuevoIndice) {
        if (estado == EstadoBatalla.FINALIZADA) {
            return false;
        }

        if (nuevoIndice < 0 || nuevoIndice >= equipoJugador.size()) {
            return false;
        }

        Pokemon nuevo = equipoJugador.get(nuevoIndice);

        if (nuevo.estaDebilitado()) {
            registrar("No puedes cambiar a un Pokémon debilitado.");
            return false;
        }

        if (nuevoIndice == indiceActivoJugador) {
            registrar("Ese Pokémon ya está en combate.");
            return false;
        }

        indiceActivoJugador = nuevoIndice;

        registrar(nombreJugador + " cambió a " + nuevo.getNombre() + ".");
        return true;
    }

    public void ejecutarTurnoRival() {
        if (!puedeContinuar()) {
            return;
        }

        Pokemon atacante = getPokemonActivoRival();
        Pokemon objetivo = getPokemonActivoJugador();

        if (atacante.estaDebilitado()) {
            cambiarPokemonRival();
            if (getPokemonActivoRival() == null) {
                finalizarBatalla();
                return;
            }
            atacante = getPokemonActivoRival();
        }

        if (atacante == null || objetivo == null) {
            finalizarBatalla();
            return;
        }

        int accion = random.nextInt(3);

        if (accion == 0) {
            int danio = calcularDanio(atacante, objetivo, 1.0);
            aplicarDanio(objetivo, danio);
            registrar(atacante.getNombre() + " utilizó un ataque básico y causó " + danio + " puntos de daño.");
        } else if (accion == 1) {
            int danio = calcularDanio(atacante, objetivo, 1.5);
            aplicarDanio(objetivo, danio);
            registrar(atacante.getNombre() + " utilizó una habilidad especial y causó " + danio + " puntos de daño.");
        } else {
            int recuperacion = (int) (atacante.getPsMaximos() * 0.25);
            int nuevosPS = Math.min(atacante.getPsMaximos(), atacante.getPsActuales() + recuperacion);
            int recuperados = nuevosPS - atacante.getPsActuales();
            atacante.setPsActuales(nuevosPS);

            registrar(atacante.getNombre() + " recuperó " + recuperados + " PS.");
        }
        verificarEstadoCombate();
    }

    private void cambiarPokemonRival() {
        for (int i = 0; i < equipoRival.size(); i++) {
            if (!equipoRival.get(i).estaDebilitado()) {
                indiceActivoRival = i;
                registrar(nombreRival + " envía a " + equipoRival.get(i).getNombre() + ".");
                return;
            }
        }
        finalizarBatalla();
    }

    private int calcularDanio(Pokemon atacante, Pokemon objetivo, double multiplicador) {
        int potencia = (int) (atacante.getAtaque() * multiplicador);
        return Math.max(1, atacante.calcularDanio(potencia, objetivo));
    }

    private void aplicarDanio(Pokemon objetivo, int danio) {
        int nuevosPS = Math.max(0, objetivo.getPsActuales() - danio);
        objetivo.setPsActuales(nuevosPS);
        if (objetivo.estaDebilitado()) {
            registrar(objetivo.getNombre() + " se ha debilitado.");
        }
    }

    private boolean tienePokemonDisponibles(List<Pokemon> equipo) {
        for (Pokemon pokemon : equipo) {
            if (!pokemon.estaDebilitado()) {
                return true;
            }
        }
        return false;
    }

    private void verificarEstadoCombate() {
        if (!tienePokemonDisponibles(equipoJugador)) {
            registrar("¡Todos los Pokémon de " + nombreJugador + " están debilitados!");
            finalizarBatalla();
            return;
        }

        if (!tienePokemonDisponibles(equipoRival)) {
            registrar("¡Todos los Pokémon del rival están debilitados!");
            finalizarBatalla();
            return;
        }

        if (getPokemonActivoJugador().estaDebilitado()) {
            cambiarPokemonAutomaticoJugador();
        }

        if (getPokemonActivoRival().estaDebilitado()) {
            cambiarPokemonRival();
        }
    }

    private void cambiarPokemonAutomaticoJugador() {
        for (int i = 0; i < equipoJugador.size(); i++) {
            if (!equipoJugador.get(i).estaDebilitado()) {
                indiceActivoJugador = i;
                registrar(nombreJugador + " envía a " + equipoJugador.get(i).getNombre() + ".");
                return;
            }
        }
        finalizarBatalla();
    }

    private boolean puedeContinuar() {
        if (estado == EstadoBatalla.FINALIZADA) {
            return false;
        }
        if (rondaActual > LIMITE_RONDAS) {
            finalizarBatalla();
            return false;
        }
        return true;
    }

    public void avanzarRonda() {
        if (estado == EstadoBatalla.FINALIZADA) {
            return;
        }

        rondaActual++;

        registrar("Comienza la ronda " + rondaActual + ".");

        if (rondaActual > LIMITE_RONDAS) {
            registrar("Se alcanzó el límite de rondas.");
            finalizarBatalla();
        }
    }

    private void finalizarBatalla() {
        estado = EstadoBatalla.FINALIZADA;
        registrar("La batalla ha finalizado.");
        boolean jugadorTienePokemones = tienePokemonDisponibles(equipoJugador);
        boolean rivalTienePokemones = tienePokemonDisponibles(equipoRival);

        if (jugadorTienePokemones && !rivalTienePokemones) {
            registrar("¡" + nombreJugador + " ha ganado!");
        } else if (!jugadorTienePokemones && rivalTienePokemones) {
            registrar("¡" + nombreRival + " ha ganado!");
        } else {
            registrar("La batalla terminó en empate.");
        }
    }

    private void registrar(String mensaje) {
        bitacora.add(mensaje);
    }

    public List<String> getBitacora() {
        return new ArrayList<>(bitacora);
    }

    public List<Pokemon> getEquipoJugador() {
        return equipoJugador;
    }

    public List<Pokemon> getEquipoRival() {
        return equipoRival;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreRival() {
        return nombreRival;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public EstadoBatalla getEstado() {
        return estado;
    }

    public boolean haFinalizado() {
        return estado == EstadoBatalla.FINALIZADA;
    }

    public String getGanador() {

        if (!haFinalizado()) {
            return "La batalla todavía está en curso.";
        }

        boolean jugadorTienePokemones =
                tienePokemonDisponibles(equipoJugador);

        boolean rivalTienePokemones =
                tienePokemonDisponibles(equipoRival);

        if (jugadorTienePokemones && !rivalTienePokemones) {
            return nombreJugador;
        }

        if (!jugadorTienePokemones && rivalTienePokemones) {
            return nombreRival;
        }

        return "Empate";
    }
}

