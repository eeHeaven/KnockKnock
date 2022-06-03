package jpaproject.knockknock.strategy.pointmodify;

import jpaproject.knockknock.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage implements PointModify{

    @Override
    public Member modifyPointof(Member m) {
        m.setPoint(m.getSharePoint()+20);
        return m;
    }

    @Override
    public Situation getSituation() {
        return Situation.receiveMessage;
    }
}
