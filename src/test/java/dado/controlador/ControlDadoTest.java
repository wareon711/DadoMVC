package dado.controlador;

import dado.modelo.IModeloDado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ControlDadoTest {

    static class ModeloEspia implements IModeloDado {
        int llamadasLanzar = 0;
        int llamadasReiniciar = 0;

        @Override
        public void lanzar() {
            llamadasLanzar++;
        }

        @Override
        public void reiniciarTurno() {
            llamadasReiniciar++;
        }
    }

    @Test
    void delegacionCorrectaAlModelo() {
        ModeloEspia espia = new ModeloEspia();
        ControlDado control = new ControlDado(espia);

        control.lanzar();
        assertEquals(1, espia.llamadasLanzar);
        assertEquals(0, espia.llamadasReiniciar);

        control.nuevoTurno();
        assertEquals(1, espia.llamadasLanzar);
        assertEquals(1, espia.llamadasReiniciar);
    }
}
