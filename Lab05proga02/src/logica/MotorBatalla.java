package logica;
        
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import logica.Entrenador;
import logica.GestorUtilidadesEquipo;
import modelo.AtaqueBasico;
import modelo.EstadoBatalla;
import modelo.HabilidadEspecial;
import modelo.Movimiento;
import modelo.Pokemon;
import modelo.Recuperacion;
import modelo.TipoAccion;

public class MotorBatalla {
    public static final int LIMITE_RONDAS = 50;
    private final String nombreJugador;
    private final String nombreRival = "Rival CPU";
    private final List<Pokemon> equipoJugador;
    private final List<Pokemon> equipoRival;
    private int indiceActivoJugador;
    private int indiceActivoRival;
    private int rondaActual;
    private EstadoBatalla estado;
    private final List<String> bitacora;
    private final Random random;

    private final Movimiento movAtaque = new AtaqueBasico();
    private final Movimiento movHabilidad = new HabilidadEspecial();
    private final Movimiento movRecuperar = new Recuperacion();

    public MotorBatalla(Entrenador entrenador, List<Pokemon> catalogo) {
        this.estado = EstadoBatalla.PREPARACION;
        this.nombreJugador = entrenador.getNombre();
        this.equipoJugador = new ArrayList<>();
        for (Pokemon p : entrenador.getEquipo()) {
            this.equipoJugador.add(p.clone());
        }
        this.random = new Random();
        this.equipoRival = generarEquipoRival(catalogo, equipoJugador.size());
        this.indiceActivoJugador = 0;
        this.indiceActivoRival = 0;
        this.rondaActual = 1;
        this.bitacora = new ArrayList<>();
        this.estado = EstadoBatalla.ESPERANDO_ACCION;
        registrar("¡Comienza la batalla entre " + nombreJugador + " y " + nombreRival + "!");
        registrar(nombreJugador + " envía a " + getPokemonActivoJugador().getNombre() + ".");
        registrar(nombreRival + " envía a " + getPokemonActivoRival().getNombre() + ".");
    }

