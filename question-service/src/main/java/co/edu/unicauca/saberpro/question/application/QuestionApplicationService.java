package co.edu.unicauca.saberpro.question.application;

import co.edu.unicauca.saberpro.question.domain.exception.PreguntaNoEncontradaException;
import co.edu.unicauca.saberpro.question.domain.model.*;
import co.edu.unicauca.saberpro.question.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.saberpro.question.domain.ports.inbound.EvaluarPreguntaUseCase;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.NotificacionPort;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.ValidacionPort;

import java.util.List;

/** Orquesta los casos de uso. No conoce JPA, HTTP ni RabbitMQ: solo puertos. */
public class QuestionApplicationService implements CrearPreguntaUseCase, EvaluarPreguntaUseCase {

    private final PreguntaRepositoryPort repositorio;
    private final NotificacionPort notificacion;
    private final ValidacionPort validacion;

    public QuestionApplicationService(PreguntaRepositoryPort repositorio,
                                      NotificacionPort notificacion,
                                      ValidacionPort validacion) {
        this.repositorio = repositorio;
        this.notificacion = notificacion;
        this.validacion = validacion;
    }

    @Override
    public Pregunta crear(String enunciado, String justificacion, List<OpcionRespuesta> opciones) {
        validacion.validar(enunciado, justificacion);
        Pregunta pregunta = Pregunta.crear(new Enunciado(enunciado), new Justificacion(justificacion), opciones);
        return repositorio.guardar(pregunta);
    }

    @Override
    public List<Pregunta> listar() {
        return repositorio.listar();
    }

    @Override
    public Pregunta obtener(Long id) {
        return repositorio.buscarPorId(id).orElseThrow(() -> new PreguntaNoEncontradaException(id));
    }

    @Override
    public Pregunta enviarARevision(Long id) {
        Pregunta pregunta = obtener(id);
        pregunta.enviarARevision();
        Pregunta guardada = repositorio.guardar(pregunta);
        notificacion.notificarEnvioARevision(guardada);
        return guardada;
    }
}
