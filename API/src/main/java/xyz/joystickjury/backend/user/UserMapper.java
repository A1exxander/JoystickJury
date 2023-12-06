package xyz.joystickjury.backend.user;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;

import javax.validation.constraints.NotNull;

@Component
public class UserMapper implements iMapper<User, UserDTO> {

    @Override
    public UserDTO entityToDTO(@NotNull User user) {
        return new UserDTO(user.getUserID(), user.getDisplayName(), user.getProfilePictureLink(), user.getProfileDescription(), user.getAccountType());
    }

    @Override
    public User dtoToEntity(@NotNull UserDTO userDTO) {
        return new User(userDTO.getUserID(), userDTO.getDisplayName(), userDTO.getProfilePictureLink(), userDTO.getProfileDescription(), null, userDTO.getAccountType());
    }

}

