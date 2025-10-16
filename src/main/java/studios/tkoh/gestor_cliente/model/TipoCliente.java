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
@Table(name = "tipo_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(name = "codigo_sunat", length = 10, nullable = false, unique = true)
    private String codigoSUNAT;

    @NotNull
    @Column(name = "tipo_cliente", length = 100, nullable = false)
    private String tipoCliente;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Builder.Default
    @OneToMany(mappedBy = "tipoCliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Cliente> clientes = new LinkedHashSet<>();
}
