package com.tourapi.mandi.domain.user;


import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostExceptionStatus implements BaseExceptionStatus {

    POST_NOT_FOUND("해당하는 게시글이 없습니다.", 400, "24000");

    @Getter
    private final String message;
    @Getter
    private final int status;
    @Getter
    private final String errorCode;

}