package xyz.joystickjury.backend.user;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;

@Component
public class UserMapper implements iMapper<User, UserDTO> {

    @Override
    public UserDTO entityToDTO(User user) {
        return new UserDTO(user.getUserID(), user.getDisplayName(), user.getProfilePictureLink(), user.getProfilePictureLink(), user.getAccountType());
    }

    @Override
    public User dtoToEntity(UserDTO userDTO) {
        return new User(userDTO.getUserID(), userDTO.getDisplayName(), userDTO.getProfilePictureLink(), userDTO.getProfilePictureLink(), null, userDTO.getAccountType());
    }

}

