
package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modelo.EstadoBatalla;
import modelo.TipoAccion;
import modelo.Pokemon;
import modelo.Movimiento;
import modelo.AtaqueBasico;
import modelo.HabilidadEspecial;
import modelo.Recuperacion;

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

    private final Movimiento movimientoAtaque;
    private final Movimiento movimientoHabilidad;
    private final Movimiento movimientoRecuperacion;

    public MotorBatalla(Entrenador entrenador, List<Pokemon> catalogo) {
        if (entrenador == null) {
            throw new IllegalArgumentException("El entrenador no puede ser nulo.");
        }

        if (catalogo == null || catalogo.isEmpty()) {
            throw new IllegalArgumentException("El catálogo de Pokémon está vacío.");
        }

        if (entrenador.getEquipo().isEmpty()) {
            throw new IllegalArgumentException("El entrenador necesita al menos un Pokémon.");
        }

        this.nombreJugador = entrenador.getNombre();
        this.nombreRival = "Rival CPU";

        this.equipoJugador = new ArrayList<>();
        this.equipoRival = new ArrayList<>();

        this.random = new Random();
        this.bitacora = new ArrayList<>();

        this.movimientoAtaque = new AtaqueBasico();
        this.movimientoHabilidad = new HabilidadEspecial();
        this.movimientoRecuperacion = new Recuperacion();

        this.indiceActivoJugador = 0;
        this.indiceActivoRival = 0;
        this.rondaActual = 1;

        for (Pokemon pokemon : entrenador.getEquipo()) {
            this.equipoJugador.add(pokemon.clone());
        }

        generarEquipoRival(catalogo, equipoJugador.size());

        this.estado = EstadoBatalla.ESPERANDO_ACCION;

        registrar("¡Comienza la batalla!");
        registrar(nombreJugador + " contra " + nombreRival);

        registrar(nombreJugador + " envía a " + getPokemonActivoJugador().getNombre() + ".");

        registrar(nombreRival + " envía a " + getPokemonActivoRival().getNombre() + ".");
    }

    private void generarEquipoRival(
        List<Pokemon> catalogo,
        int cantidad
    ) {

        List<Pokemon> disponibles = new ArrayList<>();

        for (Pokemon pokemon : catalogo) {
            if (pokemon != null) {
                disponibles.add(pokemon);
            }
        }

        if (disponibles.isEmpty()) {
            throw new IllegalArgumentException(
                "No hay Pokémon disponibles para el rival."
            );
        }

        for (int i = 0; i < cantidad; i++) {

            Pokemon seleccionado = disponibles.get(
                random.nextInt(disponibles.size())
            );

            Pokemon copia = seleccionado.clone();
            copia.reiniciarEstadoBatalla();

            equipoRival.add(copia);
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

    public void resolverRonda(
        TipoAccion accionJugador,
        int indiceCambio
    ) {

        if (estado != EstadoBatalla.ESPERANDO_ACCION) {
            registrar("No es posible realizar una acción en este momento.");
            return;
        }

        if (accionJugador == null) {
            registrar("Debe seleccionar una acción.");
            return;
        }

        Pokemon jugador = getPokemonActivoJugador();
        Pokemon rival = getPokemonActivoRival();

        if (jugador == null || rival == null) {    
            comprobarFinalizacion();
        }

        if (jugador.estaDebilitado()) {
            estado = EstadoBatalla.ELEGIR_REEMPLAZO;
            registrar("Debe seleccionar otro Pokémon.");
            return;
        }

        estado = EstadoBatalla.RESOLVIENDO_RONDA;

        TipoAccion accionRival = elegirAccionRival();

        boolean jugadorCambio = accionJugador == TipoAccion.CAMBIAR;
        boolean rivalCambio = accionRival == TipoAccion.CAMBIAR;

        if (jugadorCambio) {
            if (!cambiarPokemonInterno(indiceCambio, true)) {
                estado = EstadoBatalla.ESPERANDO_ACCION;
                return;
            }
        }

        if (rivalCambio) {
            cambiarPokemonRival();
        }

        if (jugadorCambio && rivalCambio) {
            cerrarRonda();
            return;
        }

        if (jugadorCambio) {
            ejecutarAccionRival(accionRival);
            cerrarRonda();
            return;
        }

        if (rivalCambio) {
            ejecutarAccionJugador(accionJugador);
            cerrarRonda();
            return;
        }

        jugador = getPokemonActivoJugador();
        rival = getPokemonActivoRival();

        if (jugador.getVelocidad() >= rival.getVelocidad()) {

            ejecutarAccionJugador(accionJugador);

            if (puedeActuarRival()) {
                ejecutarAccionRival(accionRival);
            }

        } else {

            ejecutarAccionRival(accionRival);

            if (puedeActuarJugador()) {
                ejecutarAccionJugador(accionJugador);
            }
        }

        cerrarRonda();
    }

    private void ejecutarAccionJugador(TipoAccion accion) {

        Pokemon jugador = getPokemonActivoJugador();
        Pokemon rival = getPokemonActivoRival();

        if (jugador == null || rival == null
            || jugador.estaDebilitado()
            || rival.estaDebilitado()) {
            return;
        }

        try {

            switch (accion) {

                case ATACAR:
                    registrar(movimientoAtaque.ejecutar(jugador, rival));
                    break;

                case HABILIDAD_ESPECIAL:
                    registrar(movimientoHabilidad.ejecutar(jugador, rival));
                    break;

                case RECUPERAR:
                    registrar(movimientoRecuperacion.ejecutar(jugador, rival));
                    break;

                default:
                    registrar("Acción no válida.");
                    break;
            }

        } catch (IllegalStateException e) {
            registrar(e.getMessage());
        }

        comprobarFinalizacion();
    }

    private void ejecutarAccionRival(TipoAccion accion) {

        Pokemon rival = getPokemonActivoRival();
        Pokemon jugador = getPokemonActivoJugador();

        if (rival == null || jugador == null
            || rival.estaDebilitado()
            || jugador.estaDebilitado()) {
            return;
        }

        try {

            switch (accion) {

                case ATACAR:
                    registrar(movimientoAtaque.ejecutar(rival, jugador));
                    break;

                case HABILIDAD_ESPECIAL:
                    registrar(movimientoHabilidad.ejecutar(rival, jugador));
                    break;

                case RECUPERAR:
                    registrar(movimientoRecuperacion.ejecutar(rival, rival));
                    break;

                default:
                    break;
            }

        } catch (IllegalStateException e) {
            registrar("Rival CPU: " + e.getMessage());
        }

        comprobarFinalizacion();
    }

    private TipoAccion elegirAccionRival() {

        Pokemon rival = getPokemonActivoRival();

        List<TipoAccion> acciones = new ArrayList<>();

        acciones.add(TipoAccion.ATACAR);

        if (rival.getUsosHabilidad() > 0) {
            acciones.add(TipoAccion.HABILIDAD_ESPECIAL);
        }

        if (rival.getUsosRecuperacion() > 0
            && rival.getPsActuales() < rival.getPsMaximos()) {
            acciones.add(TipoAccion.RECUPERAR);
        }

        if (hayOtroPokemonDisponible(equipoRival, indiceActivoRival)) {
            acciones.add(TipoAccion.CAMBIAR);
        }

        return acciones.get(random.nextInt(acciones.size()));
    }

    public boolean cambiarPokemon(int nuevoIndice) {

        if (estado != EstadoBatalla.ELEGIR_REEMPLAZO
            && estado != EstadoBatalla.ESPERANDO_ACCION) {
            return false;
        }

        boolean reemplazoObligatorio =
            estado == EstadoBatalla.ELEGIR_REEMPLAZO;

        boolean cambiado = cambiarPokemonInterno(
            nuevoIndice,
            reemplazoObligatorio
        );

        if (cambiado && reemplazoObligatorio) {
            estado = EstadoBatalla.ESPERANDO_ACCION;
        }

        return cambiado;
    }

    private boolean cambiarPokemonInterno(
        int nuevoIndice,
        boolean reemplazoObligatorio
    ) {

        if (nuevoIndice < 0 || nuevoIndice >= equipoJugador.size()) {
            registrar("Selección de Pokémon no válida.");
            return false;
        }

        Pokemon nuevo = equipoJugador.get(nuevoIndice);

        if (nuevo.estaDebilitado()) {
            registrar("No puedes seleccionar un Pokémon debilitado.");
            return false;
        }

        if (nuevoIndice == indiceActivoJugador) {
            registrar("Ese Pokémon ya está en combate.");
            return false;
        }

        indiceActivoJugador = nuevoIndice;

        registrar(nombreJugador + " envía a "
            + nuevo.getNombre() + ".");

        return true;
    }

    private void cambiarPokemonRival() {

        for (int i = 0; i < equipoRival.size(); i++) {

            if (i != indiceActivoRival
                && !equipoRival.get(i).estaDebilitado()) {

                indiceActivoRival = i;

                registrar(nombreRival + " envía a "
                    + equipoRival.get(i).getNombre() + ".");

                return;
            }
        }
    }

    private boolean hayOtroPokemonDisponible(
        List<Pokemon> equipo,
        int indiceActual
    ) {

        for (int i = 0; i < equipo.size(); i++) {

            if (i != indiceActual
                && !equipo.get(i).estaDebilitado()) {
                return true;
            }
        }

        return false;
    }

    private boolean puedeActuarJugador() {

        Pokemon jugador = getPokemonActivoJugador();
        Pokemon rival = getPokemonActivoRival();

        return jugador != null
            && rival != null
            && !jugador.estaDebilitado()
            && !rival.estaDebilitado()
            && estado != EstadoBatalla.VICTORIA
            && estado != EstadoBatalla.DERROTA
            && estado != EstadoBatalla.EMPATE;
    }

    private boolean puedeActuarRival() {

        Pokemon jugador = getPokemonActivoJugador();
        Pokemon rival = getPokemonActivoRival();

        return jugador != null
            && rival != null
            && !jugador.estaDebilitado()
            && !rival.estaDebilitado()
            && estado != EstadoBatalla.VICTORIA
            && estado != EstadoBatalla.DERROTA
            && estado != EstadoBatalla.EMPATE;
    }

    private void cerrarRonda() {

        comprobarFinalizacion();

        if (haFinalizado()) {
            return;
        }

        if (getPokemonActivoJugador().estaDebilitado()) {
            estado = EstadoBatalla.ELEGIR_REEMPLAZO;
            registrar("Selecciona un Pokémon para continuar.");
            return;
        }

        if (getPokemonActivoRival().estaDebilitado()) {
            cambiarPokemonRival();
        }

        comprobarFinalizacion();

        if (haFinalizado()) {
            return;
        }

        rondaActual++;

        if (rondaActual > LIMITE_RONDAS) {
            registrar("Se alcanzó el límite de 50 rondas.");
            finalizarPorPS();
            return;
        }

        estado = EstadoBatalla.ESPERANDO_ACCION;

        registrar("Comienza la ronda " + rondaActual + ".");
    }

    private boolean tienePokemonDisponibles(List<Pokemon> equipo) {

        for (Pokemon pokemon : equipo) {
            if (!pokemon.estaDebilitado()) {
                return true;
            }
        }

        return false;
    }

    private void comprobarFinalizacion() {

        boolean jugadorVivo =
            tienePokemonDisponibles(equipoJugador);

        boolean rivalVivo =
            tienePokemonDisponibles(equipoRival);

        if (!jugadorVivo && !rivalVivo) {
            finalizar(EstadoBatalla.EMPATE);
        } else if (!rivalVivo) {
            finalizar(EstadoBatalla.VICTORIA);
        } else if (!jugadorVivo) {
            finalizar(EstadoBatalla.DERROTA);
        }
    }

    private void finalizarPorPS() {

        int psJugador = sumarPSRecursivo(equipoJugador, 0);
        int psRival = sumarPSRecursivo(equipoRival, 0);

        registrar("PS restantes del jugador: " + psJugador);
        registrar("PS restantes del rival: " + psRival);

        if (psJugador > psRival) {
            finalizar(EstadoBatalla.VICTORIA);
        } else if (psRival > psJugador) {
            finalizar(EstadoBatalla.DERROTA);
        } else {
            finalizar(EstadoBatalla.EMPATE);
        }
    }

    private int sumarPSRecursivo(
        List<Pokemon> equipo,
        int indice
    ) {

        if (equipo == null || indice >= equipo.size()) {
            return 0;
        }

        return equipo.get(indice).getPsActuales()
            + sumarPSRecursivo(equipo, indice + 1);
    }

    private void finalizar(EstadoBatalla resultado) {

        estado = resultado;

        registrar("La batalla ha finalizado.");

        switch (resultado) {

            case VICTORIA:
                registrar("¡" + nombreJugador + " ha ganado!");
                break;

            case DERROTA:
                registrar(nombreJugador + " ha sido derrotado.");
                break;

            case EMPATE:
                registrar("La batalla terminó en empate.");
                break;

            default:
                break;
        }
    }

    private void registrar(String mensaje) {
        bitacora.add(mensaje);
    }

    public boolean haFinalizado() {

        return estado == EstadoBatalla.VICTORIA
            || estado == EstadoBatalla.DERROTA
            || estado == EstadoBatalla.EMPATE;
    }

    public String getGanador() {

        switch (estado) {

            case VICTORIA:
                return nombreJugador;

            case DERROTA:
                return nombreRival;

            case EMPATE:
                return "Empate";

            default:
                return "La batalla todavía está en curso.";
        }
    }

    public int contarDebilitados(List<Pokemon> equipo) {

        int cantidad = 0;

        for (Pokemon pokemon : equipo) {
            if (pokemon.estaDebilitado()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getPSRestantesJugador() {
        return sumarPSRecursivo(equipoJugador, 0);
    }

    public int getPSRestantesRival() {
        return sumarPSRecursivo(equipoRival, 0);
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
}
