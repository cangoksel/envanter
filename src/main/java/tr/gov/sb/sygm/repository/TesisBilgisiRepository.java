package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.TesisBilgisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TesisBilgisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TesisBilgisiRepository extends JpaRepository<TesisBilgisi,Long> {
    
}
