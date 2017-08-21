package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Kisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KisiRepository extends JpaRepository<Kisi,Long> {
    
}
