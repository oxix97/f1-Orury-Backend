package org.orury.domain.crew.domain;

import org.orury.domain.crew.domain.dto.CrewGender;
import org.orury.domain.crew.domain.entity.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrewReader {
    Optional<Crew> findById(Long crewId);

    Page<Crew> getCrewsByRecommendedSort(Pageable pageable, CrewGender userGender, int userAge);

    Page<Crew> getCrewsByPopularSort(Pageable pageable);

    Page<Crew> getCrewsByActiveSort(Pageable pageable);

    Page<Crew> getCrewsByLatestSort(Pageable pageable);

    List<Crew> getJoinedCrewsByUserId(Long userId);

    List<Crew> getAppliedCrewsByUserId(Long userId);
}
