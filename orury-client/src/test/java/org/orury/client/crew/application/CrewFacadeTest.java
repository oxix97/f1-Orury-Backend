package org.orury.client.crew.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.anyBoolean;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.only;
import static org.orury.client.ClientFixtureFactory.TestCrewRequest.createCrewRequest;
import static org.orury.domain.CrewDomainFixture.TestCrewDto.createCrewDto;
import static org.orury.domain.UserDomainFixture.TestUserDto.createUserDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.orury.client.config.FacadeTest;
import org.orury.client.crew.interfaces.request.CrewRequest;
import org.orury.domain.crew.domain.dto.CrewDto;
import org.orury.domain.user.domain.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("[Facade] 크루 Facade 테스트")
class CrewFacadeTest extends FacadeTest {

    @DisplayName("크루생성Request, 크루image 유저id를 받으면, 크루를 생성한다.")
    @Test
    void should_CreateCrew() {
        // given
        CrewRequest request = createCrewRequest().build().get();
        MultipartFile image = mock(MultipartFile.class);
        Long userId = 1L;
        Long crewId = 1L;

        UserDto userDto = createUserDto(userId).build().get();
        CrewDto crewDto = createCrewDto(crewId).build().get();

        given(userService.getUserDtoById(anyLong()))
                .willReturn(userDto);
        given(crewService.createCrew(any(), any()))
                .willReturn(crewDto);


        // when
        crewFacade.createCrew(request, image, userId);

        // then
        then(userService).should(only())
                .getUserDtoById(anyLong());
        then(crewService).should(only())
                .createCrew(any(CrewDto.class), any(MultipartFile.class));
    }

    @DisplayName("페이지, 유저id를 받으면, 추천순으로 크루목록을 반환한다.")
    @Test
    void should_GetCrewsByRecommendedSort() {
        // given
        int page = 1;
        Long userId = 23L;
        Page<CrewDto> crewDtos = new PageImpl<>(List.of(createCrewDto(3L).build().get(), createCrewDto(4L).build().get()));
        given(userService.getUserDtoById(anyLong()))
                .willReturn(createUserDto(userId).build().get());
        given(crewService.getCrewDtosByRecommendedSort(any(PageRequest.class), any(UserDto.class))
        ).willReturn(crewDtos);

        // when
        crewFacade.getCrewsByRecommendedSort(page, userId);

        // then
        then(userService).should(only())
                .getUserDtoById(anyLong());
        then(crewService).should(times(1))
                .getCrewDtosByRecommendedSort(any(PageRequest.class), any(UserDto.class));
        then(crewService).should(times(crewDtos.getSize()))
                .getUserImagesByCrew(any(), anyInt());
    }

    @DisplayName("페이지를 받으면, 인기순으로 크루목록을 반환한다.")
    @Test
    void should_GetCrewsByPopularSort() {
        // given
        int page = 1;
        Page<CrewDto> crewDtos = new PageImpl<>(List.of(createCrewDto(3L).build().get(), createCrewDto(4L).build().get()));
        given(crewService.getCrewDtosByPopularSort(any(PageRequest.class)))
                .willReturn(crewDtos);

        // when
        crewFacade.getCrewsByPopularSort(page);

        // then
        then(crewService).should(times(1))
                .getCrewDtosByPopularSort(any(PageRequest.class));
        then(crewService).should(times(crewDtos.getSize()))
                .getUserImagesByCrew(any(), anyInt());
    }

    @DisplayName("페이지를 받으면, 활동순으로 크루목록을 반환한다.")
    @Test
    void should_GetCrewsByActiveSort() {
        // given
        int page = 1;
        Page<CrewDto> crewDtos = new PageImpl<>(List.of(createCrewDto(3L).build().get(), createCrewDto(4L).build().get()));
        given(crewService.getCrewDtosByActiveSort(any(PageRequest.class)))
                .willReturn(crewDtos);

        // when
        crewFacade.getCrewsByActiveSort(page);

        // then
        then(crewService).should(times(1))
                .getCrewDtosByActiveSort(any(PageRequest.class));
        then(crewService).should(times(crewDtos.getSize()))
                .getUserImagesByCrew(any(), anyInt());
    }

