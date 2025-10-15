package studios.tkoh.gestor_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studios.tkoh.gestor_cliente.model.Cliente;

/**
 *
 * @author Studios TKOH!
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
