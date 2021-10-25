package ru.akirakozov.sd.refactoring.service;


import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDatabaseService extends AbstractDatabaseService<Product> {

    public void createEmptyDatabase() throws SQLException {
        doUpdateQuery("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void clear() throws SQLException {
        doUpdateQuery("DROP TABLE PRODUCT");
    }

    public List<Product> getAll() throws SQLException {
        return select("*", "");
    }

    public Optional<Product> getMax() throws SQLException {
        return getOneElement(select("*", "ORDER BY PRICE DESC LIMIT 1"));
    }

    public Optional<Product> getMin() throws SQLException {
        return getOneElement(select("*", "ORDER BY PRICE LIMIT 1"));
    }

    public int getPricesSum() throws SQLException {
        return selectInteger("SUM(price)", "");
    }

    public int count() throws SQLException {
        return selectInteger("COUNT(*)", "");
    }

    public void insert(final Product product) throws SQLException {
        insert(Map.of("NAME", product.getName(), "PRICE", product.getPrice()));
    }

    @Override
    public String getDatabaseName() {
        return "PRODUCT";
    }

    @Override
    protected Product cast(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getString("name"), resultSet.getInt("price"));
    }

    private Optional<Product> getOneElement(final List<Product> products) {
        return products.size() == 0 ? Optional.empty() : Optional.of(products.get(0));
    }
}
