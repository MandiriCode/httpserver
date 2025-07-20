package com.example.bansos;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler implements HttpHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bansos_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; // Ganti dengan password database Anda

    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (method.equals("GET")) {
                handleGet(exchange, conn);
            } else if (method.equals("POST")) {
                handlePost(exchange, conn);
            } else if (method.equals("PUT")) {
                handlePut(exchange, conn);
            } else if (method.equals("DELETE")) {
                handleDelete(exchange, conn);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void handleGet(HttpExchange exchange, Connection conn) throws IOException, SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT * FROM penerima";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                result.add(row);
            }
        }
        sendResponse(exchange, 200, gson.toJson(result));
    }

    private void handlePost(HttpExchange exchange, Connection conn) throws IOException, SQLException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> data = gson.fromJson(requestBody, Map.class);

        String query = "INSERT INTO penerima (nama, alamat, penghasilan) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, data.get("nama"));
            pstmt.setString(2, data.get("alamat"));
            pstmt.setInt(3, Integer.parseInt(data.get("penghasilan")));
            pstmt.executeUpdate();
        }
        sendResponse(exchange, 201, "Created");
    }

    private void handlePut(HttpExchange exchange, Connection conn) throws IOException, SQLException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> data = gson.fromJson(requestBody, Map.class);
        String path = exchange.getRequestURI().getPath();
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        String query = "UPDATE penerima SET nama = ?, alamat = ?, penghasilan = ?, status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, data.get("nama"));
            pstmt.setString(2, data.get("alamat"));
            pstmt.setInt(3, Integer.parseInt(data.get("penghasilan")));
            pstmt.setString(4, data.get("status"));
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
        sendResponse(exchange, 200, "OK");
    }

    private void handleDelete(HttpExchange exchange, Connection conn) throws IOException, SQLException {
        String path = exchange.getRequestURI().getPath();
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        String query = "DELETE FROM penerima WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        sendResponse(exchange, 200, "OK");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
