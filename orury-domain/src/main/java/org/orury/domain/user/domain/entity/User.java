package org.orury.domain.user.domain.entity;

import org.orury.domain.base.db.AuditingField;
import org.orury.domain.global.constants.NumberConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class User extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sign_up_type", nullable = false)
    private int signUpType;

    @Column(name = "gender", nullable = false)
    private int gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "is_deleted")
    private int isDeleted;

    private User(Long id, String email, String nickname, String password, int signUpType, int gender, LocalDate birthday, String profileImage, LocalDateTime createdAt, LocalDateTime updatedAt, int isDeleted) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.signUpType = signUpType;
        this.gender = gender;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public static User of(Long id, String email, String nickname, String password, int signUpType, int gender, LocalDate birthday, String profileImage, LocalDateTime createdAt, LocalDateTime updatedAt, int isDeleted) {
        return new User(id, email, nickname, password, signUpType, gender, birthday, profileImage, createdAt, updatedAt, isDeleted);
    }

    public User delete(String defaultImage) {
        this.profileImage = defaultImage;
        this.isDeleted = NumberConstants.IS_DELETED;
        return this;
    }
}