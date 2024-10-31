
	public class HiloEstados {

	    public static void main(String[] args) {
	        // Creamos un nuevo hilo utilizando una lambda
	        Thread miThread = new Thread(() -> {
	            try {
	                // Simulamos trabajo del hilo con un pequeño sleep
	                System.out.println("Estado del hilo dentro de run(): " + Thread.currentThread().getState());
	                Thread.sleep(2000); // El hilo duerme por 2 segundos
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        });

	        // Mostrar el estado del hilo antes de llamar a start()
	        System.out.println("Estado del hilo antes de start(): " + miThread.getState());

	        // Lanzamos el hilo
	        miThread.start();

	        // Mostrar el estado justo después de llamar a start()
	        System.out.println("Estado del hilo después de start(): " + miThread.getState());

	        try {
	            // Esperar un momento para dar tiempo al hilo de empezar a ejecutar
	            Thread.sleep(100); 
	            // Mostrar el estado mientras el hilo está posiblemente ejecutándose o terminando
	            System.out.println("Estado del hilo mientras se ejecuta (posible RUNNABLE): " + miThread.getState());

	            // Esperar a que el hilo termine
	            miThread.join(); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Mostrar el estado final del hilo (TERMINATED)
	        System.out.println("Estado del hilo después de que ha terminado: " + miThread.getState());
	    }
	}


