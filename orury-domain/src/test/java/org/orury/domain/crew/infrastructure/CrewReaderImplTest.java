package org.orury.domain.crew.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.orury.domain.config.InfrastructureTest;
import org.orury.domain.crew.domain.dto.CrewGender;
import org.orury.domain.crew.domain.entity.CrewApplication;
import org.orury.domain.crew.domain.entity.CrewMember;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.orury.domain.CrewDomainFixture.TestCrewApplication.createCrewApplication;
import static org.orury.domain.CrewDomainFixture.TestCrewMember.createCrewMember;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Reader] 크루 ReaderImpl 테스트")
@ActiveProfiles("test")
class CrewReaderImplTest extends InfrastructureTest {

    @DisplayName("크루id를 받아, 크루를 조회한다")
    @Test
    void findById() {
        // given & when
        crewReader.findById(anyLong());

        // then
        then(crewRepository).should(only())
                .findById(anyLong());
    }

    @DisplayName("Pageable, 성별, 나이를 받아, 해당 값이 기준에 충족하는 크루 목록을 조회한다")
    @Test
    void getCrewsByRecommended() {
        // given & when
        crewReader.getCrewsByRecommendedSort(mock(Pageable.class), mock(CrewGender.class), 27);

        // then
        then(crewRepository).should(only())
                .findAllByGenderIsInAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqualOrderByCreatedAtAsc(any(), anyInt(), anyInt(), any());
    }

    @DisplayName("Pageable을 받아, 멤버수 내림차순의 크루 목록을 조회한다")
    @Test
    void getCrewsByRank() {
        // given & when
        crewReader.getCrewsByPopularSort(mock(Pageable.class));

        // then
        then(crewRepository).should(only())
                .findByOrderByMemberCountDesc(any(Pageable.class));
    }

    @DisplayName("Pageable을 받아, 크루일정 생성일자 최신순의 크루 목록을 조회한다")
    @Test
    void getCrewsByActive() {
        // given & when
        crewReader.getCrewsByActiveSort(mock(Pageable.class));

        // then
        then(crewRepository).should(only())
                .findDistinctCrewsOrderByMeetingCreatedAtDesc(any(Pageable.class));
    }

    @DisplayName("Pageable을 받아, 생성일자 최신순의 크루 목록을 조회한다")
    @Test
    void getCrewsByRecommend() {
        // given & when
        crewReader.getCrewsByLatestSort(mock(Pageable.class));

        // then
        then(crewRepository).should(only())
                .findByOrderByCreatedAtDesc(any(Pageable.class));
    }

    @DisplayName("userId를 받아, 해당 유저가 가입한 크루 목록을 반환한다")
    @Test
    void getCrewsByUserId() {
        // given & when
        List<CrewMember> crewMembers = List.of(
                createCrewMember().build().get(),
                createCrewMember().build().get(),
                createCrewMember().build().get()
        );
        given(crewMemberRepository.findByCrewMemberPK_UserId(anyLong()))
                .willReturn(crewMembers);

        crewReader.getJoinedCrewsByUserId(4127L);

        // then
        then(crewMemberRepository).should(only())
                .findByCrewMemberPK_UserId(anyLong());
        then(crewRepository).should(times(crewMembers.size()))
                .getCrewById(anyLong());
    }

    @DisplayName("userId를 받아, 해당 유저가 가입신청한 크루 목록을 반환한다")
    @Test
    void getAppliedCrewsByUserId() {
        // given
        List<CrewApplication> crewApplications = List.of(
                createCrewApplication().build().get(),
                createCrewApplication().build().get(),
                createCrewApplication().build().get()
        );
        given(crewApplicationRepository.findByCrewApplicationPK_UserId(anyLong()))
                .willReturn(crewApplications);

        // when
        crewReader.getAppliedCrewsByUserId(4127L);

        // then
        then(crewApplicationRepository).should(only())
                .findByCrewApplicationPK_UserId(anyLong());
        then(crewRepository).should(times(crewApplications.size()))
                .getCrewById(anyLong());
    }
}
