# Literalura

Literalura es una aplicación de consola desarrollada en Java con Spring Boot que permite buscar, registrar y consultar libros y autores utilizando la API de Gutendex. La aplicación interactúa con una base de datos PostgreSQL para almacenar información sobre libros y autores, ofreciendo funcionalidades como búsqueda por título, listado de libros por idioma y consulta de autores vivos en un año específico.

## Características

- **Búsqueda de libros**: Busca libros por título utilizando la API de Gutendex y los guarda en la base de datos si no están registrados.
- **Listado de libros**: Muestra todos los libros registrados con detalles como título, autor, idioma y número de descargas.
- **Listado de autores**: Consulta todos los autores registrados, incluyendo sus fechas de nacimiento, fallecimiento y libros asociados.
- **Autores vivos por año**: Permite buscar autores que estaban vivos en un año determinado.
- **Libros por idioma**: Filtra y lista los libros según el idioma especificado.
- **Persistencia**: Utiliza PostgreSQL para almacenar datos de libros y autores, con soporte para relaciones entre entidades.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para la creación de la aplicación.
- **Spring Data JPA**: Para la interacción con la base de datos.
- **PostgreSQL**: Sistema de gestión de bases de datos relacional.
- **Jackson**: Para el procesamiento de JSON de la API.
- **HttpClient**: Cliente HTTP para consumir la API de Gutendex.
- **Maven**: Gestión de dependencias y compilación.

## Requisitos Previos

- **Java 17** o superior instalado.
- **PostgreSQL** instalado y corriendo en el puerto 5444 (o configurar el puerto en `application.properties`).
- **Maven** para gestionar dependencias.
- Acceso a internet para consumir la API de Gutendex.

## Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Dota43ver/LiterAlura.git
   cd Literalura
   ```

2. **Configurar la base de datos**:
   - Crear una base de datos en PostgreSQL llamada `LiterAlura`.
   - Asegúrate de que el usuario y contraseña en `src/main/resources/application.properties` coincidan con tu configuración de PostgreSQL:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5444/LiterAlura
     spring.datasource.username=facualura
     spring.datasource.password=facualura
     ```

3. **Compilar el proyecto**:
   ```bash
   mvn clean install
   ```

4. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

## Uso

Al iniciar la aplicación, se muestra un menú interactivo en la consola con las siguientes opciones:

1. **Buscar libro por título**: Ingresa el título del libro para buscarlo en la API de Gutendex. Si no está registrado, se guarda en la base de datos junto con su autor.
2. **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores con sus detalles y libros asociados.
4. **Listar autores vivos en un año**: Ingresa un año (entre 0 y 2025) para listar los autores que estaban vivos en ese año.
5. **Listar libros por idioma**: Ingresa un código de idioma (ej. `es` para español, `en` para inglés) para listar los libros en ese idioma.
0. **Salir**: Cierra la aplicación.



