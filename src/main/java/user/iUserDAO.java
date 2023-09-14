package user;

import org.springframework.stereotype.Repository;
import utils.iDAO;

import java.sql.SQLException;

public interface iUserDAO extends iDAO<User, Integer> {
    User getByEmail(String email) throws SQLException;
}
