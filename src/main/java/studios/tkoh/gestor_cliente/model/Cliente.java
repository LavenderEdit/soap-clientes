package studios.tkoh.gestor_cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "cliente", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_identificacion"}, name = "uk_cliente_numero_identificacion")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(name = "numero_identificacion", length = 15, nullable = false)
    private String numeroIdentificacion;

    @NotNull
    @Column(name = "nombre_razon_social", length = 400, nullable = false)
    private String nombreRazonSocial;

    @Column(name = "telefono_celular", length = 15)
    private String telefonoCelular;

    @Email
    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "direccion_principal", length = 650)
    private String direccionPrincipal;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    // --- Relaciones ---
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cliente_id", nullable = false)
    @ToString.Exclude
    private TipoCliente tipoCliente;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "cliente_direccion",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "direccion_id"))
    @ToString.Exclude
    @Builder.Default
    private Set<Direccion> direcciones = new LinkedHashSet<>();

    // --- Métodos de ayuda para sincronizar relaciones ---
    public void addDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
        direccion.getClientes().add(this);
    }

    public void removeDireccion(Direccion direccion) {
        this.direcciones.remove(direccion);
        direccion.getClientes().remove(this);
    }
}
