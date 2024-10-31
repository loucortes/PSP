import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


    

/*
 * La variable actual no está declarada explícitamente en tu código, 
 * pero es proporcionada automáticamente por el método updateAndGet() de la clase AtomicInteger. 
 * Este método toma el valor almacenado en el objeto AtomicInteger y lo pasa a la función lambda que le has proporcionado.
 * En términos más sencillos:
 * El método updateAndGet() obtiene el valor actual de AtomicInteger internamente.
 * Luego, ese valor se convierte en el argumento que la lambda recibe. En tu caso, la lambda es actual -> Math.max(actual, nuevoMaximo).
 * El valor actual es lo que la lambda trata como actual.
 */

class MaximoCompartido {
    private AtomicInteger maximo;

    public MaximoCompartido() {
        this.maximo = new AtomicInteger(Integer.MIN_VALUE);  // Inicializamos con el valor mínimo posible
    }

    // Método para actualizar el valor máximo de forma atómica
    public void setMaximo(int nuevoMaximo) {
        maximo.updateAndGet(actual -> Math.max(actual, nuevoMaximo)); // Actualización atómica
    }

    public int getMaximo() {
        return maximo.get();
    }
}


class TareaMaximo implements Runnable {
    private List<Integer> sublista;
    private MaximoCompartido maximoCompartido;

    public TareaMaximo(List<Integer> sublista, MaximoCompartido maximoCompartido) {
        this.sublista = sublista;
        this.maximoCompartido = maximoCompartido;
    }

    @Override
    public void run() {
        int maximoLocal = Integer.MIN_VALUE;
        // Calcular el máximo de la sublista
        for (int num : sublista) {
            if (num > maximoLocal) {
                maximoLocal = num;
            }
        }
        // Actualizar el máximo global de manera atómica
        maximoCompartido.setMaximo(maximoLocal);
    }
}

public class AtomicIntegerArgumentoMayor {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, proporciona una lista de números como argumento.");
            return;
        }

        // Convertir los argumentos en una lista de enteros
        List<Integer> listaNumeros = new ArrayList<>();
        for (String arg : args) {
            listaNumeros.add(Integer.parseInt(arg));
        }

        // Definir el número de hilos que vamos a usar
        int numHilos = 4; // Puedes ajustar este número según tu necesidad

        // Crear un objeto compartido para almacenar el máximo
        MaximoCompartido maximoCompartido = new MaximoCompartido();

        // Dividir la lista en sublistas
        int tamanioSublista = listaNumeros.size() / numHilos;
        List<Thread> hilos = new ArrayList<>();
        
        for (int i = 0; i < numHilos; i++) {
            int inicio = i * tamanioSublista;
            int fin = (i == numHilos - 1) ? listaNumeros.size() : inicio + tamanioSublista;  // Última sublista incluye el resto
            List<Integer> sublista = listaNumeros.subList(inicio, fin);
            
            // Crear una tarea para el hilo y lanzarlo
            Thread hilo = new Thread(new TareaMaximo(sublista, maximoCompartido));
            hilos.add(hilo);
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Imprimir el máximo calculado
        System.out.println("El valor máximo es: " + maximoCompartido.getMaximo());
    }
}

