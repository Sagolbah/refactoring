package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;
import ru.akirakozov.sd.refactoring.MockWriter;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QueryServletTest extends BaseServerTestCase<QueryServlet> {
    @Override
    protected QueryServlet create() {
        return new QueryServlet(mockDatabaseService);
    }

    @Test
    public void testNoElements() throws SQLException, IOException {
        when(mockDatabaseService.getMax()).thenReturn(Optional.empty());
        when(mockDatabaseService.getMin()).thenReturn(Optional.empty());
        when(mockRequest.getParameter("command")).thenReturn("max");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with max price: </h1></body></html>" + System.lineSeparator(), writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("min");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with min price: </h1></body></html>" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testUnknown() {
        when(mockRequest.getParameter("command")).thenReturn("hehe");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("Unknown command: hehe" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testStats() throws SQLException, IOException {
        when(mockDatabaseService.getMax()).thenReturn(Optional.of(new Product("a", 15)));
        when(mockDatabaseService.getMin()).thenReturn(Optional.of(new Product("b", 5)));
        when(mockDatabaseService.count()).thenReturn(3);
        when(mockDatabaseService.getPricesSum()).thenReturn(30);
        when(mockRequest.getParameter("command")).thenReturn("max");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with max price: </h1>a\t15</br></body></html>" + System.lineSeparator(), writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("min");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with min price: </h1>b\t5</br></body></html>" + System.lineSeparator(), writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("sum");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>Summary price: 30</body></html>" + System.lineSeparator(), writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("count");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>Number of products: 3</body></html>" + System.lineSeparator(), writer.toString());
    }

    private void refreshWriter() throws IOException {
        writer = new MockWriter();
        when(mockResponse.getWriter()).thenReturn(writer);
    }
}
