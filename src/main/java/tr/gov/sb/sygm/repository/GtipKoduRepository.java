package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.GtipKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GtipKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GtipKoduRepository extends JpaRepository<GtipKodu,Long> {
    
}
