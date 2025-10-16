package studios.tkoh.gestor_cliente.service;

import java.util.Optional;
import studios.tkoh.gestor_cliente.model.Cliente;
import studios.tkoh.gestor_cliente.schemas.TipoDocumento;

/**
 *
 * @author Studios TKOH!
 */
public interface ClienteService {

    /**
     * Registra un nuevo cliente consultando su información desde una API
     * externa a partir de su número y tipo de documento.
     *
     * @param tipoDocumento El tipo de documento (DNI o RUC).
     * @param numeroDocumento El número de documento.
     * @return El cliente guardado en la base de datos.
     * @throws IllegalArgumentException si el tipo de documento es inválido o no
     * se encuentra el cliente.
     */
    Cliente registrarClientePorDocumento(TipoDocumento tipoDocumento, String numeroDocumento);

    /**
     * Busca un cliente por su ID.
     *
     * @param id El ID del cliente.
     * @return Un Optional conteniendo el cliente si se encuentra.
     */
    Optional<Cliente> findById(Long id);
}
