package org.orury.domain.crew.domain;

import org.orury.domain.crew.domain.entity.CrewMember;

public interface CrewMemberStore {
    void addCrewMember(Long crewId, Long userId);

    void subtractCrewMember(Long crewId, Long userId);

    void updateMeetingViewed(CrewMember crewMember);
}
