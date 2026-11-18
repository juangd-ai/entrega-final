package co.edu.unicauca.saberpro.question.infrastructure.jpa;

import co.edu.unicauca.saberpro.question.domain.model.EstadoPregunta;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/** Entidad JPA: vive SOLO en infraestructura. El dominio no sabe que existe. */
@Entity
@Table(name = "preguntas")
public class QuestionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String enunciado;

    @Column(length = 2000, nullable = false)
    private String justificacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPregunta estado;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pregunta_opciones", joinColumns = @JoinColumn(name = "pregunta_id"))
    private List<OpcionRecord> opciones = new ArrayList<>();

    protected QuestionRecord() {}

    public QuestionRecord(Long id, String enunciado, String justificacion,
                          EstadoPregunta estado, List<OpcionRecord> opciones) {
        this.id = id;
        this.enunciado = enunciado;
        this.justificacion = justificacion;
        this.estado = estado;
        this.opciones = new ArrayList<>(opciones);
    }

    public Long getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public String getJustificacion() { return justificacion; }
    public EstadoPregunta getEstado() { return estado; }
    public List<OpcionRecord> getOpciones() { return opciones; }
}
