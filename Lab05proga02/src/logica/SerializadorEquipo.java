
package logica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author gigiw
 */
public class SerializadorEquipo {
    private byte[] datosSerializados;

    public void guardarEquipoEnMemoria(Entrenador entrenador) {
        if (entrenador == null || entrenador.getEquipo().isEmpty()) {
            throw new IllegalStateException("Debe tener al menos un Pokémon en el equipo para guardarlo.");
        }

        try (
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)
        ) {
            oos.writeObject(entrenador);
            oos.flush();

            this.datosSerializados = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al serializar el equipo en memoria: "+ e.getMessage(), e);
        }
    }

    public Entrenador restaurarEquipoDeMemoria() {
        if (datosSerializados == null) {
            throw new IllegalStateException("No hay ningún equipo guardado en memoria.");
        }

        try (
            ByteArrayInputStream bais = new ByteArrayInputStream(datosSerializados);
            ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return (Entrenador) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al deserializar el equipo desde memoria: " + e.getMessage(), e
            );
        }
    }

    public boolean hayEquipoGuardado() {
        return datosSerializados != null;
    } 
}
