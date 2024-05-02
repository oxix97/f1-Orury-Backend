package org.orury.domain.user.domain;

import org.orury.domain.user.domain.dto.ReportDto;

public interface ReportReader {
    boolean checkDuplicateReport(ReportDto reportDto);
}
