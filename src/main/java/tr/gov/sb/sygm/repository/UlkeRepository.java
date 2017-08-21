package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Ulke;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ulke entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UlkeRepository extends JpaRepository<Ulke,Long> {
    
}
