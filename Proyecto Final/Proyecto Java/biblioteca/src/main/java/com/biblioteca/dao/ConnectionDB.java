package com.biblioteca.dao;

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
    private static String base = "db_biblioteca.db";
    private static String path;
    private static final String url = getPath();

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url);
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

    private static String getPath(){    
        String basePath = Paths.get("").toAbsolutePath().toString();
        path = Paths.get(basePath, "db", base).toString();
        return "jdbc:sqlite:" + path;
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
