/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author gigiw
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import logica.MotorBatalla;
import modelo.EstadoBatalla;
import modelo.Pokemon;
import modelo.TipoAccion;

public class PanelBatalla extends JPanel {
    private final VentanaPrincipal ventana;

    private final JLabel lblRondaEstado;
    private final JLabel lblInfoJugador;
    private final JLabel lblInfoRival;
    private final JProgressBar barraPSJugador;
    private final JProgressBar barraPSRival;
    private final JTextArea txtBitacora;

    private final JButton btnAtacar;
    private final JButton btnHabilidad;
    private final JButton btnRecuperar;
    private final JButton btnCambiar;

    public PanelBatalla(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblRondaEstado = new JLabel("Ronda 1", SwingConstants.CENTER);
        lblRondaEstado.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(lblRondaEstado, BorderLayout.NORTH);

        JPanel panelCombatientes = new JPanel(new GridLayout(1, 2, 16, 0));

        JPanel panelJugador = new JPanel(new GridLayout(2, 1, 5, 5));
        panelJugador.setBorder(BorderFactory.createTitledBorder("Tu Pokémon Activo"));
        lblInfoJugador = new JLabel();
        barraPSJugador = new JProgressBar();
        barraPSJugador.setStringPainted(true);
        barraPSJugador.setForeground(new Color(40, 160, 70));
        panelJugador.add(lblInfoJugador);
        panelJugador.add(barraPSJugador);

        JPanel panelRival = new JPanel(new GridLayout(2, 1, 5, 5));
        panelRival.setBorder(BorderFactory.createTitledBorder("Pokémon Rival"));
        lblInfoRival = new JLabel();
        barraPSRival = new JProgressBar();
        barraPSRival.setStringPainted(true);
        barraPSRival.setForeground(new Color(200, 60, 60));
        panelRival.add(lblInfoRival);
        panelRival.add(barraPSRival);

        panelCombatientes.add(panelJugador);
        panelCombatientes.add(panelRival);

        txtBitacora = new JTextArea(12, 40);
        txtBitacora.setEditable(false);
        txtBitacora.setLineWrap(true);
        txtBitacora.setWrapStyleWord(true);
        txtBitacora.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollBitacora = new JScrollPane(txtBitacora);
        scrollBitacora.setBorder(BorderFactory.createTitledBorder("Registro de Combate"));

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelCombatientes, BorderLayout.NORTH);
        panelCentro.add(scrollBitacora, BorderLayout.CENTER);
        add(panelCentro, BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new GridLayout(1, 4, 10, 0));
        btnAtacar = new JButton("Atacar");
        btnHabilidad = new JButton("Habilidad Especial (3)");
        btnRecuperar = new JButton("Recuperar (2)");
        btnCambiar = new JButton("Cambiar Pokémon");

