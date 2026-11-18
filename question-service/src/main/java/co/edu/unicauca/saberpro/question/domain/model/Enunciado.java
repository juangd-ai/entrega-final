package co.edu.unicauca.saberpro.question.domain.model;

import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;

/** Value Object inmutable. */
public final class Enunciado {
    private final String texto;

    public Enunciado(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new ReglaNegocioException("El enunciado no puede ser nulo ni vacío");
        }
        this.texto = texto.trim();
    }

    public String getTexto() { return texto; }

    @Override public boolean equals(Object o) {
        return o instanceof Enunciado e && e.texto.equals(texto);
    }
    @Override public int hashCode() { return texto.hashCode(); }
    @Override public String toString() { return texto; }
}
