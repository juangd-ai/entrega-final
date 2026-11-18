package co.edu.unicauca.saberpro.question.domain.model;

import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Raíz del Agregado Pregunta. Java puro: sin Spring, sin JPA.
 * Protege sus invariantes: 4 opciones y exactamente 1 correcta para enviarse a revisión.
 */
public class Pregunta {
    public static final int NUMERO_OPCIONES = 4;

    private Long id;
    private final Enunciado enunciado;
    private final Justificacion justificacion;
    private final List<OpcionRespuesta> opciones;
    private EstadoPregunta estado;

    private Pregunta(Long id, Enunciado enunciado, Justificacion justificacion,
                     List<OpcionRespuesta> opciones, EstadoPregunta estado) {
        this.id = id;
        this.enunciado = enunciado;
        this.justificacion = justificacion;
        this.opciones = new ArrayList<>(opciones);
        this.estado = estado;
    }

    /** Crea una pregunta nueva en estado BORRADOR. */
    public static Pregunta crear(Enunciado enunciado, Justificacion justificacion,
                                 List<OpcionRespuesta> opciones) {
        if (enunciado == null || justificacion == null) {
            throw new ReglaNegocioException("Enunciado y justificación son obligatorios");
        }
        if (opciones == null || opciones.size() > NUMERO_OPCIONES) {
            throw new ReglaNegocioException("Una pregunta admite máximo " + NUMERO_OPCIONES + " opciones");
        }
        return new Pregunta(null, enunciado, justificacion, opciones, EstadoPregunta.BORRADOR);
    }

    /** Reconstruye una pregunta ya persistida (usado por los adaptadores/mappers). */
    public static Pregunta reconstituir(Long id, Enunciado enunciado, Justificacion justificacion,
                                        List<OpcionRespuesta> opciones, EstadoPregunta estado) {
        return new Pregunta(id, enunciado, justificacion, opciones, estado);
    }

    /** Cambio de estado a PENDIENTE_REVISION con validación de invariantes. */
    public void enviarARevision() {
        if (estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo una pregunta en BORRADOR puede enviarse a revisión (estado actual: " + estado + ")");
        }
        if (opciones.size() != NUMERO_OPCIONES) {
            throw new ReglaNegocioException("La pregunta debe tener exactamente " + NUMERO_OPCIONES + " opciones");
        }
        long correctas = opciones.stream().filter(OpcionRespuesta::isCorrecta).count();
        if (correctas != 1) {
            throw new ReglaNegocioException("La pregunta debe tener exactamente 1 opción correcta (tiene " + correctas + ")");
        }
        this.estado = EstadoPregunta.PENDIENTE_REVISION;
    }

    public Long getId() { return id; }
    public void asignarId(Long id) { this.id = id; }
    public Enunciado getEnunciado() { return enunciado; }
    public Justificacion getJustificacion() { return justificacion; }
    public List<OpcionRespuesta> getOpciones() { return Collections.unmodifiableList(opciones); }
    public EstadoPregunta getEstado() { return estado; }
}
