package com.biblioteca.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;


public class ConnectionDB {
    private static final String dbName = "db_biblioteca.db";
    private static final String dbPath = "/com/biblioteca/db/";

    public static Connection getConn() {
        // First, extract the database file from the JAR
        String tempDir = System.getProperty("java.io.tmpdir");
        Path tempPath = Paths.get(tempDir, dbName);
        if (!Files.exists(tempPath)) {
            extractDatabase(tempPath);
        }

        // Now connect to the extracted database file
        String url = "jdbc:sqlite:" + tempPath.toAbsolutePath();
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new CustomUncheckedException("Error al conectar con la base de datos", e);
        }
    }

    private static void extractDatabase(Path targetPath) {
        try (InputStream in = ConnectionDB.class.getResourceAsStream(dbPath + dbName)) {
            Files.copy(in, targetPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, Object>> consultar(String query, Object... params) {
        List<HashMap<String, Object>> lista = new LinkedList<>();
        try (Connection cnn = getConn()) {
            PreparedStatement ps = cnn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                lista.add(row);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int getKey(String sql, Object... params) {
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(+1, params[i]);
            }
            return (ps.executeUpdate() > 0) ? ps.getGeneratedKeys().getInt(1) : 0;
        } catch (SQLException e) {
            e.getStackTrace();
            return 0;
        }
    }

    public int comprobar(String sql, Object... params){
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return 1;           
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int ejecutar(String sql, Object... params) {
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

class CustomUncheckedException extends RuntimeException {
    public CustomUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}