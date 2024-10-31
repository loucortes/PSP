import java.util.concurrent.Semaphore;

class Filosofo implements Runnable {
    private int id;
    private Semaphore tenedorIzquierdo;
    private Semaphore tenedorDerecho;

    public Filosofo(int id, Semaphore tenedorIzquierdo, Semaphore tenedorDerecho) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
    }

    @Override
    public void run() {
        while (true) {
            pensar();
            comer();
        }
    }

    private void pensar() {
        System.out.println("Filósofo " + id + " está pensando.");
        try {
            Thread.sleep((long) (Math.random() * 1000)); // Simula el tiempo de pensar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() {
        try {
            // Intenta recoger los tenedores
            tenedorIzquierdo.acquire();
            tenedorDerecho.acquire();

            System.out.println("Filósofo " + id + " está comiendo.");

            // Simula el tiempo de comer
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Libera los tenedores
            tenedorDerecho.release();
            tenedorIzquierdo.release();
            System.out.println("Filósofo " + id + " ha terminado de comer.");
        }
    }
}

public class FilosofoComensal {
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        Semaphore[] tenedores = new Semaphore[NUM_FILOSOFOS];

        // Inicializa los tenedores
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            tenedores[i] = new Semaphore(1); // Semáforo binario para cada tenedor
        }

        // Crea y lanza los filósofos
        Thread[] filosofos = new Thread[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Thread(new Filosofo(i, tenedores[i], tenedores[(i + 1) % NUM_FILOSOFOS]));
            filosofos[i].start();
        }

        // Espera a que todos los filósofos terminen (en este caso nunca terminarán)
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            try {
                filosofos[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}