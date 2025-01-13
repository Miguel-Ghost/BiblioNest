# Proyecto: BiblioNest
Este proyecto es una aplicación **Java** construida con **Spring Boot** que permite **gestionar libros y autores** de forma local, además de consumir datos de la API pública de [Gutendex](https://gutendex.com/) para registrar nuevos libros.

## Características principales

1. **Buscar libro por título**
    - El sistema se conecta a la API de Gutendex para encontrar libros cuyo título contenga una palabra o frase especificada por el usuario.
    - Los libros encontrados se registran en la base de datos solo si aún no existían.

2. **Listar libros registrados**
    - Muestra todos los libros que se han guardado previamente en la base de datos, con información como título, idiomas y número de descargas.

3. **Listar autores registrados**
    - Muestra todos los autores que se han guardado, evitando duplicados.

4. **Listar autores vivos en un determinado año**
    - Solicita al usuario un año y luego filtra a aquellos autores que hayan nacido antes o durante ese año, y no hayan fallecido o cuyo año de fallecimiento sea posterior.

5. **Listar libros por idioma**
    - Permite al usuario especificar un código de idioma (ES, EN, FR, PT, etc.) y muestra todos los libros que tengan ese idioma en su lista de idiomas.

6. **Exhibir estadísticas de libros por idioma**
    - Cuenta y muestra cuántos libros hay en la base de datos para cada idioma solicitado por el usuario.

---

## Arquitectura y componentes

1. **Clases de Entidad (Entities)**
    - **Autor**: representa a un autor. Contiene campos como nombre, año de nacimiento y año de fallecimiento, y una relación muchos-a-muchos con `Libro`.
    - **Libro**: representa un libro. Contiene título, lista de idiomas, número de descargas y sus autores relacionados (también en una relación muchos-a-muchos).

2. **Repositorios (Repositories)**
    - **AutorRepository**: interfaz que extiende de `JpaRepository` para realizar operaciones CRUD con la entidad `Autor`.
    - **LibroRepository**: interfaz que extiende de `JpaRepository` para realizar operaciones CRUD con la entidad `Libro`.

3. **Clases de servicio / lógica**
    - **ClienteApi**: se encarga de realizar peticiones HTTP a la API de Gutendex para obtener datos en formato JSON.
    - **ConvierteDatos**: se encarga de deserializar el JSON en objetos Java (`DatoResultado`, `DatoLibro`, `DatoAutor`).

4. **Menú interactivo (LibroMenu)**
    - Gestiona las opciones de la aplicación mediante un menú por consola.
    - Permite buscar libros, listar libros/autores, filtrar autores vivos, listar libros por idioma, etc.

5. **Archivos de configuración**
    - `application.properties` (u otro equivalente) para la configuración de la base de datos, el puerto, etc.

---

## Requisitos del entorno

- **Java 17 o superior** (recomendado)
- **Maven** o **Gradle** (dependiendo de tu configuración) para gestionar dependencias de Spring Boot.
- **Base de datos** (H2, MySQL, etc.). Ajusta en `application.properties`.

---

## Instrucciones de ejecución

1. **Clonar o descargar** el repositorio.
2. **Configurar la base de datos** en `src/main/resources/application.properties` (si no usas H2 en memoria).
3. **Compilar** el proyecto con tu gestor de dependencias (Maven o Gradle).
4. **Ejecutar** la aplicación:
     ```bash
   ./mvnw spring-boot:run
   # o
   ./gradlew bootRun
    ```
## Menú en consola
Al iniciar, el sistema muestra un menú con opciones numeradas. Ingresa el número de la opción que deseas ejecutar.
![image](https://github.com/user-attachments/assets/c62c3ec1-d764-422f-8c7a-87bb8784f6ba)

## Uso de la aplicación (Menú por consola)
1. **Buscar libro por título
Ingresa una palabra o frase, se realiza la consulta a Gutendex, y se registra el(los) libro(s) nuevo(s) en la base.

2. **Listar libros registrados
Muestra todos los libros con título, idiomas, número de descargas y autores asociados.

3. **Listar autores registrados
Muestra todos los autores sin duplicados.

4. **Listar autores vivos en un año
Solicita un año y filtra autores que pudieran haber vivido en dicha fecha (basado en nacimiento/fallecimiento).

5. **Listar libros por idioma
Muestra los libros que incluyan el idioma ingresado (ES, EN, FR, PT, etc.).
También se muestra la cantidad total de libros encontrados en ese idioma.

6. **Salir
Finaliza la aplicación.

## Pruebas
![image](https://github.com/user-attachments/assets/5d68af94-20ba-4266-bf27-08ddbd3ccdd6)


![image](https://github.com/user-attachments/assets/5873ed03-df23-43f2-b501-a71e695dd446)


![image](https://github.com/user-attachments/assets/1d009984-e899-4f27-9f68-45b5fd8ad3a3)


![image](https://github.com/user-attachments/assets/93bee388-4e94-4c52-9651-71c40c616f3d)


![image](https://github.com/user-attachments/assets/c0909d86-2d3b-43a7-be98-dccbbc21ed58)

