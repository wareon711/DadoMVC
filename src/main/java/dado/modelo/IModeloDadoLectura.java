package dado.modelo;

import java.util.List;

public interface IModeloDadoLectura {
    int getValor();
    int getSeisSeguidos();
    boolean puedeTirar();
    void agregarObservador(ObservadorDado o);
    void eliminarObservador(ObservadorDado o);
    List<Integer> getHistorial();
}
