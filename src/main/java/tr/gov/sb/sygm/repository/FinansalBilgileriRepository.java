package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.FinansalBilgileri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinansalBilgileri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinansalBilgileriRepository extends JpaRepository<FinansalBilgileri,Long> {
    
}
