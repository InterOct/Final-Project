package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Page;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, K extends Serializable> {
    Page getPage(int offset, int numberOfRows) throws DAOException;
    List<T> getAll() throws DAOException;
    boolean add(T entity) throws DAOException;
    boolean update(T entity) throws DAOException;

    boolean remove(K id) throws DAOException;
}
