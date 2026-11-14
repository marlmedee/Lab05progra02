
package vista;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PanelInicio extends JPanel {

    private final VentanaPrincipal ventana;

    private JTextField txtNombre;
    private JLabel lblImagen;

    private final Color AZUL = new Color(45, 115, 190);
    private final Color ROJO = new Color(225, 65, 65);
    private final Color AMARILLO = new Color(255, 205, 55);
    private final Color FONDO = new Color(235, 245, 255);

    public PanelInicio(VentanaPrincipal ventana) {

        this.ventana = ventana;

        setLayout(new BorderLayout());
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 35, 25, 35));

        crearPantalla();
    }

    private void crearPantalla() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(
            new BoxLayout(panelTitulo, BoxLayout.Y_AXIS)
        );

        JLabel lblTitulo = new JLabel("BATALLAS POKÉMON");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 38));
        lblTitulo.setForeground(AZUL);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel(
            "¡Prepara tu equipo y conviértete en campeón!"
        );
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 17));
        lblSubtitulo.setForeground(new Color(75, 85, 100));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createVerticalStrut(8));
        panelTitulo.add(lblSubtitulo);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(
            new BoxLayout(panelCentral, BoxLayout.Y_AXIS)
        );

        lblImagen = new JLabel();
        lblImagen.setAlignmentX(CENTER_ALIGNMENT);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        cargarImagen();

        JLabel lblBienvenida = new JLabel("¡Bienvenido, entrenador!");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 25));
        lblBienvenida.setForeground(ROJO);
        lblBienvenida.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblInstruccion = new JLabel(
            "Escribe tu nombre para comenzar tu aventura."
        );
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInstruccion.setAlignmentX(CENTER_ALIGNMENT);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        txtNombre.setMaximumSize(new Dimension(330, 42));
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        txtNombre.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            )
        );

        JButton btnContinuar = crearBoton(
            "CONTINUAR",
            ROJO,
            Color.WHITE
        );

        JButton btnInstrucciones = crearBoton(
            "INSTRUCCIONES",
            AZUL,
            Color.WHITE
        );

        JButton btnSalir = crearBoton(
            "SALIR",
            new Color(100, 110, 125),
            Color.WHITE
        );

        btnContinuar.addActionListener((ActionEvent e) -> {
            continuar();
        });

        txtNombre.addActionListener((ActionEvent e) -> {
            continuar();
        });

        btnInstrucciones.addActionListener((ActionEvent e) -> {
            mostrarInstrucciones();
        });

        btnSalir.addActionListener((ActionEvent e) -> {
            salir();
        });

        panelCentral.add(Box.createVerticalStrut(15));
        panelCentral.add(lblImagen);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(lblBienvenida);
        panelCentral.add(Box.createVerticalStrut(8));
        panelCentral.add(lblInstruccion);
        panelCentral.add(Box.createVerticalStrut(15));
        panelCentral.add(txtNombre);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(btnContinuar);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(btnInstrucciones);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);

        JLabel lblPie = new JLabel(
            "POKÉMON BATTLE GAME | PROGRAMACIÓN II",
            SwingConstants.CENTER
        );

        lblPie.setFont(new Font("Arial", Font.BOLD, 11));
        lblPie.setForeground(new Color(110, 120, 135));

        add(lblPie, BorderLayout.SOUTH);
    }

    private void cargarImagen() {

        java.net.URL recurso =
            getClass().getResource("/imagenes/pikachu.png");

        if (recurso != null) {

            ImageIcon imagen = new ImageIcon(recurso);

            java.awt.Image imagenEscalada =
                imagen.getImage().getScaledInstance(
                    170,
                    170,
                    java.awt.Image.SCALE_SMOOTH
                );

            lblImagen.setIcon(new ImageIcon(imagenEscalada));

        } else {

            lblImagen.setText("¡Tu aventura Pokémon comienza aquí!");
            lblImagen.setFont(new Font("Arial", Font.BOLD, 14));
            lblImagen.setForeground(AZUL);
        }
    }

    private JButton crearBoton(
        String texto,
        Color fondo,
        Color letra
    ) {

        JButton boton = new JButton(texto);

        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setForeground(letra);
        boton.setBackground(fondo);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(
            new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );

        boton.setPreferredSize(new Dimension(240, 43));
        boton.setMaximumSize(new Dimension(240, 43));
        boton.setAlignmentX(CENTER_ALIGNMENT);

        return boton;
    }

    private void continuar() {

        String nombre = txtNombre.getText();

        ventana.continuarConNombre(nombre);
    }

    private void mostrarInstrucciones() {

        String instrucciones =
            "¡BIENVENIDO A BATALLAS POKÉMON!\n\n"
            + "1. Escribe el nombre de tu entrenador.\n"
            + "2. Selecciona entre 1 y 6 Pokémon.\n"
            + "3. Enfrenta al equipo rival por turnos.\n"
            + "4. Utiliza ataques, habilidades y recuperación.\n"
            + "5. ¡Derrota al equipo rival para ganar!\n\n"
            + "¡Buena suerte, entrenador!";

        JOptionPane.showMessageDialog(
            this,
            instrucciones,
            "Reglas del juego",
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

