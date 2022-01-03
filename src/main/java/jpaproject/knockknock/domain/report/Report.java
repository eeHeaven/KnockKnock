package jpaproject.knockknock.domain.report;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Report {

    @Id @GeneratedValue
    @Column(name="report_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member objectMember;

    @Enumerated(EnumType.STRING)
    private ReportReason reason;
    private String detailReason;

    //비즈니스 로직
    //1. 연관관계 관련 로직
    public void setObjectMember(Member member){
        this.objectMember = member;
       int count =  member.getReportCount();
       member.setReportCount(++count);
       if(count >=5) member.setBlocked(true);
    }
    // 생성메서드
    public Report CreateReport(Member member, ReportReason reason, String detailReason){
        Report report = new Report();
        report.setObjectMember(member);
        report.setDetailReason(detailReason);
        report.setReason(reason);
        return report;
    }

}
