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
        return new QueryServlet();
    }


    @Test
    public void testNoElements() throws IOException {
        when(mockRequest.getParameter("command")).thenReturn("max");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "</body></html>\n", writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("min");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "</body></html>\n", writer.toString());
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
        assertEquals("<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "a\t15</br>\n" +
                "</body></html>\n", writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("min");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "b\t5</br>\n" +
                "</body></html>\n", writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("sum");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "Summary price: \n" +
                "30\n" +
                "</body></html>\n", writer.toString());
        refreshWriter();
        when(mockRequest.getParameter("command")).thenReturn("count");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "Number of products: \n" +
                "3\n" +
                "</body></html>\n", writer.toString());
    }

    private void refreshWriter() throws IOException {
        writer = new MockWriter();
        when(mockResponse.getWriter()).thenReturn(writer);
    }
}
