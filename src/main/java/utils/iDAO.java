package utils;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface iDAO<T,I> {

        T get(I id) throws SQLException;
        List<T> getAll() throws SQLException;
        void save(T t) throws SQLException;
        void update(T t) throws SQLException;
        void delete(I id) throws SQLException;
}
