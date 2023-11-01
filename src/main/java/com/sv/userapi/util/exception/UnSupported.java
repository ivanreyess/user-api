package com.sv.userapi.util.exception;

import java.io.Serializable;

public class UnSupported extends BaseException implements Serializable {


    public UnSupported(String message, String title) {
        super(message, title);
    }

    public UnSupported(String message, String title, Throwable cause) {
        super(message, title, cause);
    }
}
