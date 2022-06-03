package jpaproject.knockknock.strategy.factory;

import jpaproject.knockknock.strategy.pointmodify.PointModify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PointModifyFactory {
    private Map<PointModify.Situation, PointModify> situationofPointModify;

    @Autowired
    public PointModifyFactory(Set<PointModify> pointModifySet) {
        createSituationMap(pointModifySet);
    }

    public PointModify findPointModify(PointModify.Situation situation) {
        return situationofPointModify.get(situation);
    }

    private void createSituationMap(Set<PointModify> pointModifySet) {
        situationofPointModify = new HashMap<>();
        pointModifySet.forEach(
                pointModify -> situationofPointModify.put(pointModify.getSituation(), pointModify)
        );
    }
}
