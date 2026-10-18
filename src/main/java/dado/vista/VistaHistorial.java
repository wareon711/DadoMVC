package dado.vista;

import dado.modelo.IModeloDadoLectura;
import dado.modelo.ObservadorDado;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class VistaHistorial extends JPanel
        implements ObservadorDado {
    private final JTextArea area = new JTextArea();

    public VistaHistorial(IModeloDadoLectura modelo) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Historial de tiros"));
        setPreferredSize(new Dimension(200, 300));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);
        modelo.agregarObservador(this);
    }

    @Override
    public void actualizar(IModeloDadoLectura modelo) {
        SwingUtilities.invokeLater(() -> {
            List<Integer> tiros = modelo.getHistorial();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tiros.size(); i++) {
                sb.append("Tiro ").append(i + 1).append(": ").append(tiros.get(i)).append('\n');
            }
            area.setText(sb.toString());
            area.setCaretPosition(area.getDocument().getLength());
        });
    }
}
