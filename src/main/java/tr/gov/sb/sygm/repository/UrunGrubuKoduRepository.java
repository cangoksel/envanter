package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.UrunGrubuKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UrunGrubuKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UrunGrubuKoduRepository extends JpaRepository<UrunGrubuKodu,Long> {
    
}
