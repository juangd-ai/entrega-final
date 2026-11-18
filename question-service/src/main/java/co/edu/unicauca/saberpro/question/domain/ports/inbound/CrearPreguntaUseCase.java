package co.edu.unicauca.saberpro.question.domain.ports.inbound;

import co.edu.unicauca.saberpro.question.domain.model.OpcionRespuesta;
import co.edu.unicauca.saberpro.question.domain.model.Pregunta;

import java.util.List;

/** Puerto de entrada: casos de uso de creación y consulta. */
public interface CrearPreguntaUseCase {
    Pregunta crear(String enunciado, String justificacion, List<OpcionRespuesta> opciones);
    List<Pregunta> listar();
    Pregunta obtener(Long id);
}
