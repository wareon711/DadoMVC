package dado.vista;

import dado.controlador.ControlDado;
import dado.modelo.IModeloDadoLectura;
import dado.modelo.ObservadorDado;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VistaDado extends JPanel
        implements ObservadorDado {
    private final JLabel lblValor = new JLabel("-");
    private final JButton btnLanzar = new JButton("Lanzar");
    private final JButton btnNuevoTurno = new JButton("Nuevo turno");
    private final PanelDibujoDado dibujo = new PanelDibujoDado();

    public VistaDado(IModeloDadoLectura modelo,
                     ControlDado control) {
        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(230, 300));
        modelo.agregarObservador(this);
        btnLanzar.addActionListener(e -> control.lanzar());
        btnNuevoTurno.addActionListener(e -> control.nuevoTurno());

        JPanel panelControles = new JPanel();
        panelControles.add(lblValor);
        panelControles.add(btnLanzar);
        panelControles.add(btnNuevoTurno);

        add(dibujo, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);
    }

    @Override
    public void actualizar(IModeloDadoLectura modelo) {
        SwingUtilities.invokeLater(() -> {
            lblValor.setText(String.valueOf(modelo.getValor()));
            btnLanzar.setEnabled(modelo.puedeTirar());
            dibujo.setValor(modelo.getValor());
        });
    }
}
