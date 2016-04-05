package com.lkl.springboot.exception;

public class RestException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 9061481993630020292L;

    public RestException(String message) {
        super(message);
    }

}
