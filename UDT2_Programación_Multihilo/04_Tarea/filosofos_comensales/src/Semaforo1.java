
import java.util.concurrent.Semaphore;

class RecursoCompartido {
    private int valor;
    private final Semaphore semaforo = new Semaphore(1); // Semáforo binario

    public void establecerValor(int valor) {
        try {
            semaforo.acquire(); // Solicita acceso al recurso
            this.valor = valor;
            System.out.println("Valor establecido: " + valor);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Manejo de interrupciones
        } finally {
            semaforo.release(); // Libera el semáforo
        }
    }

    public int obtenerValor() {
        int val = 0;
        try {
            semaforo.acquire(); // Solicita acceso al recurso
            val = this.valor;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Manejo de interrupciones
        } finally {
            semaforo.release(); // Libera el semáforo
        }
        return val;
    }
}

class HiloEjemplo implements Runnable {
    private RecursoCompartido recurso;

    public HiloEjemplo(RecursoCompartido recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            recurso.establecerValor(i);
            int valorActual = recurso.obtenerValor(); 
            System.out.println("Valor actual: " + valorActual);
        }
    }
}

public class Semaforo1 {
    public static void main(String[] args) {
        RecursoCompartido recurso = new RecursoCompartido();
        Thread hilo1 = new Thread(new HiloEjemplo(recurso));
        Thread hilo2 = new Thread(new HiloEjemplo(recurso));
        
        hilo1.start();
        hilo2.start();
    }
}