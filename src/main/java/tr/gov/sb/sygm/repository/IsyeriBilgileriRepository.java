package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.IsyeriBilgileri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IsyeriBilgileri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsyeriBilgileriRepository extends JpaRepository<IsyeriBilgileri,Long> {
    
}
