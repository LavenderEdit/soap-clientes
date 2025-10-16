package studios.tkoh.gestor_cliente.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studios.tkoh.gestor_cliente.integration.DecolectaService;
import studios.tkoh.gestor_cliente.integration.dto.DecolectaResponse;
import studios.tkoh.gestor_cliente.model.Cliente;
import studios.tkoh.gestor_cliente.model.Direccion;
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
        Cliente nuevoCliente = mapearRespuestaACliente(response.getData());

        // 4. Guardar y devolver el cliente
        return clienteRepository.save(nuevoCliente);
    }

    private Cliente mapearRespuestaACliente(DecolectaResponse.Data data) {
        Cliente.ClienteBuilder builder = Cliente.builder();

        if (data.getNumeroRuc() != null) { // Es un RUC
            builder.numeroIdentificacion(data.getNumeroRuc())
                    .nombreRazonSocial(data.getRazonSocial());

            // Asignar tipo de cliente basado en el código SUNAT para RUC (ejemplo "06")
            TipoCliente tipo = tipoClienteRepository.findByCodigoSUNAT("06")
                    .orElseGet(() -> tipoClienteRepository.save(
                    new TipoCliente(null, "06", "RUC - REGISTRO UNICO DE CONTRIBUYENTES", null, null))
                    );
            builder.tipoCliente(tipo);

            if (data.getDireccion() != null && !data.getDireccion().isEmpty()) {
                Direccion dir = new Direccion();
                dir.setDireccionCompleta(data.getDireccion());
                dir.setDescripcion("Dirección Fiscal Principal");
                builder.direccionPrincipal(data.getDireccion());
                builder.build().addDireccion(dir);
            }

        } else { // Es un DNI
            builder.numeroIdentificacion(data.getNumeroDni())
                    .nombreRazonSocial(data.getNombreCompleto());

            // Asignar tipo de cliente basado en el código SUNAT para DNI (ejemplo "01")
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
