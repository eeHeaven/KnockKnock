package jpaproject.knockknock.repository;

import jpaproject.knockknock.domain.report.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {

}
