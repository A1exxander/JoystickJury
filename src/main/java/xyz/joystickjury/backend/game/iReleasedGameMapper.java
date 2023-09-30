package xyz.joystickjury.backend.game;

import xyz.joystickjury.backend.utils.iMapper;

public interface iReleasedGameMapper extends iMapper<ReleasedGame, ReleasedGameDTO> {
    public SimpleReleasedGameDTO entityToSimpleDTO(ReleasedGame entityObject);
}
