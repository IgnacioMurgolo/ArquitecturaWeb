## Proyecto de Microservicios: Sistema de Alquiler de Scooters
 Este proyecto es una implementación de un sistema de alquiler de scooters utilizando microservicios con Spring Boot, Docker y bases de datos MySQL y MongoDB. A continuación, se detallan los pasos para levantar el entorno de desarrollo, ejecutar los servicios y probar los endpoints de la API.

### Configuración de Docker
Este proyecto utiliza Docker y Docker Compose para facilitar la ejecución de los servicios y bases de datos. En el archivo `docker-compose.yml` se incluyen todos los servicios necesarios, como MySQL y MongoDB, así como los microservicios que componen la aplicación. El usuario es siempre 'root' y sin contraseña.

### **Pasos para Iniciar el Proyecto**

1. **Levantar los servicios de Docker:**
   Ejecutar el siguiente comando en el directorio raíz del proyecto donde está el archivo `docker-compose.yml`:

   ```bash
   docker-compose up

Esto iniciará:

+ MySQL con la base de datos configurada.
+ MongoDB con datos iniciales.
+ Los microservicios.
  
2. **Verificar Swagger:** Una vez que los servicios estén en ejecución, puedes acceder a la documentación de los endpoints de cada microservicio en las siguientes rutas:
+ Report Service: http://localhost:8081/swagger-ui.html
+ Scooter Service: http://localhost:8082/swagger-ui.html
+ User Service: http://localhost:8083/swagger-ui.html
+ Trip Service: http://localhost:8084/swagger-ui.html
+ Maintenance Service: http://localhost:8085/swagger-ui.html
+ Admin Service: http://localhost:8086/swagger-ui.html
---
## Endpoints Importantes

 ## Report Service
 #### Gestión de reportes
+ a. Reporte de uso de monopatines por kilómetros (con opción para incluir tiempos de pausa)
  + `GET /reports/scooter-usage`: Devuelve un listado con el reporte de uso de monopatines por kilómetros.
+ b. Anular una cuenta (inhabilitar temporalmente)
  + `PUT /reports/disable-account/{accountId}`: Recibe un id de una cuenta para inhabilitar
+ c. Consultar monopatines con más de X viajes en un cierto año
  + `GET /reports/scooters-with-min-trips`: Enviando un año y una cantidad de viajes, devuelve un listado monopatines con 'x' cantidad de viajes
+ d. Consultar el total facturado en un rango de meses de un año
  + `GET /reports/total-revenue`: Enviando un año junto con meses de inicio y fin, devuelve un reporte de beneficios.
+ e. Consultar la cantidad de monopatines en operación versus en mantenimiento
  + `GET /reports/scooter-status`: Devuelve un reporte con la cantidad de scooters en mantenimiento y en operación
+ g. Listado de monopatines cercanos a una ubicación
  + `GET /reports/nearby-scooters`: Dada una ubicación, devuelve un reporte con los scooters en la misma.

 ## Scooter Service
 #### Gestión de Scooters

  + `GET /scooters/`: Obtiene la lista de todos los scooters.
  + `POST /scooters/register`: Crea un nuevo scooter.
  + `GET /scooters/{id}`: Obtiene un scooter por su ID.
  + `PUT /scooters/{id}`: Actualiza los datos de un scooter existente.
  + `DELETE /scooters/{id}`: Elimina un scooter.
 
 ## Trip Service
 #### Gestión de Trip y Pause
  **(usa base de datos MongoDB)**
  + `GET /trips/`: Obtiene la lista de todos los trips.
  + `POST /trips/register`: Crea un nuevo trip.
  + `GET /trips/{id}`: Obtiene un trip por su ID.
  + `PUT /trips/{id}`: Actualiza los datos de un trip existente.
  + `DELETE /trips/{id}`: Elimina un trip.
+ Extras
  + `GET /trips/account/{accountId}`: Muestra un listado de viajes dado un id de cuenta
  + `GET /trips//tripWithAccount/{id}`: Muestra un viaje con los datos de la cuenta.

#### Pause:
  + `GET /pauses/`: Obtiene la lista de todos las pausas.
  + `POST /pauses/register`: Crea un nuevo pause.
  + `GET /pauses/{id}`: Obtiene un pause por su ID.
  + `PUT /pauses/{id}`: Actualiza los datos de un pause existente.
  + `DELETE /pauses/{id}`: Elimina un pause.

## User Service
 #### Gestión de User y Account

  + `GET /users/`: Obtiene la lista de todos los users.
  + `POST /users/register`: Crea un nuevo user.
  + `GET /users/{id}`: Obtiene un user por su ID.
  + `PUT /users/{id}`: Actualiza los datos de un user existente.
  + `DELETE /users/{id}`: Elimina un user.
 
 #### Acount:
 + `GET /accounts/`: Obtiene la lista de todos los accounts.
 + `POST /accounts/register`: Crea un nuevo account.
 + `GET /accounts/{id}`: Obtiene un account por su ID.
 + `PUT /accounts/{id}`: Actualiza los datos de un account existente.
 + `DELETE /accounts/{id}`: Elimina un account.
+ Extras
  + `GET /accounts/tripsWithAccount/{tripId}`: Devuelve una lista de viajes dado el id de una cuenta.

 ## Admin Service
 #### Gestión de Administradores

  + `GET /admins/`: Obtiene la lista de todos los administradores.
  + `POST /admins/register`: Crea un nuevo administrador.
  + `GET /admins/{id}`: Obtiene un administrador por su ID.
  + `PUT /admins/{id}`: Actualiza los datos de un administrador existente.
  + `DELETE /admins/{id}`: Elimina un administrador.
+ Extras 
+ f. Ajuste de precios para habilitar a partir de cierta fecha
  + `PUT /admins/adjust-prices`: Recibe una fecha y un monto que seteará el precio futuro y la fecha de efectividad, comprobando la fecha actual.
 
 ## Maintenance Service
 #### Gestión de Mantenimientos

  + `GET /maintenances/`: Obtiene la lista de todos los mantenimientos.
  + `POST /maintenances/register`: Crea un nuevo mantenimiento.
  + `GET /maintenances/{id}`: Obtiene un mantenimiento por su ID.
  + `PUT /maintenances/{id}`: Actualiza los datos de un mantenimiento existente.
  + `DELETE /maintenances/{id}`: Elimina un mantenimiento.
+ Extras
  + `PATCH /maintenances/assign-scouter/{maintenanceId}/{scooterId}`: Recibe un id de scooter que asigna a un id de mantenimiento.
  + `PATCH /maintenances/end-maintenance/{maintenanceId}`: Recibe el id de un mantenimiento que cambia su estado a 'completed'

 


