package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Product;

import java.util.List;

public interface ProductDAO extends GenericDAO<Product> {

    List<Product> getProductsByCategory(String categoryName) throws DAOException;
}
