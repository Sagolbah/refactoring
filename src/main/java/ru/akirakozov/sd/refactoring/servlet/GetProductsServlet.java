package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.ProductDatabaseService;
import ru.akirakozov.sd.refactoring.util.HtmlUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {
    private final ProductDatabaseService productDatabaseService;

    public GetProductsServlet(ProductDatabaseService productDatabaseService) {
        this.productDatabaseService = productDatabaseService;
    }

    @Override
    protected void getImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        List<Product> products = productDatabaseService.getAll();
        final String result = products.stream().map(s -> s.toString() + "</br>").collect(Collectors.joining());
        HtmlUtils.renderHtmlPage(response, result);
    }
}
