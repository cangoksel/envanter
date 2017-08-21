package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Firma;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Firma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FirmaRepository extends JpaRepository<Firma,Long> {
    
}
