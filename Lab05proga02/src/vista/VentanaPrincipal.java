/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import logica.Entrenador;

public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private Entrenador entrenador;

    private PanelInicio panelInicio;
    private PanelResultado panelResultado;
    private JPanel panelEquipo;
    private JPanel panelBatalla;

    public static final String INICIO = "INICIO";
    public static final String EQUIPO = "EQUIPO";
    public static final String BATALLA = "BATALLA";
    public static final String RESULTADO = "RESULTADO";

    public VentanaPrincipal() {
        setTitle("Batallas Pokémon");
        setSize(1000, 650);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setBackground(new Color(245, 247, 255));

        panelInicio = new PanelInicio(this);
        panelResultado = new PanelResultado(this);

        panelEquipo = crearPanelTemporal("Pantalla de selección de equipo");
        panelBatalla = crearPanelTemporal("Pantalla de batalla");

        contenedor.add(panelInicio, INICIO);
        contenedor.add(panelEquipo, EQUIPO);
        contenedor.add(panelBatalla, BATALLA);
        contenedor.add(panelResultado, RESULTADO);

        add(contenedor, BorderLayout.CENTER);

        mostrarPantalla(INICIO);
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}

