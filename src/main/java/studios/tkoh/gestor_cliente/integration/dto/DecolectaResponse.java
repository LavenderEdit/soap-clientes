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
        @JsonProperty("document_number")
        private String documentNumber;
        @JsonProperty("full_name")
        private String fullName;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("first_last_name")
        private String firstLastName;
        @JsonProperty("second_last_name")
        private String secondLastName;

        // Campos para RUC
        @JsonProperty("numero_documento")
        private String numeroDocumentoRuc;
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
