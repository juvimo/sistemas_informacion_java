package sistemas_informacion.Programa;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelEntropia extends javax.swing.JPanel {
private JTextField txtCantidad;
    private JTextField txtProbabilidades;
    private JTextArea txtResultado;
    private JButton btnCalcular;

    public PanelEntropia() {
        initComponents();
        initCustomComponents();
    }
    private void initCustomComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        txtCantidad = new JTextField(4);
        txtProbabilidades = new JTextField(22);
        btnCalcular = new JButton("Calcular Entropía");

        panelSuperior.add(new JLabel("N° eventos (n):"));
        panelSuperior.add(txtCantidad);
        panelSuperior.add(new JLabel("Probabilidades (ej. 0.5, 0.5):"));
        panelSuperior.add(txtProbabilidades);
        panelSuperior.add(btnCalcular);

        txtResultado = new JTextArea(12, 40);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        btnCalcular.addActionListener(e -> calcularEntropia());
    }

    private void calcularEntropia() {
        txtResultado.setText("");
        try {
            int n = Integer.parseInt(txtCantidad.getText().trim());
            if (n <= 0) {
                txtResultado.setText("Error: el número de eventos debe ser mayor a 0.");
                return;
            }

            String[] partes = txtProbabilidades.getText().trim().replace(",", " ").split("\\s+");
            if (partes.length != n) {
                txtResultado.setText("Error: ingresaste " + partes.length + " valores y esperabas " + n + ".");
                return;
            }

            double[] probs = new double[n];
            double suma = 0.0;
            for (int i = 0; i < n; i++) {
                probs[i] = Double.parseDouble(partes[i]);
                if (probs[i] < 0 || probs[i] > 1) {
                    txtResultado.setText("Error: cada probabilidad debe estar entre 0 y 1.");
                    return;
                }
                suma += probs[i];
            }

            StringBuilder sb = new StringBuilder();
            if (Math.abs(suma - 1.0) > 0.001) {
                sb.append(String.format("Advertencia: la suma de probabilidades es %.4f (ideal: 1.0000)%n%n", suma));
            }

            double entropia = 0.0;
            for (int i = 0; i < n; i++) {
                double p = probs[i];
                double infoPropia = 0.0;
                double aporte = 0.0;
                if (p > 0) {
                    infoPropia = -(Math.log(p) / Math.log(2));
                    aporte = p * infoPropia;
                    entropia += aporte;
                }
                sb.append(String.format("x%-2d | P(x)=%.4f | I(x)=%.4f bits | Aporte=%.4f%n",
                        (i + 1), p, infoPropia, aporte));
            }

            sb.append("\n--------------------------------------------\n");
            sb.append(String.format("Suma total: %.4f%n", suma));
            sb.append(String.format("Entropía de Shannon H(X): %.4f bits%n", entropia));
            txtResultado.setText(sb.toString());

        } catch (NumberFormatException ex) {
            txtResultado.setText("Error: verifica que los números sean válidos (usa punto decimal, ej. 0.25).");
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
