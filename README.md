# 📄 Gestor de Clientes - API SOAP

Este proyecto es una **API de servicios web basada en SOAP**, desarrollada con **Spring Boot** y **Apache CXF**.
Su función principal es **gestionar un registro de clientes**, integrándose con una **API externa (Decolecta)** para obtener datos de **DNI y RUC** en Perú, y persistirlos en una base de datos **MySQL**.

El proyecto sigue un enfoque **Contract-First**, donde el contrato del servicio (los mensajes de entrada y salida) se define primero en un esquema **XSD**, a partir del cual se generan las clases Java correspondientes.

---

## 🚀 Características Principales

* **Tecnologías Modernas:** Construido sobre *Spring Boot 3*, *Java 17* y *Apache CXF 4*.
* **Base de Datos Relacional:** Utiliza *Spring Data JPA* (con *Hibernate*) para interactuar con una base de datos *MySQL*.
* **Integración Externa:** Se conecta a la API REST de *Decolecta* para validar y enriquecer los datos de clientes (DNI/RUC).
* **Diseño Contract-First:** El servicio web se define a través de un XSD (`/src/main/resources/schemas/clientes.xsd`), asegurando un contrato claro y robusto.
* **Gestión de Dependencias:** Manejado eficientemente con *Maven* y el *Maven Wrapper* incluido.
* **Configuración Flexible:** Utiliza variables de entorno para credenciales y tokens, facilitando el despliegue en diferentes entornos.

---

## Estructura del Proyecto

El código está organizado en los siguientes paquetes principales:

| Paquete         | Descripción                                                                               |
| --------------- | ----------------------------------------------------------------------------------------- |
| **config**      | Clases de configuración de Spring y Apache CXF.                                           |
| **endpoint**    | Implementación del servicio web SOAP (`ClienteEndpoint`).                                 |
| **integration** | Comunicación con la API externa de Decolecta.                                             |
| **dto**         | Objetos de transferencia de datos (DTO) para mapear la respuesta JSON.                    |
| **model**       | Entidades JPA que representan el modelo de datos (`Cliente`, `Direccion`, `TipoCliente`). |
| **repository**  | Interfaces de Spring Data JPA para acceso a la base de datos.                             |
| **service**     | Lógica de negocio de la aplicación.                                                       |
| **impl**        | Implementaciones concretas de la lógica de negocio.                                       |
| **schemas**     | Clases Java generadas automáticamente a partir del archivo XSD.                           |

---

## ⚙️ Requisitos Previos

* **Java 17** o superior
* **Maven 3.x** (o el *wrapper* incluido)
* **MySQL 8** o una base de datos compatible
* **Token de API válido de Decolecta**

---

## 🔧 Configuración y Ejecución

Sigue estos pasos para configurar y levantar la aplicación en tu entorno local.

### Configuración de la Base de Datos

Asegúrate de que tu servidor MySQL esté en ejecución.
La aplicación creará automáticamente la base de datos y las tablas si no existen, gracias a la propiedad:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

### Variables de Entorno

Antes de ejecutar la aplicación, configura las siguientes variables de entorno:

#### 💻 En Windows (cmd):

```bash
set MYSQL_HOST=127.0.0.1
set MYSQL_PORT=3306
set MYSQL_DATABASE=studiostkoh_clientes_api
set MYSQL_USER=tu_usuario_mysql
set MYSQL_PASSWORD=tu_contraseña_mysql
set DECOLECTA_TOKEN=tu_token_de_decolecta
set DECOLECTA_RUC_URL=https://api.decolecta.com/v1/sunat/ruc?numero=
set DECOLECTA_DNI_URL=https://api.decolecta.com/v1/reniec/dni?numero=
```

#### 🐧 En Linux/macOS o Git Bash:

```bash
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
export MYSQL_DATABASE=studiostkoh_clientes_api
export MYSQL_USER=tu_usuario_mysql
export MYSQL_PASSWORD=tu_contraseña_mysql
export DECOLECTA_TOKEN=tu_token_de_decolecta
export DECOLECTA_RUC_URL=https://api.decolecta.com/v1/sunat/ruc?numero=
export DECOLECTA_DNI_URL=https://api.decolecta.com/v1/reniec/dni?numero=
```

---

### ️Compilación y Ejecución

Abre una terminal en la raíz del proyecto y ejecuta el siguiente comando para compilar y arrancar la aplicación:

#### 💻 En Windows

```bash
.\mvnw.cmd spring-boot:run
```

#### 🐧 En Linux/macOS

```bash
./mvnw spring-boot:run
```

Si todo está configurado correctamente, verás el logo de Spring Boot en la consola y un mensaje indicando que el servidor **Tomcat** ha iniciado en el puerto **8080**.

---

## Cómo Probar el Servicio

Una vez que la aplicación esté en ejecución, el servicio SOAP estará disponible.

### Verificar el WSDL

Abre tu navegador y visita:

```
http://localhost:8080/soap-api/ClienteService?wsdl
```

Si ves un documento XML largo, ¡felicidades! 🎉
Tu servicio SOAP está activo y listo para recibir peticiones.

---

### Probar con POSTMAN

Puedes usar **Postman** o cualquier cliente SOAP para enviar peticiones y probar la funcionalidad.

---

#### Prueba 1: Registrar un Cliente por DNI

**Método:** `POST`
**URL:** `http://localhost:8080/soap-api/ClienteService`
**Headers:**

```
Content-Type: text/xml
```

**Body (raw, XML):**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:sch="http://studios/tkoh/gestor_cliente/schemas">
   <soapenv:Header/>
   <soapenv:Body>
      <sch:registrarClienteRequest>
         <sch:tipoDocumento>DNI</sch:tipoDocumento>
         <sch:numeroDocumento>12345678</sch:numeroDocumento>
      </sch:registrarClienteRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Respuesta Esperada (Éxito):**
Una respuesta SOAP con el estado **"ÉXITO"** y los datos del cliente recién creado en la base de datos.

---

#### Prueba 2: Registrar un Cliente por RUC

**Método:** `POST`
**URL:** `http://localhost:8080/soap-api/ClienteService`
**Headers:**

```
Content-Type: text/xml
```

**Body (raw, XML):**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:sch="http://studios/tkoh/gestor_cliente/schemas">
   <soapenv:Header/>
   <soapenv:Body>
      <sch:registrarClienteRequest>
         <sch:tipoDocumento>RUC</sch:tipoDocumento>
         <sch:numeroDocumento>12345678911</sch:numeroDocumento>
      </sch:registrarClienteRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Respuesta Esperada (Éxito):**
Recibirás una respuesta exitosa con los datos de la persona o empresa asociada al RUC.

---

#### ️Prueba 3: Error - Cliente ya Existente

Si intentas registrar el mismo **DNI o RUC** una segunda vez, el servicio devolverá una respuesta de error controlada indicando que **el cliente ya existe**, gracias a la lógica implementada en `ClienteServiceImpl`.

---

## 🏁 Documentación

- 📄 **Generado por:** *Studios TKOH!*
- 📅 **Año:** 2025
