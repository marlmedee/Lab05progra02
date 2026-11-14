
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import logica.Entrenador;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class PanelInicio extends JPanel {

    private final VentanaPrincipal ventana;
    private JTextField campoNombre;

    private final Color azulOscuro = new Color(35, 55, 105);
    private final Color amarillo = new Color(255, 195, 35);
    private final Color blanco = Color.WHITE;

    public PanelInicio(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout());
        setOpaque(false);

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

        dibujarNube(g2, 75, 100);
        dibujarNube(g2, ancho - 210, 145);
        dibujarNube(g2, ancho - 110, 65);

        g2.setColor(new Color(105, 205, 100));
        g2.fillOval(-130, alto - 145, ancho + 260, 260);

        g2.setColor(new Color(65, 175, 80));
        g2.fillOval(-180, alto - 95, ancho + 360, 210);

        g2.setColor(new Color(45, 145, 65));
        g2.fillRect(0, alto - 30, ancho, 30);

        dibujarPokebola(g2, 75, alto - 115, 45);
        dibujarPokebola(g2, ancho - 115, alto - 130, 50);

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
        encabezado.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        JLabel titulo = new JLabel(
                "BATALLAS POKÉMON",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial Black", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "¡Tu aventura comienza aquí!",
                SwingConstants.CENTER
        );

        subtitulo.setFont(new Font("Arial", Font.BOLD, 19));
        subtitulo.setForeground(new Color(25, 75, 145));
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(5));
        encabezado.add(subtitulo);

        add(encabezado, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(tarjeta, BoxLayout.Y_AXIS)
        );

        tarjeta.setBackground(blanco);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(255, 255, 255),
                                3
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 40, 15, 40
                        )
                )
        );

        ImageIcon icono = cargarImagen("/imagenes/pikachu.png");

        JLabel imagen = new JLabel();
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        imagen.setAlignmentX(CENTER_ALIGNMENT);
        imagen.setPreferredSize(new Dimension(200, 170));
        imagen.setMaximumSize(new Dimension(200, 170));

        if (icono != null) {
            imagen.setIcon(icono);
        } else {
            imagen.setText("PIKACHU");
            imagen.setFont(new Font("Arial Black", Font.BOLD, 28));
            imagen.setForeground(amarillo);
        }

        JLabel bienvenida = new JLabel(
                "¡Bienvenido, entrenador!",
                SwingConstants.CENTER
        );

        bienvenida.setFont(new Font("Arial Black", Font.BOLD, 22));
        bienvenida.setForeground(azulOscuro);
        bienvenida.setAlignmentX(CENTER_ALIGNMENT);

        JLabel indicacion = new JLabel(
                "¿Cómo te llamas?",
                SwingConstants.CENTER
        );

        indicacion.setFont(new Font("Arial", Font.PLAIN, 17));
        indicacion.setForeground(new Color(90, 100, 125));
        indicacion.setAlignmentX(CENTER_ALIGNMENT);

        campoNombre = new JTextField(20);
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.setPreferredSize(new Dimension(320, 42));
        campoNombre.setMaximumSize(new Dimension(320, 42));

        campoNombre.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(175, 190, 215),
                                2
                        ),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        campoNombre.addActionListener(e -> continuar());

        JButton botonContinuar = crearBoton(
                "COMENZAR AVENTURA",
                amarillo,
                new Color(75, 55, 20)
        );

        botonContinuar.addActionListener(e -> continuar());

        JButton botonInstrucciones = crearBoton(
                "CÓMO JUGAR",
                new Color(55, 145, 220),
                Color.WHITE
        );

        botonInstrucciones.addActionListener(
                e -> mostrarInstrucciones()
        );

        JButton botonSalir = crearBoton(
                "SALIR DEL JUEGO",
                new Color(225, 65, 65),
                Color.WHITE
        );

        botonSalir.addActionListener(e -> salir());

        tarjeta.add(imagen);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(bienvenida);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(indicacion);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(campoNombre);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(botonContinuar);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(botonInstrucciones);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(botonSalir);

        JPanel contenedorTarjeta = new JPanel();
        contenedorTarjeta.setOpaque(false);
        contenedorTarjeta.add(tarjeta);

        add(contenedorTarjeta, BorderLayout.CENTER);
    }

    private ImageIcon cargarImagen(String ruta) {

        java.net.URL recurso = getClass().getResource(ruta);

        if (recurso == null) {
            return null;
        }

        ImageIcon original = new ImageIcon(recurso);

        Image imagen = original.getImage().getScaledInstance(
                170,
                170,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(imagen);
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

        boton.setFont(new Font("Arial Black", Font.BOLD, 13));
        boton.setForeground(letra);
        boton.setBackground(fondo);
        boton.setPreferredSize(new Dimension(280, 43));
        boton.setMaximumSize(new Dimension(280, 43));
        boton.setMinimumSize(new Dimension(280, 43));
        boton.setAlignmentX(CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setRolloverEnabled(true);

        return boton;
    }

    private void continuar() {

        try {

            String nombre = Entrenador.validarYFormatearNombre(
                    campoNombre.getText()
            );

            ventana.continuarConNombre(nombre);

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Nombre no válido",
                    JOptionPane.WARNING_MESSAGE
            );

            campoNombre.requestFocus();
        }
    }

            private void mostrarInstrucciones() {

                JDialog dialogo = new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Cómo jugar",
                        java.awt.Dialog.ModalityType.APPLICATION_MODAL
                );

                dialogo.setUndecorated(true);
                dialogo.setSize(590, 470);
                dialogo.setLocationRelativeTo(this);

                JPanel principal = new JPanel(new BorderLayout());
                principal.setBackground(new Color(75, 180, 240));
                principal.setBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                );

                JPanel tarjeta = new JPanel();
                tarjeta.setLayout(
                        new BoxLayout(tarjeta, BoxLayout.Y_AXIS)
                );
                tarjeta.setBackground(Color.WHITE);
                tarjeta.setBorder(
                        BorderFactory.createEmptyBorder(25, 35, 25, 35)
                );

                JLabel titulo = new JLabel(
                        "¡PREPÁRATE PARA BATALLAR!",
                        SwingConstants.CENTER
                );

                titulo.setFont(new Font("Arial Black", Font.BOLD, 23));
                titulo.setForeground(new Color(35, 75, 145));
                titulo.setAlignmentX(CENTER_ALIGNMENT);
                titulo.setMaximumSize(new Dimension(500, 35));

                JLabel subtitulo = new JLabel(
                        "Conoce las reglas de tu aventura",
                        SwingConstants.CENTER
                );

                subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
                subtitulo.setForeground(new Color(100, 110, 130));
                subtitulo.setAlignmentX(CENTER_ALIGNMENT);
                subtitulo.setMaximumSize(new Dimension(500, 30));

                tarjeta.add(titulo);
                tarjeta.add(Box.createVerticalStrut(5));
                tarjeta.add(subtitulo);
                tarjeta.add(Box.createVerticalStrut(25));

                String[] pasos = {
                    "01.  Ingresa el nombre de tu entrenador.",
                    "02.  Selecciona entre 1 y 6 Pokémon para tu equipo.",
                    "03.  Enfréntate a un equipo rival controlado por la CPU.",
                    "04.  Elige tus acciones durante cada ronda.",
                    "05.  Derrota a todos los Pokémon del rival para ganar."
                };

                for (String paso : pasos) {

                    JLabel etiqueta = new JLabel(paso);

                    etiqueta.setFont(new Font("Arial", Font.BOLD, 15));
                    etiqueta.setForeground(new Color(45, 65, 105));
                    etiqueta.setAlignmentX(CENTER_ALIGNMENT);
                    etiqueta.setPreferredSize(new Dimension(490, 28));
                    etiqueta.setMaximumSize(new Dimension(490, 28));

                    tarjeta.add(etiqueta);
                    tarjeta.add(Box.createVerticalStrut(13));
                }

                tarjeta.add(Box.createVerticalStrut(10));

                JLabel consejo = new JLabel(
                        "¡Elige bien tus Pokémon y aprovecha sus habilidades!",
                        SwingConstants.CENTER
                );

                consejo.setFont(new Font("Arial", Font.BOLD, 14));
                consejo.setForeground(new Color(45, 145, 85));
                consejo.setAlignmentX(CENTER_ALIGNMENT);
                consejo.setMaximumSize(new Dimension(500, 30));

                tarjeta.add(consejo);
                tarjeta.add(Box.createVerticalStrut(20));

                JButton botonCerrar = crearBoton(
                        "¡ENTENDIDO!",
                        amarillo,
                        new Color(75, 55, 20)
                );

                botonCerrar.setAlignmentX(CENTER_ALIGNMENT);
                botonCerrar.setPreferredSize(new Dimension(250, 45));
                botonCerrar.setMaximumSize(new Dimension(250, 45));

                botonCerrar.addActionListener(e -> dialogo.dispose());

                tarjeta.add(botonCerrar);

                principal.add(tarjeta, BorderLayout.CENTER);

                dialogo.setContentPane(principal);
                dialogo.setVisible(true);
            }


   
    private void salir() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas salir del juego?",
                "Salir",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

