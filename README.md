# Sistema de Depreciación de Activos Fijos

## Encargados:
- Frank Salazar
- Favio Huaman
- Victor Cuaresma

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
