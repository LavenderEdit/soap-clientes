package studios.tkoh.gestor_cliente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studios.tkoh.gestor_cliente.model.TipoCliente;

/**
 *
 * @author Studios TKOH!
 */
@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long> {

    Optional<TipoCliente> findByCodigoSUNAT(String codigo);
}
