package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Belge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Belge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelgeRepository extends JpaRepository<Belge,Long> {
    
}
