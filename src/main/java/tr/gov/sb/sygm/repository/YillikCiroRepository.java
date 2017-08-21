package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.YillikCiro;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the YillikCiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YillikCiroRepository extends JpaRepository<YillikCiro,Long> {
    
}
