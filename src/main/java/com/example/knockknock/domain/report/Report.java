package com.example.knockknock.domain.report;

import com.example.knockknock.domain.Member;
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


}
