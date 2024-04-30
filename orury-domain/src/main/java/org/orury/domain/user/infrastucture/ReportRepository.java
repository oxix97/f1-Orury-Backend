package org.orury.domain.user.infrastucture;

import org.orury.domain.user.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
