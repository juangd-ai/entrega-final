package co.edu.unicauca.saberpro.question.infrastructure.http;

import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.question.domain.exception.ServicioValidacionNoDisponibleException;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.ValidacionPort;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

/** Adaptador secundario: llama por REST (síncrono) al validation-service. */
public class ValidationHttpAdapter implements ValidacionPort {

    public record ValidacionRequest(String enunciado, String justificacion) {}
    public record ValidacionResponse(boolean valido, List<String> errores) {}

    private final RestClient client;

    public ValidationHttpAdapter(String baseUrl) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        this.client = RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
    }

    @Override
    public void validar(String enunciado, String justificacion) {
        ValidacionResponse respuesta;
        try {
            respuesta = client.post()
                    .uri("/api/validaciones")
                    .body(new ValidacionRequest(enunciado, justificacion))
                    .retrieve()
                    .body(ValidacionResponse.class);
        } catch (RestClientException e) {
            throw new ServicioValidacionNoDisponibleException(
                    "El servicio de validación (:8082) no está disponible", e);
        }
        if (respuesta != null && !respuesta.valido()) {
            throw new ReglaNegocioException(String.join("; ", respuesta.errores()));
        }
    }
}
