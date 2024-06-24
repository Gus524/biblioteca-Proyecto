-- Insertar datos
INSERT INTO Login (user, pass) VALUES
('usuario', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');

INSERT INTO Usuario (nom_user, ap_user, email) VALUES
('Juan', 'Perez', 'juanperes@gmail.com'),
('Maria', 'Lopez', 'mari_lopez@hotmail.com'),
('Pedro', 'Gomez', 'pedrop@gmail.com'),
('Ana', 'Martinez', 'anmart@gmail.com'),
('Luis', 'Torres', 'luist@gmail.com');

INSERT INTO Editorial (nom_editorial) VALUES
('Editorial Alfa'),
('Editorial Beta'),
('Editorial Gamma'),
('Editorial Delta'),
('Editorial Epsilon');

INSERT INTO Libro (id_editorial, titulo) VALUES
(1, 'El libro de los ejemplos'),
(2, 'Aprendiendo SQL'),
(3, 'Programación Avanzada'),
(4, 'La historia interminable'),
(5, 'Ciencia y Tecnología');

INSERT INTO Edicion (id_libro, ISBN, publicacion, no_edicion, precio, disponibles) VALUES
(1, 1234567890123, 2022, 1, 25.99, 5),
(1, 1234567890124, 2023, 2, 27.99, 3),
(2, 2345678901234, 2021, 1, 29.99, 3),
(3, 3456789012345, 2023, 1, 12.99, 2),
(4, 4567890123456, 2020, 1, 18.99, 4),
(5, 5678901234567, 2019, 1, 35.99, 6);

INSERT INTO Autor (nom_autor) VALUES
('Carlos Ruiz Zafón'),
('Isabel Allende'),
('Gabriel García Márquez'),
('Michael Ende'),
('Stephen Hawking');

INSERT INTO Categoria (descripcion) VALUES
('Novela'),
('Ciencia Ficción'),
('Educación'),
('Fantasía'),
('Ciencia');

INSERT INTO Libro_Categoria (id_categoria, id_libro) VALUES
(1, 1),
(3, 2),
(3, 3),
(4, 4),
(5, 5),
(2, 4),
(5, 3);

INSERT INTO Libro_Autor (id_libro, id_autor) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 4),
(3, 2);

INSERT INTO Estado (desc_estado) VALUES
('Prestado'),
('Devuelto'),
('Atrasado');

INSERT INTO Prestamo (id_user, fecha_prestamo) VALUES
(1, '2023-01-01'),
(2, '2023-02-01'),
(3, '2023-03-01'),
(4, '2023-04-01'),
(5, '2023-05-01'),
(1, '2023-06-01');

INSERT INTO Prestamo_Concentrado (id_prestamo, ISBN, fecha_devolucion) VALUES
(1, 1234567890123, '2023-02-01'),
(2, 1234567890124, '2023-03-01'),
(3, 2345678901234, '2023-04-01'),
(4, 3456789012345, '2023-05-01'),
(5, 4567890123456, '2023-06-01'),
(6, 1234567890124, '2023-07-01'),
(1, 1234567890124, '2023-02-15'),
(2, 2345678901234, '2023-03-15');

INSERT INTO Venta (costo_total) VALUES
(59.99),
(29.99),
(49.99),
(39.99),
(99.99),
(19.99);

INSERT INTO Venta_Detalle (id_venta, ISBN, costo_parcial) VALUES
(1, 1234567890123, 25.99),
(1, 1234567890124, 27.99),
(2, 2345678901234, 29.99),
(3, 3456789012345, 22.99),
(4, 4567890123456, 18.99),
(5, 5678901234567, 35.99),
(5, 1234567890123, 25.99),
(5, 3456789012345, 22.99),
(6, 1234567890124, 27.99),
(1, 2345678901234, 29.99);

