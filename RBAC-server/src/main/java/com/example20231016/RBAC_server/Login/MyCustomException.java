package com.example20231016.RBAC_server.Login;


public class MyCustomException extends RuntimeException {

    public MyCustomException() {
        super();
    }

    public MyCustomException(String message) {
        super(message);
    }

    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
