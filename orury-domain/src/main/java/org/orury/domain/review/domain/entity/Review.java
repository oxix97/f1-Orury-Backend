package org.orury.domain.review.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.orury.domain.base.db.AuditingField;
import org.orury.domain.global.listener.ReviewImagesConverter;
import org.orury.domain.gym.domain.entity.Gym;
import org.orury.domain.review.domain.dto.Difficulty;
import org.orury.domain.review.domain.dto.DifficultyConverter;
import org.orury.domain.user.domain.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "review")
public class Review extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content")
    private String content;

    @Convert(converter = ReviewImagesConverter.class)
    @Column(name = "images")
    private List<String> images;

    @Column(name = "score", nullable = false)
    private float score;

    @Column(name = "want_to_go_count", nullable = false)
    private int wantToGoCount;

    @Column(name = "helped_count", nullable = false)
    private int helpedCount;

    @Column(name = "great_count", nullable = false)
    private int greatCount;

    @Column(name = "funny_count", nullable = false)
    private int funnyCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(name = "description")
    private String description;

    @Convert(converter = DifficultyConverter.class)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    public Review(
            Long id,
            String content,
            List<String> images,
            float score,
            int wantToGoCount,
            int helpedCount,
            int greatCount,
            int funnyCount,
            User user,
            Gym gym,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String description,
            Difficulty difficulty
    ) {
        this.id = id;
        this.content = content;
        this.images = images;
        this.score = score;
        this.wantToGoCount = wantToGoCount;
        this.helpedCount = helpedCount;
        this.greatCount = greatCount;
        this.funnyCount = funnyCount;
        this.user = user;
        this.gym = gym;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.difficulty = difficulty;
    }

    public static Review of(
            Long id,
            String content,
            List<String> images,
            float score,
            int wantToGoCount,
            int helpedCount,
            int greatCount,
            int funnyCount,
            User user,
            Gym gym,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String description,
            Difficulty difficulty
    ) {
        return new Review(
                id,
                content,
                images,
                score,
                wantToGoCount,
                helpedCount,
                greatCount,
                funnyCount,
                user,
                gym,
                createdAt,
                updatedAt,
                description,
                difficulty
        );
    }
}
