package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GetProductsServletTest extends BaseServerTestCase<GetProductsServlet> {
    @Override
    protected GetProductsServlet create() {
        return new GetProductsServlet(mockDatabaseService);
    }

    @Test
    public void testEmpty() throws SQLException {
        when(mockDatabaseService.getAll()).thenReturn(List.of());
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body></body></html>" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testMultiple() throws SQLException {
        final List<Product> products = new ArrayList<>();
        products.add(new Product("a", 10));
        products.add(new Product("b", 20));
        products.add(new Product("c", 5));
        when(mockDatabaseService.getAll()).thenReturn(products);
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>a\t10</br>b\t20</br>c\t5</br></body></html>" + System.lineSeparator(), writer.toString());

    }

}