    @DisplayName("페이지를 받으면, 최신순으로 크루목록을 반환한다.")
    @Test
    void should_GetCrewsByLatestSort() {
        // given
        int page = 1;
        Page<CrewDto> crewDtos = new PageImpl<>(List.of(createCrewDto(3L).build().get(), createCrewDto(4L).build().get()));
        given(crewService.getCrewDtosByLatestSort(any(PageRequest.class)))
                .willReturn(crewDtos);

        // when
        crewFacade.getCrewsByLatestSort(page);

        // then
        then(crewService).should(times(1))
                .getCrewDtosByLatestSort(any(PageRequest.class));
        then(crewService).should(times(crewDtos.getSize()))
                .getUserImagesByCrew(any(), anyInt());
    }

    @DisplayName("유저id를 받으면, 유저id에 따른 가입된 크루 목록을 반환한다.")
    @Test
    void should_GetJoinedCrews() {
        // given
        Long userId = 23L;
        List<CrewDto> crewDtos = List.of(createCrewDto(3L).build().get(), createCrewDto(4L).build().get());
        given(crewService.getJoinedCrewDtos(anyLong()))
                .willReturn(crewDtos);
        given(crewService.getUserImagesByCrew(any(CrewDto.class), anyInt()))
                .willReturn(mock(List.class));
        given((crewService.getJoinedAt(anyLong(), anyLong())))
                .willReturn(LocalDateTime.now().minusYears(1));

        // when
        crewFacade.getJoinedCrews(userId);

        // then
        then(crewService).should(times(1))
                .getJoinedCrewDtos(anyLong());
        then(crewService).should(times(crewDtos.size()))
                .getUserImagesByCrew(any(), anyInt());
        then(crewService).should(times(crewDtos.size()))
                .getJoinedAt(anyLong(), anyLong());
    }

    @DisplayName("유저id를 받으면, 유저id에 따른 가입신청한 크루 목록을 반환한다.")
    @Test
    void should_getAppliedCrews() {
        // given
        Long userId = 262L;
        List<CrewDto> crewDtos = List.of(createCrewDto(694L).build().get(), createCrewDto(256L).build().get());
        given(crewService.getAppliedCrewDtos(anyLong()))
                .willReturn(crewDtos);
        given(crewService.getUserImagesByCrew(any(CrewDto.class), anyInt()))
                .willReturn(mock(List.class));
        given((crewService.getAppliedAt(anyLong(), anyLong())))
                .willReturn(LocalDateTime.now().minusYears(1));

        // when
        crewFacade.getAppliedCrews(userId);

        // then
        then(crewService).should(times(1))
                .getAppliedCrewDtos(anyLong());
        then(crewService).should(times(crewDtos.size()))
                .getUserImagesByCrew(any(), anyInt());
        then(crewService).should(times(crewDtos.size()))
                .getAppliedAt(anyLong(), anyLong());
    }

