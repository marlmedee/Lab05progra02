
package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelo.Pokemon;
/**
 *
 * @author gigiw
 */
public class Entrenador implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public static final int MAX_EQUIPO = 6;
    public static final int MAX_COPIAS_MISMO_POKEMON = 2;
    private String nombre;
    private final List<Pokemon> equipo;

    public Entrenador() {
        this.equipo = new ArrayList<>();
    }

    public static String validarYFormatearNombre(String nombreIngresado) {
        if (nombreIngresado == null) {
            throw new IllegalArgumentException("Debe ingresar un nombre de entrenador.");
        }

        if (nombreIngresado.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar formado únicamente por espacios.");
        }

        String limpio = nombreIngresado.trim();

        if (limpio.length() < 3 || limpio.length() > 10) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 10 caracteres.");
        }

        if (!limpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ0-9 ]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras, números y espacios.");
        }
        return limpio;
    }

    public void setNombre(String nombreIngresado) {
        this.nombre = validarYFormatearNombre(nombreIngresado);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pokemon> getEquipo() {
        return equipo;
    }
}
