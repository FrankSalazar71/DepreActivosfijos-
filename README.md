# Sistema de Depreciación de Activos Fijos

## Descripción
Sistema web para la gestión y cálculo de depreciación de activos fijos empresariales. Desarrollado con Spring Boot y MongoDB, este sistema permite llevar un control detallado de los activos fijos de una organización y calcular su depreciación de manera automatizada.

## Tecnologías Utilizadas
- Java 17
- Spring Boot 3.4.5
- MongoDB
- Spring WebFlux
- Lombok
- Maven

## Requisitos Previos
- JDK 17 o superior
- MongoDB instalado y en ejecución
- Maven

## Instalación y Configuración

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/DepreActivosfijos.git
cd DepreActivosfijos
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Características Principales
- Gestión de activos fijos
- Cálculo automático de depreciación
- Base de datos NoSQL con MongoDB
- API RESTful
- Arquitectura reactiva con WebFlux

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── pe/edu/vallegrande/demo3/
│   │       ├── controllers/
│   │       ├── models/
│   │       ├── repositories/
│   │       └── services/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Contribución
Si deseas contribuir al proyecto, por favor:
1. Haz un Fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Realiza tus cambios y haz commit (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia
Este proyecto está bajo la Licencia [Especificar tipo de licencia]

## Contacto
[Información de contacto del equipo o responsable del proyecto]

# DepreActivosfijos

Este proyecto es una aplicación Spring Boot para la gestión de activos fijos y usuarios, incluyendo funcionalidades de depreciación.

## Endpoints de la API

Aquí se listan los principales endpoints disponibles en la API:

### Activos

| Método | Ruta                       | Descripción                                            |
| :----- | :------------------------- | :----------------------------------------------------- |
| `GET`  | `/api/activos`             | Lista todos los activos.                               |
| `GET`  | `/api/activos/inactivos`   | Lista todos los activos con estado INACTIVO.           |
| `GET`  | `/api/activos/{id}`        | Obtiene un activo por su ID.                           |
| `POST` | `/api/activos`             | Crea un nuevo activo.                                  |
| `PUT`  | `/api/activos/{id}`        | Actualiza un activo por su ID.                         |
| `DELETE`|`/api/activos/{id}`        | Elimina un activo por su ID.                           |
| `PUT`  | `/api/activos/{id}/desactivar`| Cambia el estado del activo a INACTIVO.                |
| `PUT`  | `/api/activos/{id}/activar`| Cambia el estado del activo a ACTIVO.                  |
| `GET`  | `/api/activos/categorias`  | Lista todas las categorías de activos.                 |
| `GET`  | `/api/activos/{id}/valor-libros/{anio}`| Calcula el valor en libros de un activo para un año específico.|

### Categorías de Activos

| Método | Ruta                       | Descripción                                            |
| :----- | :------------------------- | :----------------------------------------------------- |
| `GET`  | `/api/categorias`          | Lista todas las categorías de activos.                 |
| `GET`  | `/api/categorias/{id}`     | Obtiene una categoría por su ID.                       |
| `POST` | `/api/categorias`          | Crea una nueva categoría de activo.                    |
| `PUT`  | `/api/categorias/{id}`     | Actualiza una categoría de activo por su ID.           |
| `DELETE`|`/api/categorias/{id}`     | Elimina una categoría de activo por su ID.             |

### Usuarios

| Método | Ruta                       | Descripción                                            |
| :----- | :------------------------- | :----------------------------------------------------- |
| `POST` | `/api/usuarios/login`      | Login de usuario (requiere correo y contraseña en el body).|
| `GET`  | `/api/usuarios`            | Lista todos los usuarios.                              |
| `GET`  | `/api/usuarios?estado={estado}`| Lista usuarios filtrando por estado (ej: `ACTIVO`, `INACTIVO`).|
| `GET`  | `/api/usuarios/{id}`       | Obtiene un usuario por su ID.                          |
| `POST` | `/api/usuarios`            | Crea un nuevo usuario.                                 |
| `PUT`  | `/api/usuarios/{id}`       | Actualiza un usuario por su ID.                        |
| `PUT`  | `/api/usuarios/{id}/desactivar`| Cambia el estado del usuario a INACTIVO.               |
| `PUT`  | `/api/usuarios/{id}/activar`| Cambia el estado del usuario a ACTIVO.                 |
| `GET`  | `/api/usuarios/correo/{correo}`| Obtiene un usuario por su correo.                      |
| `GET`  | `/api/usuarios/dni/{dni}`  | Obtiene un usuario por su DNI.                         |

### Depreciación

La lógica de cálculo de depreciación y valor en libros está integrada en los endpoints de Activos.

| Método | Ruta                                  | Descripción                                            |
| :----- | :------------------------------------ | :----------------------------------------------------- |
| `GET`  | `/api/activos/{id}/valor-libros/{anio}`| Calcula el valor en libros de un activo para un año específico.|

---