package user;

import org.springframework.stereotype.Service;
import utils.DatabaseConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service
public interface iUserService {

    public User getUser(Integer id) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public void saveUser(User user) throws SQLException;
    public void updateUser(User user) throws SQLException;
    public void deleteUser(Integer id) throws SQLException;

}
