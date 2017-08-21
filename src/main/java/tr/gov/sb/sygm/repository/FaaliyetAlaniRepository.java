package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.FaaliyetAlani;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FaaliyetAlani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaaliyetAlaniRepository extends JpaRepository<FaaliyetAlani,Long> {
    
}
