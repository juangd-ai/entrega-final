package co.edu.unicauca.saberpro.question.api;

import co.edu.unicauca.saberpro.question.domain.model.OpcionRespuesta;
import co.edu.unicauca.saberpro.question.domain.model.Pregunta;
import co.edu.unicauca.saberpro.question.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.saberpro.question.domain.ports.inbound.EvaluarPreguntaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Adaptador primario REST. */
@RestController
@RequestMapping("/api/preguntas")
public class QuestionRestController {

    public record OpcionDto(String texto, boolean correcta) {}

    public record CrearPreguntaRequest(String enunciado, String justificacion, List<OpcionDto> opciones) {}

    public record PreguntaResponse(Long id, String enunciado, String justificacion,
                                   List<OpcionDto> opciones, String estado) {
        static PreguntaResponse from(Pregunta p) {
            List<OpcionDto> ops = p.getOpciones().stream()
                    .map(o -> new OpcionDto(o.getTexto(), o.isCorrecta())).toList();
            return new PreguntaResponse(p.getId(), p.getEnunciado().getTexto(),
                    p.getJustificacion().getTexto(), ops, p.getEstado().name());
        }
    }

    public record EnvioRevisionResponse(String mensaje, PreguntaResponse pregunta) {}

    private final CrearPreguntaUseCase crearUseCase;
    private final EvaluarPreguntaUseCase evaluarUseCase;

    public QuestionRestController(CrearPreguntaUseCase crearUseCase, EvaluarPreguntaUseCase evaluarUseCase) {
        this.crearUseCase = crearUseCase;
        this.evaluarUseCase = evaluarUseCase;
    }

    @GetMapping
    public List<PreguntaResponse> listar() {
        return crearUseCase.listar().stream().map(PreguntaResponse::from).toList();
    }

    @GetMapping("/{id}")
    public PreguntaResponse obtener(@PathVariable Long id) {
        return PreguntaResponse.from(crearUseCase.obtener(id));
    }

    @PostMapping
    public ResponseEntity<PreguntaResponse> crear(@RequestBody CrearPreguntaRequest req) {
        List<OpcionDto> dtos = req.opciones() == null ? List.of() : req.opciones();
        List<OpcionRespuesta> opciones = dtos.stream()
                .map(o -> new OpcionRespuesta(o.texto(), o.correcta())).toList();
        Pregunta creada = crearUseCase.crear(req.enunciado(), req.justificacion(), opciones);
        return ResponseEntity.status(HttpStatus.CREATED).body(PreguntaResponse.from(creada));
    }

    @PostMapping("/{id}/enviar-revision")
    public ResponseEntity<EnvioRevisionResponse> enviarRevision(@PathVariable Long id) {
        Pregunta pregunta = evaluarUseCase.enviarARevision(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new EnvioRevisionResponse(
                "Solicitud de revisión despachada a RabbitMQ (pregunta.revisar.queue)",
                PreguntaResponse.from(pregunta)));
    }
}
