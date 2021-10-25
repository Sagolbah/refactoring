package ru.akirakozov.sd.refactoring.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDatabaseService<T> {
    private static final String DATABASE_NAME = "jdbc:sqlite:test.db";

    protected void doUpdateQuery(final String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE_NAME)) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(query);
            }
        }
    }

    protected List<T> doSelectQuery(final String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE_NAME)) {
            try (Statement stmt = c.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(cast(rs));
                }
                return result;
            }
        }
    }

    protected int doSelectInteger(final String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE_NAME)) {
            try (Statement stmt = c.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    protected List<T> select(final String fields, final String queryBody) throws SQLException {
        return doSelectQuery(String.format("SELECT %s FROM %s %s", fields, getDatabaseName(), queryBody).trim());
    }

    protected int selectInteger(final String fields, final String queryBody) throws SQLException {
        return doSelectInteger(String.format("SELECT %s FROM %s %s", fields, getDatabaseName(), queryBody).trim());
    }

    protected void insert(final Map<String, Object> keyValueMap) throws SQLException {
        final String attrs = keyValueMap.keySet().stream().map(Object::toString).collect(Collectors.joining(", "));
        // Using implicit conversion for integer attrs
        final String values = keyValueMap.values().stream().map(Object::toString).map(s -> '\"' + s + '\"').collect(Collectors.joining(", "));
        final String query = "INSERT INTO " + getDatabaseName() + " (" + attrs + ") " + "VALUES (" + values + ")";
        doUpdateQuery(query);
    }

    public abstract String getDatabaseName();

    protected abstract T cast(ResultSet rs) throws SQLException;
}
