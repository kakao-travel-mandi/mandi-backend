package com.tourapi.mandi.domain.badge.util;

import com.tourapi.mandi.domain.badge.dto.BadgeResponseDto;
import com.tourapi.mandi.domain.badge.entity.Badge;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BadgeMapper {

    private static final String DEFAULT_IMG_URL = "DEFAULT_IMG_URL";

    public static BadgeResponseDto toBadgeResponseDto(Badge badge, boolean isObtained) {
        BadgeResponseDto.BadgeResponseDtoBuilder badgeBuilder = BadgeResponseDto.builder()
                .id(badge.getBadgeId())
                .name(badge.getName())
                .requirements(badge.getRequirements());

        if (isObtained) {
            return badgeBuilder.imgUrl(badge.getImgUrl()).build();
        }
        return badgeBuilder.imgUrl(DEFAULT_IMG_URL).build();
    }
}