package jpaproject.knockknock.strategy.pointmodify;

import jpaproject.knockknock.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ViewPost implements PointModify{

    @Override
    public Member modifyPointof(Member m){
        m.setPoint(m.getSharePoint()-5);
        return m;
    }

    @Override
    public Situation getSituation() {
        return Situation.viewPost;
    }
}
