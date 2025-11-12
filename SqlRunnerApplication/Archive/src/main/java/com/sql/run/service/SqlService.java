package com.sql.run.service;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class SqlService {

    private final String DB_URL = "jdbc:sqlite:src/main/resources/sql_runner.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            boolean isResultSet = stmt.execute(query);
            if (isResultSet) {
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= colCount; i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            results.add(error);
        }
        return results;
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            while (rs.next()) {
                tables.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public Map<String, Object> getTableInfo(String tableName) {
        Map<String, Object> tableInfo = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();
        List<Map<String, Object>> sampleData = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Columns
            ResultSet rsCols = conn.createStatement()
                    .executeQuery("PRAGMA table_info(" + tableName + ");");
            while (rsCols.next()) {
                Map<String, String> col = new HashMap<>();
                col.put("name", rsCols.getString("name"));
                col.put("type", rsCols.getString("type"));
                columns.add(col);
            }
            // Sample rows
            ResultSet rsRows = conn.createStatement()
                    .executeQuery("SELECT * FROM " + tableName + " LIMIT 5;");
            ResultSetMetaData meta = rsRows.getMetaData();
            int colCount = meta.getColumnCount();
            while (rsRows.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    row.put(meta.getColumnName(i), rsRows.getObject(i));
                }
                sampleData.add(row);
            }
        } catch (SQLException e) {
            tableInfo.put("error", e.getMessage());
        }
        tableInfo.put("columns", columns);
        tableInfo.put("sampleData", sampleData);
        return tableInfo;
    }
}
