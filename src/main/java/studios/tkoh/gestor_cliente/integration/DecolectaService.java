package studios.tkoh.gestor_cliente.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public DecolectaService(RestTemplate restTemplate,
            @Value("${integration.decolecta.token}") String decolectaToken,
            @Value("${integration.decolecta.ruc-url}") String decolectaRucUrl,
            @Value("${integration.decolecta.dni-url}") String decolectaDniUrl,
            ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.decolectaToken = decolectaToken;
        this.decolectaRucUrl = decolectaRucUrl;
        this.decolectaDniUrl = decolectaDniUrl;
        this.objectMapper = objectMapper;
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
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = baseUrl + documento;

        System.out.println("Consultando URL: " + url);

        try {
            ResponseEntity<String> responseAsString = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("Respuesta RAW de API: " + responseAsString.getBody());

            if (responseAsString.getStatusCode() == HttpStatus.OK && responseAsString.getBody() != null) {

                DecolectaResponse.Data data = objectMapper.readValue(responseAsString.getBody(), DecolectaResponse.Data.class);

                DecolectaResponse decolectaResponse = new DecolectaResponse();
                decolectaResponse.setSuccess(true);
                decolectaResponse.setData(data);

                return Optional.of(decolectaResponse);
            }
        } catch (RestClientException | JsonProcessingException e) {
            System.err.println("Error al procesar la respuesta de la API de Decolecta: " + e.getMessage());
        }
        return Optional.empty();
    }
}
