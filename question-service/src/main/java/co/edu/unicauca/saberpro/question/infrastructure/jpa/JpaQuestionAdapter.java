package co.edu.unicauca.saberpro.question.infrastructure.jpa;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.PreguntaRepositoryPort;

import java.util.List;
import java.util.Optional;

/** Adaptador secundario: implementa el puerto con Spring Data JPA (H2). */
public class JpaQuestionAdapter implements PreguntaRepositoryPort {

    private final SpringDataQuestionRepository repository;
    private final QuestionDomainMapper mapper;

    public JpaQuestionAdapter(SpringDataQuestionRepository repository, QuestionDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Pregunta guardar(Pregunta pregunta) {
        QuestionRecord saved = repository.save(mapper.toRecord(pregunta));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Pregunta> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Pregunta> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
