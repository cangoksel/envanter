package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.UrunAltKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UrunAltKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UrunAltKoduRepository extends JpaRepository<UrunAltKodu,Long> {
    
}
