package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.report.ReportReason;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.requestForm.ReportForm;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.ReportService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ReportServiceTest {

    @Autowired
    ReportService reportService;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 신고접수(){

        //given
        ReportForm reportForm = new ReportForm();
        Member object = memberRepository.findByNickName("테스트멤버2");
        reportForm.setReason(ReportReason.WRONGINFO);
        reportForm.setDetailReason("테스트");
        reportForm.setObjectMemberId(object.getId());

        //when
        reportService.save(reportForm);

        //then
        Assertions.assertThat(object.getReportCount()).isEqualTo(1);
    }

    @Test
    public void 멤버정지(){
        //given
        ReportForm reportForm = new ReportForm();
        Member object = memberRepository.findByNickName("테스트멤버2");
        reportForm.setReason(ReportReason.WRONGINFO);
        reportForm.setDetailReason("테스트");
        reportForm.setObjectMemberId(object.getId());

        ReportForm reportForm2 = new ReportForm();
        reportForm2.setReason(ReportReason.WRONGINFO);
        reportForm2.setDetailReason("테스트");
        reportForm2.setObjectMemberId(object.getId());

        ReportForm reportForm3 = new ReportForm();
        reportForm3.setReason(ReportReason.WRONGINFO);
        reportForm3.setDetailReason("테스트");
        reportForm3.setObjectMemberId(object.getId());

        ReportForm reportForm4 = new ReportForm();
        reportForm4.setReason(ReportReason.WRONGINFO);
        reportForm4.setDetailReason("테스트");
        reportForm4.setObjectMemberId(object.getId());

        ReportForm reportForm5 = new ReportForm();
        reportForm5.setReason(ReportReason.WRONGINFO);
        reportForm5.setDetailReason("테스트");
        reportForm5.setObjectMemberId(object.getId());

        //when
        reportService.save(reportForm);
        reportService.save(reportForm2);
        reportService.save(reportForm3);
        reportService.save(reportForm4);
        reportService.save(reportForm5);


        //then
        Assertions.assertThat(object.getReportCount()).isEqualTo(5);
        Assertions.assertThat(object.isBlocked()).isEqualTo(true);
    }
}
