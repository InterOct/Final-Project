package by.epam.eshop.service.impl;

import by.epam.eshop.dao.CategoryDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.impl.CategoryDAOImpl;
import by.epam.eshop.entity.Category;
import by.epam.eshop.service.CategoryService;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryServiceImpl() {
    }

    public static CategoryService getInstance() {
        return CategoryServiceImpl.Holder.HOLDER_INSTANCE;
    }

    @Override
    public boolean addCategory(Category category) throws ServiceException {
        if (Validator.categoryValidate(category)) {
            CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();
            try {
                return categoryDAO.add(category);
            } catch (DAOException e) {
                throw new ServiceException("Error access database, while adding category", e);
            }
        }
        return false;
    }

    @Override
    public List<Category> getAll() throws ServiceException {
        CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();
        try {
            return categoryDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting category", e);
        }
    }

    @Override
    public boolean updateCategory(Category category) throws ServiceException {
        CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();
        try {
            return categoryDAO.update(category);
        } catch (DAOException e) {
            throw new ServiceException("Error updating category", e);
        }
    }

    @Override
    public boolean removeCategory(Integer id) throws ServiceException {
        CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();
        try {
            return categoryDAO.remove(id);
        } catch (DAOException e) {
            throw new ServiceException("Error removing category", e);
        }
    }

    private static class Holder {
        private static final CategoryService HOLDER_INSTANCE = new CategoryServiceImpl();
    }

    private static class Validator {
        public static boolean categoryValidate(Category category) {
            if (category.getName().isEmpty() || category.getName().length() > 50) {
                return false;
            }
            return !(category.getDescription().isEmpty() || category.getDescription().length() > 200);
        }
    }
}
