package co.edu.unicauca.saberpro.question.api;

import co.edu.unicauca.saberpro.question.domain.exception.PreguntaNoEncontradaException;
import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.question.domain.exception.ServicioValidacionNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(int status, String error) {}

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> reglaNegocio(ReglaNegocioException e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonInvalido(HttpMessageNotReadableException e) {
        return build(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es un JSON válido");
    }

    @ExceptionHandler(PreguntaNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> noEncontrada(PreguntaNoEncontradaException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ServicioValidacionNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> validacionCaida(ServicioValidacionNoDisponibleException e) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), mensaje));
    }
}
