package ru.akirakozov.sd.refactoring;

import org.junit.Before;
import ru.akirakozov.sd.refactoring.service.ProductDatabaseService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BaseServerTestCase<T extends HttpServlet> {
    protected HttpServletRequest mockRequest;
    protected HttpServletResponse mockResponse;
    protected ProductDatabaseService mockDatabaseService;
    protected MockWriter writer;
    protected T servlet;

    @Before
    public void init() {
        this.mockRequest = mock(HttpServletRequest.class);
        this.mockResponse = mock(HttpServletResponse.class);
        this.mockDatabaseService = mock(ProductDatabaseService.class);
        this.writer = new MockWriter();
        try {
            when(mockResponse.getWriter()).thenReturn(this.writer);
        } catch (IOException e) {
            System.err.println("Can't mock writer: " + e.getMessage());
        }
        this.servlet = create();
    }

    protected abstract T create();
}
