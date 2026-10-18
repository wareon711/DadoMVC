package dado.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelDibujoDado extends JPanel {

    private static final int[][][] PUNTOS = {
        {},
        {{1, 1}},
        {{0, 0}, {2, 2}},
        {{0, 0}, {1, 1}, {2, 2}},
        {{0, 0}, {2, 0}, {0, 2}, {2, 2}},
        {{0, 0}, {2, 0}, {1, 1}, {0, 2}, {2, 2}},
        {{0, 0}, {2, 0}, {0, 1}, {2, 1}, {0, 2}, {2, 2}}
    };

    private int valor;

    public PanelDibujoDado() {
        setPreferredSize(new Dimension(180, 180));
    }

    public void setValor(int valor) {
        this.valor = valor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int lado = Math.min(getWidth(), getHeight()) - 20;
        int x = (getWidth() - lado) / 2;
        int y = (getHeight() - lado) / 2;
        int arco = lado / 5;

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, lado, lado, arco, arco);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(x, y, lado, lado, arco, arco);

        int diametro = lado / 6;
        int margen = lado / 6;
        int paso = (lado - 2 * margen) / 2;
        g2.setColor(valor == 6 ? new Color(190, 40, 40) : Color.BLACK);
        for (int[] p : PUNTOS[Math.max(0, Math.min(6, valor))]) {
            g2.fillOval(x + margen + p[0] * paso - diametro / 2,
                        y + margen + p[1] * paso - diametro / 2,
                        diametro, diametro);
        }
        g2.dispose();
    }
}
