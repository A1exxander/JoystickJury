package xyz.joystickjury.backend.game;

import xyz.joystickjury.backend.utils.iMapper;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface iGameMapper extends iMapper<Game, GameDTO> {
    public List<GameDTO> entityToDTOs(@NotNull List<Game> games); // Probably should be inside of the iMapper interface, since it should be possible to get all for each type of resource
}
