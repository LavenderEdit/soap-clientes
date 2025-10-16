package studios.tkoh.gestor_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studios.tkoh.gestor_cliente.model.Direccion;

/**
 *
 * @author Studios TKOH!
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}
