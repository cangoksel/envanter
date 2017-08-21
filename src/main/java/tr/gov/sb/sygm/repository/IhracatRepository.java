package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Ihracat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ihracat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IhracatRepository extends JpaRepository<Ihracat,Long> {
    
}