    private List<Pokemon> generarEquipoRival(List<Pokemon> catalogo, int cantidad) {
        List<Pokemon> rival = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Pokemon plantilla = catalogo.get(random.nextInt(catalogo.size()));
            rival.add(plantilla.clone());
        }
        return rival;
    }

    public void ejecutarRonda(TipoAccion accionJugador, Integer indiceCambioJugador) {
        if (estado != EstadoBatalla.ESPERANDO_ACCION) {
            return;
        }
        validarAccionJugador(accionJugador, indiceCambioJugador);

        estado = EstadoBatalla.RESOLVIENDO_RONDA;
        registrar("\n--- Ronda " + rondaActual + " ---");

        TipoAccion accionRival = elegirAccionRival();
        Integer indiceCambioRival = (accionRival == TipoAccion.CAMBIAR) ? elegirCambioRival() : null;

        if (accionJugador == TipoAccion.CAMBIAR) {
            ejecutarCambioJugador(indiceCambioJugador);
        }
        if (accionRival == TipoAccion.CAMBIAR) {
            ejecutarCambioRival(indiceCambioRival);
        }

        boolean jugadorActua = (accionJugador != TipoAccion.CAMBIAR);
        boolean rivalActua = (accionRival != TipoAccion.CAMBIAR);

        if (jugadorActua && rivalActua) {
            int velJugador = getPokemonActivoJugador().getVelocidad();
            int velRival = getPokemonActivoRival().getVelocidad();
            if (velJugador >= velRival) {
                ejecutarTurnoIndividual(true, accionJugador);
                if (!getPokemonActivoRival().estaDebilitado()) {
                    ejecutarTurnoIndividual(false, accionRival);
                } else {
                    registrar(getPokemonActivoRival().getNombre() + " rival fue debilitado y pierde su acción.");
                }
            } else {
                ejecutarTurnoIndividual(false, accionRival);
                if (!getPokemonActivoJugador().estaDebilitado()) {
                    ejecutarTurnoIndividual(true, accionJugador);
                } else {
                    registrar(getPokemonActivoJugador().getNombre() + " fue debilitado y pierde su acción.");
                }
            }
        } else if (jugadorActua) {
            ejecutarTurnoIndividual(true, accionJugador);
        } else if (rivalActua) {
            ejecutarTurnoIndividual(false, accionRival);
        }

        cerrarRonda();
    }

    private void validarAccionJugador(TipoAccion accion, Integer indiceCambio) {
        Pokemon activo = getPokemonActivoJugador();
        if (activo.estaDebilitado()) {
            throw new IllegalStateException("El Pokémon activo está debilitado.");
        }
        switch (accion) {
            case HABILIDAD_ESPECIAL:
                if (activo.getUsosHabilidad() <= 0) {
                    throw new IllegalStateException("No quedan usos de habilidad especial.");
                }
                break;
            case RECUPERAR:
                if (activo.getUsosRecuperacion() <= 0) {
                    throw new IllegalStateException("No quedan usos de recuperación.");
                }
                if (activo.getPsActuales() >= activo.getPsMaximos()) {
                    throw new IllegalStateException("Los PS ya están completos.");
                }
                break;
            case CAMBIAR:
                if (indiceCambio == null || indiceCambio < 0 || indiceCambio >= equipoJugador.size()) {
                    throw new IllegalArgumentException("Seleccione un Pokémon válido para cambiar.");
                }
                if (indiceCambio == indiceActivoJugador) {
                    throw new IllegalStateException("Ese Pokémon ya está en combate.");
                }
                if (equipoJugador.get(indiceCambio).estaDebilitado()) {
                    throw new IllegalStateException("No puede cambiar a un Pokémon debilitado.");
                }
                break;
            default:
                break;
        }
    }

    private void ejecutarTurnoIndividual(boolean esJugador, TipoAccion accion) {
        Pokemon atacante = esJugador ? getPokemonActivoJugador() : getPokemonActivoRival();
        Pokemon defensor = esJugador ? getPokemonActivoRival() : getPokemonActivoJugador();
        String prefijo = esJugador ? "[" + nombreJugador + "] " : "[" + nombreRival + "] ";

        switch (accion) {
            case ATACAR:
                registrar(prefijo + movAtaque.ejecutar(atacante, defensor));
                break;
            case HABILIDAD_ESPECIAL:
                registrar(prefijo + movHabilidad.ejecutar(atacante, defensor));
                break;
            case RECUPERAR:
                registrar(prefijo + movRecuperar.ejecutar(atacante, defensor));
                break;
            default:
                break;
        }
    }

    private void ejecutarCambioJugador(int nuevoIndice) {
        indiceActivoJugador = nuevoIndice;
        registrar("[" + nombreJugador + "] retira a su Pokémon y envía a " + getPokemonActivoJugador().getNombre() + ".");
    }

    private void ejecutarCambioRival(int nuevoIndice) {
        indiceActivoRival = nuevoIndice;
        registrar("[" + nombreRival + "] cambia y envía a " + getPokemonActivoRival().getNombre() + ".");
    }

    private void cerrarRonda() {
        if (getPokemonActivoRival().estaDebilitado()) {
            registrar("¡" + getPokemonActivoRival().getNombre() + " rival ha quedado debilitado!");
            int siguienteRival = GestorUtilidadesEquipo.buscarSiguienteDisponibleRecursivo(equipoRival, 0);
            if (siguienteRival == -1) {
                estado = EstadoBatalla.VICTORIA;
                registrar("¡Todos los Pokémon rivales fueron debilitados! Victoria para " + nombreJugador + ".");
                return;
            } else {
                indiceActivoRival = siguienteRival;
                registrar(nombreRival + " envía a " + getPokemonActivoRival().getNombre() + ".");
            }
        }

        if (getPokemonActivoJugador().estaDebilitado()) {
            registrar("¡" + getPokemonActivoJugador().getNombre() + " ha quedado debilitado!");
            int siguienteJugador = GestorUtilidadesEquipo.buscarSiguienteDisponibleRecursivo(equipoJugador, 0);
            if (siguienteJugador == -1) {
                estado = EstadoBatalla.DERROTA;
                registrar("Todos tus Pokémon han quedado debilitados. Derrota para " + nombreJugador + ".");
                return;
            } else {
                estado = EstadoBatalla.ELEGIR_REEMPLAZO;
                registrar("Debes elegir un Pokémon disponible para continuar el combate.");
                return;
            }
        }

        if (rondaActual >= LIMITE_RONDAS) {
            evaluarLimiteRondas();
            return;
        }

        rondaActual++;
        estado = EstadoBatalla.ESPERANDO_ACCION;
    }

    public void seleccionarReemplazoJugador(int nuevoIndice) {
        if (estado != EstadoBatalla.ELEGIR_REEMPLAZO) {
            return;
        }
        if (nuevoIndice < 0 || nuevoIndice >= equipoJugador.size() || equipoJugador.get(nuevoIndice).estaDebilitado()) {
            throw new IllegalArgumentException("Debe seleccionar un Pokémon con PS mayores a 0.");
        }
        indiceActivoJugador = nuevoIndice;
        registrar(nombreJugador + " envía a " + getPokemonActivoJugador().getNombre() + " al combate.");

        if (rondaActual >= LIMITE_RONDAS) {
            evaluarLimiteRondas();
        } else {
            rondaActual++;
            estado = EstadoBatalla.ESPERANDO_ACCION;
        }
    }

    private void evaluarLimiteRondas() {
        int psJugador = GestorUtilidadesEquipo.sumarPSRestantesRecursivo(equipoJugador, 0);
        int psRival = GestorUtilidadesEquipo.sumarPSRestantesRecursivo(equipoRival, 0);
        registrar("Se alcanzó el límite de " + LIMITE_RONDAS + " rondas.");
        registrar("PS restantes -> " + nombreJugador + ": " + psJugador + " | " + nombreRival + ": " + psRival);
        if (psJugador > psRival) {
            estado = EstadoBatalla.VICTORIA;
        } else if (psRival > psJugador) {
            estado = EstadoBatalla.DERROTA;
        } else {
            estado = EstadoBatalla.EMPATE;
        }
    }

    private TipoAccion elegirAccionRival() {
        Pokemon activo = getPokemonActivoRival();
        List<TipoAccion> validas = new ArrayList<>();
        validas.add(TipoAccion.ATACAR);
        if (activo.getUsosHabilidad() > 0) {
            validas.add(TipoAccion.HABILIDAD_ESPECIAL);
        }
        if (activo.getUsosRecuperacion() > 0 && activo.getPsActuales() < activo.getPsMaximos()) {
            validas.add(TipoAccion.RECUPERAR);
        }
        if (elegirCambioRival() != null) {
            validas.add(TipoAccion.CAMBIAR);
        }
        return validas.get(random.nextInt(validas.size()));
    }

    private Integer elegirCambioRival() {
        List<Integer> disponibles = new ArrayList<>();
        for (int i = 0; i < equipoRival.size(); i++) {
            if (i != indiceActivoRival && !equipoRival.get(i).estaDebilitado()) {
                disponibles.add(i);
            }
        }
        if (disponibles.isEmpty()) return null;
        return disponibles.get(random.nextInt(disponibles.size()));
    }

    public boolean hayReemplazoJugadorDisponible() {
        for (int i = 0; i < equipoJugador.size(); i++) {
            if (i != indiceActivoJugador && !equipoJugador.get(i).estaDebilitado()) {
                return true;
            }
        }
        return false;
    }

    private void registrar(String mensaje) {
        bitacora.add(mensaje);
    }

    public String getTextoBitacoraCompleto() {
        return String.join("\n", bitacora);
    }

    public String getNombreJugador() { return nombreJugador; }
    public String getNombreRival() { return nombreRival; }
    public List<Pokemon> getEquipoJugador() { return equipoJugador; }
    public List<Pokemon> getEquipoRival() { return equipoRival; }
    public Pokemon getPokemonActivoJugador() { return equipoJugador.get(indiceActivoJugador); }
    public Pokemon getPokemonActivoRival() { return equipoRival.get(indiceActivoRival); }
    public int getRondaActual() { return rondaActual; }
    public EstadoBatalla getEstado() { return estado; }
}

