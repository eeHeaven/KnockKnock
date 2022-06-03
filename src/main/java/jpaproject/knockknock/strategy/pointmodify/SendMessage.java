package jpaproject.knockknock.strategy.pointmodify;

import jpaproject.knockknock.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class SendMessage implements PointModify{

    @Override
    public Member modifyPointof(Member m) {
        m.setPoint(m.getSharePoint()-30);
        return m;
    }

    @Override
    public Situation getSituation() {
        return Situation.sendMessage;
    }
}
