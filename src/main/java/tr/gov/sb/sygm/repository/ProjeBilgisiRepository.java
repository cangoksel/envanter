package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.ProjeBilgisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProjeBilgisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjeBilgisiRepository extends JpaRepository<ProjeBilgisi,Long> {
    
}
