package org.orury.domain.user.infrastucture;

import lombok.RequiredArgsConstructor;
import org.orury.domain.user.domain.ReportReader;
import org.orury.domain.user.domain.dto.ReportDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {
    private final ReportRepository reportRepository;

    @Override
    public boolean checkDuplicateReport(ReportDto reportDto) {
        return reportRepository.existsByTargetIdAndTypeAndReporterUser_Id(reportDto.targetId(), reportDto.reportType().getCode(), reportDto.reporterDto().id());
    }
}
