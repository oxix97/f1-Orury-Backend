package org.fastcampus.orurydomain.post.dto;

import org.fastcampus.orurydomain.post.db.model.PostLikePK;

public record PostLikePKDto(
        Long userId,
        Long postId
) {
    public static PostLikePKDto of(
            Long userId,
            Long postId
    ) {
        return new PostLikePKDto(
                userId,
                postId
        );
    }

    public static PostLikePKDto from(PostLikePK entity) {
        return PostLikePKDto.of(
                entity.getUserId(),
                entity.getPostId()
        );
    }

    public PostLikePK toEntity() {
        return PostLikePK.of(
                userId, postId
        );
    }
}
