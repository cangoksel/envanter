package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Kurulus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kurulus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KurulusRepository extends JpaRepository<Kurulus,Long> {
    
}
