package org.orury.domain.user.infrastucture;

import lombok.RequiredArgsConstructor;
import org.orury.domain.user.domain.ReportStore;
import org.orury.domain.user.domain.entity.Report;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportStoreImpl implements ReportStore {
    private final ReportRepository reportRepository;

    @Override
    public void save(Report entity) {
        reportRepository.save(entity);
    }
}
