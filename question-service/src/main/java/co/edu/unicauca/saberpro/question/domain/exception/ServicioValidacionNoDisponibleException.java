package co.edu.unicauca.saberpro.question.domain.exception;

public class ServicioValidacionNoDisponibleException extends RuntimeException {
    public ServicioValidacionNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
