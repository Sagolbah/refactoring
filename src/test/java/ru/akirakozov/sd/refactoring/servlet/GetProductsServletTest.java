package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseServerTestCase;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class GetProductsServletTest extends BaseServerTestCase<GetProductsServlet> {
    @Override
    protected GetProductsServlet create() {
        return new GetProductsServlet();
    }

    @Test
    public void testEmpty() throws IOException {

        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n</body></html>" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testMultiple() throws IOException {
        doUpdate("INSERT INTO PRODUCT(NAME, PRICE) VALUES\n" +
                "(\"a\", 10), (\"b\", 20), (\"c\", 5)");
        servlet.doGet(mockRequest, mockResponse);
        assertEquals("<html><body>\n" +
                "a\t10</br>\n" +
                "b\t20</br>\n" +
                "c\t5</br>\n" +
                "</body></html>\n", writer.toString());

    }

}
