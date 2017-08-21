package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.BelgeTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BelgeTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelgeTipiRepository extends JpaRepository<BelgeTipi,Long> {
    
}
