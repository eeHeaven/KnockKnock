package jpaproject.knockknock.repository;

import jpaproject.knockknock.domain.report.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final EntityManager em;

    public void save(Report report){em.persist(report);}

    public void remove(Report report){em.remove(report);}

}
