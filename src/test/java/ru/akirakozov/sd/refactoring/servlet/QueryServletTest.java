package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;
import ru.akirakozov.sd.refactoring.MockWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QueryServletTest extends BaseServerTestCase<QueryServlet> {
    @Override
    protected QueryServlet create() {
        return new QueryServlet(databaseService);
    }


    @Test
    public void testNoElements() throws IOException {
        when(mockRequest.getParameter("command")).thenReturn("max");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with max price: </h1></body></html>" + System.lineSeparator(), writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("min");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body><h1>Product with min price: </h1></body></html>" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testUnknown() throws IOException {
        when(mockRequest.getParameter("command")).thenReturn("hehe");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("Unknown command: hehe" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testStats() throws IOException {
        doUpdate("INSERT INTO PRODUCT(NAME, PRICE) VALUES\n" +
                "(\"a\", 15), (\"b\", 5), (\"c\", 10)");
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
