package studios.tkoh.gestor_cliente.config;

import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studios.tkoh.gestor_cliente.endpoint.ClienteEndpoint;

/**
 *
 * @author Studios TKOH!
 */
@Configuration
@RequiredArgsConstructor
public class CxfSoapConfig {

    // Spring inyectará el Bus principal de CXF y nuestro endpoint
    private final Bus bus;
    private final ClienteEndpoint clienteEndpoint;

    /**
     * Define y publica el endpoint como un bean de Spring. El starter de CXF
     * encontrará este bean y lo gestionará.
     *
     * @return La instancia del endpoint publicado.
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, clienteEndpoint);
        // Publicamos el servicio en la dirección "/ClienteService".
        // Esta dirección es relativa a la ruta base definida en cxf.path
        endpoint.publish("/ClienteService");
        return endpoint;
    }
}
