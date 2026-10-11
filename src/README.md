# EFT - Sistema Biblioteca Escolar

## Autor
Blanca Rojas Navarrete

## Descripción del Proyecto

Sistema de Biblioteca Escolar desarrollado en Java utilizando programación orientada a objetos, 
interfaces gráficas con Swing y base de datos MySQL.

El sistema permite administrar libros, estudiantes y préstamos de una biblioteca escolar, 
implementando además patrones de diseño y conceptos avanzados de programación solicitados en la evaluación.

## Tecnologías Utilizadas

- Java
- Java Swing
- JDBC
- MySQL
- IntelliJ IDEA

## Patrones de Diseño Implementados

### MVC (Modelo - Vista - Controlador)

El sistema se encuentra organizado en las capas:

- Model: entidades del sistema.
- View: interfaces gráficas.
- DAO: acceso a datos.

### DAO (Data Access Object)

Se utilizan clases DAO para encapsular las operaciones sobre la base de datos.

Ejemplos:

- UsuarioDAO
- LibroDAO
- EstudianteDAO
- PrestamoDAO
- ReporteDAO

### Singleton

La clase DatabaseConnection implementa el patrón Singleton para garantizar una única conexión a la base de datos.

## Funcionalidades Implementadas

### Login

- Inicio de sesión de usuarios.
- Validación de credenciales.

### Gestión de Libros

- Agregar libros.
- Modificar libros.
- Eliminar libros.
- Listar libros.

### Gestión de Estudiantes

- Agregar estudiantes.
- Modificar estudiantes.
- Eliminar estudiantes.
- Listar estudiantes.

### Gestión de Préstamos

- Registrar préstamos.
- Registrar devoluciones.
- Actualizar stock automáticamente.

### Reportes

- Libros más prestados.
- Historial de préstamos.
- Préstamos activos.

## Programación Concurrente

### Thread

Se implementó la clase PrestamoThread para procesar préstamos mediante hilos.

### synchronized

Se utiliza synchronized en PrestamoDAO para evitar inconsistencias en el stock de libros cuando varios
préstamos intentan ejecutarse simultáneamente.

## Base de Datos

La base de datos utilizada es:

biblioteca_escolar

Tablas principales:

- usuarios
- estudiantes
- libros
- prestamos

## Archivos SQL Incluidos

- crear_tablas.sql
- insertar_datos.sql
- consultas_reportes.sql

## Instrucciones de Ejecución

1. Crear la base de datos ejecutando crear_tablas.sql.
2. Insertar datos ejecutando insertar_datos.sql.
3. Configurar credenciales de MySQL en DatabaseConnection.java.
4. Ejecutar Main.java.
5. Iniciar sesión con:

Correo:
admin@biblioteca.cl

Contraseña:
1234

## Observaciones

Proyecto desarrollado para la Evaluación Final Transversal (EFT), aplicando conceptos de Programación Orientada a Objetos, Bases de Datos, Patrones de Diseño, Concurrencia e Interfaces Gráficas.