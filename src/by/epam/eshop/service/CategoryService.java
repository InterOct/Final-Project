package by.epam.eshop.service;

import by.epam.eshop.entity.Category;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Aspire on 04.05.2016.
 */
public interface CategoryService {
    boolean addCategory(Category category) throws ServiceException;

    List<Category> getAll() throws ServiceException;

    boolean updateCategory(Category category) throws ServiceException;

    boolean removeCategory(Integer id) throws ServiceException;
}
