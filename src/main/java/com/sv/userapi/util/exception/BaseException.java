package com.sv.userapi.util.exception;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class BaseException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String detail;
    private final transient Map<String, Object> properties ;

    public BaseException(String message, String title) {
        super(message);
        this.properties = new HashMap<>();
        this.detail = message;
        this.title = title;
    }

    public BaseException(String message, String title, Throwable cause) {
        super(message, cause);
        this.properties = new HashMap<>();
        this.detail = message;
        this.title = title;
    }

    /**
     * @return this  {@linkplain BaseException} to add property for {@linkplain org.springframework.http.ProblemDetail}
     * */
    public BaseException setProperty(String name, @Nullable Object value){
        properties.put(name, value);
        return this;
    }

    /**
     * @return title {@linkplain String} for {@linkplain org.springframework.http.ProblemDetail}
     * */
    public String getTitle() {
        return title;
    }

    /**
     * @return detail {@linkplain String} for {@linkplain org.springframework.http.ProblemDetail}
     * */
    public String getDetail() {
        return detail;
    }

    /**
     * @return properties {@linkplain String} for {@linkplain org.springframework.http.ProblemDetail}
     * */
    public Map<String, Object> getProperties() {
        return properties;
    }

}
