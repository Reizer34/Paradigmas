package cl.unab.inf.ptec102.tarea2Fabi.frontend;

import cl.unab.inf.ptec102.tarea2Fabi.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaRevision extends JPanel {

    private Prueba prueba;
    private VentanaPrincipal ventanaPrincipal;
    private CardLayout cardLayout;
    private JPanel contenedor;
    private int indiceActual;

    private JLabel lblEnunciado;
    private JPanel panelOpciones;
    private JButton btnAnterior, btnSiguiente, btnVolverResumen;
    private JLabel lblResultado;

    public VentanaRevision(Prueba prueba, VentanaPrincipal ventanaPrincipal) {
        this.prueba = prueba;
        this.ventanaPrincipal = ventanaPrincipal;
        this.indiceActual = 0;

        setLayout(new BorderLayout());

        // Enunciado
        lblEnunciado = new JLabel("", SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblEnunciado, BorderLayout.NORTH);

        // Panel opciones / respuestas
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        add(panelOpciones, BorderLayout.CENTER);

        // Resultado correcto o incorrecto
        lblResultado = new JLabel("", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblResultado, BorderLayout.SOUTH);

        // Panel botones
        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnVolverResumen = new JButton("Volver al Resumen");

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnVolverResumen);

        add(panelBotones, BorderLayout.PAGE_END);

        // Eventos botones
        btnAnterior.addActionListener(e -> {
            if (indiceActual > 0) {
                indiceActual--;
                actualizarVista();
            }
        });

        btnSiguiente.addActionListener(e -> {
            if (indiceActual < prueba.getCantidadItems() - 1) {
                indiceActual++;
                actualizarVista();
            }
        });

        btnVolverResumen.addActionListener(e -> {
            ventanaPrincipal.mostrarResumen();
        });

        actualizarVista();
    }

    private void actualizarVista() {
        Item item = prueba.getItemActual();
        RespuestaUsuario respuesta = prueba.getRespuestaActual();

        lblEnunciado.setText("<html><body style='width: 400px'>" + item.getEnunciado() + "</body></html>");

        panelOpciones.removeAll();

        String respuestaUsuario = respuesta.getRespuesta();

        if (item.getTipo() == TipoItem.Opcion_Multiple) {
            OpcionMultiple om = (OpcionMultiple) item;
            for (String opcion : om.getOpciones()) {
                JLabel labelOpcion = new JLabel(opcion);
                // Marca la respuesta elegida por el usuario
                if (opcion.equalsIgnoreCase(respuestaUsuario)) {
                    labelOpcion.setForeground(Color.BLUE);
                    labelOpcion.setFont(labelOpcion.getFont().deriveFont(Font.BOLD));
                }
                panelOpciones.add(labelOpcion);
            }
        } else if (item.getTipo() == TipoItem.Verdadero_Falso) {
            JLabel lblVF = new JLabel("Respuesta seleccionada: " + respuestaUsuario);
            panelOpciones.add(lblVF);
        }

        // Mostrar resultado correcto/incorrecto
        if (respuesta.esCorrecta()) {
            lblResultado.setText("Respuesta correcta");
            lblResultado.setForeground(new Color(0, 128, 0)); // verde
        } else {
            lblResultado.setText("Respuesta incorrecta");
            lblResultado.setForeground(Color.RED);
        }

        panelOpciones.revalidate();
        panelOpciones.repaint();

        // Actualizar índices de prueba
        prueba.setIndiceActual(indiceActual);
        // Actualiza índice actual en prueba
    }
}
