package studios.tkoh.gestor_cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Studios TKOH!
 */
@Entity
@Table(name = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(name = "direccion_completa", length = 650, nullable = false)
    private String direccionCompleta;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Builder.Default
    @ManyToMany(mappedBy = "direcciones")
    @ToString.Exclude
    private Set<Cliente> clientes = new LinkedHashSet<>();
}
