/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logica.Entrenador;

/**
 *
 * @author gigiw
 */
public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contenedor;

    private Entrenador entrenador;

    public static final String CARD_INICIO = "INICIO";
    public static final String CARD_EQUIPO = "EQUIPO";
    public static final String CARD_BATALLA = "BATALLA";
    public static final String CARD_RESULTADO = "RESULTADO";

    public VentanaPrincipal() {
        setTitle("Batallas Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        PanelInicio panelInicio = new PanelInicio(this);

        contenedor.add(panelInicio, CARD_INICIO);

        contenedor.add(new JPanel(), CARD_EQUIPO);
        contenedor.add(new JPanel(), CARD_BATALLA);
        contenedor.add(new JPanel(), CARD_RESULTADO);

        add(contenedor);

        mostrarInicio();
    }

    public void mostrarInicio() {
        cardLayout.show(contenedor, CARD_INICIO);
    }

    public void mostrarEquipo() {
        cardLayout.show(contenedor, CARD_EQUIPO);
    }

    public void mostrarBatalla() {
        cardLayout.show(contenedor, CARD_BATALLA);
    }

    public void mostrarResultado() {
        cardLayout.show(contenedor, CARD_RESULTADO);
    }

    public void continuarConNombre(String nombre) {

        try {
            entrenador = new Entrenador();
            entrenador.setNombre(nombre);

            mostrarEquipo();

        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Nombre no válido",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
} 

