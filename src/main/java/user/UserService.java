package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements iUserService {

    @Autowired
    UserDAO userDao;

}
