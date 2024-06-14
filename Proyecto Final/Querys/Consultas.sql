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
	


