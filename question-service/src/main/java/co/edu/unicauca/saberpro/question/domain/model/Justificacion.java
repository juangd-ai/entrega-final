package co.edu.unicauca.saberpro.question.domain.model;

import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;

/** Value Object inmutable: mínimo 15 caracteres. */
public final class Justificacion {
    public static final int LONGITUD_MINIMA = 15;
    private final String texto;

    public Justificacion(String texto) {
        if (texto == null || texto.trim().length() < LONGITUD_MINIMA) {
            throw new ReglaNegocioException(
                "La justificación debe tener al menos " + LONGITUD_MINIMA + " caracteres");
        }
        this.texto = texto.trim();
    }

    public String getTexto() { return texto; }

    @Override public boolean equals(Object o) {
        return o instanceof Justificacion j && j.texto.equals(texto);
    }
    @Override public int hashCode() { return texto.hashCode(); }
    @Override public String toString() { return texto; }
}
