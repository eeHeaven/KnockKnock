package jpaproject.knockknock.requestForm;

import jpaproject.knockknock.domain.report.ReportReason;
import lombok.Data;

@Data
public class ReportForm {

    Long objectMemberId;
    String detailReason;
    ReportReason reason;
}
