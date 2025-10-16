package studios.tkoh.gestor_cliente.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import studios.tkoh.gestor_cliente.integration.dto.DecolectaResponse;

import java.util.Optional;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author Studios TKOH!
 */
@Service
public class DecolectaService {

    private final RestTemplate restTemplate;
    private final String decolectaToken;
    private final String decolectaRucUrl;
    private final String decolectaDniUrl;

    public DecolectaService(RestTemplate restTemplate,
            @Value("${integration.decolecta.token}") String decolectaToken,
            @Value("${integration.decolecta.ruc-url}") String decolectaRucUrl,
            @Value("${integration.decolecta.dni-url}") String decolectaDniUrl) {
        this.restTemplate = restTemplate;
        this.decolectaToken = decolectaToken;
        this.decolectaRucUrl = decolectaRucUrl;
        this.decolectaDniUrl = decolectaDniUrl;
    }

    public Optional<DecolectaResponse> consultarRuc(String ruc) {
        return consultarApi(decolectaRucUrl, ruc);
    }

    public Optional<DecolectaResponse> consultarDni(String dni) {
        return consultarApi(decolectaDniUrl, dni);
    }

    private Optional<DecolectaResponse> consultarApi(String baseUrl, String documento) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(decolectaToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = baseUrl + "/" + documento;

        try {
            ResponseEntity<DecolectaResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, DecolectaResponse.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().isSuccess()) {
                return Optional.of(response.getBody());
            }
        } catch (RestClientException e) {
            // Loggear el error en un escenario real
            System.err.println("Error al consultar la API de Decolecta: " + e.getMessage());
        }
        return Optional.empty();
    }
}
