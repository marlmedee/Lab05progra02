/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import logica.Entrenador;
import logica.MotorBatalla;
import logica.SerializadorEquipo;
import modelo.CatalogoPokemon;

public class VentanaPrincipal extends JFrame {
    private static final String CARD_INICIO = "INICIO";
    private static final String CARD_EQUIPO = "EQUIPO";
    private static final String CARD_BATALLA = "BATALLA";
    private static final String CARD_RESULTADO = "RESULTADO";

    private final CardLayout cardLayout;
    private final JPanel contenedor;

    private final Entrenador entrenador;
    private final CatalogoPokemon catalogo;
    private final SerializadorEquipo serializador;
    private MotorBatalla motorBatalla;

    private final PanelInicio panelInicio;
    private final PanelEquipo panelEquipo;
    private final PanelBatalla panelBatalla;
    private final PanelResultado panelResultado;
    
    

    public VentanaPrincipal() {
        super("Batallas Pokémon");
        this.entrenador = new Entrenador();
        this.catalogo = new CatalogoPokemon();
        this.serializador = new SerializadorEquipo();

        this.cardLayout = new CardLayout();
        this.contenedor = new JPanel(cardLayout);

        this.panelInicio = new PanelInicio(this);
        this.panelEquipo = new PanelEquipo(this);
        this.panelBatalla = new PanelBatalla(this);
        this.panelResultado = new PanelResultado(this);

        contenedor.add(panelInicio, CARD_INICIO);
        contenedor.add(panelEquipo, CARD_EQUIPO);
        contenedor.add(panelBatalla, CARD_BATALLA);
        contenedor.add(panelResultado, CARD_RESULTADO);

        add(contenedor);
        setSize(860, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel crearPanelTemporal(String mensaje) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 255));

        javax.swing.JLabel etiqueta = new javax.swing.JLabel(
                mensaje,
                javax.swing.SwingConstants.CENTER
        );

        etiqueta.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 26));
        etiqueta.setForeground(new Color(45, 65, 110));

        panel.add(etiqueta, BorderLayout.CENTER);

        return panel;
    }

    public void mostrarPantalla(String pantalla) {
        cardLayout.show(contenedor, pantalla);
    }

    public void continuarConNombre(String nombre) {
        entrenador = new Entrenador();
        entrenador.setNombre(nombre);

        mostrarPantalla(EQUIPO);
    }

    public void mostrarInicio() {
        mostrarPantalla(INICIO);
    }

    public void mostrarEquipo() {
        mostrarPantalla(EQUIPO);
    }

    public void mostrarBatalla() {
        mostrarPantalla(BATALLA);
    }

    public void mostrarResultado(
            String resultado,
            int rondas,
            int debilitadosJugador,
            int debilitadosRival,
            int pokemonEnPieJugador,
            int pokemonEnPieRival
    ) {
        panelResultado.configurarResultado(
                entrenador != null ? entrenador.getNombre() : "Entrenador",
                resultado,
                rondas,
                debilitadosJugador,
                debilitadosRival,
                pokemonEnPieJugador,
                pokemonEnPieRival
        );

        mostrarPantalla(RESULTADO);
    }

    public void establecerPanelEquipo(JPanel nuevoPanel) {
        if (nuevoPanel == null) {
            return;
        }

        contenedor.remove(panelEquipo);
        panelEquipo = nuevoPanel;
        contenedor.add(panelEquipo, EQUIPO);

        contenedor.revalidate();
        contenedor.repaint();
    }

    public void establecerPanelBatalla(JPanel nuevoPanel) {
        if (nuevoPanel == null) {
            return;
        }

        contenedor.remove(panelBatalla);
        panelBatalla = nuevoPanel;
        contenedor.add(panelBatalla, BATALLA);

        contenedor.revalidate();
        contenedor.repaint();
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public PanelResultado getPanelResultado() {
        return panelResultado;
    }
    public void iniciarBatalla() {
    this.motorBatalla = new MotorBatalla(entrenador, catalogo.getPokemonDisponibles());
        panelBatalla.actualizarInterfaz();
        cardLayout.show(contenedor, CARD_BATALLA);
}

    public void irAResultado() {
        panelResultado.cargarResultado();
        cardLayout.show(contenedor, CARD_RESULTADO);
    }

    public Entrenador getEntrenador(){
        return entrenador; 
    }
    public CatalogoPokemon getCatalogo(){
        return catalogo;
    }
    public SerializadorEquipo getSerializador(){
        return serializador;
    }
    public MotorBatalla getMotorBatalla(){
        return motorBatalla;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}

