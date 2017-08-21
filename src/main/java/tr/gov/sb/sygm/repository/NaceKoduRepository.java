package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.NaceKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NaceKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaceKoduRepository extends JpaRepository<NaceKodu,Long> {
    
}
