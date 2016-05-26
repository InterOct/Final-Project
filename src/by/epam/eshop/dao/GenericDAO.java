package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Entity;

import java.util.List;

public interface GenericDAO<T extends Entity> {
    List<T> getAll() throws DAOException;
    boolean add(T entity) throws DAOException;
    boolean update(T entity) throws DAOException;
    boolean remove(T entity) throws DAOException;
}
