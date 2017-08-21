package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Il;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Il entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IlRepository extends JpaRepository<Il,Long> {
    
}
