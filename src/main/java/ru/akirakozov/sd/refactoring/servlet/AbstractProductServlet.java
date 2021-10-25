package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractProductServlet extends HttpServlet {
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            getImpl(request, response);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void getImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}
