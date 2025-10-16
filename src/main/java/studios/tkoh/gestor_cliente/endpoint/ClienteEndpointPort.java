package studios.tkoh.gestor_cliente.endpoint;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import studios.tkoh.gestor_cliente.schemas.RegistrarClienteRequest;
import studios.tkoh.gestor_cliente.schemas.RegistrarClienteResponse;

/**
 *
 * @author Studios TKOH!
 */
@WebService(targetNamespace = "http://studios/tkoh/gestor_cliente/schemas", name = "ClienteEndpointPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ClienteEndpointPort {

    @WebMethod
    @WebResult(name = "registrarClienteResponse", targetNamespace = "http://studios/tkoh/gestor_cliente/schemas", partName = "parameters")
    RegistrarClienteResponse registrarCliente(
            @WebParam(partName = "parameters", name = "registrarClienteRequest", targetNamespace = "http://studios/tkoh/gestor_cliente/schemas") RegistrarClienteRequest request
    );
}
