package co.edu.unicauca.saberpro.validation.api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/** Servicio de validación (Pipes & Filters simplificado): cada regla es un filtro. */
@RestController
@RequestMapping("/api/validaciones")
public class ValidationRestController {

    public record ValidacionRequest(String enunciado, String justificacion) {}

    public record ValidacionResponse(boolean valido, List<String> errores) {}

    @GetMapping("/health")
    public String health() {
        return "validation-service OK";
    }

    @PostMapping
    public ValidacionResponse validar(@RequestBody ValidacionRequest req) {
        List<String> errores = new ArrayList<>();

        if (req.enunciado() == null || req.enunciado().isBlank()) {
            errores.add("El enunciado es obligatorio");
        } else if (req.enunciado().trim().length() < 10) {
            errores.add("El enunciado debe tener al menos 10 caracteres");
        }

        if (req.justificacion() == null || req.justificacion().trim().length() < 15) {
            errores.add("La justificación debe tener al menos 15 caracteres");
        }

        return new ValidacionResponse(errores.isEmpty(), errores);
    }
}
