package studios.tkoh.gestor_cliente.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author Studios TKOH!
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecolectaResponse {

    private boolean success;
    private String message;
    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        // Campos para DNI
        @JsonProperty("numero")
        private String numeroDni;
        @JsonProperty("nombre_completo")
        private String nombreCompleto;
        private String nombres;
        @JsonProperty("apellido_paterno")
        private String apellidoPaterno;
        @JsonProperty("apellido_materno")
        private String apellidoMaterno;

        // Campos para RUC
        @JsonProperty("ruc")
        private String numeroRuc;
        @JsonProperty("razon_social")
        private String razonSocial;
        @JsonProperty("estado")
        private String estado;
        @JsonProperty("direccion")
        private String direccion;
        @JsonProperty("ubigeo")
        private String ubigeo;
        @JsonProperty("departamento")
        private String departamento;
        @JsonProperty("provincia")
        private String provincia;
        @JsonProperty("distrito")
        private String distrito;
    }
}
