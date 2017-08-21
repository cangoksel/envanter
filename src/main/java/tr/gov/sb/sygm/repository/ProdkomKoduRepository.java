package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.ProdkomKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProdkomKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdkomKoduRepository extends JpaRepository<ProdkomKodu,Long> {
    
}
