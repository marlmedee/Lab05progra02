
package logica;

import java.util.List;
import modelo.Pokemon;
/**
 *
 * @author gigiw
 */
public class GestorUtilidadesEquipo {
    public static int sumarPSRestantesRecursivo(
         List<Pokemon> equipo, int indice) {

        if (equipo == null || indice >= equipo.size()) {
            return 0;
        }
        return equipo.get(indice).getPsActuales() + sumarPSRestantesRecursivo(equipo, indice + 1);
    }

    public static int buscarSiguienteDisponibleRecursivo(
            List<Pokemon> equipo, int indice) {
        if (equipo == null || indice >= equipo.size()) {
            return -1;
        }

        if (!equipo.get(indice).estaDebilitado()) {
            return indice;
        }

        return buscarSiguienteDisponibleRecursivo(equipo, indice + 1);
    }

    public static int contarDebilitadosRecursivo(
            List<Pokemon> equipo, int indice) {
        if (equipo == null || indice >= equipo.size()) {
            return 0;
        }
        int actual = equipo.get(indice).estaDebilitado() ? 1 : 0;

        return actual + contarDebilitadosRecursivo(equipo, indice + 1);
    }
}
 

