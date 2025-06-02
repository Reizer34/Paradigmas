package cl.unab.inf.ptec102.tarea2Fabi.frontend;

import cl.unab.inf.ptec102.tarea2Fabi.backend.Item;
import cl.unab.inf.ptec102.tarea2Fabi.backend.OpcionMultiple;
import cl.unab.inf.ptec102.tarea2Fabi.backend.Prueba;
import cl.unab.inf.ptec102.tarea2Fabi.backend.VerdaderoFalso;

import javax.swing.*;
import java.awt.*;

public class VentanaPrueba extends JPanel {
    private Prueba prueba;
    private CardLayout cardLayout;
    private JPanel panelCards;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaPrueba(Prueba prueba, VentanaPrincipal ventanaPrincipal) {
        this.prueba = prueba;
        this.ventanaPrincipal = ventanaPrincipal;
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCards = new JPanel(cardLayout);

        for (int i = 0; i < prueba.getCantidadItems(); i++) {
            Item item = prueba.getItemActual();
            JPanel panelItem = crearPanelParaItem(item);
            panelCards.add(panelItem, String.valueOf(i));
            prueba.avanzar();
        }

        add(panelCards, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        btnAnterior.addActionListener(e -> {
            prueba.retroceder();
            actualizarEstado();
        });

        btnSiguiente.addActionListener(e -> {
            if (prueba.puedeAvanzar()) {
                prueba.avanzar();
                actualizarEstado();
            } else {
                // Aquí puedes llamar al método de la ventana principal para mostrar el resumen
                ventanaPrincipal.mostrarResumen();
            }
        });

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        add(panelBotones, BorderLayout.SOUTH);

        prueba.retroceder(); // Volver al índice 0
        actualizarEstado();
    }

    private JPanel crearPanelParaItem(Item item) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblEnunciado = new JLabel("<html><h3>" + item.getEnunciado() + "</h3></html>");
        panel.add(lblEnunciado, BorderLayout.NORTH);

        if (item instanceof OpcionMultiple) {
            OpcionMultiple om = (OpcionMultiple) item;
            ButtonGroup group = new ButtonGroup();
            JPanel opcionesPanel = new JPanel(new GridLayout(0, 1));
            for (String opcion : om.getOpciones()) {
                JRadioButton radio = new JRadioButton(opcion);
                group.add(radio);
                opcionesPanel.add(radio);
            }
            panel.add(opcionesPanel, BorderLayout.CENTER);
        } else if (item instanceof VerdaderoFalso) {
            JPanel vfPanel = new JPanel(new GridLayout(0, 1));
            JRadioButton verdadero = new JRadioButton("Verdadero");
            JRadioButton falso = new JRadioButton("Falso");
            ButtonGroup group = new ButtonGroup();
            group.add(verdadero);
            group.add(falso);
            vfPanel.add(verdadero);
            vfPanel.add(falso);

            JTextField justificacion = new JTextField();
            vfPanel.add(new JLabel("Justifique si es falso:"));
            vfPanel.add(justificacion);

            panel.add(vfPanel, BorderLayout.CENTER);
        }

        return panel;
    }

    private void actualizarEstado() {
        cardLayout.show(panelCards, String.valueOf(prueba.getIndiceActual()));
        btnAnterior.setEnabled(prueba.puedeRetroceder());
        btnSiguiente.setText(prueba.puedeAvanzar() ? "Siguiente" : "Enviar respuestas");
    }
}
