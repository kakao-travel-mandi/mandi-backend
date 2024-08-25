package com.tourapi.mandi.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "프로필 변경 요청 DTO")
public record ProfileUpdateRequestDto(
        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @Schema(description = "새로운 닉네임 값", nullable = true)
        @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 영문, 숫자, 한글만 사용 가능합니다.")
        String nickname,

        @Schema(description = "새로운 한줄소개 값", nullable = true)
        @Size(max = 40, message = "한줄소개는 40자 이하여야 합니다.")
        String description
) {
}