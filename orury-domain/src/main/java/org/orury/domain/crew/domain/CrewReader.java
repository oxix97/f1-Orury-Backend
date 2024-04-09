package org.orury.domain.crew.domain;

import org.orury.domain.crew.domain.entity.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrewReader {
    Optional<Crew> findById(Long crewId);

    Page<Crew> getCrewsByRank(Pageable pageable);

    Page<Crew> getCrewsByRecommend(Pageable pageable);

    List<Crew> getJoinedCrewsByUserId(Long userId);

    List<Crew> getAppliedCrewsByUserId(Long userId);
}
