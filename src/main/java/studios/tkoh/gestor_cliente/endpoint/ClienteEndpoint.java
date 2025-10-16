package studios.tkoh.gestor_cliente.endpoint;

import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import studios.tkoh.gestor_cliente.model.Cliente;
import studios.tkoh.gestor_cliente.schemas.RegistrarClienteRequest;
import studios.tkoh.gestor_cliente.schemas.RegistrarClienteResponse;
import studios.tkoh.gestor_cliente.service.ClienteService;

/**
 *
 * @author Studios TKOH!
 */
@Component
@WebService(
        serviceName = "ClienteService",
        portName = "ClientePort",
        targetNamespace = "http://studios/tkoh/gestor_cliente/schemas",
        endpointInterface = "studios/tkoh/gestor_cliente.endpoint.ClienteEndpointPort"
)
@RequiredArgsConstructor
public class ClienteEndpoint implements ClienteEndpointPort {

    private final ClienteService clienteService;

    @Override
    public RegistrarClienteResponse registrarCliente(RegistrarClienteRequest request) {
        RegistrarClienteResponse response = new RegistrarClienteResponse();
        try {
            Cliente nuevoCliente = clienteService.registrarClientePorDocumento(
                    request.getTipoDocumento(),
                    request.getNumeroDocumento()
            );

            studios.tkoh.gestor_cliente.schemas.Cliente clienteSchema = new studios.tkoh.gestor_cliente.schemas.Cliente();
            clienteSchema.setId(nuevoCliente.getId());
            clienteSchema.setNumeroIdentificacion(nuevoCliente.getNumeroIdentificacion());
            clienteSchema.setNombreRazonSocial(nuevoCliente.getNombreRazonSocial());
            clienteSchema.setEmail(nuevoCliente.getEmail());
            clienteSchema.setTelefonoCelular(nuevoCliente.getTelefonoCelular());
            clienteSchema.setDireccionPrincipal(nuevoCliente.getDireccionPrincipal());
            clienteSchema.setTipoCliente(nuevoCliente.getTipoCliente().getTipoCliente());

            response.setCliente(clienteSchema);
            response.setStatus("ÉXITO");
            response.setMensaje("Cliente registrado correctamente.");

        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setMensaje("Error al registrar el cliente: " + e.getMessage());
        }
        return response;
    }

}
