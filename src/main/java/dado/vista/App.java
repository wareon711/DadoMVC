package dado.vista;

import dado.controlador.ControlDado;
import dado.modelo.ModeloDado;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeloDado modelo = new ModeloDado();
            ControlDado control = new ControlDado(modelo);
            JFrame frame = new JFrame("Dado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new VistaDado(modelo, control), BorderLayout.CENTER);
            frame.add(new VistaHistorial(modelo), BorderLayout.EAST);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