        btnAtacar.addActionListener(e -> ejecutarAccion(TipoAccion.ATACAR, null));
        btnHabilidad.addActionListener(e -> ejecutarAccion(TipoAccion.HABILIDAD_ESPECIAL, null));
        btnRecuperar.addActionListener(e -> ejecutarAccion(TipoAccion.RECUPERAR, null));
        btnCambiar.addActionListener(e -> solicitarCambio(false));

        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnHabilidad);
        panelAcciones.add(btnRecuperar);
        panelAcciones.add(btnCambiar);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public void actualizarInterfaz() {
        MotorBatalla motor = ventana.getMotorBatalla();
        if (motor == null) return;

        Pokemon pJugador = motor.getPokemonActivoJugador();
        Pokemon pRival = motor.getPokemonActivoRival();

        lblRondaEstado.setText("Ronda " + motor.getRondaActual() + " / " + MotorBatalla.LIMITE_RONDAS
                + " — Estado: " + motor.getEstado().getDescripcion());

        lblInfoJugador.setText(motor.getNombreJugador() + ": " + pJugador.getNombre()
                + " [" + pJugador.getTipo() + " | Nv." + pJugador.getNivel() + "]");
        barraPSJugador.setMaximum(pJugador.getPsMaximos());
        barraPSJugador.setValue(pJugador.getPsActuales());
        barraPSJugador.setString("PS: " + pJugador.getPsActuales() + " / " + pJugador.getPsMaximos());

        lblInfoRival.setText(motor.getNombreRival() + ": " + pRival.getNombre()
                + " [" + pRival.getTipo() + " | Nv." + pRival.getNivel() + "]");
        barraPSRival.setMaximum(pRival.getPsMaximos());
        barraPSRival.setValue(pRival.getPsActuales());
        barraPSRival.setString("PS: " + pRival.getPsActuales() + " / " + pRival.getPsMaximos());

        txtBitacora.setText(motor.getTextoBitacoraCompleto());
        txtBitacora.setCaretPosition(txtBitacora.getDocument().getLength());

        btnHabilidad.setText("Habilidad (" + pJugador.getUsosHabilidad() + "/" + Pokemon.USOS_HABILIDAD_MAX + ")");
        btnRecuperar.setText("Recuperar (" + pJugador.getUsosRecuperacion() + "/" + Pokemon.USOS_RECUPERACION_MAX + ")");

        EstadoBatalla estado = motor.getEstado();
        boolean esperando = (estado == EstadoBatalla.ESPERANDO_ACCION);
        btnAtacar.setEnabled(esperando && !pJugador.estaDebilitado());
        btnHabilidad.setEnabled(esperando && !pJugador.estaDebilitado() && pJugador.getUsosHabilidad() > 0);
        btnRecuperar.setEnabled(esperando && !pJugador.estaDebilitado()
                && pJugador.getUsosRecuperacion() > 0 && pJugador.getPsActuales() < pJugador.getPsMaximos());
        btnCambiar.setEnabled((esperando || estado == EstadoBatalla.ELEGIR_REEMPLAZO)
                && motor.hayReemplazoJugadorDisponible());

        if (estado == EstadoBatalla.ELEGIR_REEMPLAZO) {
            solicitarCambio(true);
        } else if (estado == EstadoBatalla.VICTORIA || estado == EstadoBatalla.DERROTA || estado == EstadoBatalla.EMPATE) {
            JOptionPane.showMessageDialog(this, "La batalla ha finalizado: " + estado.getDescripcion(),
                    "Fin del combate", JOptionPane.INFORMATION_MESSAGE);
            ventana.irAResultado();
        }
    }

    private void ejecutarAccion(TipoAccion accion, Integer indiceCambio) {
        try {
            ventana.getMotorBatalla().ejecutarRonda(accion, indiceCambio);
            actualizarInterfaz();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Acción no permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void solicitarCambio(boolean obligatorioPorDebilitamiento) {
        MotorBatalla motor = ventana.getMotorBatalla();
        List<Pokemon> equipo = motor.getEquipoJugador();
        List<String> opciones = new ArrayList<>();
        List<Integer> indicesReales = new ArrayList<>();

        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            if (!p.estaDebilitado() && p != motor.getPokemonActivoJugador()) {
                opciones.add((i + 1) + ". " + p.getNombre() + " (PS: " + p.getPsActuales() + "/" + p.getPsMaximos() + ")");
                indicesReales.add(i);
            }
        }

        if (opciones.isEmpty()) return;

        Object seleccion = JOptionPane.showInputDialog(this,obligatorioPorDebilitamiento? "Tu Pokémon fue debilitado. Elige el reemplazo:": "Selecciona el Pokémon que entrará al combate:",
                "Cambiar Pokémon",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones.toArray(),
                opciones.get(0)
        );

        if (seleccion != null) {
            int pos = opciones.indexOf(seleccion.toString());
            int indiceEquipo = indicesReales.get(pos);
            if (obligatorioPorDebilitamiento) {
                motor.seleccionarReemplazoJugador(indiceEquipo);
                actualizarInterfaz();
            } else {
                ejecutarAccion(TipoAccion.CAMBIAR, indiceEquipo);
            }
        } else if (obligatorioPorDebilitamiento) {
            // Si cerró el diálogo sin elegir, vuelve a solicitarlo porque es obligatorio
            solicitarCambio(true);
        }
    }
}
