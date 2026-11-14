
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class PanelResultado extends JPanel {

    private final VentanaPrincipal ventana;

    private final Color azulOscuro = new Color(35, 55, 105);
    private final Color amarillo = new Color(255, 195, 35);
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

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(
                BorderFactory.createEmptyBorder(20, 35, 30, 35)
        );

        crearInterfaz();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int ancho = getWidth();
        int alto = getHeight();

        GradientPaint cielo = new GradientPaint(
                0, 0,
                new Color(70, 180, 245),
                0, alto,
                new Color(190, 235, 255)
        );

        g2.setPaint(cielo);
        g2.fillRect(0, 0, ancho, alto);

        dibujarNube(g2, 65, 100);
        dibujarNube(g2, ancho - 190, 115);
        dibujarNube(g2, ancho - 110, 55);

        g2.setColor(new Color(105, 205, 100));
        g2.fillOval(-130, alto - 145, ancho + 260, 260);

        g2.setColor(new Color(65, 175, 80));
        g2.fillOval(-180, alto - 95, ancho + 360, 210);

        g2.setColor(new Color(45, 145, 65));
        g2.fillRect(0, alto - 30, ancho, 30);

        dibujarPokebola(g2, 65, alto - 115, 45);
        dibujarPokebola(g2, ancho - 115, alto - 125, 50);

        g2.dispose();
    }

    private void dibujarNube(Graphics2D g2, int x, int y) {

        g2.setColor(new Color(255, 255, 255, 225));

        g2.fillOval(x, y + 15, 65, 35);
        g2.fillOval(x + 25, y, 65, 55);
        g2.fillOval(x + 55, y + 15, 65, 35);
    }

    private void dibujarPokebola(
            Graphics2D g2,
            int x,
            int y,
            int tamano
    ) {

        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, tamano, tamano);

        g2.setColor(new Color(225, 55, 55));
        g2.fillArc(x, y, tamano, tamano, 0, 180);

        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(x, y + tamano / 2 - 2, tamano, 5);

        g2.setColor(Color.WHITE);
        g2.fillOval(
                x + tamano / 2 - 8,
                y + tamano / 2 - 8,
                16,
                16
        );

        g2.setColor(new Color(40, 40, 40));
        g2.drawOval(x, y, tamano, tamano);
        g2.drawOval(
                x + tamano / 2 - 8,
                y + tamano / 2 - 8,
                16,
                16
        );
    }

    private void crearInterfaz() {

        JPanel encabezado = new JPanel();

        encabezado.setOpaque(false);
        encabezado.setLayout(
                new BoxLayout(encabezado, BoxLayout.Y_AXIS)
        );

        etiquetaResultado = new JLabel(
                "¡RESULTADO!",
                SwingConstants.CENTER
        );

        etiquetaResultado.setFont(
                new Font("Arial Black", Font.BOLD, 39)
        );
        etiquetaResultado.setForeground(Color.WHITE);
        etiquetaResultado.setAlignmentX(CENTER_ALIGNMENT);

        etiquetaEntrenador = new JLabel(
                "ENTRENADOR: ",
                SwingConstants.CENTER
        );

        etiquetaEntrenador.setFont(
                new Font("Arial Black", Font.BOLD, 17)
        );
        etiquetaEntrenador.setForeground(azulOscuro);
        etiquetaEntrenador.setAlignmentX(CENTER_ALIGNMENT);

        encabezado.add(etiquetaResultado);
        encabezado.add(Box.createVerticalStrut(8));
        encabezado.add(etiquetaEntrenador);

        add(encabezado, BorderLayout.NORTH);

        JPanel tarjetaPrincipal = new JPanel();

        tarjetaPrincipal.setLayout(
                new BoxLayout(tarjetaPrincipal, BoxLayout.Y_AXIS)
        );

        tarjetaPrincipal.setBackground(blanco);
        tarjetaPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel tituloEstadisticas = new JLabel(
                "RESUMEN DEL COMBATE",
                SwingConstants.CENTER
        );

        tituloEstadisticas.setFont(
                new Font("Arial Black", Font.BOLD, 21)
        );

        tituloEstadisticas.setForeground(azulOscuro);
        tituloEstadisticas.setAlignmentX(CENTER_ALIGNMENT);

        tarjetaPrincipal.add(tituloEstadisticas);
        tarjetaPrincipal.add(Box.createVerticalStrut(15));

        JPanel tarjetaRondas = new JPanel(
                new BorderLayout(5, 5)
        );

        tarjetaRondas.setBackground(
                new Color(230, 241, 255)
        );

        tarjetaRondas.setBorder(
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        );

        JLabel tituloRondas = new JLabel(
                "RONDAS JUGADAS",
                SwingConstants.CENTER
        );

        tituloRondas.setFont(
                new Font("Arial Black", Font.BOLD, 13)
        );

        tituloRondas.setForeground(
                new Color(85, 110, 150)
        );

        etiquetaRondas = new JLabel(
                "0",
                SwingConstants.CENTER
        );

        etiquetaRondas.setFont(
                new Font("Arial Black", Font.BOLD, 28)
        );

        etiquetaRondas.setForeground(azulOscuro);

        tarjetaRondas.add(tituloRondas, BorderLayout.NORTH);
        tarjetaRondas.add(etiquetaRondas, BorderLayout.CENTER);

        tarjetaRondas.setMaximumSize(
                new Dimension(650, 75)
        );

        tarjetaPrincipal.add(tarjetaRondas);
        tarjetaPrincipal.add(Box.createVerticalStrut(18));

        JPanel panelEquipos = new JPanel(
                new GridLayout(1, 2, 18, 0)
        );

        panelEquipos.setOpaque(false);

        JPanel tarjetaJugador = crearTarjetaEquipo(
                "TU EQUIPO",
                new Color(225, 240, 255)
        );

        JPanel tarjetaRival = crearTarjetaEquipo(
                "RIVAL CPU",
                new Color(255, 235, 225)
        );

        panelEquipos.add(tarjetaJugador);
        panelEquipos.add(tarjetaRival);

        panelEquipos.setMaximumSize(
                new Dimension(750, 220)
        );

        tarjetaPrincipal.add(panelEquipos);

        JPanel contenedorTarjeta = new JPanel(
                new BorderLayout()
        );

        contenedorTarjeta.setOpaque(false);

        contenedorTarjeta.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        contenedorTarjeta.add(
                tarjetaPrincipal,
                BorderLayout.CENTER
        );

        add(contenedorTarjeta, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );

        panelBotones.setOpaque(false);

        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 20, 0, 20
                )
        );

        JButton botonNuevaPartida = crearBoton(
                "NUEVA PARTIDA",
                amarillo,
                new Color(75, 55, 20)
        );

        botonNuevaPartida.addActionListener(
                e -> nuevaPartida()
        );

        JButton botonSalir = crearBoton(
                "SALIR DEL JUEGO",
                new Color(225, 65, 65),
                blanco
        );

        botonSalir.addActionListener(e -> salir());

        panelBotones.add(botonNuevaPartida);
        panelBotones.add(botonSalir);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearTarjetaEquipo(
            String titulo,
            Color fondo
    ) {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(tarjeta, BoxLayout.Y_AXIS)
        );

        tarjeta.setBackground(fondo);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 210, 240),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 15, 20, 15
                        )
                )
        );

        JLabel etiquetaTitulo = new JLabel(
                titulo,
                SwingConstants.CENTER
        );

        etiquetaTitulo.setFont(
                new Font("Arial Black", Font.BOLD, 23)
        );

        etiquetaTitulo.setForeground(azulOscuro);
        etiquetaTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel etiquetaDebilitados = new JLabel(
                "0 DEBILITADOS",
                SwingConstants.CENTER
        );

        etiquetaDebilitados.setFont(
                new Font("Arial Black", Font.BOLD, 20)
        );

        etiquetaDebilitados.setForeground(
                new Color(205, 65, 65)
        );

        etiquetaDebilitados.setAlignmentX(CENTER_ALIGNMENT);

        JLabel etiquetaEnPie = new JLabel(
                "0 EN PIE",
                SwingConstants.CENTER
        );

        etiquetaEnPie.setFont(
                new Font("Arial Black", Font.BOLD, 20)
        );

        etiquetaEnPie.setForeground(
                new Color(35, 145, 75)
        );

        etiquetaEnPie.setAlignmentX(CENTER_ALIGNMENT);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(etiquetaTitulo);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(etiquetaDebilitados);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(etiquetaEnPie);
        tarjeta.add(Box.createVerticalGlue());

        if (titulo.equals("TU EQUIPO")) {

            etiquetaDebilitadosJugador = etiquetaDebilitados;
            etiquetaEnPieJugador = etiquetaEnPie;

        } else {

            etiquetaDebilitadosRival = etiquetaDebilitados;
            etiquetaEnPieRival = etiquetaEnPie;
        }

        return tarjeta;
    }

    private JButton crearBoton(
            String texto,
            Color fondo,
            Color letra
    ) {

        JButton boton = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                Color color = getBackground();

                if (getModel().isPressed()) {
                    color = color.darker().darker();
                } else if (getModel().isRollover()) {
                    color = color.brighter();
                }

                g2.setColor(color);

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        22,
                        22
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        boton.setFont(
                new Font("Arial Black", Font.BOLD, 13)
        );

        boton.setForeground(letra);
        boton.setBackground(fondo);

        boton.setPreferredSize(
                new Dimension(280, 45)
        );

        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);

        boton.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 15, 10, 15
                )
        );

        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        boton.setRolloverEnabled(true);

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

        if (resultado.equalsIgnoreCase("Victoria")) {

            etiquetaResultado.setText("¡GANASTE!");

            etiquetaResultado.setForeground(
                    new Color(25, 125, 65)
            );

        } else if (resultado.equalsIgnoreCase("Derrota")) {

            etiquetaResultado.setText("¡FIN DEL COMBATE!");

            etiquetaResultado.setForeground(
                    new Color(195, 45, 45)
            );

        } else {

            etiquetaResultado.setText("¡EMPATE!");

            etiquetaResultado.setForeground(
                    new Color(35, 75, 145)
            );
        }

        etiquetaEntrenador.setText(
                "ENTRENADOR: " + nombreEntrenador.toUpperCase()
        );

        etiquetaRondas.setText(
                String.valueOf(rondas)
        );

        etiquetaDebilitadosJugador.setText(
                debilitadosJugador + " DEBILITADOS"
        );

        etiquetaDebilitadosRival.setText(
                debilitadosRival + " DEBILITADOS"
        );

        etiquetaEnPieJugador.setText(
                pokemonEnPieJugador + " EN PIE"
        );

        etiquetaEnPieRival.setText(
                pokemonEnPieRival + " EN PIE"
        );
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
