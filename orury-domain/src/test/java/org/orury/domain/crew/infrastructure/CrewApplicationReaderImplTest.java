package org.orury.domain.crew.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orury.domain.config.InfrastructureTest;
import org.orury.domain.crew.domain.entity.CrewApplication;
import org.orury.domain.user.domain.entity.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.orury.domain.CrewDomainFixture.TestCrewApplication.createCrewApplication;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Reader] 크루지원 ReaderImpl 테스트")
@ActiveProfiles("test")
class CrewApplicationReaderImplTest extends InfrastructureTest {

    @DisplayName("크루id와 유저id를 받아, 크루지원 존재 여부를 확인한다.")
    @Test
    void existsByCrewIdAndUserId() {
        // given & when
        crewApplicationReader.existsByCrewIdAndUserId(anyLong(), anyLong());

        // then
        then(crewApplicationRepository).should(only())
                .existsByCrewApplicationPK_CrewIdAndCrewApplicationPK_UserId(anyLong(), anyLong());
    }

    @DisplayName("크루id를 받아, 해당 크루의 모든 크루지원 목록을 조회한다.")
    @Test
    void findAllByCrewId() {
        // given & when
        crewApplicationReader.findAllByCrewId(anyLong());

        // then
        then(crewApplicationRepository).should(only())
                .findByCrewApplicationPK_CrewId(anyLong());
    }

    @DisplayName("유저id를 받아, 해당 유저의 모든 크루지원 횟수를 조회한다.")
    @Test
    void countByUserId() {
        // given & when
        crewApplicationReader.countByUserId(anyLong());

        // then
        then(crewApplicationRepository).should(only())
                .countByCrewApplicationPK_UserId(anyLong());
    }

    @DisplayName("크루id를 받아, 해당 크루에 지원한 모든 유저 목록을 조회한다.")
    @Test
    void getApplicantsByCrewId() {
        // given
        Long crewId = 6920L;
        List<CrewApplication> crewApplications = List.of(
                createCrewApplication(crewId, 11L).build().get(),
                createCrewApplication(crewId, 21L).build().get(),
                createCrewApplication(crewId, 31L).build().get(),
                createCrewApplication(crewId, 41L).build().get()
        );
        given(crewApplicationRepository.findByCrewApplicationPK_CrewId(anyLong()))
                .willReturn(crewApplications);
        given(userRepository.findUserById(anyLong()))
                .willReturn(mock(User.class));

        // when
        crewApplicationReader.getApplicantsByCrewId(crewId);

        // then
        then(crewApplicationRepository).should(only())
                .findByCrewApplicationPK_CrewId(anyLong());
        then(userRepository).should(times(crewApplications.size()))
                .findUserById(anyLong());
    }
}
