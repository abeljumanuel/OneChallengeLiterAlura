# LiterAlura

<div align="center">
  <h1 align="center">
    API de LiterAlura
    <br>
    <br>
    <img src="https://app.aluracursos.com/assets/images/logos/logo-aluraespanhol.svg" alt="Logo LiterAlura" width="200" align=auto/>
  </h1>
</div>

![Estado del Proyecto](https://img.shields.io/badge/ESTADO-EN%20DESARROLLO-green)

API REST para la gestión de libros y autores utilizando datos de la API de Gutendex.

## 🔧 Funcionalidades del proyecto

- `Funcionalidad 1`: Búsqueda de libros por título.
- `Funcionalidad 2`: Listado de libros registrados.
- `Funcionalidad 3`: Listado de autores registrados.
- `Funcionalidad 4`: Listado de autores vivos en un determinado año.
- `Funcionalidad 5`: Listado de libros por idioma.

## Cómo usar la API

### Pre-requisitos 📋

- Java 17
- PostgreSQL
- Maven

## 📁 Acceso al proyecto

Puedes clonar el proyecto usando el siguiente comando:

```bash
git clone https://github.com/abeljumanuel/OneChallengeLiterAlura.git
```


## 🛠️ Abre y ejecuta el proyecto

1. Configura la base de datos PostgreSQL en `application.properties`.
```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```
2. Ejecuta `mvn clean install` para instalar las dependencias.
3. Inicia la aplicación con `mvn spring-boot:run`.

## Construido con 🛠️

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [Maven](https://maven.apache.org/)

## Endpoints principales

### Menu Principal

1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
0. Salir

## Ejecucion del Menu
```java
@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroRepository, autorRepository);
        principal.muestraElMenu();
    }
}
```

## Contribuyendo 🖇️

Las contribuciones son lo que hacen a la comunidad de código abierto un lugar increíble para aprender, inspirar y crear. Cualquier contribución que hagas será **muy apreciada**.

1. Haz un Fork del proyecto
2. Crea tu rama de característica (`git checkout -b feature/AmazingFeature`)
3. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Haz Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Autor ✒️

* **Juan Manuel Rojas Gonzalez** - *Implementando JPA* - [Abeljumanuel](https://github.com/Abeljumanuel)

## Expresiones de Gratitud 🎁

* Comenta a otros sobre este proyecto 📢
* Invita una cerveza 🍺 o un café ☕ a alguien del equipo.
* Da las gracias públicamente 🤓.

---
⌨️ con ❤️ por [Abeljumanuel](https://github.com/Abeljumanuel) 😊