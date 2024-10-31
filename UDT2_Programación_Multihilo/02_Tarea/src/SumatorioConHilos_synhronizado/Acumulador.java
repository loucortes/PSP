
class Acumulador {
    long acumulador = 0;

    Acumulador() { };

    long get() {
        return this.acumulador;
    }

    void set(long cantidad) {
        System.out.println("Actualizando contador a " + cantidad);
        this.acumulador = cantidad;
    }
}
