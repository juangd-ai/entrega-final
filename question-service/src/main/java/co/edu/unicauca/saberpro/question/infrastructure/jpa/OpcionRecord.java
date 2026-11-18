package co.edu.unicauca.saberpro.question.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OpcionRecord {
    @Column(name = "texto", length = 1000, nullable = false)
    private String texto;

    @Column(name = "correcta", nullable = false)
    private boolean correcta;

    protected OpcionRecord() {}

    public OpcionRecord(String texto, boolean correcta) {
        this.texto = texto;
        this.correcta = correcta;
    }

    public String getTexto() { return texto; }
    public boolean isCorrecta() { return correcta; }
}
