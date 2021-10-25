package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.ProductDatabaseService;
import ru.akirakozov.sd.refactoring.util.HtmlUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {
    private final ProductDatabaseService productDatabaseService;

    public AddProductServlet(ProductDatabaseService productDatabaseService) {
        this.productDatabaseService = productDatabaseService;
    }

    @Override
    protected void getImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        productDatabaseService.insert(new Product(name, price));
        HtmlUtils.renderPlaintext(response, "OK");
    }
}
