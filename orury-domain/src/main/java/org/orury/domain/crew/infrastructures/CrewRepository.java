package org.orury.domain.crew.infrastructures;

import org.orury.domain.crew.domain.dto.CrewGender;
import org.orury.domain.crew.domain.entity.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    Crew getCrewById(Long crewId);

    Page<Crew> findAllByGenderIsInAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqualOrderByCreatedAtAsc(List<CrewGender> userGenders, int userAge1, int userAge2, Pageable pageable);

    Page<Crew> findByOrderByMemberCountDesc(Pageable pageable);

    @Query("SELECT c FROM crew c " +
            "WHERE c.id IN " +
            "(SELECT m.crew.id FROM crew_meeting m " +
            "WHERE m.createdAt = (SELECT MAX(m2.createdAt) FROM crew_meeting m2 WHERE m2.crew = m.crew)) " +
            "ORDER BY (SELECT MAX(m.createdAt) FROM crew_meeting m WHERE m.crew = c) DESC")
    Page<Crew> findDistinctCrewsOrderByMeetingCreatedAtDesc(Pageable pageable);

    Page<Crew> findByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying
    @Query("UPDATE crew SET memberCount = memberCount + 1 WHERE id = :crewId")
    void increaseMemberCount(Long crewId);

    @Modifying
    @Query("UPDATE crew SET memberCount = memberCount - 1 WHERE id = :crewId")
    void decreaseMemberCount(Long crewId);
}






