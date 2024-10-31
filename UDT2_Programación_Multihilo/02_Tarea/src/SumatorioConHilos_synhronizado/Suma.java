
class Suma implements Runnable {

    long n1;
    long n2;
    Acumulador ac;

    // Constructores
    Suma() {
        this.n1 = 0;
        this.n2 = 0;
    }

    Suma(long n1, long n2, Acumulador ac) {
        this.n1 = n1;
        this.n2 = n2;
        this.ac = ac;
    }

    @Override
    public void run() {
        long result = 0;

        try {
            Thread hiloActual = Thread.currentThread();
            System.out.println("Iniciando el hilo " + hiloActual.getName() + ": Suma (" + this.n1 + "," + this.n2 + ")");

            for (long y = this.n1; y <= this.n2; y++) {
                result = result + y;
            }

            synchronized(ac){
                long acTmp = ac.get();

                // Añadimos una pausa para simular mayor carga en los cálculos
                Thread.sleep(500); // Utilizamos la versión estática de sleep
                System.out.println("Finalizado el hilo " + hiloActual.getName());
                System.out.println("Resultado del hilo: " + result);
                ac.set(acTmp + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
