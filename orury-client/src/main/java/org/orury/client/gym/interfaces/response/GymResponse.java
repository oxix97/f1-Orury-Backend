package org.orury.client.gym.interfaces.response;

import org.orury.domain.global.constants.Constants;
import org.orury.domain.gym.domain.dto.GymDto;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public record GymResponse(
        Long id,
        String name,
        String roadAddress,
        String address,
        Float scoreAverage,
        int reviewCount,
        List<String> images,
        Position position,
        String brand,
        String phoneNumber,
        String kakaoMapLink,
        String instagramLink,
        String homepageLink,
        String settingDay,
        List<Map.Entry<String, String>> businessHours,
        boolean doingBusiness,
        boolean isLike,
        String gymType,
        List<GymReviewStatistics.TotalReviewChart.ReviewCount> barChartData,
        List<GymReviewStatistics.MonthlyReviewChart.MonthlyReviewCount> lineChartData
) {
    public static GymResponse of(
            GymDto gymDto,
            boolean doingBusiness,
            boolean isLike,
            GymReviewStatistics gymReviewStatistics
    ) {

        // 요일별 순서를 정의
        Map<String, Integer> dayOrder = Map.of(
                "월", 1,
                "화", 2,
                "수", 3,
                "목", 4,
                "금", 5,
                "토", 6,
                "일", 7
        );

        List<Map.Entry<String, String>> koreanBusinessHours = gymDto.businessHours().entrySet().stream()
                .map(entry -> {
                    // 운영시간이 null인 값은 null로 보내주기
                    String key = entry.getKey() == null ? null : entry.getKey().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
                    return new AbstractMap.SimpleEntry<>(key, entry.getValue());
                })
                .sorted(Comparator.comparingInt(entry -> dayOrder.getOrDefault(entry.getKey(), 0)))
                .collect(Collectors.toList());

        return new GymResponse(
                gymDto.id(),
                gymDto.name(),
                gymDto.roadAddress(),
                gymDto.address(),
                (gymDto.reviewCount() == 0) ? 0 : Math.round(gymDto.totalScore() * 10 / gymDto.reviewCount()) / 10f,
                gymDto.reviewCount(),
                gymDto.images(),
                Position.of(gymDto.latitude(), gymDto.longitude()),
                gymDto.brand(),
                gymDto.phoneNumber(),
                Constants.KAKAO_MAP_BASE_URL.getMessage() + gymDto.kakaoId(),
                gymDto.instagramLink(),
                gymDto.homepageLink(),
                gymDto.settingDay(),
                koreanBusinessHours,
                doingBusiness,
                isLike,
                gymDto.gymType().getDescription(),
                gymReviewStatistics.barChartData(),
                gymReviewStatistics.lineChartData()
        );
    }

    private record Position(
            double latitude,
            double longitude
    ) {
        private static Position of(double latitude, double longitude) {
            return new Position(latitude, longitude);
        }
    }
}

