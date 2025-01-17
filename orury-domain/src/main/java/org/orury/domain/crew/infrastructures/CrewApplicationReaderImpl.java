package org.orury.domain.crew.infrastructures;

import lombok.RequiredArgsConstructor;
import org.orury.domain.crew.domain.CrewApplicationReader;
import org.orury.domain.crew.domain.entity.CrewApplication;
import org.orury.domain.user.infrastucture.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewApplicationReaderImpl implements CrewApplicationReader {
    private final CrewApplicationRepository crewApplicationRepository;
    private final UserRepository userRepository;

    @Override
    public boolean existsByCrewIdAndUserId(Long crewId, Long userId) {
        return crewApplicationRepository.existsByCrewApplicationPK_CrewIdAndCrewApplicationPK_UserId(crewId, userId);
    }

    @Override
    public List<CrewApplication> findAllByCrewId(Long crewId) {
        return crewApplicationRepository.findByCrewApplicationPK_CrewId(crewId);
    }

    @Override
    public int countByUserId(Long userId) {
        return crewApplicationRepository.countByCrewApplicationPK_UserId(userId);
    }

    @Override
    public List<CrewApplication> getApplicantsByCrewId(Long crewId) {
        return crewApplicationRepository.findByCrewApplicationPK_CrewId(crewId);
    }

    @Override
    public CrewApplication getCrewApplicationByCrewIdAndUserId(Long crewId, Long userId) {
        return crewApplicationRepository.getCrewApplicationByCrewApplicationPK_CrewIdAndCrewApplicationPK_UserId(crewId, userId);
    }
}
