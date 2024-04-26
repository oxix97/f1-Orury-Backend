package org.orury.client.user.interfaces.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.orury.client.global.IdIdentifiable;
import org.orury.domain.global.constants.NumberConstants;
import org.orury.domain.review.domain.dto.ReviewDto;

import java.time.LocalDateTime;
import java.util.List;

public record MyReviewResponse(
        Long id,
        String content,
        List<String> images,
        float score,
        List<ReviewReactionCount> reviewReactionCount,
        String myReaction,
        Writer writer,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime updatedAt,
        boolean isMine
) implements IdIdentifiable {
    public static MyReviewResponse of(ReviewDto reviewDto, int myReaction) {

        List<MyReviewResponse.ReviewReactionCount> reviewReactionCount = List.of(
                new MyReviewResponse.ReviewReactionCount("wantToGo", reviewDto.wantToGoCount()),
                new MyReviewResponse.ReviewReactionCount("helped", reviewDto.helpedCount()),
                new MyReviewResponse.ReviewReactionCount("great", reviewDto.greatCount()),
                new MyReviewResponse.ReviewReactionCount("funny", reviewDto.funnyCount())
        );

        String myReactionType = mapReactionType(myReaction);

        Writer writer = new Writer(reviewDto.userDto()
                .id(), reviewDto.userDto()
                .nickname(),
                reviewDto.userDto().profileImage()
        );

        return new MyReviewResponse(
                reviewDto.id(),
                reviewDto.content(),
                reviewDto.images(),
                reviewDto.score(),
                reviewReactionCount,
                myReactionType,
                writer,
                reviewDto.createdAt(),
                reviewDto.updatedAt(),
                true
        );
    }

    private static String mapReactionType(int reaction) {
        return switch (reaction) {
            case NumberConstants.REACTION_WANT_TO_GO -> "wantToGo";
            case NumberConstants.REACTION_HELPED -> "helped";
            case NumberConstants.REACTION_GREAT -> "great";
            case NumberConstants.REACTION_FUNNY -> "funny";
            default -> null;
        };
    }

    private record ReviewReactionCount(String type, int count) {
    }

    private record Writer(Long id, String nickname, String profileImage) {
    }
}
