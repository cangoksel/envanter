package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.UrunKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UrunKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UrunKoduRepository extends JpaRepository<UrunKodu,Long> {
    
}
