<img width="3453" height="1322" alt="image" src="https://github.com/user-attachments/assets/120d69c3-43f4-4342-9f4c-ee245d607965" />



# 🌐 Spring Cloud Microservices Ecosystem

A complete **Spring Cloud–based microservices setup** demonstrating service discovery, API gateway routing, and secure communication between distributed services using Keycloak.

This project contains the following components:

- 🧩 **Eureka Server** – Service registry for discovery and load balancing  
- 🚪 **Gateway Service** – Entry point for all client requests using Spring Cloud Gateway  
- 👤 **User Service** – A sample microservice registered with Eureka and exposed via the gateway
- 🧩 **Keycloak Server** – Authorization server to manage apps clients and user and provide oauth cabapilities
---

- The **Gateway** routes incoming requests to registered services using `lb://` URIs.
- The **Eureka Server** keeps track of all available services.
- Each microservice registers itself to Eureka and communicates via the gateway.
- The Keycloak server add the security layer by enabling clients to register themselves and fetch an access token.

---

## ⚙️ **Modules**

| Module | Description | Default Port |
|---------|--------------|--------------|
| `eureka-server` | Service registry for microservice discovery | `8761` |
| `gateway-service` | API Gateway that routes external requests | `8083` |
| `user-service` | Sample microservice providing REST APIs | Dynamic / `33889` |
| `keycloak` | Keycloak server as an Authorization server | `8080` |


## 🚀 **How to Run Locally**

### **1. Clone the repository**
```bash
git clone https://github.com/Abdullah8006/spring-microservices-ecosystem.git
cd spring-microservices-ecosystem
````

### **2. Start the Eureka Server**

```bash
cd eureka-server
mvn spring-boot:run
```

Access the Eureka dashboard at 👉 [http://localhost:8761](http://localhost:8761)

### **3. Start the User Service**

```bash
cd ../user-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=3457
# multiple instances of user-service can be run on different ports. The gateway will load balance the services.
```

Once started, it will register itself with Eureka as `user-service`.

### **4. Start the Gateway Service**

```bash
cd ../gateway-service
mvn spring-boot:run
```

Gateway runs at 👉 [http://localhost:8083](http://localhost:8083)

### **4. Start the Keycloak service**

```bash
mkdir -m 777 ./keycloak_data # to be able to persist changes between docker restarts
docker run -v ./keycloak_data:/opt/keycloak/data/h2 -p 127.0.0.1:8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.4.0 start-dev
```
Once the keycloak server is up, open the admin console http://localhost:8080 and create an app specific realm. Then create a client and enable its client credential features.

### **5. Fetch the access token for end users and clients**

There are different grant types that can be initiated for different end users(machine, actual user).

- For microservices
For machines/microservices, client credentials grant types can be initiated with the following commands.

```
curl \
  -d "client_id=user_service" \ # your client id in keycloak
  -d "client_secret=SR1XRGfGEFaV7dsMf5bvUqdWXehVUygq" \  #your client secret in keycloak
  -d "grant_type=client_credentials" \
  "http://localhost:8080/realms/YOUR_REALM/protocol/openid-connect/token"

```

The above command will return an access token which can be used like so

```
curl -H "Authorization: Bearer ACCESS_TOKEN" -v http://localhost:8083/user-service/api/v1/users
```



- For users (from the browser)
For end users/browsers, authorization code grant can be initiated by redirecting the users to the below url

```
http://localhost:8080/realms/YOUR_REALM/protocol/openid-connect/auth?client_id=CLIENT_ID_IN_KEYCLOAK&redirect_uri=http://localhost:3000/callback&response_type=code&scope=openid%20profile%20email
```

The user can use the screen provided by keycloak to log into the system. Once the login is complete, keycloak will redirect to the redirect_uri and send a code in the url.
The code in the url can be used to get an access token. The below curl will get an access token.

```
curl --request POST 'http://localhost:8080/realms/YOUR_REALM/protocol/openid-connect/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=authorization_code' \
  --data-urlencode 'client_id=YOUR_CLIENT_IN_KEYCLOAK' \
  --data-urlencode 'code=CODE_FROM_THE_PREVIOUS_STEP' \
  --data-urlencode 'redirect_uri=http://localhost:3000/callback'
```

---

## 🌐 **Accessing APIs**

Once all services are up:

```bash
curl http://192.168.1.9:8083/user-service/api/v1/users
```

| API                          | Description                           | Example URL                                       |
| ---------------------------- | ------------------------------------- | ------------------------------------------------- |
| `/user-service/api/v1/users` | User service endpoint through gateway | `http://localhost:8083/user-service/api/v1/users` |
| `/actuator/health`           | Health check endpoint                 | `http://localhost:8083/actuator/health`           |

> The gateway uses a `StripPrefix=1` filter, so `/user-service/api/v1/users` becomes `/api/v1/users` when forwarded to the actual microservice.

---

## 🔐 **Security (Optional Setup)**

You can secure this system using **Spring Security + OAuth2**:

* Configure JWT validation in the **Gateway** for authentication.
* Optionally, validate tokens in microservices for fine-grained authorization.
* Supported identity providers: **Keycloak**, **Okta**, **Azure AD**, or any OAuth2-compatible IdP.


---

## 🧰 **Tech Stack**

* ☕ **Java 17+**
* 🧩 **Spring Boot 3.x**
* 🌩️ **Spring Cloud (Eureka, Gateway, Config)**
* 🧠 **Spring Security / OAuth2 (optional)**
* 🐳 **Docker & Docker Compose (optional)**
* 🧾 **Maven**

---

## 💡 **Future Enhancements**

* Add centralized **Spring Cloud Config Server**
* Integrate **Keycloak** for authentication and authorization
* Add **more microservices** (e.g., order-service, inventory-service)
* Implement **distributed tracing** with Sleuth + Zipkin
* Enable **circuit breakers** using Resilience4J

---

## 🏁 **License**

This project is licensed under the MIT License — feel free to use and modify it for learning or production setups.

```
