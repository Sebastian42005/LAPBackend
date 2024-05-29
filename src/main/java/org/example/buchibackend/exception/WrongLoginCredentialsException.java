package org.example.buchibackend.exception;

public class WrongLoginCredentialsException extends RuntimeException {
    public WrongLoginCredentialsException() {
        super("Wrong login credentials");
    }
}
