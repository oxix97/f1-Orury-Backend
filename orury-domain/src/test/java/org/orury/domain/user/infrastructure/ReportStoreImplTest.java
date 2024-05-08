package org.orury.domain.user.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.orury.domain.config.InfrastructureTest;
import org.orury.domain.user.domain.entity.Report;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@DisplayName("[ReportStoreImpl] Report StoreImpl 테스트")
class ReportStoreImplTest extends InfrastructureTest {

    @Test
    @DisplayName("save(Long id) Test: Report Entity가 들어오면 해당하는 엔티티를 저장한다. [성공]")
    void should_saveReportEntity() {
        //given
        Report report = mock(Report.class);

        //when
        reportStore.save(report);

        //then
        then(reportRepository).should(times(1)).save(any());
    }
}