package com.sv.userapi.util.exception;

import java.io.Serializable;

public class NotFound extends BaseException implements Serializable {


    public NotFound(String message, String title) {
        super(message, title);
    }

    public NotFound(String message, String title, Throwable cause) {
        super(message, title, cause);
    }
}