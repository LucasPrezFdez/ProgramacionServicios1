package examenB.ej4B;

import java.io.Serializable;

public class Casa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numHabitaciones;
    private int numBanos;
    private boolean tieneTrastero;

    public Casa(int habitaciones, int banos, boolean trastero) {
        this.numHabitaciones = habitaciones;
        this.numBanos = banos;
        this.tieneTrastero = trastero;
    }

    public int getNumHabitaciones() { return numHabitaciones; }
    public int getNumBanos() { return numBanos; }
    public boolean tieneTrastero() { return tieneTrastero; }
}