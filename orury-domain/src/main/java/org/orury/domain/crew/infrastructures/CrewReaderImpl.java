package org.orury.domain.crew.infrastructures;

import lombok.RequiredArgsConstructor;
import org.orury.domain.crew.domain.CrewReader;
import org.orury.domain.crew.domain.dto.CrewGender;
import org.orury.domain.crew.domain.entity.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CrewReaderImpl implements CrewReader {
    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewApplicationRepository crewApplicationRepository;

    @Override
    public Optional<Crew> findById(Long crewId) {
        return crewRepository.findById(crewId);
    }

    @Override
    public Page<Crew> getCrewsByRecommendedSort(Pageable pageable, CrewGender userGender, int userAge) {
        List<CrewGender> userGenders = List.of(userGender, CrewGender.ANY);
        return crewRepository.findAllByGenderIsInAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqualOrderByCreatedAtAsc(userGenders, userAge, userAge, pageable);
    }

    @Override
    public Page<Crew> getCrewsByPopularSort(Pageable pageable) {
        return crewRepository.findByOrderByMemberCountDesc(pageable);
    }

    @Override
    public Page<Crew> getCrewsByActiveSort(Pageable pageable) {
        return crewRepository.findDistinctCrewsOrderByMeetingCreatedAtDesc(pageable);
    }

    @Override
    public Page<Crew> getCrewsByLatestSort(Pageable pageable) {
        return crewRepository.findByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public List<Crew> getJoinedCrewsByUserId(Long userId) {
        return crewMemberRepository.findByCrewMemberPK_UserId(userId).stream()
                .map(crewMember -> crewMember.getCrewMemberPK().getCrewId())
                .map(crewRepository::getCrewById)
                .toList();
    }

    @Override
    public List<Crew> getAppliedCrewsByUserId(Long userId) {
        return crewApplicationRepository.findByCrewApplicationPK_UserId(userId).stream()
                .map(crewApplication -> crewApplication.getCrewApplicationPK().getCrewId())
                .map(crewRepository::getCrewById)
                .toList();
    }
}
