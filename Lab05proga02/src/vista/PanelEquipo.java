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
import javax.swing.*;
import java.awt.*;
import logica.Entrenador;
import modelo.Pokemon;
import modelo.TipoPokemon;

public class PanelEquipo extends JPanel{
    private final VentanaPrincipal ventana;
    private final JLabel lblEntrenador;
    private final JLabel lblContadorEquipo;
    private final DefaultListModel<Pokemon> modeloCatalogo;
    private final DefaultListModel<Pokemon> modeloEquipo;
    private final JList<Pokemon> listaCatalogo;
    private final JList<Pokemon> listaEquipo;
    private final JTextArea txtFicha;
    private final JButton btnComenzar;
    private final JButton btnRestaurar;

    public PanelEquipo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JPanel panelSuperior = new JPanel(new BorderLayout());
        lblEntrenador = new JLabel("Entrenador: ");
        lblEntrenador.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblContadorEquipo = new JLabel("Equipo: 0/6");
        lblContadorEquipo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelSuperior.add(lblEntrenador, BorderLayout.WEST);
        panelSuperior.add(lblContadorEquipo, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);
        modeloCatalogo = new DefaultListModel<>();
        modeloEquipo = new DefaultListModel<>();
        listaCatalogo = new JList<>(modeloCatalogo);
        listaEquipo = new JList<>(modeloEquipo);
        listaCatalogo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtFicha = new JTextArea();
        txtFicha.setEditable(false);
        txtFicha.setLineWrap(true);
        txtFicha.setWrapStyleWord(true);
        txtFicha.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtFicha.setBorder(BorderFactory.createTitledBorder("Ficha del Pokémon"));

        listaCatalogo.addListSelectionListener(e -> mostrarFicha(listaCatalogo.getSelectedValue()));
        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 12, 0));
        JScrollPane scrollCatalogo = new JScrollPane(listaCatalogo);
        scrollCatalogo.setBorder(BorderFactory.createTitledBorder("Catalogo Disponible"));
        JScrollPane scrollEquipo = new JScrollPane(listaEquipo);
        scrollEquipo.setBorder(BorderFactory.createTitledBorder("Mi Equipo (1 a 6)"));
        panelCentro.add(scrollCatalogo);
        panelCentro.add(txtFicha);
        panelCentro.add(scrollEquipo);
        add(panelCentro, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton btnAgregar = new JButton("Agregar ->");
        JButton btnQuitar = new JButton("<- Quitar");
        JButton btnPersonalizado = new JButton("Crear Pokémon");
        JButton btnGuardar = new JButton("Guardar equipo");
        btnRestaurar = new JButton("Restaurar equipo");
        btnComenzar = new JButton("Comenzar batalla");
        btnComenzar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAgregar.addActionListener(e -> agregarSeleccionado());
        btnQuitar.addActionListener(e -> quitarSeleccionado());
        btnPersonalizado.addActionListener(e -> dialogCrearPersonalizado());
        btnGuardar.addActionListener(e -> guardarEquipoMemoria());
        btnRestaurar.addActionListener(e -> restaurarEquipoMemoria());
        btnComenzar.addActionListener(e -> ventana.iniciarBatalla());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnQuitar);
        panelBotones.add(btnPersonalizado);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnRestaurar);
        panelBotones.add(btnComenzar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void refrescarVista() {
        lblEntrenador.setText("Entrenador: " + ventana.getEntrenador().getNombre());
        modeloCatalogo.clear();
        for (Pokemon p : ventana.getCatalogo().getPokemonDisponibles()) {
            modeloCatalogo.addElement(p);
        }
        actualizarListaEquipo();
        if (!modeloCatalogo.isEmpty()) {
            listaCatalogo.setSelectedIndex(0);
        }
    }

    private void actualizarListaEquipo() {
        modeloEquipo.clear();
        for (Pokemon p : ventana.getEntrenador().getEquipo()) {
            modeloEquipo.addElement(p);
        }
        int total = modeloEquipo.size();
        lblContadorEquipo.setText("Equipo: " + total + "/" + Entrenador.MAX_EQUIPO);
        btnComenzar.setEnabled(total >= 1);
        btnRestaurar.setEnabled(ventana.getSerializador().hayEquipoGuardado());
    }

    private void mostrarFicha(Pokemon p) {
        if(p == null){
            txtFicha.setText("Seleccione un Pokémon del catalogo para ver sus atributos");
            return;
        }
        txtFicha.setText("Nombre: " + p.getNombre()+"\n"+ "Tipo: " + p.getTipo().getNombreVisible()+"\n"+ "Nivel: " + p.getNivel() + "\n"+"PS Maximos: " + p.getPsMaximos() + "\n"
                + "Ataque: "+p.getAtaque()+ "\n"+ "Defensa: "+p.getDefensa() + "\n"+ "Velocidad: "+p.getVelocidad() + "\n\n"+"Habilidad Especial:\n" + p.getNombreHabilidadEspecial()+"\n"
                + p.getDescripcionHabilidad()
        );
    }
    private void agregarSeleccionado() {
        try {
            Pokemon seleccionado = listaCatalogo.getSelectedValue();
            ventana.getEntrenador().agregarPokemon(seleccionado);
            actualizarListaEquipo();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Accion no valida", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void quitarSeleccionado() {
        try {
            int idx = listaEquipo.getSelectedIndex();
            ventana.getEntrenador().quitarPokemon(idx);
            actualizarListaEquipo();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Accion no valida", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void guardarEquipoMemoria() {
        try {
            ventana.getSerializador().guardarEquipoEnMemoria(ventana.getEntrenador());
            btnRestaurar.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Equipo serializado y guardado en memoria correctamente!", "Equipo guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al guardar", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void restaurarEquipoMemoria() {
        try {
            Entrenador recuperado = ventana.getSerializador().restaurarEquipoDeMemoria();
            ventana.getEntrenador().reemplazarEquipo(recuperado.getEquipo());
            actualizarListaEquipo();
            JOptionPane.showMessageDialog(this, "Equipo restaurado desde memoria (" + recuperado.getEquipo().size() + " integrantes) ",
                    "Equipo restaurado", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al restaurar", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void dialogCrearPersonalizado() {
        JTextField txtNombreNuevo = new JTextField();
        JComboBox<TipoPokemon> cmbTipo = new JComboBox<>(TipoPokemon.values());
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.setPreferredSize(new Dimension(280, 70));
        panel.add(new JLabel("Nombre (3-12 chars):"));
        panel.add(txtNombreNuevo);
        panel.add(new JLabel("Tipo elemental:"));
        panel.add(cmbTipo);

        int res = JOptionPane.showConfirmDialog(this, panel, "Nuevo Pokémon Personalizado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Pokemon creado = ventana.getCatalogo().agregarPersonalizado(txtNombreNuevo.getText(), (TipoPokemon) cmbTipo.getSelectedItem());
                refrescarVista();
                listaCatalogo.setSelectedValue(creado, true);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
