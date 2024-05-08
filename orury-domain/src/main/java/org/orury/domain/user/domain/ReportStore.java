package org.orury.domain.user.domain;

import org.orury.domain.user.domain.entity.Report;

public interface ReportStore {
    void save(Report entity);
}
