package com.example.edoc.DAO;

import java.util.List;
import java.util.Optional;

public interface CRUD <T,pk>{
    boolean create(T t);
    boolean update(T t);
    boolean delete(pk pk);
    Optional<T> findById(pk pk);
    List<T> getAll();

}
