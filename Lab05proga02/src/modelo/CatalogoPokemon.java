/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author marlo
 */
import java.util.List;
import java.util.ArrayList;
public class CatalogoPokemon {
    private final List<Pokemon> pokemonDisponibles;
    public CatalogoPokemon() {
        this.pokemonDisponibles = new ArrayList<>();
        cargarCatalogoBase();
    }
    private void cargarCatalogoBase() {
        pokemonDisponibles.add(new PokemonFuego("Charmander", 5, 90, 14, 8, 11));
        pokemonDisponibles.add(new PokemonFuego("Vulpix", 5, 85, 13, 9, 12));
        pokemonDisponibles.add(new PokemonAgua("Squirtle", 5, 100, 11, 12, 9));
        pokemonDisponibles.add(new PokemonAgua("Psyduck", 5, 95, 12, 9, 10));
        pokemonDisponibles.add(new PokemonPlanta("Bulbasaur", 5, 100, 12, 10, 8));
        pokemonDisponibles.add(new PokemonPlanta("Oddish", 5, 90, 13, 8, 7));
        pokemonDisponibles.add(new PokemonElectrico("Pikachu", 5, 80, 14, 7, 15));
        pokemonDisponibles.add(new PokemonElectrico("Voltorb", 5, 75, 12, 9, 16));
    }
    public List<Pokemon> getPokemonDisponibles() {
        return pokemonDisponibles;
    }
    public Pokemon agregarPersonalizado(String nombreRaw, TipoPokemon tipo) {
        if (nombreRaw == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        String nombre = nombreRaw.trim();
        if (nombre.length()<3||nombre.length()>12||!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$")) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 12 caracteres alfanumericos");
        }
        for (Pokemon p : pokemonDisponibles){
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe un Pokémon con ese nombre en el catalogo");
            }
        }
        Pokemon nuevo;
        switch (tipo){
            case FUEGO:
                nuevo = new PokemonFuego(nombre, 5, 88, 13, 9, 12);
                break;
            case AGUA:
                nuevo = new PokemonAgua(nombre, 5, 96, 12, 10, 10);
                break;
            case PLANTA:
                nuevo = new PokemonPlanta(nombre, 5, 95, 12, 9, 9);
                break;
            case ELECTRICO:
            default:
                nuevo = new PokemonElectrico(nombre, 5, 80, 13, 8, 15);
                break;
        }
        pokemonDisponibles.add(nuevo);
        return nuevo;
    }
}
