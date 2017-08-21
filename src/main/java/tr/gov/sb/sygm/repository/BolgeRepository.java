package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Bolge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bolge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BolgeRepository extends JpaRepository<Bolge,Long> {
    
}
