package com.tourapi.mandi.domain.trekking.dto;

import com.tourapi.mandi.domain.course.entity.Coordinate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TrekkingStartRequestDto(

	@NotNull(message = "좌표는 필수 입력 사항입니다.")
	@Schema(description = "사용자 위치 좌표")
	Coordinate userLocation
) {
}