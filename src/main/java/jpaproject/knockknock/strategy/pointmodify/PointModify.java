package jpaproject.knockknock.strategy.pointmodify;

import jpaproject.knockknock.domain.Member;

public interface PointModify {
    Member modifyPointof(Member m);

    Situation getSituation();

public enum Situation{
    viewPost,
    deletePost,
    writePost,
    sendMessage,
    receiveMessage
}
}
