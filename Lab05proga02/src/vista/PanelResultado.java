package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelResultado extends JPanel {

    private final VentanaPrincipal ventana;

    private final Color azulOscuro = new Color(35, 55, 105);
    private final Color azulClaro = new Color(225, 235, 255);
    private final Color amarillo = new Color(255, 205, 55);
    private final Color blanco = Color.WHITE;

    private JLabel etiquetaResultado;
    private JLabel etiquetaEntrenador;
    private JLabel etiquetaRondas;
    private JLabel etiquetaDebilitadosJugador;
    private JLabel etiquetaDebilitadosRival;
    private JLabel etiquetaEnPieJugador;
    private JLabel etiquetaEnPieRival;

    public PanelResultado(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout(20, 20));
        setBackground(azulClaro);
        setBorder(BorderFactory.createEmptyBorder(35, 55, 35, 55));

        crearInterfaz();
    }

    private void crearInterfaz() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.setOpaque(false);

        etiquetaResultado = new JLabel(
                "RESULTADO DE LA BATALLA",
                SwingConstants.CENTER
        );

        etiquetaResultado.setFont(new Font("Arial", Font.BOLD, 34));
        etiquetaResultado.setForeground(azulOscuro);

        etiquetaEntrenador = new JLabel(
                "Entrenador: ",
                SwingConstants.CENTER
        );

        etiquetaEntrenador.setFont(new Font("Arial", Font.PLAIN, 20));
        etiquetaEntrenador.setForeground(azulOscuro);

        panelSuperior.add(etiquetaResultado);
        panelSuperior.add(etiquetaEntrenador);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelEstadisticas = new JPanel(new GridLayout(5, 1, 10, 10));
        panelEstadisticas.setBackground(blanco);
        panelEstadisticas.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(azulOscuro, 2),
                        BorderFactory.createEmptyBorder(25, 35, 25, 35)
                )
        );

        etiquetaRondas = crearEtiqueta("Rondas jugadas: 0");
        etiquetaDebilitadosJugador = crearEtiqueta(
                "Pokémon debilitados del jugador: 0"
        );
        etiquetaDebilitadosRival = crearEtiqueta(
                "Pokémon debilitados del rival: 0"
        );
        etiquetaEnPieJugador = crearEtiqueta(
                "Pokémon en pie del jugador: 0"
        );
        etiquetaEnPieRival = crearEtiqueta(
                "Pokémon en pie del rival: 0"
        );

        panelEstadisticas.add(etiquetaRondas);
        panelEstadisticas.add(etiquetaDebilitadosJugador);
        panelEstadisticas.add(etiquetaDebilitadosRival);
        panelEstadisticas.add(etiquetaEnPieJugador);
        panelEstadisticas.add(etiquetaEnPieRival);

        add(panelEstadisticas, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBotones.setOpaque(false);

        JButton botonNuevaPartida = crearBoton(
                "NUEVA PARTIDA",
                amarillo,
                azulOscuro
        );

        botonNuevaPartida.addActionListener(e -> nuevaPartida());

        JButton botonSalir = crearBoton(
                "SALIR",
                new Color(220, 80, 80),
                blanco
        );

        botonSalir.addActionListener(e -> salir());

        panelBotones.add(botonNuevaPartida);
        panelBotones.add(botonSalir);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);

        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        etiqueta.setForeground(azulOscuro);

        return etiqueta;
    }

    private JButton crearBoton(String texto, Color fondo, Color letra) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(fondo);
        boton.setForeground(letra);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        return boton;
    }

    public void configurarResultado(
            String nombreEntrenador,
            String resultado,
            int rondas,
            int debilitadosJugador,
            int debilitadosRival,
            int pokemonEnPieJugador,
            int pokemonEnPieRival
    ) {
        etiquetaResultado.setText(resultado.toUpperCase());
        etiquetaEntrenador.setText(
                "Entrenador: " + nombreEntrenador
        );

        etiquetaRondas.setText(
                "Rondas jugadas: " + rondas
        );

        etiquetaDebilitadosJugador.setText(
                "Pokémon debilitados del jugador: " + debilitadosJugador
        );

        etiquetaDebilitadosRival.setText(
                "Pokémon debilitados del rival: " + debilitadosRival
        );

        etiquetaEnPieJugador.setText(
                "Pokémon en pie del jugador: " + pokemonEnPieJugador
        );

        etiquetaEnPieRival.setText(
                "Pokémon en pie del rival: " + pokemonEnPieRival
        );

        if (resultado.equalsIgnoreCase("Victoria")) {
            etiquetaResultado.setForeground(new Color(35, 145, 75));
        } else if (resultado.equalsIgnoreCase("Derrota")) {
            etiquetaResultado.setForeground(new Color(200, 55, 55));
        } else {
            etiquetaResultado.setForeground(azulOscuro);
        }
    }

    private void nuevaPartida() {
        ventana.mostrarEquipo();
    }

    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas salir del juego?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}