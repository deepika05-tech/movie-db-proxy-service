package com.moviedbproxy.exception;

/**
 * Created by deepika on 27-11-2019.
 */
public class CustomException {

    private String errorMessage;

    public CustomException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
