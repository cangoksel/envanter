package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.FaaliyetKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FaaliyetKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaaliyetKoduRepository extends JpaRepository<FaaliyetKodu,Long> {
    
}
