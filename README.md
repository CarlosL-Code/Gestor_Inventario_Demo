
# Gestión de Inventario

Aplicación de escritorio desarrollada en Java utilizando Maven, que permite gestionar productos con funcionalidades básicas de CRUD (Crear, Leer, Actualizar, Eliminar), incluyendo búsqueda, registro, actualización y eliminación de datos.

---

## Características

- Interfaz gráfica amigable para el usuario.
- Registro y administración de productos.
- Funcionalidad de búsqueda eficiente.
- Actualización y eliminación de registros existentes.
- Uso de base de datos MySQL con procedimientos almacenados.
- Empaquetado y gestión de dependencias mediante Maven.

---

## Tecnologías

- Java 21
- Maven 3.8+
- MySQL
- Swing para la interfaz gráfica
- Procedimientos almacenados en la base de datos

---

## Requisitos

- JDK 21 o superior instalado
- MySQL Server configurado con la base de datos y tablas necesarias
- Maven instalado y configurado en el sistema

---

## Instalación

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/tuusuario/gestion-inventario.git
    ```

2. Configurar la conexión a la base de datos en la clase `ConexionBD`:
    - Cambia la URL, usuario y contraseña según tu entorno.

3. Ejecutar el proyecto:
    - Desde tu IDE favorito (NetBeans, IntelliJ, Eclipse)
    - O desde consola con Maven:
      ```bash
      mvn clean install
      mvn exec:java
      ```

---

## Uso

- Al iniciar la aplicación, podrás registrar nuevos productos con su código, descripción, precio, stock y proveedor.
- Usa la barra de búsqueda para encontrar productos específicos.
- Selecciona un producto para actualizar o eliminar su información.

---

## Procedimientos almacenados

Asegúrate de tener creados los procedimientos almacenados requeridos en la base de datos, por ejemplo:

```sql
CREATE PROCEDURE registrarProducto(
    IN p_codigo_producto VARCHAR(30),
    IN p_nombre_producto VARCHAR(100),
    IN p_precio DECIMAL(10,2),
    IN p_stock INT,
    IN p_nombre_proveedor VARCHAR(100)
)
BEGIN
    INSERT INTO producto (codigo_producto, nombre_producto, precio, stock, nombre_proveedor)
    VALUES (p_codigo_producto, p_nombre_producto, p_precio, p_stock, p_nombre_proveedor);
END;
```

---

## Contribuciones

¡Las contribuciones son bienvenidas!  
Por favor abre un issue para discutir los cambios antes de enviar pull requests.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.
