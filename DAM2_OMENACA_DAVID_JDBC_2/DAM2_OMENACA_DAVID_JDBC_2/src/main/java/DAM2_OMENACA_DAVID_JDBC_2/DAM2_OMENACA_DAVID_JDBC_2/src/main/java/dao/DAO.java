package dao;

import beans.CentroForense;

import java.util.List;

public interface DAO<T> {

    int add(T bean);

    int update(T bean);

    int delete(int id);

    T findById(int id);

    List<T> findAll();
}