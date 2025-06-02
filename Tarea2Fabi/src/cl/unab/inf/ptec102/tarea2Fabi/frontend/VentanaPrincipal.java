package cl.unab.inf.ptec102.tarea2Fabi.frontend;

import cl.unab.inf.ptec102.tarea2Fabi.backend.Prueba;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VentanaPrincipal extends JFrame {
    private CardLayout cardLayout;
    private JPanel contenedor;
    private Prueba prueba;

    public VentanaPrincipal() {
        setTitle("Sistema de Pruebas - Taxonomía de Bloom");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        JPanel panelInicio = new JPanel();
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        JLabel lblTitulo = new JLabel("Bienvenido al Sistema de Pruebas");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCargar = new JButton("Cargar CSV y Comenzar");
        btnCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCargar.addActionListener(e -> cargarPrueba());

        panelInicio.add(Box.createVerticalGlue());
        panelInicio.add(lblTitulo);
        panelInicio.add(Box.createRigidArea(new Dimension(0, 20)));
        panelInicio.add(btnCargar);
        panelInicio.add(Box.createVerticalGlue());

        contenedor.add(panelInicio, "inicio");
        add(contenedor);

        setVisible(true);
    }

    private void cargarPrueba() {
        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            prueba = new Prueba();
            try {
                prueba.cargarDesdeCSV(archivo.getAbsolutePath());
                VentanaPrueba panelPrueba = new VentanaPrueba(prueba, this);
                contenedor.add(panelPrueba, "prueba");
                cardLayout.show(contenedor, "prueba");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar archivo: " + ex.getMessage());
            }
        }
    }

    public void mostrarResumen() {
        ResumenResultados resumen = new ResumenResultados(prueba, () -> mostrarRevision());
        contenedor.add(resumen, "resumen");
        cardLayout.show(contenedor, "resumen");
    }



    public void volverAlInicio() {
        cardLayout.show(contenedor, "inicio");
    }

    public void mostrarRevision() {
        VentanaRevision revision = new VentanaRevision(prueba, this);
        contenedor.add(revision, "revision");
        cardLayout.show(contenedor, "revision");
    }

}
