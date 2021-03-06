package ru.shipcollision.api.exceptions;

/**
 * Неверный логин или пароль.
 */
public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException() {
        super("Неверный логин или пароль");
    }

    public InvalidCredentialsException(String error) {
        super(error);
    }

    public InvalidCredentialsException(Throwable throwable) {
        super(throwable.getMessage());
    }
}
