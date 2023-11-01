package com.sv.userapi.util.exception;

import java.io.Serializable;

public class BadRequest extends BaseException implements Serializable {

    public BadRequest(String message, String title) {
        super(message, title);
    }

    public BadRequest(String message, String title, Throwable cause) {
        super(message, title, cause);
    }
}
