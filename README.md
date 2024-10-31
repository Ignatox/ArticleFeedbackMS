### Acerca de ArticleFeedbackMS Beta
Microservicio presentado como caso de estudio para el e-commerce de la cátedra de Arquitectura de microservicios.

Se encarga de facilitarle al usuario la posibilidad de dar un feedback a los artículos que ya compró.

Permite a los usuarios poder consultar las experiencias de otros con un artículo específico, además de poder consultar todos los feedbacks realizados por un usuario.

#### MongoDB
La base de datos del microservicio es almacenada en MongoDB

#### Estructura de Datos

![](resources/ClassDiagramV1.jpg)


#### Conexiones a otros Microservicios - RabbitMQ
Este microservicio se comunica con los demás del ecosistema del e-commerce a través de Rabbit.
Se realizan conexiones tales como:
##### Catalog Service (Cátalogo de Artículos) 
- Cuando un usuario busca feedbacks de un artículo en específico,  **ArticleFeedbackMS** puede conectarse con el **Catalog Service** para asegurarse de que el artículo existe.

- Si un usuario intenta dar un feedback  con comentario: like/dislike a un artículo existente, la validación viene de este microservicio.

(CONTINUAR)

##### Auth Service (Autenticación de Usuarios)

 (PREGUNTAR)
 
- Al dar un feedback sobre un artículo, es importante validar que el usuario esté autenticado. ArticleFeedbackService enviaría el token JWT al **AuthService** para validar si el usuario es válido.

- Este microservicio debe escuchar el evento ** "logout"** que emite **Auth** para invalidar los tokens localmente.

##### Order Service (Órdenes de Compra)
- Antes de permitir que el usuario publique un feedback sobre un artículo, **ArticleFeedbackMS**  se conecta con el **Order Service** para verificar si este usuario compró el artículo.
(PREGUNTAR EL SIGUIENTE)
- Al consultar los comentarios se utiliza también para mostrar sólo los comentarios que los usuarios realmente hayan comprado.

#### Casos de Uso de ArticleFeedback
##### 1. Registrar Feedback de un Artículo

**Descripción: **Un usuario puede registrar un comentario y su valoración (like o dislike) en un artículo que ha comprado.
**Entradas:** userId, articleId, comment, liked (booleano), createdAt.
**Resultado: ** El feedback se almacena y se asocia con el artículo y el usuario correspondiente.

##### 2. Obtener Feedback por Artículo

**Descripción: **Permite a los usuarios consultar todos los comentarios realizados sobre un artículo específico.
**Entradas:** articleId.
**Salida:** Lista de comentarios asociados a ese artículo, incluyendo el texto del comentario, autor y fecha de creación.
**Información Adicional: **Número total de likes y dislikes acumulados para ese artículo, ordenados para mostrar los comentarios más populares primero.

##### 3. Obtener Feedback por Usuario

**Descripción: **Permite consultar todos los comentarios realizados por un usuario específico en diferentes artículos.
**Entradas: **userId.
**Salida**: Lista de artículos con sus comentarios, fecha y valoración (liked) que hizo el usuario.
