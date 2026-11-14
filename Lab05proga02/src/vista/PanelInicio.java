
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;
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

public class PanelInicio extends JPanel {

    private final VentanaPrincipal ventana;
    private JTextField campoNombre;

    private final Color azulOscuro = new Color(35, 55, 105);
    private final Color azulFondo = new Color(220, 233, 255);
    private final Color amarillo = new Color(255, 205, 55);
    private final Color blanco = Color.WHITE;

    public PanelInicio(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout());
        setBackground(azulFondo);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        crearInterfaz();
    }

    private void crearInterfaz() {

        JLabel titulo = new JLabel(
                "BATALLAS POKÉMON",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 38));
        titulo.setForeground(azulOscuro);

        JLabel subtitulo = new JLabel(
                "¡Tu aventura comienza aquí!",
                SwingConstants.CENTER
        );

        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(azulOscuro);

        JPanel encabezado = new JPanel();
        encabezado.setOpaque(false);
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));

        titulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(8));
        encabezado.add(subtitulo);

        add(encabezado, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(blanco);
        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 205, 235), 2
                        ),
                        BorderFactory.createEmptyBorder(20, 45, 20, 45)
                )
        );

        ImageIcon icono = cargarImagen("/imagenes/pikachu.png");

        JLabel imagen = new JLabel();
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        imagen.setAlignmentX(CENTER_ALIGNMENT);
        imagen.setPreferredSize(new Dimension(210, 190));
        imagen.setMaximumSize(new Dimension(210, 190));

        if (icono != null) {
            imagen.setIcon(icono);
        } else {
            imagen.setText("⚡");
            imagen.setFont(new Font("Arial", Font.PLAIN, 100));
            imagen.setForeground(amarillo);
        }

        JLabel bienvenida = new JLabel(
                "¡Bienvenido, entrenador!",
                SwingConstants.CENTER
        );

        bienvenida.setFont(new Font("Arial", Font.BOLD, 23));
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
        campoNombre.setPreferredSize(new Dimension(320, 45));
        campoNombre.setMaximumSize(new Dimension(320, 45));
        campoNombre.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(azulOscuro, 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        campoNombre.addActionListener(e -> continuar());

        JButton botonContinuar = crearBoton(
                "COMENZAR AVENTURA",
                amarillo,
                azulOscuro
        );

        botonContinuar.addActionListener(e -> continuar());

        JButton botonInstrucciones = crearBoton(
                "INSTRUCCIONES",
                new Color(230, 238, 255),
                azulOscuro
        );

        botonInstrucciones.addActionListener(
                e -> mostrarInstrucciones()
        );

        JButton botonSalir = crearBoton(
                "SALIR DEL JUEGO",
                new Color(220, 80, 80),
                blanco
        );

        botonSalir.addActionListener(e -> salir());

        tarjeta.add(imagen);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(bienvenida);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(indicacion);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(campoNombre);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(botonContinuar);
        tarjeta.add(Box.createVerticalStrut(10));
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
                180,
                180,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(imagen);
    }

    private JButton crearBoton(
            String texto,
            Color fondo,
            Color letra
    ) {

        JButton boton = new JButton(texto);

        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(letra);
        boton.setBackground(fondo);
        boton.setPreferredSize(new Dimension(280, 42));
        boton.setMaximumSize(new Dimension(280, 42));
        boton.setAlignmentX(CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        String instrucciones =
                "¡Bienvenido a Batallas Pokémon!\n\n"
                + "1. Ingresa el nombre de tu entrenador.\n"
                + "2. Forma tu equipo de Pokémon.\n"
                + "3. Enfréntate a un rival controlado por la CPU.\n"
                + "4. Utiliza ataques, habilidades y recuperación.\n"
                + "5. Derrota al equipo rival para ganar.\n\n"
                + "¡Que comience la aventura!";

        JOptionPane.showMessageDialog(
                this,
                instrucciones,
                "Instrucciones",
                JOptionPane.INFORMATION_MESSAGE
        );
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

