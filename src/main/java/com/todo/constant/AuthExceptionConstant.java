package com.todo.constant;

public class AuthExceptionConstant {
    public static final String[] JWT_MISSING = {
        "auth.jwt.missing",
        "JWT token is missing"
    };

    public static final String[] JWT_INVALID = {
        "auth.jwt.invalid",
        "JWT token is invalid"
    };

    public static final String[] JWT_EXPIRED = {
        "auth.jwt.expired",
        "JWT token is expired"
    };

    public static final String[] JWT_NO_PERMISSION = {
        "auth.user.noPermission",
        "User is no permission"
    };
}
