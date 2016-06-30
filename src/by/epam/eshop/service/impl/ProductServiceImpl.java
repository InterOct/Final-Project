package by.epam.eshop.service.impl;

import by.epam.eshop.dao.ProductDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.impl.ProductDAOImpl;
import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.Product;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return ProductServiceImpl.Holder.HOLDER_INSTANCE;
    }

    @Override
    public boolean addProduct(Product product) throws ServiceException {
        if (Validator.productValidate(product)) {
            ProductDAO productDAO = ProductDAOImpl.getInstance();
            try {
                return productDAO.add(product);
            } catch (DAOException e) {
                throw new ServiceException("Error access database, while adding product", e);
            }
        }
        return false;
    }

    @Override
    public List<Product> getAll() throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting products", e);
        }
    }

    @Override
    public Product getProductById(int id) throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.getProductById(id);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting products", e);
        }
    }

    @Override
    public Page getPage(int offset, int numberOfRows) throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.getPage(offset, numberOfRows);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting products", e);
        }
    }

    @Override
    public Page getPageByCategory(int offset, int numberOfRows, String categoryName) throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.getPageByCategory(offset, numberOfRows, categoryName);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting products", e);
        }
    }


    @Override
    public List<Product> getProductsByCategory(String categoryName) throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.getProductsByCategory(categoryName);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting products", e);
        }
    }

    @Override
    public boolean updateProduct(Product product) throws ServiceException {
        if (Validator.productValidate(product)) {
            ProductDAO productDAO = ProductDAOImpl.getInstance();
            try {
                return productDAO.update(product);
            } catch (DAOException e) {
                throw new ServiceException("Error updating product", e);
            }
        }
        return false;
    }

    @Override
    public boolean removeProduct(Integer id) throws ServiceException {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        try {
            return productDAO.remove(id);
        } catch (DAOException e) {
            throw new ServiceException("Error removing product", e);
        }
    }

    private static class Holder {
        private static final ProductService HOLDER_INSTANCE = new ProductServiceImpl();
    }

    private static class Validator {
        public static boolean productValidate(Product product) {
            if (product.getCatName().isEmpty() || product.getCatName().length() > 50) {
                return false;
            }
            if (product.getName().isEmpty() || product.getName().length() > 100) {
                return false;
            }
            if (product.getShortDescription().isEmpty() || product.getShortDescription().length() > 400) {
                return false;
            }
            if (product.getImgPath().isEmpty() || product.getImgPath().length() > 100) {
                return false;
            }
            if (product.getDescription().isEmpty() || product.getDescription().length() > 600) {
                return false;
            }
            if (product.getPrice() <= 0) {
                return false;
            }
            if (product.getDiscountPrice() <= 0) {
                return false;
            }
            return product.getPrice() >= product.getDiscountPrice();
        }
    }
}
