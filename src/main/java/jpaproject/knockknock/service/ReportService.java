package jpaproject.knockknock.service;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.report.Report;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.ReportRepository;
import jpaproject.knockknock.requestForm.ReportForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    // 신고건 저장
    @Transactional
    public Report save(ReportForm reportForm){
        Report report = new Report();
        Member objectMember = memberRepository.findOne(reportForm.getObjectMemberId());
        report.setObjectMember(objectMember);
        report.setReason(reportForm.getReason());
        report.setDetailReason(report.getDetailReason());
        reportRepository.save(report);
        return report;
    }




}
