package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends BaseServerTestCase<AddProductServlet> {
    @Override
    protected AddProductServlet create() {
        return new AddProductServlet(databaseService);
    }

    @Test
    public void testAdd() throws IOException {
        when(mockRequest.getParameter("name")).thenReturn("iphone");
        when(mockRequest.getParameter("price")).thenReturn("300");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("OK" + System.lineSeparator(), writer.toString());
    }
}
