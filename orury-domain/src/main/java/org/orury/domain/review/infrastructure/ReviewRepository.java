package org.orury.domain.review.infrastructure;

import org.orury.domain.review.domain.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGymId(Long gymId);

    boolean existsByUserIdAndGymId(Long userId, Long gymId);

    List<Review> findByGymIdOrderByIdDesc(Long gymId, Pageable pageable);

    List<Review> findByGymIdAndIdLessThanOrderByIdDesc(Long gymId, Long cursor, Pageable pageable);

    List<Review> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    List<Review> findByUserIdAndIdLessThanOrderByIdDesc(Long userId, Long cursor, Pageable pageable);

    @Modifying
    @Query("update review r set " +
            "r.wantToGoCount = CASE WHEN :reactionType = 1 THEN r.wantToGoCount + 1 ELSE r.wantToGoCount END, " +
            "r.helpedCount = CASE WHEN :reactionType = 2 THEN r.helpedCount + 1 ELSE r.helpedCount END, " +
            "r.greatCount = CASE WHEN :reactionType = 3 THEN r.greatCount + 1 ELSE r.greatCount END, " +
            "r.funnyCount = CASE WHEN :reactionType = 4 THEN r.funnyCount + 1 ELSE r.funnyCount END " +
            "where r.id = :reviewId")
    void increaseReactionCount(@Param("reviewId") Long reviewId, @Param("reactionType") int reactionType);

    @Modifying
    @Query("update review r set " +
            "r.wantToGoCount = CASE WHEN :reactionType = 1 THEN r.wantToGoCount - 1 ELSE r.wantToGoCount END, " +
            "r.helpedCount = CASE WHEN :reactionType = 2 THEN r.helpedCount - 1 ELSE r.helpedCount END, " +
            "r.greatCount = CASE WHEN :reactionType = 3 THEN r.greatCount - 1 ELSE r.greatCount END, " +
            "r.funnyCount = CASE WHEN :reactionType = 4 THEN r.funnyCount - 1 ELSE r.funnyCount END " +
            "where r.id = :reviewId")
    void decreaseReactionCount(@Param("reviewId") Long reviewId, @Param("reactionType") int reactionType);

    @Modifying
    @Query("update review r set " +
            "r.wantToGoCount = CASE WHEN :oldReactionType = 1 THEN r.wantToGoCount - 1 WHEN :newReactionType = 1 THEN r.wantToGoCount + 1 ELSE r.wantToGoCount END, " +
            "r.helpedCount = CASE WHEN :oldReactionType = 2 THEN r.helpedCount - 1 WHEN :newReactionType = 2 THEN r.helpedCount + 1 ELSE r.helpedCount END, " +
            "r.greatCount = CASE WHEN :oldReactionType = 3 THEN r.greatCount - 1 WHEN :newReactionType = 3 THEN r.greatCount + 1 ELSE r.greatCount END, " +
            "r.funnyCount = CASE WHEN :oldReactionType = 4 THEN r.funnyCount - 1 WHEN :newReactionType = 4 THEN r.funnyCount + 1 ELSE r.funnyCount END " +
            "where r.id = :reviewId")
    void updateReactionCount(@Param("reviewId") Long reviewId, @Param("oldReactionType") int oldReactionType, @Param("newReactionType") int newReactionType);
}
