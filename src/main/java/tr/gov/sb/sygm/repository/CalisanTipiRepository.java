package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.CalisanTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CalisanTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalisanTipiRepository extends JpaRepository<CalisanTipi,Long> {
    
}
