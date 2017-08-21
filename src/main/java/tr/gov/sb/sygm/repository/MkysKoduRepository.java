package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.MkysKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MkysKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MkysKoduRepository extends JpaRepository<MkysKodu,Long> {
    
}
