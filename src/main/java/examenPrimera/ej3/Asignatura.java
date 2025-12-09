package examenPrimera.ej3;

import java.io.Serializable;

public class Asignatura implements Serializable {

    String nombre;
    int curso;
    double nota;

    public Asignatura(String nombre, int curso) {
        this.nombre = nombre;
        this.curso = curso;
    }

    public void setNota(double nota) {
        if (nota >= 0 && nota <= 10) {
            this.nota = nota;
        }
    }
}
