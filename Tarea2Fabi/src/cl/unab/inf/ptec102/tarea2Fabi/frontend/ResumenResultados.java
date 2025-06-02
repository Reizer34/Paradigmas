package cl.unab.inf.ptec102.tarea2Fabi.frontend;

import cl.unab.inf.ptec102.tarea2Fabi.backend.Prueba;
import cl.unab.inf.ptec102.tarea2Fabi.backend.NivelBloom;
import cl.unab.inf.ptec102.tarea2Fabi.backend.TipoItem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResumenResultados extends JPanel {
    private Prueba prueba;
    private JTextArea resumenArea;
    private JButton btnRevisar;

    public ResumenResultados(Prueba prueba, Runnable onRevisar) {
        this.prueba = prueba;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Resumen de Resultados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        resumenArea = new JTextArea();
        resumenArea.setEditable(false);
        add(new JScrollPane(resumenArea), BorderLayout.CENTER);

        btnRevisar = new JButton("Revisar Respuestas");
        btnRevisar.addActionListener(e -> onRevisar.run());
        add(btnRevisar, BorderLayout.SOUTH);

        mostrarResumen();
    }

    private void mostrarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Porcentaje de respuestas correctas por nivel de Bloom:\n");
        for (Map.Entry<NivelBloom, Double> entry : prueba.resumenPorNivel().entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ")
                    .append(String.format("%.2f", entry.getValue())).append("%\n");
        }

        sb.append("\nPorcentaje de respuestas correctas por tipo de ítem:\n");
        for (Map.Entry<TipoItem, Double> entry : prueba.resumenPorTipo().entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ")
                    .append(String.format("%.2f", entry.getValue())).append("%\n");
        }

        resumenArea.setText(sb.toString());
    }
}
