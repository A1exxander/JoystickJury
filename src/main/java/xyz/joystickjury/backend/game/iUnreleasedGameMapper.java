package xyz.joystickjury.backend.game;

import xyz.joystickjury.backend.utils.iMapper;


public interface iUnreleasedGameMapper extends iMapper<UnreleasedGame, UnreleasedGameDTO> {
    public SimpleGameDTO entityToSimpleDTO(UnreleasedGame entityObject);
}
