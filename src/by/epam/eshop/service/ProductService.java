package by.epam.eshop.service;

import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.Product;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product) throws ServiceException;

    Product getProductById(int id) throws ServiceException;

    Page getPage(int offset, int numberOfRows) throws ServiceException;

    Page getPageByCategory(int offset, int numberOfRows, String categoryName) throws ServiceException;

    List<Product> getAll() throws ServiceException;

    List<Product> getProductsByCategory(String categoryName) throws ServiceException;

    boolean updateProduct(Product product) throws ServiceException;

    boolean removeProduct(Integer id) throws ServiceException;
}
