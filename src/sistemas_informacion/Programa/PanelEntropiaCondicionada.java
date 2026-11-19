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

public class PanelEntropiaCondicionada extends javax.swing.JPanel {

  private JTextField txtFilas;
    private JTextField txtColumnas;
    private JTextField txtMatriz;
    private JTextArea txtResultado;
    private JButton btnCalcular;
    public PanelEntropiaCondicionada() {
        initComponents();
        initCustomComponents();
    }
    private void initCustomComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        txtFilas = new JTextField(3);
        txtColumnas = new JTextField(3);
        txtMatriz = new JTextField(24);
        btnCalcular = new JButton("Calcular H(X,Y) y H(Y|X)");

        panelSuperior.add(new JLabel("Filas (X):"));
        panelSuperior.add(txtFilas);
        panelSuperior.add(new JLabel("Cols (Y):"));
        panelSuperior.add(txtColumnas);
        panelSuperior.add(new JLabel("Matriz (filas con ';', cols con ','):"));
        panelSuperior.add(txtMatriz);
        panelSuperior.add(btnCalcular);

        txtResultado = new JTextArea(12, 40);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        btnCalcular.addActionListener(e -> calcularConjuntaYCondicional());
    }

    private void calcularConjuntaYCondicional() {
        txtResultado.setText("");
        try {
            int filas = Integer.parseInt(txtFilas.getText().trim());
            int columnas = Integer.parseInt(txtColumnas.getText().trim());

            if (filas <= 0 || columnas <= 0) {
                txtResultado.setText("Error: las dimensiones deben ser mayores a 0.");
                return;
            }

            String[] filasTexto = txtMatriz.getText().trim().split("\\s*;\\s*");
            if (filasTexto.length != filas) {
                txtResultado.setText("Error: se esperaban " + filas + " filas separadas por ';'.");
                return;
            }

            double[][] pxy = new double[filas][columnas];
            double sumaTotal = 0.0;

            for (int i = 0; i < filas; i++) {
                String[] colsTexto = filasTexto[i].trim().replace(",", " ").split("\\s+");
                if (colsTexto.length != columnas) {
                    txtResultado.setText("Error: la fila " + (i + 1) + " debe tener " + columnas + " valores.");
                    return;
                }
                for (int j = 0; j < columnas; j++) {
                    pxy[i][j] = Double.parseDouble(colsTexto[j]);
                    if (pxy[i][j] < 0 || pxy[i][j] > 1) {
                        txtResultado.setText("Error: las probabilidades deben estar entre 0 y 1.");
                        return;
                    }
                    sumaTotal += pxy[i][j];
                }
            }

            double[] px = new double[filas];
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    px[i] += pxy[i][j];
                }
            }

            double hxy = 0.0;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    double p = pxy[i][j];
                    if (p > 0) {
                        hxy -= p * (Math.log(p) / Math.log(2));
                    }
                }
            }

            double hyx = 0.0;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    double pJoint = pxy[i][j];
                    if (pJoint > 0 && px[i] > 0) {
                        double pCond = pJoint / px[i];
                        hyx -= pJoint * (Math.log(pCond) / Math.log(2));
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            if (Math.abs(sumaTotal - 1.0) > 0.001) {
                sb.append(String.format("Advertencia: la suma total de la matriz es %.4f (ideal: 1.0000)%n%n", sumaTotal));
            }
            sb.append(String.format("Entropía Conjunta H(X,Y):    %.4f bits%n", hxy));
            sb.append(String.format("Entropía Condicional H(Y|X): %.4f bits%n", hyx));
            txtResultado.setText(sb.toString());

        } catch (NumberFormatException ex) {
            txtResultado.setText("Error: verifica los números ingresados en la matriz.");
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
