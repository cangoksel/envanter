package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.TibbiCihazTehlikeSinifi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TibbiCihazTehlikeSinifi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TibbiCihazTehlikeSinifiRepository extends JpaRepository<TibbiCihazTehlikeSinifi,Long> {
    
}
