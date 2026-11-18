package co.edu.unicauca.saberpro.question.infrastructure.jpa;

import co.edu.unicauca.saberpro.question.domain.model.*;

import java.util.List;

/** Traduce entre el modelo de dominio puro y el registro JPA. */
public class QuestionDomainMapper {

    public QuestionRecord toRecord(Pregunta p) {
        List<OpcionRecord> opciones = p.getOpciones().stream()
                .map(o -> new OpcionRecord(o.getTexto(), o.isCorrecta()))
                .toList();
        return new QuestionRecord(p.getId(), p.getEnunciado().getTexto(),
                p.getJustificacion().getTexto(), p.getEstado(), opciones);
    }

    public Pregunta toDomain(QuestionRecord r) {
        List<OpcionRespuesta> opciones = r.getOpciones().stream()
                .map(o -> new OpcionRespuesta(o.getTexto(), o.isCorrecta()))
                .toList();
        return Pregunta.reconstituir(r.getId(), new Enunciado(r.getEnunciado()),
                new Justificacion(r.getJustificacion()), opciones, r.getEstado());
    }
}
