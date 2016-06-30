package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.Product;

import java.util.List;

public interface ProductDAO extends GenericDAO<Product, Integer> {

    Page getPageByCategory(int offset, int numberOfRows, String categoryName) throws DAOException;

    Product getProductById(int id) throws DAOException;

    List<Product> getProductsByCategory(String categoryName) throws DAOException;
}
