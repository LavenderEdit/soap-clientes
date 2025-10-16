package studios.tkoh.gestor_cliente.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studios.tkoh.gestor_cliente.integration.DecolectaService;
import studios.tkoh.gestor_cliente.integration.dto.DecolectaResponse;
import studios.tkoh.gestor_cliente.model.Cliente;
import studios.tkoh.gestor_cliente.model.TipoCliente;
import studios.tkoh.gestor_cliente.repository.ClienteRepository;
import studios.tkoh.gestor_cliente.repository.TipoClienteRepository;
import studios.tkoh.gestor_cliente.schemas.TipoDocumento;
import studios.tkoh.gestor_cliente.service.ClienteService;

/**
 *
 * @author Studios TKOH!
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TipoClienteRepository tipoClienteRepository;
    private final DecolectaService decolectaService;

    @Override
    @Transactional
    public Cliente registrarClientePorDocumento(TipoDocumento tipoDocumento, String numeroDocumento) {
        // 1. Verificar si el cliente ya existe
        if (clienteRepository.findByNumeroIdentificacion(numeroDocumento).isPresent()) {
            throw new IllegalStateException("El cliente con documento " + numeroDocumento + " ya existe.");
        }

        // 2. Consultar la API externa
        DecolectaResponse response = (tipoDocumento == TipoDocumento.RUC)
                ? decolectaService.consultarRuc(numeroDocumento)
                        .orElseThrow(() -> new IllegalArgumentException("No se encontró RUC: " + numeroDocumento))
                : decolectaService.consultarDni(numeroDocumento)
                        .orElseThrow(() -> new IllegalArgumentException("No se encontró DNI: " + numeroDocumento));

        // 3. Mapear la respuesta a la entidad Cliente
        Cliente nuevoCliente = mapearRespuestaACliente(response.getData(), tipoDocumento);

        // 4. Guardar y devolver el cliente
        return clienteRepository.save(nuevoCliente);
    }

    private Cliente mapearRespuestaACliente(DecolectaResponse.Data data, TipoDocumento tipoDocumento) {
        Cliente.ClienteBuilder builder = Cliente.builder();

        if (tipoDocumento == TipoDocumento.RUC) {
            builder.numeroIdentificacion(data.getNumeroDocumentoRuc()) // Usamos el campo corregido
                    .nombreRazonSocial(data.getRazonSocial());

            TipoCliente tipo = tipoClienteRepository.findByCodigoSUNAT("06")
                    .orElseGet(() -> tipoClienteRepository.save(
                    new TipoCliente(null, "06", "RUC - REGISTRO UNICO DE CONTRIBUYENTES", null, null))
                    );
            builder.tipoCliente(tipo);

            if (data.getDireccion() != null && !data.getDireccion().isEmpty()) {
                builder.direccionPrincipal(data.getDireccion());
            }

        } else { // Es un DNI
            builder.numeroIdentificacion(data.getDocumentNumber())
                    .nombreRazonSocial(data.getFullName());

            TipoCliente tipo = tipoClienteRepository.findByCodigoSUNAT("01")
                    .orElseGet(() -> tipoClienteRepository.save(
                    new TipoCliente(null, "01", "DNI - DOC. NACIONAL DE IDENTIDAD", null, null))
                    );
            builder.tipoCliente(tipo);
        }

        return builder.build();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
}