    @DisplayName("유저id, 크루id를 받으면, 크루정보를 반환한다.")
    @Test
    void should_GetCrewByCrewId() {
        // given
        Long userId = 23L;
        Long crewId = 3L;
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(createCrewDto(crewId).build().get());
        given(crewService.existCrewMember(anyLong(), anyLong()))
                .willReturn(anyBoolean());

        // when
        crewFacade.getCrewByCrewId(userId, crewId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .existCrewMember(anyLong(), anyLong());
    }

    @DisplayName("크루id, 크루정보Request, 유저id를 받으면, 크루정보를 업데이트한다.")
    @Test
    void should_UpdateCrewInfo() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        CrewRequest request = createCrewRequest().build().get();
        CrewDto oldCrewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(oldCrewDto);

        // when
        crewFacade.updateCrewInfo(crewId, request, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .updateCrewInfo(any(), any(), anyLong());
    }

    @DisplayName("크루id, 크루image, 유저id를 받으면, 크루이미지를 업데이트한다.")
    @Test
    void should_UpdateCrewImage() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        MultipartFile image = mock(MultipartFile.class);
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.updateCrewImage(crewId, image, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .updateCrewImage(any(), any(), anyLong());
    }

    @DisplayName("크루id, 유저id를 받으면, 크루를 삭제한다.")
    @Test
    void should_DeleteCrew() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.deleteCrew(crewId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .deleteCrew(any(), anyLong());
    }

    @DisplayName("크루id, 유저id, 답변을 받으면, 크루에 가입신청한다.")
    @Test
    void should_ApplyCrew() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        String answer = "답변";
        CrewDto crewDto = createCrewDto(crewId).build().get();
        UserDto userDto = createUserDto(userId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);
        given(userService.getUserDtoById(anyLong()))
                .willReturn(userDto);

        // when
        crewFacade.applyCrew(crewId, userId, answer);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(userService).should(only())
                .getUserDtoById(anyLong());
        then(crewService).should(times(1))
                .applyCrew(any(), any(), any());
    }

    @DisplayName("크루id, 유저id를 받으면, 크루가입신청을 취소한다.")
    @Test
    void should_WithdrawApplication() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.withdrawApplication(crewId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .withdrawApplication(any(), anyLong());
    }

    @DisplayName("크루id, 지원자id, 유저id를 받아, 크루가입신청을 승인한다.")
    @Test
    void should_ApproveApplication() {
        // given
        Long crewId = 3L;
        Long applicantId = 23L;
        Long userId = 24L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.approveApplication(crewId, applicantId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .approveApplication(any(), anyLong(), anyLong());
    }

    @DisplayName("크루id, 지원자id, 유저id를 받아, 크루가입신청을 거절한다.")
    @Test
    void should_DisapproveApplication() {
        // given
        Long crewId = 3L;
        Long applicantId = 23L;
        Long userId = 24L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.disapproveApplication(crewId, applicantId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .disapproveApplication(any(), anyLong(), anyLong());
    }

    @DisplayName("크루id, 유저id를 받아, 크루를 탈퇴한다.")
    @Test
    void should_LeaveCrew() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.leaveCrew(crewId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .leaveCrew(any(), anyLong());
    }

    @DisplayName("크루id, 멤버id, 유저id를 받아, 멤버를 강퇴한다.")
    @Test
    void should_ExpelMember() {
        // given
        Long crewId = 3L;
        Long memberId = 23L;
        Long userId = 24L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);

        // when
        crewFacade.expelMember(crewId, memberId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .expelMember(any(), anyLong(), anyLong());
    }

    @DisplayName("크루id, 유저id를 받아, 크루원 목록을 반환한다.")
    @Test
    void should_GetCrewMembers() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        CrewDto crewDto = createCrewDto(crewId).build().get();
        given(crewService.getCrewDtoById(anyLong()))
                .willReturn(crewDto);
        given(crewService.getMembersByCrew(anyLong(), anyLong()))
                .willReturn(mock(List.class));

        // when
        crewFacade.getCrewMembers(crewId, userId);

        // then
        then(crewService).should(times(1))
                .getCrewDtoById(anyLong());
        then(crewService).should(times(1))
                .getMembersByCrew(anyLong(), anyLong());
    }

    @DisplayName("크루id, 유저id를 받아, 크루신청자 목록을 반환한다.")
    @Test
    void should_GetCrewApplicants() {
        // given
        Long crewId = 3L;
        Long userId = 23L;
        given(crewService.getApplicantsByCrew(anyLong(), anyLong()))
                .willReturn(mock(List.class));

        // when
        crewFacade.getCrewApplicants(crewId, userId);

        // then
        then(crewService).should(only())
                .getApplicantsByCrew(anyLong(), anyLong());
    }
}