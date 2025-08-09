DROP DATABASE sistemaInventario;
CREATE DATABASE sistemaInventario;

USE sistemaInventario;

CREATE TABLE usuario(
	id_usuario INT AUTO_INCREMENT PRIMARY kEY,
    nombre_usuario VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    email_usuario VARCHAR(50) NOT NULL,
    rol_usuario VARCHAR(30) NOT NULL,
    contrasenia_usuario VARCHAR(50) NOT NULL
);

select * from detalle;
select * from proveedor;
CREATE TABLE cliente(
    id_cliente INT AUTO_INCREMENT PRIMARY kEY,
    rut_cliente VARCHAR(13) NOT NULL,
    nombre_cliente VARCHAR(100) NOT NULL,
    telefono_cliente VARCHAR(15) NOT NULL,
    email_cliente VARCHAR(50) NOT NULL,
    direccion_cliente VARCHAR(30) NOT NULL,
    rol_cliente VARCHAR(100) 
);

CREATE TABLE proveedor(
    id_proveedor INT AUTO_INCREMENT PRIMARY kEY,
    rut_proveedor VARCHAR(13) NOT NULL,
    nombre_proveedor VARCHAR(100) NOT NULL,
    telefono_proveedor VARCHAR(15) NOT NULL,
    email_proveedor VARCHAR(50) NOT NULL,
    direccion_proveedor VARCHAR(30) NOT NULL,
    rol_proveedor VARCHAR(100) 
);
ALTER TABLE proveedor MODIFY telefono_proveedor VARCHAR(50);

CREATE TABLE bodega(
    id_bodega INT AUTO_INCREMENT PRIMARY kEY,
    nombre_bodega VARCHAR(100) NOT NULL,
    direccion_bodega VARCHAR(30) NOT NULL
);

CREATE TABLE producto(
    id_producto INT AUTO_INCREMENT PRIMARY kEY,
    codigo_producto VARCHAR(30) NOT NULL,
    nombre_producto VARCHAR(100) NOT NULL,
    precio DECIMAL (10,2),
    stock INT NOT NULL,
	nombre_proveedor VARCHAR(100) NOT NULL
    
);

CREATE TABLE detalle(
idDetalle INT PRIMARY KEY AUTO_INCREMENT,
codigo_Producto VARCHAR(50)  NOT NULL,
cantidad INT NOT NULL,
precio DECIMAL(10,2) NOT NULL,
idVentas INT NOT NULL
);

CREATE TABLE ventas(
idVentas INT PRIMARY KEY AUTO_INCREMENT,
cliente VARCHAR(50)  NOT NULL,
vendedor VARCHAR(50) NOT NULL,
total DECIMAL(10,2) NOT NULL,
fechaVenta DATETIME NOT NULL
);

CREATE TABLE proveedores(
idProveedor INT PRIMARY KEY AUTO_INCREMENT,
nombreProveedor VARCHAR(60) NOT NULL
);


SELECT * FROM cliente;

INSERT INTO usuario (nombre_usuario, telefono, email_usuario, rol_usuario, contrasenia_usuario)
VALUES
('Carlos José Lozano Silva','+569 77124251', 'carlos@gmail.com', 'Administrador', '12345678');

INSERT INTO cliente (rut_cliente,nombre_cliente, telefono_cliente, email_cliente, direccion_cliente,rol_cliente)
VALUES
('20.456.789-0','Julio Cesar Flores Chavez', '+569 44889097', 'julio@gmail.com', 'Trizano 490, Temuco','S/R');

INSERT INTO proveedor (rut_proveedor,nombre_proveedor, telefono_proveedor, email_proveedor, direccion_proveedor,rol_proveedor)
VALUES
('77.456.789.0','Asus', '+569 76138462', 'asus@gasusrog.com', 'Concepcion','Comercializadora Tecnologica');

