
# Ciclo de vida de un hilo en Java

- **NEW**: El hilo es creado pero aún no ha iniciado.
- **RUNNABLE**: Después de invocar `start()`, el hilo está listo para ejecutarse y puede alternar entre este estado y el estado **RUNNING** dependiendo de la disponibilidad de la CPU.
- **TIMED_WAITING**: Cuando el hilo está dormido (en este caso, usando `Thread.sleep()`), indicando que está en espera durante un tiempo específico.
- **TERMINATED**: Una vez que el hilo completa su ejecución.

El método `getState()` permite monitorear el estado de un hilo en tiempo real, lo cual es útil para depuración y gestión de la ejecución.

El método `join()` asegura que el hilo principal espere a que otro hilo específico termine antes de continuar, lo que resulta fundamental para sincronizar tareas y evitar problemas de concurrencia.

En conjunto, `join()` y `getState()` son herramientas importantes para manejar el flujo y la coordinación de hilos de manera efectiva en aplicaciones concurrentes.
