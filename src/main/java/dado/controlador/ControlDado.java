package dado.controlador;

import dado.modelo.IModeloDado;

public class ControlDado {
    private final IModeloDado modelo;

    public ControlDado(IModeloDado modelo) {
        this.modelo = modelo;
    }

    public void lanzar() { modelo.lanzar(); }

    public void nuevoTurno() { modelo.reiniciarTurno(); }
}
