package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends BaseServerTestCase<AddProductServlet> {
    @Override
    protected AddProductServlet create() {
        return new AddProductServlet(mockDatabaseService);
    }

    @Test
    public void testAdd() {
        when(mockRequest.getParameter("name")).thenReturn("iphone");
        when(mockRequest.getParameter("price")).thenReturn("300");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("OK" + System.lineSeparator(), writer.toString());
    }
}
