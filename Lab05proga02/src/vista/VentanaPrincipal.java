/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import logica.Entrenador;
import logica.MotorBatalla;
import logica.GestorUtilidadesEquipo;
import logica.SerializadorEquipo;
import modelo.CatalogoPokemon;
import modelo.EstadoBatalla;

public class VentanaPrincipal extends JFrame {

    private static final String CARD_INICIO = "INICIO";
    private static final String CARD_EQUIPO = "EQUIPO";
    private static final String CARD_BATALLA = "BATALLA";
    private static final String CARD_RESULTADO = "RESULTADO";

    private final CardLayout cardLayout;
    private final JPanel contenedor;

    private Entrenador entrenador;
    private final CatalogoPokemon catalogo;
    private final SerializadorEquipo serializador;
    private MotorBatalla motorBatalla;

    private final PanelInicio panelInicio;
    private final PanelEquipo panelEquipo;
    private final PanelBatalla panelBatalla;
    private final PanelResultado panelResultado;

    public VentanaPrincipal() {

        super("Batallas Pokémon");

        entrenador = new Entrenador();
        catalogo = new CatalogoPokemon();
        serializador = new SerializadorEquipo();

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelInicio = new PanelInicio(this);
        panelEquipo = new PanelEquipo(this);
        panelBatalla = new PanelBatalla(this);
        panelResultado = new PanelResultado(this);

        contenedor.add(panelInicio, CARD_INICIO);
        contenedor.add(panelEquipo, CARD_EQUIPO);
        contenedor.add(panelBatalla, CARD_BATALLA);
        contenedor.add(panelResultado, CARD_RESULTADO);

        add(contenedor, BorderLayout.CENTER);

        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout.show(contenedor, CARD_INICIO);
    }

    public void mostrarPantalla(String pantalla) {
        cardLayout.show(contenedor, pantalla);
    }

    public void continuarConNombre(String nombre) {

        entrenador = new Entrenador();
        entrenador.setNombre(nombre);

        panelEquipo.refrescarVista();

        mostrarPantalla(CARD_EQUIPO);
    }

    public void mostrarInicio() {
        mostrarPantalla(CARD_INICIO);
    }

    public void mostrarEquipo() {
        panelEquipo.refrescarVista();
        mostrarPantalla(CARD_EQUIPO);
    }

    public void mostrarBatalla() {
        mostrarPantalla(CARD_BATALLA);
    }

    public void iniciarBatalla() {

        if (entrenador.getEquipo().isEmpty()) {
            return;
        }

        motorBatalla = new MotorBatalla(
                entrenador,
                catalogo.getPokemonDisponibles()
        );

        panelBatalla.actualizarInterfaz();

        mostrarPantalla(CARD_BATALLA);
    }

    public void irAResultado() {

        if (motorBatalla == null) {
            return;
        }

        EstadoBatalla estado = motorBatalla.getEstado();

        String resultado;

        if (estado == EstadoBatalla.VICTORIA) {
            resultado = "Victoria";
        } else if (estado == EstadoBatalla.DERROTA) {
            resultado = "Derrota";
        } else {
            resultado = "Empate";
        }

        int debilitadosJugador =
                GestorUtilidadesEquipo.contarDebilitadosRecursivo(
                        motorBatalla.getEquipoJugador(), 0
                );

        int debilitadosRival =
                GestorUtilidadesEquipo.contarDebilitadosRecursivo(
                        motorBatalla.getEquipoRival(), 0
                );

        int enPieJugador =
                motorBatalla.getEquipoJugador().size()
                - debilitadosJugador;

        int enPieRival =
                motorBatalla.getEquipoRival().size()
                - debilitadosRival;

        panelResultado.configurarResultado(
                entrenador.getNombre(),
                resultado,
                motorBatalla.getRondaActual(),
                debilitadosJugador,
                debilitadosRival,
                enPieJugador,
                enPieRival
        );

        mostrarPantalla(CARD_RESULTADO);
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public CatalogoPokemon getCatalogo() {
        return catalogo;
    }

    public SerializadorEquipo getSerializador() {
        return serializador;
    }

    public MotorBatalla getMotorBatalla() {
        return motorBatalla;
    }

    public PanelResultado getPanelResultado() {
        return panelResultado;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();

            ventana.setVisible(true);
        });
    }
}

