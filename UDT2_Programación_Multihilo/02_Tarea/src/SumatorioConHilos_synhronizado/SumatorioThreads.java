

public class SumatorioThreads {
    public static void main(String[] args) {

        // Capturamos los parámetros
        Long index1 = Long.parseLong(args[0]);
        Long index2 = Long.parseLong(args[1]);

        // Creamos el acumulador
        Acumulador ac = new Acumulador();

        try {
            // Ordenamos los índices, por si el primero es mayor que el segundo
            if (index1 > index2) {
                Long tmp = index1;
                index1 = index2;
                index2 = tmp;
            }

            // Particionem el rango de valores en tantos rangos como procesadores tenemos
            // Calculamos primero la cantidad de procesadores
            Runtime runtime = Runtime.getRuntime();
            int num_procesadores = runtime.availableProcessors();
            System.out.println("Dividiendo la tarea " + num_procesadores + " hilos");

            // Obtención de los rangos
            Long incremento = ((index2 - index1) / (num_procesadores - 1));

            // Creación del vector de hilos
            Thread vectorHilos[] = new Thread[num_procesadores];

            for (int i = 0; i < num_procesadores; i++) {

                // Creamos un objeto de tipo Suma, que es threadable
                long ini = index1 + incremento * i;
                long fin = index1 + incremento * (i + 1) - 1;
                if (fin > index2)
                    fin = index2;
                Suma sumParcial = new Suma(ini, fin, ac);

                // Y creamos y lanzamos el hilo
                vectorHilos[i] = new Thread(sumParcial);
                vectorHilos[i].setName("Hilo " + i);
                vectorHilos[i].start();
            }

            for (int i = 0; i < num_procesadores; i++)
                vectorHilos[i].join();

            System.out.println("Suma total: " + ac.get());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
