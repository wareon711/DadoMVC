# Dado Virtual con MVC

Proyecto académico desarrollado para la materia de **Arquitectura de Software (Unidad I - ITSON)**. Consiste en la implementación de un simulador de dado virtual aplicando el patrón de arquitectura **Modelo-Vista-Controlador (MVC)**, el patrón de diseño **Observer propio** y el principio de **Segregación de Interfaces (ISP)** de SOLID.

---

## Características y Reglas

- **Generación de valores:** Rango del 1 al 6.
- **Regla del Parchís:**
  - Al obtener un **6**, el jugador puede volver a tirar en el mismo turno, hasta un **máximo de 3 veces consecutivas**.
  - Si sale cualquier otro número (1 al 5) o se alcanza el límite de tres 6 seguidos, el botón de lanzamiento se deshabilita automáticamente.
- **Gestión de turnos:** Botón **«Nuevo turno»** que reinicia el contador de tiros y habilita de nuevo la posibilidad de lanzar.
- **Dos vistas simultáneas del mismo modelo:**
  1. **Vista del Dado:** Dibuja el dado en tiempo real utilizando `Graphics2D` (con esquinas redondeadas, antialiasing y puntos rojos al sacar 6).
  2. **Vista del Historial:** Registra cronológicamente cada tiro efectuado en un panel con scroll.

---

## Decisiones de Arquitectura

1. **Separación MVC Estricta:**
   - **Modelo (`dado.modelo`):** Contiene exclusivamente la lógica de negocio, estado del dado y notificaciones. No depende de bibliotecas gráficas (`javax.swing` ni `java.awt`).
   - **Controlador (`dado.controlador`):** Recibe las acciones del usuario y delega las operaciones al modelo.
   - **Vistas (`dado.vista`):** Implementan `ObservadorDado` y se actualizan de forma pasiva en respuesta a los cambios del modelo.

2. **Segregación de Interfaces (ISP):**
   - El controlador solo conoce `IModeloDado`, exponiendo únicamente métodos mutadores (`lanzar()`, `reiniciarTurno()`).
   - Las vistas solo conocen `IModeloDadoLectura`, exponiendo únicamente métodos de consulta y suscripción de observadores.

3. **Patrón Observer Propio:**
   - Implementado a través de la interfaz `ObservadorDado`, permitiendo que múltiples vistas observen y reaccionen al mismo modelo sin acoplamiento.

---

## Estructura del Proyecto

```text
DadoMVC/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/
    │   └── java/
    │       └── dado/
    │           ├── controlador/
    │           │   └── ControlDado.java
    │           ├── modelo/
    │           │   ├── IModeloDado.java
    │           │   ├── IModeloDadoLectura.java
    │           │   ├── ModeloDado.java
    │           │   └── ObservadorDado.java
    │           └── vista/
    │               ├── App.java
    │               ├── PanelDibujoDado.java
    │               ├── VistaDado.java
    │               └── VistaHistorial.java
    └── test/
        └── java/
            └── dado/
                ├── controlador/
                │   └── ControlDadoTest.java
                └── modelo/
                    └── ModeloDadoTest.java
```

---

## Pruebas Unitarias (JUnit 5)

El proyecto cuenta con cobertura de pruebas unitarias sobre la lógica del modelo y la delegación del controlador:

- Estado inicial del modelo y valores por defecto.
- Validación de rango de valores generados `[1, 6]`.
- Bloqueo de tiro tras sacar un número diferente de 6.
- Habilitación de repetición de tiro al obtener 6.
- Límite estricto de máximo 3 seis consecutivos.
- Restauración del turno al invocar `reiniciarTurno()`.
- Registro cronológico en el historial e inmutabilidad de la lista retornada.
- Registro, notificación y eliminación de observadores.
- Delegación correcta del controlador hacia el modelo.

---

## Ejecución del Proyecto

### Requisitos
- **Java JDK:** 11 o superior.
- **Apache Maven** o **Apache NetBeans IDE**.

### Opciones de ejecución

- **Desde Apache NetBeans:**
  1. Abrir NetBeans: `File` → `Open Project...` y seleccionar la carpeta del proyecto.
  2. Para ejecutar la aplicación: Clic derecho en el proyecto → `Run` (o presionar `F6`).
  3. Para ejecutar las pruebas unitarias: Clic derecho en el proyecto → `Test` (o presionar `Alt + F6`).

- **Desde la terminal (Maven):**
  - Compilar y ejecutar:
    ```bash
    mvn clean compile exec:java
    ```
  - Ejecutar las pruebas unitarias:
    ```bash
    mvn test
    ```