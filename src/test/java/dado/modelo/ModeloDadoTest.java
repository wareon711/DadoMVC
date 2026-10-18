package dado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModeloDadoTest {

    static class SecuenciaRandom extends Random {
        private final int[] valores;
        private int indice = 0;

        SecuenciaRandom(int... valores) {
            this.valores = valores;
        }

        @Override
        public int nextInt(int bound) {
            int val = valores[indice % valores.length];
            indice++;
            return val - 1;
        }
    }

    private ModeloDado modelo;

    @BeforeEach
    void setUp() {
        modelo = new ModeloDado();
    }

    @Test
    void estadoInicial() {
        assertEquals(0, modelo.getValor());
        assertEquals(0, modelo.getSeisSeguidos());
        assertTrue(modelo.puedeTirar());
        assertTrue(modelo.getHistorial().isEmpty());
    }

    @Test
    void valoresEntreUnoYSeis() {
        for (int i = 0; i < 50; i++) {
            modelo.reiniciarTurno();
            modelo.lanzar();
            int v = modelo.getValor();
            assertTrue(v >= 1 && v <= 6);
        }
    }

    @Test
    void tiroSinSeisNoPermiteVolverATirar() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(4));
        m.lanzar();
        assertEquals(4, m.getValor());
        assertEquals(0, m.getSeisSeguidos());
        assertFalse(m.puedeTirar());

        m.lanzar();
        assertEquals(4, m.getValor());
        assertEquals(1, m.getHistorial().size());
    }

    @Test
    void tiroConSeisPermiteVolverATirar() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(6, 3));
        m.lanzar();
        assertEquals(6, m.getValor());
        assertEquals(1, m.getSeisSeguidos());
        assertTrue(m.puedeTirar());

        m.lanzar();
        assertEquals(3, m.getValor());
        assertEquals(0, m.getSeisSeguidos());
        assertFalse(m.puedeTirar());
    }

    @Test
    void maximoTresSeisSeguidos() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(6, 6, 6, 6));

        m.lanzar();
        assertEquals(1, m.getSeisSeguidos());
        assertTrue(m.puedeTirar());

        m.lanzar();
        assertEquals(2, m.getSeisSeguidos());
        assertTrue(m.puedeTirar());

        m.lanzar();
        assertEquals(3, m.getSeisSeguidos());
        assertFalse(m.puedeTirar());

        m.lanzar();
        assertEquals(3, m.getHistorial().size());
        assertEquals(3, m.getSeisSeguidos());
    }

    @Test
    void reiniciarTurnoRestauraPuedeTirar() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(2, 5));
        m.lanzar();
        assertFalse(m.puedeTirar());

        m.reiniciarTurno();
        assertTrue(m.puedeTirar());
        assertEquals(0, m.getSeisSeguidos());

        m.lanzar();
        assertEquals(5, m.getValor());
        assertEquals(2, m.getHistorial().size());
    }

    @Test
    void historialRegistraTodosLosTiros() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(6, 6, 2));
        m.lanzar();
        m.lanzar();
        m.lanzar();

        List<Integer> esperado = List.of(6, 6, 2);
        assertEquals(esperado, m.getHistorial());
    }

    @Test
    void historialEsInmutable() {
        ModeloDado m = new ModeloDado(new SecuenciaRandom(3));
        m.lanzar();
        List<Integer> h = m.getHistorial();
        assertThrows(UnsupportedOperationException.class, () -> h.add(99));
    }

    @Test
    void observadorEsNotificadoEnLanzarYReiniciar() {
        List<Integer> notificaciones = new ArrayList<>();
        ObservadorDado obs = mod -> notificaciones.add(mod.getValor());

        modelo.agregarObservador(obs);
        modelo.lanzar();
        assertEquals(1, notificaciones.size());

        modelo.reiniciarTurno();
        assertEquals(2, notificaciones.size());

        modelo.eliminarObservador(obs);
        modelo.lanzar();
        assertEquals(2, notificaciones.size());
    }
}
