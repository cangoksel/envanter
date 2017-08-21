package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.IsbirligiFirma;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IsbirligiFirma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsbirligiFirmaRepository extends JpaRepository<IsbirligiFirma,Long> {
    
}
