SELECT 
    l.titulo,
    e.*,
    ed.nom_editorial,
    (SELECT GROUP_CONCAT(a.nom_autor, ',') FROM (
        SELECT DISTINCT a.nom_autor FROM Autor a
        JOIN Libro_Autor la ON a.id_autor = la.id_autor 
        WHERE la.id_libro = l.id_libro
    ) a) AS autores,
    (SELECT GROUP_CONCAT(c.descripcion, ',') FROM (
        SELECT DISTINCT c.descripcion FROM Categoria c
        JOIN Libro_Categoria lc ON c.id_categoria = lc.id_categoria 
        WHERE lc.id_libro = l.id_libro
    ) c) AS categorias
FROM 
    Libro l
    JOIN Edicion e ON l.id_libro = e.id_libro
    JOIN Editorial ed ON ed.id_editorial = l.id_editorial;
	
SELECT 
    (SELECT GROUP_CONCAT(descripcion, ', ') FROM (SELECT DISTINCT descripcion FROM Categoria)) AS categorias,
    (SELECT GROUP_CONCAT(nom_autor, ', ') FROM (SELECT DISTINCT nom_autor FROM Autor)) AS autores,
    (SELECT GROUP_CONCAT(nom_editorial, ', ') FROM (SELECT DISTINCT nom_editorial FROM Editorial)) AS editoriales;
	
SELECT p.id_prestamo, p.id_user, u.email, l.titulo, es.desc_estado, p.fecha_prestamo, pc.fecha_devolucion, pc.id_prestamo_user, pc.cantidad
FROM Usuario u JOIN Prestamo p ON u.id_user = p.id_user JOIN Prestamo_Concentrado pc ON pc.id_prestamo = p.id_prestamo JOIN Edicion e ON e.ISBN = pc.ISBN JOIN Libro l ON l.id_libro = e.id_libro JOIN Estado es ON es.id_estado = pc.id_estado;

SELECT * FROM Estado

SELECT m.*, l.titulo, u.email, e.ISBN, p.fecha_prestamo, pc.fecha_devolucion
FROM Multa m JOIN Prestamo_Concentrado pc ON m.id_prestamo_user = pc.id_prestamo_user JOIN Prestamo p ON pc.id_prestamo = p.id_prestamo JOIN Edicion e ON e.ISBN = pc.ISBN JOIN Libro l ON l.id_libro = e.id_libro JOIN Usuario u ON u.id_user = m.id_user 

SELECT * FROM Prestamo_Concentrado WHERE devolucion IS NULL

SELECT * FROM Multa m JOIN Prestamo_Concentrado pc ON m.id_prestamo_user = pc.id_prestamo_user WHERE pc.id_prestamo_user = 1;

SELECT * FROM Prestamo_Concentrado pc JOIN Prestamo p ON p.id_prestamo = pc.id_prestamo WHERE date('now') > fecha_devolucion AND id_estado = 1



