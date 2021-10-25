package ru.akirakozov.sd.refactoring;

import org.junit.Before;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BaseServerTestCase<T extends HttpServlet> {
    protected HttpServletRequest mockRequest;
    protected HttpServletResponse mockResponse;
    protected MockWriter writer;
    protected T servlet;

    @Before
    public void init() {
        this.mockRequest = mock(HttpServletRequest.class);
        this.mockResponse = mock(HttpServletResponse.class);
        this.writer = new MockWriter();
        doUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
        doUpdate("DELETE FROM PRODUCT");
        try {
            when(mockResponse.getWriter()).thenReturn(this.writer);
        } catch (IOException e) {
            System.err.println("Can't mock writer: " + e.getMessage());
        }
        this.servlet = create();
    }

    protected void doUpdate(final String query) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Could not do update: " + e.getMessage());
        }
    }

    protected abstract T create();
}
