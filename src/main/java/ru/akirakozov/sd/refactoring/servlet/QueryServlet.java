package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.ProductDatabaseService;
import ru.akirakozov.sd.refactoring.util.HtmlUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {

    private final ProductDatabaseService productDatabaseService;

    public QueryServlet(ProductDatabaseService productDatabaseService) {
        this.productDatabaseService = productDatabaseService;
    }

    @Override
    protected void getImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String command = request.getParameter("command");

        switch (command) {
            case "max":
                renderOneItem("Product with max price: ", productDatabaseService.getMax(), response);
                break;
            case "min":
                renderOneItem("Product with min price: ", productDatabaseService.getMin(), response);
                break;
            case "sum":
                HtmlUtils.renderHtmlPage(response, "Summary price: " + productDatabaseService.getPricesSum());
                break;
            case "count":
                HtmlUtils.renderHtmlPage(response, "Number of products: " + productDatabaseService.count());
                break;
            default:
                HtmlUtils.renderPlaintext(response, "Unknown command: " + command);
        }
    }

    private void renderOneItem(String title, Optional<Product> product, HttpServletResponse response) throws IOException {
        StringBuilder result = new StringBuilder().append("<h1>").append(title).append("</h1>");
        product.ifPresent(value -> result.append(value).append("</br>"));
        HtmlUtils.renderHtmlPage(response, result.toString());
    }

}
