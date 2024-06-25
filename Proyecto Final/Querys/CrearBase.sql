-- Crear tablas

CREATE TABLE Login (
	user			TEXT NOT NULL,
	pass			TEXT NOT NULL
);

CREATE TABLE Usuario (
    id_user         INTEGER PRIMARY KEY AUTOINCREMENT,
    nom_user        TEXT NOT NULL,
    ap_user         TEXT NOT NULL,
    email		    TEXT NOT NULL
);

CREATE TABLE Editorial (
    id_editorial    INTEGER PRIMARY KEY AUTOINCREMENT,
    nom_editorial   TEXT NOT NULL
);

CREATE TABLE Libro (
    id_libro        INTEGER PRIMARY KEY AUTOINCREMENT,
    id_editorial    INTEGER NOT NULL,
    titulo          TEXT NOT NULL,
    FOREIGN KEY (id_editorial) REFERENCES Editorial(id_editorial)
);

CREATE TABLE Edicion (
	ISBN            INTEGER PRIMARY KEY,
    id_libro        INTEGER,
    publicacion     INTEGER NOT NULL,
    no_edicion      INTEGER NOT NULL,
    disponibles     INTEGER NOT NULL,
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro)
);

CREATE TABLE Autor (
    id_autor        INTEGER PRIMARY KEY AUTOINCREMENT,
    nom_autor       TEXT NOT NULL
);

CREATE TABLE Categoria (
    id_categoria    INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion     TEXT
);

CREATE TABLE Libro_Categoria (
    id_categoria    INTEGER,
    id_libro        INTEGER,
    PRIMARY KEY (id_categoria, id_libro),
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro)
);

CREATE TABLE Libro_Autor (
    id_libro        INTEGER,
    id_autor        INTEGER,
    PRIMARY KEY (id_libro, id_autor),
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro),
    FOREIGN KEY (id_autor) REFERENCES Autor(id_autor)
);

CREATE TABLE Estado (
    id_estado       INTEGER PRIMARY KEY AUTOINCREMENT,
    desc_estado     TEXT NOT NULL
);

CREATE TABLE Prestamo (
    id_prestamo     INTEGER PRIMARY KEY AUTOINCREMENT,
    id_user         INTEGER,
    fecha_prestamo  TEXT DEFAULT (date('now')),
    FOREIGN KEY (id_user) REFERENCES Usuario(id_user)
);

CREATE TABLE Prestamo_Concentrado (
    id_prestamo_user    INTEGER PRIMARY KEY AUTOINCREMENT,
    id_prestamo         INTEGER,
    ISBN                INTEGER NOT NULL,
    id_estado           INTEGER DEFAULT 1,
    fecha_devolucion    TEXT NOT NULL,
    devolucion          TEXT,
    FOREIGN KEY (id_prestamo) REFERENCES Prestamo(id_prestamo),
    FOREIGN KEY (ISBN) REFERENCES Edicion(ISBN),
    FOREIGN KEY (id_estado) REFERENCES Estado(id_estado)
);

CREATE TABLE Multa (
    id_multa            INTEGER PRIMARY KEY AUTOINCREMENT,
    id_user		        INTEGER,
    id_prestamo_user    INTEGER,
	estatus 			TEXT,
    costo_multa         REAL NOT NULL,
	FOREIGN KEY (id_user) REFERENCES Usuario(id_user)
    FOREIGN KEY (id_prestamo_user) REFERENCES Prestamo_Concentrado(id_prestamo_user)
);

-- Consultas de selección
SELECT * FROM Usuario;
SELECT * FROM Editorial;
SELECT * FROM Libro;
SELECT * FROM Edicion;
SELECT * FROM Autor;
SELECT * FROM Categoria;
SELECT * FROM Libro_Autor;
SELECT * FROM Libro_Categoria;
SELECT * FROM Estado;
SELECT * FROM Prestamo;
SELECT * FROM Prestamo_Concentrado;
SELECT * FROM Multa;
