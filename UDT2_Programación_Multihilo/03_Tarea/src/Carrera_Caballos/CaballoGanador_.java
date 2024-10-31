import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

class Caballo extends Thread {
    private String nombre;
    private static final int META = 100;
    private static AtomicBoolean carreraTerminada = new AtomicBoolean(false);  // Para detectar el ganador de forma segura

    public Caballo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        Random random = new Random();
        int avance = 0;

        while (avance < META) {
            // Simular avance del caballo
            avance += random.nextInt(10);  // El caballo avanza un número aleatorio entre 0 y 9
            System.out.println(nombre + " ha avanzado hasta: " + avance);
            
            try {
                Thread.sleep(random.nextInt(500));  // Simular tiempo de espera para el siguiente avance
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Verificar si el caballo ha llegado a la meta
            if (avance >= META && carreraTerminada.compareAndSet(false, true)) {
                System.out.println("¡" + nombre + " ha ganado la carrera!");
            }
        }
    }
}
public class CaballoGanador_ {
    public static void main(String[] args) {
        // Crear los hilos (caballos)
        Caballo[] caballos = new Caballo[5];
        for (int i = 0; i < caballos.length; i++) {
            caballos[i] = new Caballo("Caballo " + (i + 1));
        }

        // Iniciar la carrera
        System.out.println("¡Empieza la carrera de caballos!");

        for (Caballo caballo : caballos) {
            caballo.start();  // Iniciar cada hilo (caballo)
        }

        // Esperar a que todos los caballos terminen
        for (Caballo caballo : caballos) {
            try {
                caballo.join();  // Esperar a que cada hilo termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("La carrera ha terminado.");
    }
}
