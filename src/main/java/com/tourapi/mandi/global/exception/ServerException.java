package com.tourapi.mandi.global.exception;

import com.tourapi.mandi.global.util.ApiUtils;
import org.springframework.http.HttpStatus;

public abstract class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return null;
    }

    public HttpStatus status() {
        return null;
    }
}

