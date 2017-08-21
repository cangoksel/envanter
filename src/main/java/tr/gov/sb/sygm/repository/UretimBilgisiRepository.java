package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.UretimBilgisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UretimBilgisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UretimBilgisiRepository extends JpaRepository<UretimBilgisi,Long> {
    
}
