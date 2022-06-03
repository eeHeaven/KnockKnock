package jpaproject.knockknock.api.request;

import jpaproject.knockknock.domain.report.ReportReason;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportRequest {
    @NotBlank
    private long objectMemberId;
    private String detailReason;
    @NotNull
    private ReportReason reason;
}
