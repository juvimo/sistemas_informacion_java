package sistemas_informacion.Programa;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelReedSolomon extends javax.swing.JPanel {
    private JTextField txtA;
    private JTextField txtB;
    private JTextField txtC;
    private JComboBox<String> cmbEliminada;
    private JTextArea txtResultado;
    private JButton btnCalcular;
    public PanelReedSolomon() {
        initComponents();
        initCustomComponents();
    }
    private void initCustomComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        txtA = new JTextField(6);
        txtB = new JTextField(6);
        txtC = new JTextField(6);
        cmbEliminada = new JComboBox<>(new String[]{"A", "B", "C"});
        btnCalcular = new JButton("Codificar y Recuperar");

        panelSuperior.add(new JLabel("A:"));
        panelSuperior.add(txtA);
        panelSuperior.add(new JLabel("B:"));
        panelSuperior.add(txtB);
        panelSuperior.add(new JLabel("C:"));
        panelSuperior.add(txtC);
        panelSuperior.add(new JLabel("Variable eliminada:"));
        panelSuperior.add(cmbEliminada);
        panelSuperior.add(btnCalcular);

        txtResultado = new JTextArea(12, 40);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        btnCalcular.addActionListener(e -> procesarReedSolomon());
    }

    private void procesarReedSolomon() {
        txtResultado.setText("");
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            double c = Double.parseDouble(txtC.getText().trim());

            double d = (2 * a) + b + (3 * c);
            double e = a + (5 * b) + (4 * c);

            StringBuilder sb = new StringBuilder();
            sb.append("CODIFICACIÓN RED-SOLOMON\n");
            sb.append(String.format("Mensaje original  -> A: %.2f | B: %.2f | C: %.2f%n", a, b, c));
            sb.append(String.format("Redundancia       -> D: %.2f | E: %.2f%n", d, e));
            sb.append(String.format("Mensaje completo  -> A: %.2f | B: %.2f | C: %.2f | D: %.2f | E: %.2f%n%n", a, b, c, d, e));

            String eliminada = (String) cmbEliminada.getSelectedItem();
            sb.append("--- RECUPERACIÓN DE DATOS ---\n");

            switch (eliminada) {
                case "A":
                    double aRecuperado = (d - b - (3 * c)) / 2.0;
                    sb.append("Variable 'A' eliminada.\n");
                    sb.append(String.format("Valor original:   %.4f%n", a));
                    sb.append(String.format("Valor recuperado: %.4f%n", aRecuperado));
                    break;
                case "B":
                    double bRecuperado = d - (2 * a) - (3 * c);
                    sb.append("Variable 'B' eliminada.\n");
                    sb.append(String.format("Valor original:   %.4f%n", b));
                    sb.append(String.format("Valor recuperado: %.4f%n", bRecuperado));
                    break;
                case "C":
                    double cRecuperado = (d - (2 * a) - b) / 3.0;
                    sb.append("Variable 'C' eliminada.\n");
                    sb.append(String.format("Valor original:   %.4f%n", c));
                    sb.append(String.format("Valor recuperado: %.4f%n", cRecuperado));
                    break;
            }

            txtResultado.setText(sb.toString());

        } catch (NumberFormatException ex) {
            txtResultado.setText("Error: ingresa valores numéricos válidos para A, B y C.");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
