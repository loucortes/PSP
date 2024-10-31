import java.util.concurrent.*;
import java.util.ArrayList;

public class SumatorioFutureTask {
    public static void main(String[] args) {

        // Capturamos los parámetros
        Long index1 = Long.parseLong(args[0]);
        Long index2 = Long.parseLong(args[1]);

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

            // Creación del vector de sumas parciales (FutureTask)
            ArrayList<FutureTask<Long>> sumaFuture=new ArrayList<FutureTask<Long>>();

            // Creación del vector de hilos
            Thread vectorHilos[] = new Thread[num_procesadores];

            for (int i = 0; i < num_procesadores; i++) {

                // Creamos un objeto de tipo Suma, que es threadable
                long ini = index1 + incremento * i;
                long fin = (i == num_procesadores - 1) ? index2 : ini + incremento - 1;
                
                // Creamos ahora suma, que es Callable
                Suma sumaParcial = new Suma(ini, fin);

                // Y el FutureTask que la encapsula para que sea Runnable
                sumaFuture.add(new FutureTask<Long>(sumaParcial));

                // Y creamos y lanzamos el hilo a partir de l futureTask y lo lanzamos
                vectorHilos[i] = new Thread(sumaFuture.get(i));
                vectorHilos[i].setName("Hilo " + i);
                vectorHilos[i].start();
            }


            // Esperamos que terminen todos, y vamos añadiendo su valor de 
            // retorno a un acumulador
            long ac=0;
            
            for (int i = 0; i < num_procesadores; i++){
                vectorHilos[i].join();
                ac=ac+sumaFuture.get(i).get();
            }

            System.out.println("Suma total: " + ac);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}



