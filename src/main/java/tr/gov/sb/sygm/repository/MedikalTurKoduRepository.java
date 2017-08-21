package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.MedikalTurKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MedikalTurKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedikalTurKoduRepository extends JpaRepository<MedikalTurKodu,Long> {
    
}
