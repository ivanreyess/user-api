package com.sv.userapi.controller.exception;

import com.sv.userapi.util.exception.BadRequest;
import com.sv.userapi.util.exception.NotFound;
import com.sv.userapi.util.exception.UnSupported;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {


    /**
     * @param unSupported {@linkplain  UnSupported}
     * @param req         {@linkplain  HttpServletRequest}
     * @return problemDetail {@linkplain ProblemDetail} response for when a controlled error occurs
     */
    @ExceptionHandler(UnSupported.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ProblemDetail handlerUnSupported(UnSupported unSupported, HttpServletRequest req) throws URISyntaxException {
        log.warn("handlerUnSupported", unSupported);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, unSupported.getMessage());
        problemDetail.setTitle(unSupported.getTitle());
        problemDetail.setDetail(unSupported.getDetail());
        problemDetail.setType(new URI(req.getRequestURL().toString()));
        unSupported.getProperties().forEach(problemDetail::setProperty);
        return problemDetail;
    }

    /**
     * @param notFound {@linkplain  NotFound}
     * @param req      {@linkplain  HttpServletRequest}
     * @return problemDetail {@linkplain ProblemDetail} response for when a controlled error occurs
     */
    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handlerNotFount(NotFound notFound, HttpServletRequest req) throws URISyntaxException {
        log.warn("handlerNotFount", notFound);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, notFound.getMessage());
        problemDetail.setTitle(notFound.getTitle());
        problemDetail.setDetail(notFound.getDetail());
        problemDetail.setType(new URI(req.getRequestURL().toString()));
        notFound.getProperties().forEach(problemDetail::setProperty);
        return problemDetail;
    }

    /**
     * @param badRequest {@linkplain  NotFound}
     * @param req        {@linkplain  HttpServletRequest}
     * @return problemDetail {@linkplain ProblemDetail} response for when a controlled error occurs
     */
    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handlerBadRequest(BadRequest badRequest, HttpServletRequest req) throws URISyntaxException {
        log.warn("handlerBadRequest", badRequest);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, badRequest.getMessage());
        problemDetail.setTitle(badRequest.getTitle());
        problemDetail.setDetail(badRequest.getDetail());
        problemDetail.setType(new URI(req.getRequestURL().toString()));
        badRequest.getProperties().forEach(problemDetail::setProperty);
        return problemDetail;
    }

}
