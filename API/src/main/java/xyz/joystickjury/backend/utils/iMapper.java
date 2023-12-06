package xyz.joystickjury.backend.utils;

public interface iMapper <Entity, DTO> {
    public Entity dtoToEntity(DTO dtoObject);
    public DTO entityToDTO(Entity entityObject);
}
