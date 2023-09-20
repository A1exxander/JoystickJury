package xyz.joystickjury.backend.utils;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface iDAO<I, T> {

        public T get(I id) throws SQLException;
        public List<T> getAll() throws SQLException;
        public void save(T t) throws SQLException;
        public void update(T t) throws SQLException;
        public void delete(I id) throws SQLException;

}
