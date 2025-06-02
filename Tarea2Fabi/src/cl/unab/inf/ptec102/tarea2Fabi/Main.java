package cl.unab.inf.ptec102.tarea2Fabi;

import cl.unab.inf.ptec102.tarea2Fabi.frontend.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configura el look and feel del sistema operativo (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Crea y muestra la ventana principal en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aplicación de Pruebas - Taxonomía de Bloom");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // Tamaño ajustable
            frame.setLocationRelativeTo(null); // Centrar la ventana
            frame.setContentPane(new VentanaPrincipal()); // Agrega el panel principal
            frame.setVisible(true);
        });
    }
}
