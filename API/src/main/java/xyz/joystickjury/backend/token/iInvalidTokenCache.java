package xyz.joystickjury.backend.token;

public interface iInvalidTokenCache<T> {
    public void addToken(T token);
    public void removeToken(T token);
    public boolean containsToken(T token);
}
