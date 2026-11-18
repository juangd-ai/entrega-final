package co.edu.unicauca.saberpro.question.domain.exception;

/** Se lanza cuando se viola una regla/invariante de negocio del dominio. */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
