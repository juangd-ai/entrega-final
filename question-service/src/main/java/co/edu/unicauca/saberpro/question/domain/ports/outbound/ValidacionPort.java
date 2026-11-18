package co.edu.unicauca.saberpro.question.domain.ports.outbound;

/** Puerto de salida: validación síncrona contra el validation-service. */
public interface ValidacionPort {
    /** Lanza ReglaNegocioException si la validación falla. */
    void validar(String enunciado, String justificacion);
}
