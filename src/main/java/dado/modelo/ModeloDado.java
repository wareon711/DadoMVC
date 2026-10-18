package dado.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ModeloDado
        implements IModeloDado, IModeloDadoLectura {
    private final Random random;
    private int valor;
    private int seisSeguidos;
    private boolean puedeTirar = true;
    private final List<ObservadorDado> observadores
            = new ArrayList<>();
    private final List<Integer> historial = new ArrayList<>();

    public ModeloDado() {
        this(new Random());
    }

    public ModeloDado(Random random) {
        this.random = random;
    }

    @Override
    public void lanzar() {
        if (!puedeTirar) return;
        valor = random.nextInt(6) + 1;
        seisSeguidos = (valor == 6) ? seisSeguidos + 1 : 0;
        puedeTirar = valor == 6 && seisSeguidos < 3;
        historial.add(valor);
        notificar();
    }

    @Override
    public void reiniciarTurno() {
        seisSeguidos = 0;
        puedeTirar = true;
        notificar();
    }

    @Override public int getValor() { return valor; }
    @Override public int getSeisSeguidos() { return seisSeguidos; }
    @Override public boolean puedeTirar() { return puedeTirar; }

    @Override
    public List<Integer> getHistorial() {
        return Collections.unmodifiableList(new ArrayList<>(historial));
    }

    @Override
    public void agregarObservador(ObservadorDado o) {
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(ObservadorDado o) {
        observadores.remove(o);
    }

    protected void notificar() {
        for (ObservadorDado o : observadores) {
            o.actualizar(this);
        }
    }
}
