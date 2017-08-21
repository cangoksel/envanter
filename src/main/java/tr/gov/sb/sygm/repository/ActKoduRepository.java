package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.ActKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActKoduRepository extends JpaRepository<ActKodu,Long> {
    
}
