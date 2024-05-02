package org.orury.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.Getter;
import org.orury.domain.global.constants.NumberConstants;
import org.orury.domain.user.domain.dto.*;
import org.orury.domain.user.domain.entity.Report;
import org.orury.domain.user.domain.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDomainFixture {

    private UserDomainFixture() {
        throw new IllegalStateException("Utility class");
    }

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Getter
    @Builder
    public static class TestUser {
        private @Builder.Default Long id = 117143L;
        private @Builder.Default String email = "테스트이메일";
        private @Builder.Default String nickname = "테스트닉네임";
        private @Builder.Default String password = "테스트비밀번호";
        private @Builder.Default int signUpType = 1;
        private @Builder.Default int gender = NumberConstants.MALE;
        private @Builder.Default LocalDate birthday = LocalDate.now().minusYears(25);
        private @Builder.Default String profileImage = "프로필이미지";
        private @Builder.Default UserStatus status = UserStatus.ENABLE;
        private @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
        private @Builder.Default LocalDateTime updatedAt = LocalDateTime.now();

        public static TestUser.TestUserBuilder createUser() {
            return TestUser.builder();
        }

        public static TestUser.TestUserBuilder createUser(Long userId) {
            return TestUser.builder().id(userId);
        }

        public User get() {
            return mapper.convertValue(this, User.class);
        }
    }

    @Getter
    @Builder
    public static class TestUserDto {
        private @Builder.Default Long id = 672622L;
        private @Builder.Default String email = "testEamil";
        private @Builder.Default String nickname = "testNickname";
        private @Builder.Default String password = "testPassword";
        private @Builder.Default int signUpType = 2;
        private @Builder.Default int gender = NumberConstants.FEMALE;
        private @Builder.Default LocalDate birthday = LocalDate.now().minusYears(24);
        private @Builder.Default String profileImage = "testProfileImage";
        private @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
        private @Builder.Default LocalDateTime updatedAt = LocalDateTime.now();
        private @Builder.Default UserStatus status = UserStatus.ENABLE;

        public static TestUserDto.TestUserDtoBuilder createUserDto() {
            return TestUserDto.builder();
        }

        public static TestUserDto.TestUserDtoBuilder createUserDto(Long userId) {
            return TestUserDto.builder().id(userId);
        }

        public UserDto get() {
            return mapper.convertValue(this, UserDto.class);
        }
    }

    @Getter
    @Builder
    public static class TestReport {
        private @Builder.Default Long id = 1171231L;
        private @Builder.Default Long reporteeId = 117143L;
        private @Builder.Default Long reporterId = 1123451L;
        private @Builder.Default int type = 1;
        private @Builder.Default int reason = 1;
        private @Builder.Default Long targetId = 117143L;
        private @Builder.Default String description = "테스트신고내용";
        private @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
        private @Builder.Default LocalDateTime updatedAt = LocalDateTime.now();

        public static TestReport.TestReportBuilder createReport() {
            return TestReport.builder();
        }

        public static TestReport.TestReportBuilder createReport(Long reportId) {
            return TestReport.builder().id(reportId);
        }

        public Report get() {
            return mapper.convertValue(this, Report.class);
        }
    }

    @Getter
    @Builder
    public static class TestReportDto {
        private @Builder.Default Long id = 125521L;
        private @Builder.Default ReportType reportType = ReportType.POST;
        private @Builder.Default UserDto reporterDto = TestUserDto.createUserDto(1123451L).build().get();
        private @Builder.Default UserDto reporteeDto = TestUserDto.createUserDto(2512568L).build().get();
        private @Builder.Default ReportInfo reportInfo = ReportInfo.CONTAIN_PERSONAL_INFO;
        private @Builder.Default Long targetId = 12512L;

        public static TestReportDto.TestReportDtoBuilder createReportDto() {
            return TestReportDto.builder();
        }

        public static TestReportDto.TestReportDtoBuilder createReportDto(Long reportId, Long reporterId, Long reporteeId) {
            return TestReportDto.builder()
                    .id(reportId)
                    .reporterDto(TestUserDto.createUserDto(reporterId).build().get())
                    .reporteeDto(TestUserDto.createUserDto(reporteeId).build().get());
        }

        public ReportDto get() {
            return mapper.convertValue(this, ReportDto.class);
        }
    }
}
