package co.edu.unicauca.saberpro.question.domain.model;

import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;

/** Value Object inmutable: texto + indicador de clave correcta. */
public final class OpcionRespuesta {
    private final String texto;
    private final boolean correcta;

    public OpcionRespuesta(String texto, boolean correcta) {
        if (texto == null || texto.isBlank()) {
            throw new ReglaNegocioException("El texto de la opción no puede estar vacío");
        }
        this.texto = texto.trim();
        this.correcta = correcta;
    }

    public String getTexto() { return texto; }
    public boolean isCorrecta() { return correcta; }

    @Override public boolean equals(Object o) {
        return o instanceof OpcionRespuesta op && op.correcta == correcta && op.texto.equals(texto);
    }
    @Override public int hashCode() { return texto.hashCode() * 31 + (correcta ? 1 : 0); }
}
