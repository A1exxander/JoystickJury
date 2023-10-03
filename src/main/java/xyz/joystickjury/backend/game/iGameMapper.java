package xyz.joystickjury.backend.game;

import xyz.joystickjury.backend.utils.iMapper;
import java.util.Set;


public interface iGameMapper extends iMapper<Game, GameDTO> {
    GameDTO entityToDTO(Game entityObject, Set<String> fields);
}
