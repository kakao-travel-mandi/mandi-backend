package com.tourapi.mandi.global.exception;

import com.tourapi.mandi.global.util.ApiUtils;
import org.springframework.http.HttpStatus;

//Bad Gateway에러 시 사용
public class Exception502 extends ServerException {
    public Exception502(BaseExceptionStatus exception) {
        super(exception.getMessage());
    }

    @Override
    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.BAD_GATEWAY.value(), "05020");
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_GATEWAY;
    }
}
