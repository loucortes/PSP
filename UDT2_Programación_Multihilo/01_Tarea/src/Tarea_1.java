import java.util.ArrayList;

class MiRunnable implements Runnable {

    String nombre; // Los objetos de tipo MiRunnable tendrán una propiedad nombre

    // Constructores: Inicializan la propiedad nombre
    MiRunnable() {
        this.nombre = "Anónimo";
    }

    MiRunnable(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        // Este es el método que se ejeutará cuando
        // se invoque al método start del thread.

        try {
            // Tomamos la referencia al thread actual
            Thread hiloActual = Thread.currentThread();
            hiloActual.setName("Thread-" + this.nombre);

            // E imprimimos información sobre su nombre y algunas propiedades
            System.out.println("Hola Mundo de los threads. Soy " + this.nombre + ":"
                    + "\n\tMi id de thread es " + hiloActual.getId()
                    + "\n\tMi nombre de thread es " + hiloActual.getName()
                    + "\n\tMi prioridad es " + hiloActual.getPriority()
                    + "\n\tMi estado es " + hiloActual.getState() + "\n");

            // Realizamos una pausa de 1 segundo
            Thread.sleep(1000);

            // Imprimimos un mensaje de despedida
            System.out.println("Adiós, soy " + this.nombre + ". Me voy.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Tarea_1 {

    public static void main(String[] args) {
        try {
            ArrayList<Thread> hilos = new ArrayList<>();
            
            for (String name : args) {
                // Creamos algunos objetos de ejemplo
                MiRunnable runnable = new MiRunnable(name);
                // Y los hilos correspondientes
                Thread hilo = new Thread(runnable);
                hilos.add(hilo);
            }

            // Lanzamos los hilos
            for (Thread hilo : hilos) {
                hilo.start();
            }
            
            // Y los juntamos con el principal cuando acaben cuando acaban
            for (Thread thread : hilos) {
                thread.join();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}