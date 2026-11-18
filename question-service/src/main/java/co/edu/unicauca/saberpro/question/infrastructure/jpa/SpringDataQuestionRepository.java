package co.edu.unicauca.saberpro.question.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataQuestionRepository extends JpaRepository<QuestionRecord, Long> {
}
