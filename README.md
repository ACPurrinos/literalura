# literalura
Literalura
Literalura es una aplicación Java basada en Spring Boot que permite buscar y gestionar libros y autores utilizando una API externa (Gutendex). 
La aplicación utiliza una base de datos relacional para almacenar los libros y sus autores.

Funcionalidades
Buscar libro por título: Permite buscar libros utilizando una API externa basada en el título proporcionado.
Listar libros buscados: Muestra todos los libros guardados en la base de datos.
Listar autores de los libros buscados: Muestra los autores asociados a los libros encontrados.
Listar autores vivos en un determinado año: Permite buscar y listar autores vivos en un año específico.
Buscar libros registrados por idioma: Busca y lista libros registrados en la base de datos por idioma, mostrando la cantidad de libros en ese idioma.

Requisitos
Java 11 o superior
Maven 3.6.x o superior
Acceso a una base de datos MySQL (u otro compatible con Spring Data JPA)

Configurar la base de datos:
Configura las credenciales de la base de datos en src/main/resources/application.properties.

Uso
Una vez que la aplicación esté en ejecución, sigue las opciones del menú para utilizar las funcionalidades disponibles.

Tecnologías utilizadas
Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
